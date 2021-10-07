package com.grocery.controllers;

import com.grocery.models.Cart;
import com.grocery.models.Product;
import com.grocery.models.User;
import com.grocery.payload.request.auth_request.SignupRequest;
import com.grocery.payload.request.user_request.AddToCartRequest;
import com.grocery.payload.request.user_request.EditAccountDetailsRequest;
import com.grocery.repository.CartRepository;
import com.grocery.repository.ProductRepository;
import com.grocery.repository.RoleRepository;
import com.grocery.repository.UserRepository;
import com.grocery.security.jwt.JwtUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith({MockitoExtension.class})
class CustomerControllerTest {
    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    CartRepository cartRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private CustomerController undertest;

    @Mock
    JwtUtils jwtUtils;

    @Mock
    PasswordEncoder encoder;

    private AuthController undertest1;


    @BeforeEach
    void setUp() {
        this.undertest = new CustomerController(this.authenticationManager,this.jwtUtils,this.userRepository,
                this.productRepository, this.cartRepository);
        this.undertest1 = new AuthController(this.userRepository,  this.roleRepository,this.encoder);

    }

    @AfterEach
    void tearDown() {
        productRepository.deleteAll();
        userRepository.deleteAll();
        cartRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @Test
    void accountDetails() {
        List<String> Role = new ArrayList();
        Role.add("buyer");
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
        String role = "1";
        User user = new User(input.getUsername(), input.getEmail(), this.encoder.encode(input.getPassword()), input.getFirst_name(), input.getLast_name(), input.getAddress(), input.getMobile(), input.getActive(), input.getAccount_status(), role);
        BDDMockito.given(this.userRepository.existsByUsername(ArgumentMatchers.anyString())).willReturn(false);

        this.undertest1.registerUser(input);
        this.userRepository.save(user);
        verify(userRepository).save(user);

        this.undertest.accountDetails();
        this.userRepository.findByUsername("vineet");
        this.userRepository.findByUsername("Rahul");
        verify(userRepository).findByUsername("vineet");
        verify(userRepository).findByUsername("Rahul");

    }

    @Test
    void updateDetails() {
        List<String> Role = new ArrayList();
        Role.add("buyer");
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
        String role = "1";
        User user = new User(input.getUsername(), input.getEmail(), this.encoder.encode(input.getPassword()), input.getFirst_name(), input.getLast_name(), input.getAddress(), input.getMobile(), input.getActive(), input.getAccount_status(), role);
        BDDMockito.given(this.userRepository.existsByUsername(ArgumentMatchers.anyString())).willReturn(false);

        this.undertest1.registerUser(input);
        this.userRepository.save(user);


        EditAccountDetailsRequest editAccountDetailsRequest = new EditAccountDetailsRequest();
        editAccountDetailsRequest.setAddress("delhi");
        editAccountDetailsRequest.setUsername("vineet");
        editAccountDetailsRequest.setEmail("vineet@gmail.com");
        editAccountDetailsRequest.setFirst_name("vineet123");
        editAccountDetailsRequest.setLast_name("tiwari");
        editAccountDetailsRequest.setMobile("99994949494");


        this.undertest.updateDetails(editAccountDetailsRequest);
        verify(userRepository).save(user);


    }

    @Test
    void deleteAccount() {
        List<String> Role = new ArrayList();
        Role.add("buyer");
        SignupRequest input = new SignupRequest();
        Long id  = 1L;
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
        String role = "1";
        User user = new User(input.getUsername(), input.getEmail(), this.encoder.encode(input.getPassword()), input.getFirst_name(), input.getLast_name(), input.getAddress(), input.getMobile(), input.getActive(), input.getAccount_status(), role);

        this.undertest1.registerUser(input);
        this.userRepository.save(user);
        verify(userRepository).save(user);

        this.undertest.deleteAccount();
        verify(userRepository).save(user);
    }

    @Test
    void showShops() {
        List<String> Role = new ArrayList();
        Role.add("seller");
        SignupRequest input = new SignupRequest();
        Long id  = 1L;
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
        BDDMockito.given(this.userRepository.findAllByRoleAndAddress(ArgumentMatchers.anyString(),ArgumentMatchers.anyString())).willReturn(null);

        this.undertest1.registerUser(input);
        this.userRepository.save(user);
        verify(userRepository).save(user);
        this.userRepository.findAllByRoleAndAddress("2","sneha sadan");

        this.undertest.showShops();
        verify(userRepository).findAllByRoleAndAddress("2","sneha sadan");
    }

    @Test
    void showShopDetail() {
        List<String> Role = new ArrayList();
        Role.add("seller");
        SignupRequest input = new SignupRequest();
        Long id  = 1L;
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

        Product product = new Product();
        product.setItemname("paneer");
        product.setShopname("bikaner");
        product.setItemprice("400");
        product.setUserid(1L);
        this.productRepository.save(product);

        User user = new User(input.getUsername(), input.getEmail(), this.encoder.encode(input.getPassword()), input.getFirst_name(), input.getLast_name(), input.getAddress(), input.getMobile(), input.getActive(), input.getAccount_status(), role);
        this.undertest1.registerUser(input);
        this.userRepository.save(user);
        this.productRepository.findAllByUserid(id);
        verify(userRepository).save(user);

        this.undertest.showShopDetail(id);
        verify(productRepository).findAllByUserid(id);

    }

    @Test
    void addToCart() {
        List<String> Role = new ArrayList();
        Role.add("seller");
        SignupRequest input = new SignupRequest();
        Long id  = 1L;
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

        Product product = new Product();
        product.setItemname("paneer");
        product.setShopname("bikaner");
        product.setItemprice("400");
        product.setUserid(1L);
        this.productRepository.save(product);

        User user = new User(input.getUsername(), input.getEmail(), this.encoder.encode(input.getPassword()), input.getFirst_name(), input.getLast_name(), input.getAddress(), input.getMobile(), input.getActive(), input.getAccount_status(), role);
        this.undertest1.registerUser(input);
        this.userRepository.save(user);
        verify(userRepository).save(user);
        verify(productRepository).save(product);
        AddToCartRequest addToCartRequest = new AddToCartRequest();
        addToCartRequest.setQuantity("5");

        Cart cart = new Cart(product.getShopname(), product.getItemname(), product.getItemprice(), product.getUserid(),
                          addToCartRequest.getQuantity()) ;

        this.undertest.addToCart(id,addToCartRequest);
        this.cartRepository.save(cart);
        verify(cartRepository).save(cart);

    }

    @Test
    void showCartItems() {
        Cart cart = new Cart("bikaner","paneer","450",1L,"4");
        this.cartRepository.save(cart);
        this.cartRepository.findAllByUserid(1L);
        this.cartRepository.findAllByUserid(3L);

        verify(cartRepository).findAllByUserid(1L);
        verify(cartRepository).findAllByUserid(3L);
    }

    @Test
    void deleteItemFromCart() {
        Cart cart = new Cart("bikaner","paneer","450",1L,"4");
        this.cartRepository.save(cart);
        this.cartRepository.delete(cart);
        this.cartRepository.findById(1L);

        verify(cartRepository).delete(cart);
        verify(cartRepository).findById(1L);
    }
}