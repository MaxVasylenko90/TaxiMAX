package dev.mvasylenko.authservice.service.impl;

import dev.mvasylenko.authservice.entity.AuthUser;
import dev.mvasylenko.authservice.repository.AuthUserRepository;
import dev.mvasylenko.authservice.service.OAuthUserRegistrationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;

import static dev.mvasylenko.core.constants.CoreConstants.EMAIL;
import static dev.mvasylenko.core.constants.CoreConstants.ROLE_PREFIX;

@Service("oauth2UserServiceImpl")
public class OAuth2UserServiceImpl extends DefaultOAuth2UserService {
    private final AuthUserRepository userRepository;
    private final OAuthUserRegistrationService registrationService;

    @Autowired
    public OAuth2UserServiceImpl(AuthUserRepository userRepository, OAuthUserRegistrationService registrationService) {
        this.userRepository = userRepository;
        this.registrationService = registrationService;
    }

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);
        final String email = oauth2User.getAttribute(EMAIL);

        AuthUser requestedUser = userRepository.findByEmail(email).orElseGet(() ->
                registrationService.registerNewOAuth2User(oauth2User));

        return new DefaultOAuth2User(
                List.of(new SimpleGrantedAuthority(ROLE_PREFIX + requestedUser.getRole().name())),
                oauth2User.getAttributes(), EMAIL);
    }
}
