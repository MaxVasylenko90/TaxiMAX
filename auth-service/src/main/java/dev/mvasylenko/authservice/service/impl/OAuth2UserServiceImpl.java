package dev.mvasylenko.authservice.service.impl;

import dev.mvasylenko.authservice.entity.AuthUser;
import dev.mvasylenko.authservice.repository.AuthUserRepository;
import dev.mvasylenko.core.enums.Role;
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
    private static final String NAME = "name";

    private final AuthUserRepository userRepository;

    @Autowired
    public OAuth2UserServiceImpl(AuthUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);
        String email = oauth2User.getAttribute(EMAIL);

        var userFromDb = userRepository.findByEmail(email);
        AuthUser requestedUser = userFromDb.orElseGet(() -> createNewAuthUserInDb(email, oauth2User.getAttribute(NAME)));

        return new DefaultOAuth2User(
                List.of(new SimpleGrantedAuthority(ROLE_PREFIX + requestedUser.getRole().name())),
                oauth2User.getAttributes(), EMAIL);
    }

    private AuthUser createNewAuthUserInDb(String email, String name) {
        AuthUser user = new AuthUser();
        user.setEmail(email);
        user.setRole(Role.PASSENGER);
        userRepository.save(user);
        return user;
    }
}
