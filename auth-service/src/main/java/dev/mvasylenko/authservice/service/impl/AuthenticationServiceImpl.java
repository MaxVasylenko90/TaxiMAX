package dev.mvasylenko.authservice.service.impl;

import dev.mvasylenko.authservice.entity.AuthUser;
import dev.mvasylenko.authservice.exception.RegistrationFailedException;
import dev.mvasylenko.authservice.mapper.AuthUserMapper;
import dev.mvasylenko.authservice.repository.AuthUserRepository;
import dev.mvasylenko.authservice.security.jwt.JwtService;
import dev.mvasylenko.authservice.service.AuthenticationService;
import dev.mvasylenko.core.dto.*;
import dev.mvasylenko.core.enums.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

import static dev.mvasylenko.core.constants.CoreConstants.*;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final AuthUserRepository userRepository;
    private final JwtService jwtService;
    private final RestTemplate restTemplate;
    private final String passengerServiceUrl;

    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, AuthUserRepository userRepository,
                                     JwtService jwtService, RestTemplate restTemplate,
                                     @Value("${passenger.service.url}") String passengerServiceUrl) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.restTemplate = restTemplate;
        this.passengerServiceUrl = passengerServiceUrl;
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
            return getResponseEntity(HttpStatus.FORBIDDEN, MESSAGE, "Invalid refresh token. Please log in again.");
        }
        var email = jwtService.extractEmailFromTokenClaims(refreshToken);
        jwtService.deleteRefreshToken(refreshToken);
        return generateNewTokens(email);
    }

    @Override
    @Transactional
    public AuthUser registerNewOAuth2User(OAuth2User oauthUser) {
        String email = oauthUser.getAttribute(EMAIL);
        String name = oauthUser.getAttribute(NAME);
        UUID uuid = createPassenger(email, name).getId();

        return createAuthUser(email, uuid, Role.PASSENGER);
    }

    @Override
    public AuthUserDto register(DriverRegistrationDto driverRegistrationDto) {
        return null;
    }

    @Override
    public AuthUserDto register(PassengerRegistrationDto passengerRegistrationDto) {
        var httpEntity = createHttpEntity(passengerRegistrationDto);

        UserDto userDto  = (UserDto) restTemplate.exchange(passengerServiceUrl + "/create", HttpMethod.POST,
                        httpEntity, new ParameterizedTypeReference<>() {}).getBody();

        if (userDto == null) {
            throw new RegistrationFailedException("PassengerService return null object after registration");
        }

        var authUser = createAuthUser(userDto.getEmail(), userDto.getId(), Role.PASSENGER);
        return AuthUserMapper.INSTANCE.authUserToAuthUserDto(authUser);
    }

    private UserDto createPassenger(String email, String name) {
        Map<String, String> body = Map.of(EMAIL, email, NAME, name);
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body);

       return (UserDto) restTemplate.exchange(passengerServiceUrl + "/create", HttpMethod.POST,
                        entity, new ParameterizedTypeReference<>() {
                }).getBody();
    }

    private HttpEntity<PassengerRegistrationDto> createHttpEntity(PassengerRegistrationDto passengerRegistrationDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new HttpEntity<>(passengerRegistrationDto, headers);
    }

    private AuthUser createAuthUser(String email, UUID externalId, Role role) {
        AuthUser user = new AuthUser();
        user.setEmail(email);
        user.setRole(role);
        user.setExternalId(externalId);

        return userRepository.save(user);
    }

    private ResponseEntity<Map<String, String>> generateNewTokens(String email) {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with current email wasn't found!"));

        Map<String, Object> claims = Map.of(
                EXTERNAL_ID, user.getExternalId().toString(),
                ROLE, user.getRole().name()
        );

        return ResponseEntity.ok()
                .body(Map.of(ACCESS_TOKEN, jwtService.generateAccessToken(user, claims),
                        REFRESH_TOKEN, jwtService.generateRefreshToken(user, claims)));
    }

    private ResponseEntity<Map<String, String>> getResponseEntity(HttpStatus status, String key, String value) {
        return ResponseEntity.status(status).body(Collections.singletonMap(key, value));
    }
}
