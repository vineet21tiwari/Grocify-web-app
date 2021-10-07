//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grocery.controllers;

import com.grocery.models.ERole;
import com.grocery.models.Product;
import com.grocery.models.Role;
import com.grocery.models.User;
import com.grocery.payload.request.auth_request.SignupRequest;
import com.grocery.payload.request.user_request.SellerSignupAddItem;
import com.grocery.repository.ProductRepository;
import com.grocery.repository.RoleRepository;
import com.grocery.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.client.ExpectedCount;

import static net.bytebuddy.matcher.ElementMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith({MockitoExtension.class})
class AuthControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private RoleRepository roleRepository;

    private AuthController undertest;

    @Mock
    PasswordEncoder encoder;

    AuthControllerTest() {
    }

    @BeforeEach
    void setUp() {
        this.undertest = new AuthController(this.userRepository,  this.roleRepository,this.encoder);
    }

    @AfterEach
    void tearDown() {
        this.userRepository.deleteAll();
        this.roleRepository.deleteAll();
        this.productRepository.deleteAll();
    }


    @Test
    void registerUser1() {

        List<String> Role = new ArrayList();
        Role.add("seller");
        SignupRequest input = new SignupRequest();

        input.setUsername("vineet");
        input.setAccount_status("1");
        input.setPassword("12345678");
        input.setEmail("vineet@gmail.com");
        input.setRole(Role);
        input.setAccount_status("1");
        input.setActive("1");
        input.setAddress("sneha sadan");
        input.setMobile("989333232");
        input.setFirst_name("vineet");
        input.setLast_name("tiwari");
        String role = "2";
        User user = new User(input.getUsername(), input.getEmail(), this.encoder.encode(input.getPassword()), input.getFirst_name(), input.getLast_name(), input.getAddress(), input.getMobile(), input.getActive(), input.getAccount_status(), role);
        BDDMockito.given(this.userRepository.existsByUsername(ArgumentMatchers.anyString())).willReturn(false);
        BDDMockito.given(this.userRepository.existsByEmail(ArgumentMatchers.anyString())).willReturn(false);
        this.undertest.registerUser(input);
        this.userRepository.save(user);

        verify(userRepository).save(user);
    }
    @Test
    void registerUser2() {

        List<String> Role = new ArrayList();
        Role.add("admin");
        SignupRequest input = new SignupRequest();

        input.setUsername("admin");
        input.setAccount_status("1");
        input.setPassword("12345678");
        input.setEmail("admin@gmail.com");
        input.setRole(Role);
        input.setAccount_status("1");
        input.setActive("1");
        input.setAddress("delhi");
        input.setMobile("989333232");
        input.setFirst_name("admin");
        input.setLast_name("tiwari");
        String role = "3";
        User user = new User(input.getUsername(), input.getEmail(), this.encoder.encode(input.getPassword()), input.getFirst_name(), input.getLast_name(), input.getAddress(), input.getMobile(), input.getActive(), input.getAccount_status(), role);
        BDDMockito.given(this.userRepository.existsByUsername(ArgumentMatchers.anyString())).willReturn(false);
        BDDMockito.given(this.userRepository.existsByEmail(ArgumentMatchers.anyString())).willReturn(false);
        this.undertest.registerUser(input);
        this.userRepository.save(user);
        verify(userRepository).save(user);
    }
    @Test
    void registerUser3() {

        List<String> Role = new ArrayList();
        Role.add("customer");
        SignupRequest input = new SignupRequest();

        input.setUsername("customer");
        input.setAccount_status("0");
        input.setPassword("12345678");
        input.setEmail("customer@gmail.com");
        input.setRole(Role);
        input.setAccount_status("1");
        input.setActive("1");
        input.setAddress("hyderabad");
        input.setMobile("989333232");
        input.setFirst_name("customer");
        input.setLast_name("tiwari");
        String role = "1";
        User user = new User(input.getUsername(), input.getEmail(), this.encoder.encode(input.getPassword()), input.getFirst_name(), input.getLast_name(), input.getAddress(), input.getMobile(), input.getActive(), input.getAccount_status(), role);
        BDDMockito.given(this.userRepository.existsByUsername(ArgumentMatchers.anyString())).willReturn(false);
        BDDMockito.given(this.userRepository.existsByEmail(ArgumentMatchers.anyString())).willReturn(false);
        this.undertest.registerUser(input);
        this.userRepository.save(user);
        verify(userRepository).save(user);
    }


    @Test
    void sellerSignupAddItem() {
        Long id = 1L;
        SellerSignupAddItem input = new SellerSignupAddItem();
        input.setId(1L);
        input.setShopname("bikaner");
        input.setItemname("rice");
        input.setItemprice("400");

        Product product = new Product(input.getShopname(),input.getItemname(),input.getItemprice(),id);

        this.undertest.sellerSignupAddItem(id,input);
        this.productRepository.save(product);
        verify(productRepository).save(product);


    }
}
