package com.grocery.controllers;

import com.grocery.models.Product;
import com.grocery.models.User;
import com.grocery.payload.request.auth_request.SignupRequest;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
@ExtendWith({MockitoExtension.class})
class AdminControllerTest {

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
    private AdminController undertest;

    @Mock
    JwtUtils jwtUtils;

    @Mock
    PasswordEncoder encoder;

    private AuthController undertest1;



    @BeforeEach
    void setUp() {
        this.undertest = new AdminController(this.authenticationManager,this.userRepository,
                this.cartRepository,this.productRepository);
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
    void aLLBuyers() {
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


        this.undertest1.registerUser(input);
        this.userRepository.save(user);
        verify(userRepository).save(user);

        this.undertest.aLLBuyers();
        this.userRepository.findByUsername("vineet");
        verify(userRepository).findByUsername("vineet");


    }

    @Test
    void buyerDetails() {

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

        this.undertest.buyerDetails(id);
        verify(userRepository).findById(id);
    }

    @Test
    void deletebuyer() {
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

        this.undertest.deletebuyer(id);
        verify(userRepository).save(user);

    }

    @Test
    void aLLSellers() {
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
        BDDMockito.given(this.userRepository.findAllByRoleAndAccount_status(ArgumentMatchers.anyString(),ArgumentMatchers.anyString())).willReturn(null);

        this.undertest1.registerUser(input);
        this.userRepository.save(user);
        verify(userRepository).save(user);

        this.undertest.aLLSellers();
        verify(userRepository).findAllByRoleAndAccount_status("2","1");

    }

    @Test
    void sellerDetails() {
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

        this.undertest1.registerUser(input);
        this.userRepository.save(user);
        verify(userRepository).save(user);

        this.undertest.sellerDetails(id);
        verify(userRepository).save(user);
    }

    @Test
    void deleteSeller() {
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

        this.undertest.deleteSeller(id);
        verify(userRepository).save(user);

    }

    @Test
    void sellerItemDetails() {
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

        this.undertest.sellerItemDetails(id);
        verify(userRepository).findById(id);

    }

    @Test
    void allPendingSellers() {
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

        this.undertest.allPendingSellers();
        verify(userRepository).findAllByRoleAndAccount_status("2","0");

    }

    @Test
    void pendingSellerItemDetails() {
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

        this.undertest.pendingSellerItemDetails(id);
        verify(userRepository).findById(id);

    }

    @Test
    void pendingSellerDetails() {
        List<String> Role = new ArrayList();
        Role.add("seller");
        SignupRequest input = new SignupRequest();
        Long id  = 1L;
        input.setUsername("vineet");
        input.setAccount_status("1");
        input.setPassword("12345678");
        input.setEmail("vineet@gmail.com");
        input.setRole(Role);
        input.setAccount_status("0");
        input.setActive("1");
        input.setAddress("sneha sadan");
        input.setMobile("989333232");
        input.setFirst_name("vineet");
        input.setLast_name("tiwari");
        String role = "2";
        User user = new User(input.getUsername(), input.getEmail(), this.encoder.encode(input.getPassword()), input.getFirst_name(), input.getLast_name(), input.getAddress(), input.getMobile(), input.getActive(), input.getAccount_status(), role);

        this.undertest1.registerUser(input);
        this.userRepository.save(user);
        verify(userRepository).save(user);

        this.undertest.pendingSellerDetails(id);
        verify(userRepository).findById(id);

    }

    @Test
    void deletePendingSeller() {
        List<String> Role = new ArrayList();
        Role.add("seller");
        SignupRequest input = new SignupRequest();
        Long id  = 1L;
        input.setUsername("vineet");
        input.setAccount_status("0");
        input.setPassword("12345678");
        input.setEmail("vineet@gmail.com");
        input.setRole(Role);
        input.setAccount_status("0");
        input.setActive("1");
        input.setAddress("sneha sadan");
        input.setMobile("989333232");
        input.setFirst_name("vineet");
        input.setLast_name("tiwari");
        String role = "2";
        User user = new User(input.getUsername(), input.getEmail(), this.encoder.encode(input.getPassword()), input.getFirst_name(), input.getLast_name(), input.getAddress(), input.getMobile(), input.getActive(), input.getAccount_status(), role);

        this.undertest1.registerUser(input);
        this.userRepository.save(user);
        verify(userRepository).save(user);

        this.undertest.deletePendingSeller(id);
        verify(userRepository).findById(id);

    }

    @Test
    void approveSeller() {
        List<String> Role = new ArrayList();
        Role.add("seller");
        SignupRequest input = new SignupRequest();
        Long id  = 1L;
        input.setUsername("vineet");
        input.setAccount_status("0");
        input.setPassword("12345678");
        input.setEmail("vineet@gmail.com");
        input.setRole(Role);
        input.setAccount_status("0");
        input.setActive("1");
        input.setAddress("sneha sadan");
        input.setMobile("989333232");
        input.setFirst_name("vineet");
        input.setLast_name("tiwari");
        String role = "2";
        User user = new User(input.getUsername(), input.getEmail(), this.encoder.encode(input.getPassword()), input.getFirst_name(), input.getLast_name(), input.getAddress(), input.getMobile(), input.getActive(), input.getAccount_status(), role);

        this.undertest1.registerUser(input);
        this.userRepository.save(user);
        verify(userRepository).save(user);

        this.undertest.approveSeller(id);
        verify(userRepository).findById(id);


    }
}