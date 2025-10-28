package dev.mvasylenko.authservice.service;

import dev.mvasylenko.authservice.entity.AuthUser;
import dev.mvasylenko.core.dto.UserLoginDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

public interface AuthenticationService {
    /**
     * User authentication
     * @param request
     * @return - ResponseEntity with code and corresponding message
     */
    ResponseEntity<Map<String, String>> authenticate(UserLoginDto request);

    /**
     * Request ro refresh access token
     * @param refreshToken - old refresh token
     * @return - Map with new access and refresh tokens
     */
    ResponseEntity<Map<String, String>> refreshAccessToken(String refreshToken);

    /**
     * Registration of a new OAuth user
     * @param oauthUser
     * @return AuthUser from database
     */
    AuthUser registerNewOAuth2User(OAuth2User oauthUser);
}
