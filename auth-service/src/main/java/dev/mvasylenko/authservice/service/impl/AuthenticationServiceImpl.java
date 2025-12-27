package dev.mvasylenko.authservice.service.impl;

import dev.mvasylenko.authservice.entity.AuthUser;
import dev.mvasylenko.authservice.mapper.AuthUserMapper;
import dev.mvasylenko.authservice.repository.AuthUserRepository;
import dev.mvasylenko.authservice.security.jwt.JwtService;
import dev.mvasylenko.authservice.service.AuthenticationService;
import dev.mvasylenko.core.dto.*;
import dev.mvasylenko.core.enums.Role;
import dev.mvasylenko.core.events.DriverRegisteredEvent;
import dev.mvasylenko.core.events.PassengerRegisteredEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final AuthUserRepository authUserRepository;
    private final JwtService jwtService;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String registrationEventsTopicName;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, AuthUserRepository authUserRepository,
                                     JwtService jwtService, KafkaTemplate<String, Object> kafkaTemplate,
                                     @Value("${registration.events.topic.name}") String registrationEventsTopicName,
                                     PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.authUserRepository = authUserRepository;
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
    public AuthUserDto register(PassengerRegistrationDto passenger) {
        AuthUser authUser = new AuthUser(
                passenger.getEmail(), passwordEncoder.encode(passenger.getPassword()), Role.PASSENGER);
        authUserRepository.save(authUser);
        authUserRepository.flush();

        PassengerRegisteredEvent event = createPassengerRegisteredEvent(authUser.getId(), passenger);
        kafkaTemplate.send(registrationEventsTopicName, authUser.getId().toString(), event);

        LOG.info("AuthUser with email = {} has been registered successfully", authUser.getEmail());
        return AuthUserMapper.INSTANCE.authUserToAuthUserDto(authUser);
    }

    @Override
    @Transactional
    public AuthUserDto register(DriverRegistrationDto driver) {
        AuthUser authUser = new AuthUser(
                driver.getEmail(), passwordEncoder.encode(driver.getPassword()), Role.DRIVER);
        authUserRepository.save(authUser);
        authUserRepository.flush();

        DriverRegisteredEvent event = createDriverRegisteredEvent(authUser.getId(), driver);
        kafkaTemplate.send(registrationEventsTopicName, authUser.getId().toString(), event);

        LOG.info("AuthUser with email = {} has been registered successfully", authUser.getEmail());
        return AuthUserMapper.INSTANCE.authUserToAuthUserDto(authUser);
    }

    private ResponseEntity<Map<String, String>> generateNewTokens(String email) {
        var user = authUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with current email wasn't found!"));

        Map<String, Object> claims = Map.of(
                ID, user.getId().toString(),
                ROLE, user.getRole().name()
        );

        return ResponseEntity.ok()
                .body(Map.of(ACCESS_TOKEN, jwtService.generateAccessToken(user, claims),
                        REFRESH_TOKEN, jwtService.generateRefreshToken(user, claims)));
    }

    private PassengerRegisteredEvent createPassengerRegisteredEvent(UUID userId, PassengerRegistrationDto passengerDto) {
        PassengerRegisteredEvent event = new PassengerRegisteredEvent();
        event.setUserId(userId);
        event.setName(passengerDto.getName());
        event.setSurname(passengerDto.getSurname());
        event.setEmail(passengerDto.getEmail());
        event.setPhone(passengerDto.getPhone());
        event.setRole(passengerDto.getRole());
        return event;
    }

    private DriverRegisteredEvent createDriverRegisteredEvent(UUID userId, DriverRegistrationDto driverDto) {
        DriverRegisteredEvent event = new DriverRegisteredEvent();
        event.setUserId(userId);
        event.setName(driverDto.getName());
        event.setSurname(driverDto.getSurname());
        event.setEmail(driverDto.getEmail());
        event.setPhone(driverDto.getPhone());
        event.setRole(driverDto.getRole());
        event.setDrivingLicense(driverDto.getDriverLicenceNumber());
        event.setCar(driverDto.getCar());
        return event;
    }
}