package com.grocery.controllers;

import com.grocery.repository.ProductRepository;
import com.grocery.repository.UserRepository;
import com.grocery.security.jwt.JwtUtils;
import com.grocery.security.services.RefreshTokenService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    JwtUtils jwtUtils;

    @Mock
    RefreshTokenService refreshTokenService;

    @Mock
    UserRepository userRepository;

    @Mock
    ProductRepository productRepository;

    private CustomerController undertest;


    @BeforeEach
    void setUp() {
        undertest = new CustomerController(authenticationManager,jwtUtils,refreshTokenService,userRepository,
                                           productRepository );
    }

    @Test
    void accountDetails() {

    }

    @Test
    void updateDetails() {
    }

    @Test
    void deleteAccount() {
    }

    @Test
    void showShops() {
    }
}