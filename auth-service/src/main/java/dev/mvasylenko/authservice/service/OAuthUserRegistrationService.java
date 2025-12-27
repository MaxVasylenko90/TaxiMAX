package dev.mvasylenko.authservice.service;

import dev.mvasylenko.authservice.entity.AuthUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface OAuthUserRegistrationService {
    /**
     * Registration of a new OAuth user
     * @param oauthUser
     * @return AuthUser object
     */
    AuthUser registerNewOAuth2User(OAuth2User oauthUser);
}
