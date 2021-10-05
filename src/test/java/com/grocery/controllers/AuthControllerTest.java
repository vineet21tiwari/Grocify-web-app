package com.grocery.controllers;

import com.grocery.models.User;
import java.util.Optional;
import com.grocery.payload.request.auth_request.SignupRequest;
import com.grocery.repository.ProductRepository;
import com.grocery.repository.RoleRepository;
import com.grocery.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class AuthControllerTest {


    private AuthController undertest = new AuthController();

    @Mock
    private UserRepository userRepository;



   /* @BeforeEach
    void setUp() {
        undertest = new AuthController(userRepository);
    }*/


    @Test
    void authenticateUser() {

    }

    @Test
    void registerUser() {
            List<String> role = new ArrayList<String>();
            role.add("seller");
            SignupRequest input = new SignupRequest();
            input.setUsername("vineet");
            input.setAccount_status("1");
            input.setPassword("12345678");
            input.setEmail("vineet@gmail.com");
            input.setRole(role);
            input.setAccount_status("1");
            input.setActive("1");
            input.setAddress("sneha-sadan");
            input.setMobile("989333232");
            input.setFirst_name("vineet");
            input.setLast_name("tiwari");
            String role1 = "seller";



            // ArgumentCaptor<SignupRequest> studentArgumentCaptor = ArgumentCaptor.forClass(SignupRequest.class);
            User user = new User(input.getUsername(), input.getEmail(), input.getPassword(),
                    input.getFirst_name(), input.getLast_name(), input.getAddress(),
                   input.getMobile(), input.getActive(), input.getAccount_status(), role1);

            undertest.registerUser(input);

        ArgumentCaptor<User> studentArgumentCaptor = ArgumentCaptor.forClass(User.class);

        verify(userRepository).save(studentArgumentCaptor.capture());

        User capturedvalue = studentArgumentCaptor.getValue();

        assertThat(capturedvalue).isEqualTo(user);


            //SignupRequest capturedvalue = studentArgumentCaptor.getValue();

            //  assertThat(capturedvalue).isEqualTo(input);
            assertNotNull(undertest.registerUser(input));
        }


    @Test
    void sellerSignupAddItem() {
    }
}