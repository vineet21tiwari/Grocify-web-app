package com.grocery.controllers;

import com.grocery.models.User;
import com.grocery.payload.request.auth_request.SignupRequest;
import com.grocery.repository.ProductRepository;
import com.grocery.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    private AuthController undertest;


    @BeforeEach
    void setUp() {

        undertest = new AuthController(userRepository);
    }

    @Test
    void authenticateUser() {

    }


    @Test
    void registerUser() {

    }

    @Test
    void sellerSignupAddItem() {
    }
}