package dev.mvasylenko.authservice.service.impl;

import dev.mvasylenko.authservice.entity.AuthUser;
import dev.mvasylenko.authservice.repository.AuthUserRepository;
import dev.mvasylenko.authservice.service.OAuthUserRegistrationService;
import dev.mvasylenko.core.enums.Role;
import dev.mvasylenko.core.events.PassengerRegisteredEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static dev.mvasylenko.core.constants.CoreConstants.EMAIL;
import static dev.mvasylenko.core.constants.CoreConstants.NAME;

@Service
public class OAuthUserRegistrationServiceImpl implements OAuthUserRegistrationService {
    private static final Logger LOG = LoggerFactory.getLogger(OAuthUserRegistrationServiceImpl.class);
    private static final int SURNAME_INDEX = 1;
    private static final int NAME_INDEX = 0;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final AuthUserRepository userRepository;
    private final String registrationEventsTopicName;

    public OAuthUserRegistrationServiceImpl(KafkaTemplate<String, Object> kafkaTemplate, AuthUserRepository userRepository,
                                            @Value("${registration.events.topic.name}") String registrationEventsTopicName) {
        this.kafkaTemplate = kafkaTemplate;
        this.userRepository = userRepository;
        this.registrationEventsTopicName = registrationEventsTopicName;
    }

    @Override
    @Transactional
    public AuthUser registerNewOAuth2User(OAuth2User oauthUser) {
        final String email = oauthUser.getAttribute(EMAIL);
        final String name = oauthUser.getAttribute(NAME);

        AuthUser authUser = new AuthUser(email, Role.PASSENGER);
        var registeredUser = userRepository.save(authUser);
        userRepository.flush();

        PassengerRegisteredEvent event = createPassengerRegisteredEvent(authUser.getId(), email, name, Role.PASSENGER);
        kafkaTemplate.send(registrationEventsTopicName, registeredUser.getId().toString(), event);

        LOG.info("AuthUser with email = {} has been registered successfully", email);
        return registeredUser;
    }

    private PassengerRegisteredEvent createPassengerRegisteredEvent(UUID userId, String email, String name, Role role) {
        String[] userName = name.split(" ");
        PassengerRegisteredEvent event = new PassengerRegisteredEvent();
        event.setUserId(userId);
        event.setEmail(email);
        event.setName(userName[NAME_INDEX]);
        event.setSurname(userName[SURNAME_INDEX]);
        event.setRole(role);
        return event;
    }
}
