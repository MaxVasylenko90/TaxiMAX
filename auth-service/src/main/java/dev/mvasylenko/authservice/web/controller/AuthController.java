package dev.mvasylenko.authservice.web.controller;

import dev.mvasylenko.authservice.security.jwt.JwtService;
import dev.mvasylenko.authservice.service.AuthenticationService;
import dev.mvasylenko.core.dto.*;
import dev.mvasylenko.core.enums.Role;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

import static dev.mvasylenko.core.constants.CoreConstants.MESSAGE;
import static dev.mvasylenko.core.constants.CoreConstants.REFRESH_TOKEN;

@RestController
@RequestMapping("/auth")
public class AuthController {
//    private static final String GOOGLE_LOGIN = "googleLogin";
//    private static final String OAUTH_2_AUTHORIZATION_GOOGLE_LINK = "http://localhost:10200/oauth2/authorization/google";
    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    public AuthController(AuthenticationService authenticationService, JwtService jwtService) {
        this.authenticationService = authenticationService;
        this.jwtService = jwtService;
    }

    @GetMapping("/driver/registration")
    public Map<String, String> driverRegistration() {
        return Collections.singletonMap(MESSAGE, "This is driver registration page");
    }

    @PostMapping("/driver/registration")
    public AuthUserDto driverRegistration(@RequestBody @Valid UserRegistrationDto userDto) {
        return authenticationService.register(userDto, Role.DRIVER);
    }

    @GetMapping("/passenger/registration")
    public Map<String, String> passengerRegistration() {
        return Collections.singletonMap(MESSAGE, "This is passenger registration page");
    }

    @PostMapping("/passenger/registration")
    public AuthUserDto passengerRegistration(@RequestBody @Valid UserRegistrationDto userDto) {
        return authenticationService.register(userDto, Role.PASSENGER);
    }

//    @GetMapping("/login")
//    public Map<String, String> login() {
//        return Map.of(MESSAGE, "This is login page",
//                GOOGLE_LOGIN, OAUTH_2_AUTHORIZATION_GOOGLE_LINK);
//    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody @Valid UserLoginDto userLoginDto) {
        return authenticationService.authenticate(userLoginDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(@RequestBody Map<String, String> request) {
        jwtService.deleteRefreshToken(request.get(REFRESH_TOKEN));
        return ResponseEntity.ok(Collections.singletonMap(MESSAGE, "Logged out successfully"));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<Map<String, String>> refreshAccessToken(@RequestBody Map<String,String> request) {
        return authenticationService.refreshAccessToken(request.get(REFRESH_TOKEN));
    }
}
