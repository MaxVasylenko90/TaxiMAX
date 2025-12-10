package dev.mvasylenko.authservice.service.impl;

import dev.mvasylenko.authservice.entity.AuthUser;
import dev.mvasylenko.authservice.mapper.AuthUserMapper;
import dev.mvasylenko.authservice.repository.AuthUserRepository;
import dev.mvasylenko.authservice.security.jwt.JwtService;
import dev.mvasylenko.authservice.service.AuthenticationService;
import dev.mvasylenko.core.dto.*;
import dev.mvasylenko.core.enums.Role;
import dev.mvasylenko.core.events.UserRegisteredEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

import static dev.mvasylenko.core.constants.CoreConstants.*;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationServiceImpl.class);
    private final AuthenticationManager authenticationManager;
    private final AuthUserRepository userRepository;
    private final JwtService jwtService;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String registrationEventsTopicName;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, AuthUserRepository userRepository,
                                     JwtService jwtService, KafkaTemplate<String, Object> kafkaTemplate,
                                     @Value("${registration.events.topic.name}") String registrationEventsTopicName,
                                     PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.kafkaTemplate = kafkaTemplate;
        this.registrationEventsTopicName = registrationEventsTopicName;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseEntity<Map<String, String>> authenticate(UserLoginDto request) {
        var email = request.getEmail();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, request.getPassword()));
        return generateNewTokens(email);
    }

    @Override
    public ResponseEntity<Map<String, String>> refreshAccessToken(String refreshToken) {
        if (!jwtService.isRefreshTokenValid(refreshToken)) {
            jwtService.deleteRefreshToken(refreshToken);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Collections.singletonMap(
                    MESSAGE, "Invalid refresh token. Please log in again."));
        }
        var email = jwtService.extractEmailFromTokenClaims(refreshToken);
        jwtService.deleteRefreshToken(refreshToken);
        return generateNewTokens(email);
    }

    @Override
    @Transactional
    public AuthUser registerNewOAuth2User(OAuth2User oauthUser) {
        final String email = oauthUser.getAttribute(EMAIL);
        final String name = oauthUser.getAttribute(NAME);

        AuthUser authUser = new AuthUser(email, Role.PASSENGER);
        var registeredUser = userRepository.save(authUser);
        userRepository.flush();

        UserRegisteredEvent event = createUserRegisteredEvent(authUser.getId(), email, name, Role.PASSENGER);
        kafkaTemplate.send(registrationEventsTopicName, registeredUser.getId().toString(), event);

        LOG.info("AuthUser with email = {} has been registered successfully", email);
        return registeredUser;
    }

    @Override
    @Transactional
    public AuthUserDto register(UserRegistrationDto userDto, Role role) {
        AuthUser authUser = new AuthUser(
                userDto.getEmail(), passwordEncoder.encode(userDto.getPassword()), role);
        userRepository.save(authUser);
        userRepository.flush();

        UserRegisteredEvent event = createUserRegisteredEvent(authUser.getId(), userDto, Role.PASSENGER);
        kafkaTemplate.send(registrationEventsTopicName, authUser.getId().toString(), event);

        LOG.info("AuthUser with email = {} has been registered successfully", authUser.getEmail());
        return AuthUserMapper.INSTANCE.authUserToAuthUserDto(authUser);
    }

    private ResponseEntity<Map<String, String>> generateNewTokens(String email) {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with current email wasn't found!"));

        Map<String, Object> claims = Map.of(
                ID, user.getId().toString(),
                ROLE, user.getRole().name()
        );

        return ResponseEntity.ok()
                .body(Map.of(ACCESS_TOKEN, jwtService.generateAccessToken(user, claims),
                        REFRESH_TOKEN, jwtService.generateRefreshToken(user, claims)));
    }

    private UserRegisteredEvent createUserRegisteredEvent(UUID userId, String email, String name, Role role) {
        UserRegisteredEvent event = new UserRegisteredEvent();
        event.setUserId(userId);
        event.setEmail(email);
        event.setName(name);
        event.setRole(role);
        return event;
    }

    private UserRegisteredEvent createUserRegisteredEvent(UUID userId, UserRegistrationDto userDto, Role role) {
        UserRegisteredEvent event = new UserRegisteredEvent();
        event.setUserId(userId);
        event.setName(userDto.getName());
        event.setSurname(userDto.getSurname());
        event.setEmail(userDto.getEmail());
        event.setPhone(userDto.getPhone());
        event.setRole(role);
        event.setDriverInfo(userDto.getDriverInfo());
        return event;
    }
}
