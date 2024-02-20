package com.dsmovie.jacoco.services;

import com.dsmovie.jacoco.entities.UserEntity;
import com.dsmovie.jacoco.projections.UserDetailsProjection;
import com.dsmovie.jacoco.repositories.UserRepository;
import com.dsmovie.jacoco.tests.UserDetailsFactory;
import com.dsmovie.jacoco.tests.UserFactory;
import com.dsmovie.jacoco.utils.CustomUserUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
public class UserServiceTests {

    @InjectMocks
    private UserService service;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CustomUserUtil customUserUtil;

    private UserEntity userEntity;
    private List<UserDetailsProjection> userDetails;
    private String existingUsername, nonExistingUsername;

    @BeforeEach
    void setup() throws Exception {

        existingUsername = "maria@gmail.com";
        nonExistingUsername = "user@gmail.com";

        userEntity = UserFactory.createUserEntity();
        userDetails = UserDetailsFactory.createCustomAdminClientUser(existingUsername);

        when(userRepository.findByUsername(existingUsername)).thenReturn(Optional.of(userEntity));

        when(userRepository.searchUserAndRolesByUsername(existingUsername)).thenReturn(userDetails);
    }

    @Test
    public void authenticatedShouldReturnUserEntityWhenUserExists() {

        when(customUserUtil.getLoggedUsername()).thenReturn(existingUsername);

        UserEntity result = service.authenticated();

        assertNotNull(result);
        assertEquals(result.getUsername(), existingUsername);
    }

    @Test
    public void authenticatedShouldThrowUsernameNotFoundExceptionWhenUserDoesNotExists() {

        doThrow(ClassCastException.class).when(customUserUtil).getLoggedUsername();

        assertThrows(UsernameNotFoundException.class, () -> {
            service.authenticated();
        });
    }

    @Test
    public void loadUserByUsernameShouldReturnUserDetailsWhenUserExists() {

        UserDetails result = service.loadUserByUsername(existingUsername);

        assertNotNull(result);
        assertEquals(result.getUsername(), existingUsername);
    }

    @Test
    public void loadUserByUsernameShouldThrowUsernameNotFoundExceptionWhenUserDoesNotExists() {

        assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername(nonExistingUsername));
    }
}
