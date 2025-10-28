package dev.mvasylenko.authservice.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mvasylenko.authservice.entity.AuthUser;
import dev.mvasylenko.authservice.repository.AuthUserRepository;
import dev.mvasylenko.authservice.security.jwt.JwtService;
import dev.mvasylenko.authservice.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

import static dev.mvasylenko.core.constants.CoreConstants.*;

@Component("oAuth2AuthenticationSuccessHandler")
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private static final String APPLICATION_JSON = "application/json";

    private final AuthUserRepository authUserRepository;
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    public OAuth2AuthenticationSuccessHandler(AuthUserRepository authUserRepository, JwtService jwtService, AuthenticationService authenticationService) {
        this.authUserRepository = authUserRepository;
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        final OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
        String email = oauthUser.getAttribute(EMAIL);
        AuthUser user = authUserRepository.findByEmail(email).orElseGet(() ->
                authenticationService.registerNewOAuth2User(oauthUser));

        Map<String, Object> claims = Map.of(
                EXTERNAL_ID, user.getExternalId().toString(),
                ROLE, user.getRole().name()
        );

        String accessToken = jwtService.generateAccessToken(user, claims);
        String refreshToken = jwtService.generateRefreshToken(user, claims);

        response.setContentType(APPLICATION_JSON);
        response.getWriter().write(
                new ObjectMapper().writeValueAsString(Map.of(ACCESS_TOKEN, accessToken, REFRESH_TOKEN, refreshToken)));
    }
}
