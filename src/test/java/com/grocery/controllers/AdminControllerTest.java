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
import org.springframework.web.bind.annotation.RequestHeader;

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
    void checkAllBuyers() {
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


        this.undertest.aLLBuyers("Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjaGF5Iiwicm9sZSI6InNlbGxlciIsImV4cCI6MTYzMjkyNTkzNiwiaWF0IjoxNjMyOTIyMzM2fQ.urWWNBtfuwQ_iJT1tFFNTEeXnAo6JPH2FvVWvBApYVpE0ws0N8S1Y-n1wZuXb9siCljveVhkCgtouVmV6Pw7aw");
        this.userRepository.findByUsername("vineet");
        verify(userRepository).findByUsername("vineet");



    }

    @Test
    void checkBuyerDetails() {

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

        this.undertest.buyerDetails(id,"Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjaGF5Iiwicm9sZSI6InNlbGxlciIsImV4cCI6MTYzMjkyNTkzNiwiaWF0IjoxNjMyOTIyMzM2fQ.urWWNBtfuwQ_iJT1tFFNTEeXnAo6JPH2FvVWvBApYVpE0ws0N8S1Y-n1wZuXb9siCljveVhkCgtouVmV6Pw7aw");
        verify(userRepository).findById(id);
    }

    @Test
    void checkDeletebuyer() {
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

        this.undertest.deletebuyer(id,"Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjaGF5Iiwicm9sZSI6InNlbGxlciIsImV4cCI6MTYzMjkyNTkzNiwiaWF0IjoxNjMyOTIyMzM2fQ.urWWNBtfuwQ_iJT1tFFNTEeXnAo6JPH2FvVWvBApYVpE0ws0N8S1Y-n1wZuXb9siCljveVhkCgtouVmV6Pw7aw");
        verify(userRepository).save(user);

    }

    @Test
    void checkAllSellers() {
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

        this.undertest.aLLSellers("Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjaGF5Iiwicm9sZSI6InNlbGxlciIsImV4cCI6MTYzMjkyNTkzNiwiaWF0IjoxNjMyOTIyMzM2fQ.urWWNBtfuwQ_iJT1tFFNTEeXnAo6JPH2FvVWvBApYVpE0ws0N8S1Y-n1wZuXb9siCljveVhkCgtouVmV6Pw7aw");
        verify(userRepository).findAllByRoleAndAccount_status("2","1");

    }

    @Test
    void checkSellerDetails() {
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

        this.undertest.sellerDetails(id,"Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjaGF5Iiwicm9sZSI6InNlbGxlciIsImV4cCI6MTYzMjkyNTkzNiwiaWF0IjoxNjMyOTIyMzM2fQ.urWWNBtfuwQ_iJT1tFFNTEeXnAo6JPH2FvVWvBApYVpE0ws0N8S1Y-n1wZuXb9siCljveVhkCgtouVmV6Pw7aw");
        verify(userRepository).save(user);
    }

    @Test
    void checkDeleteSeller() {
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

        this.undertest.deleteSeller(id,"Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjaGF5Iiwicm9sZSI6InNlbGxlciIsImV4cCI6MTYzMjkyNTkzNiwiaWF0IjoxNjMyOTIyMzM2fQ.urWWNBtfuwQ_iJT1tFFNTEeXnAo6JPH2FvVWvBApYVpE0ws0N8S1Y-n1wZuXb9siCljveVhkCgtouVmV6Pw7aw");
        verify(userRepository).save(user);

    }

    @Test
    void checkSellerItemDetails() {
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

        this.undertest.sellerItemDetails(id,"Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjaGF5Iiwicm9sZSI6InNlbGxlciIsImV4cCI6MTYzMjkyNTkzNiwiaWF0IjoxNjMyOTIyMzM2fQ.urWWNBtfuwQ_iJT1tFFNTEeXnAo6JPH2FvVWvBApYVpE0ws0N8S1Y-n1wZuXb9siCljveVhkCgtouVmV6Pw7aw");
        verify(userRepository).findById(id);

    }

    @Test
    void checkAllPendingSellers() {
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

        this.undertest.allPendingSellers("Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjaGF5Iiwicm9sZSI6InNlbGxlciIsImV4cCI6MTYzMjkyNTkzNiwiaWF0IjoxNjMyOTIyMzM2fQ.urWWNBtfuwQ_iJT1tFFNTEeXnAo6JPH2FvVWvBApYVpE0ws0N8S1Y-n1wZuXb9siCljveVhkCgtouVmV6Pw7aw");
        verify(userRepository).findAllByRoleAndAccount_status("2","0");

    }

    @Test
    void checkPendingSellerItemDetails() {
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

        this.undertest.pendingSellerItemDetails(id,"Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjaGF5Iiwicm9sZSI6InNlbGxlciIsImV4cCI6MTYzMjkyNTkzNiwiaWF0IjoxNjMyOTIyMzM2fQ.urWWNBtfuwQ_iJT1tFFNTEeXnAo6JPH2FvVWvBApYVpE0ws0N8S1Y-n1wZuXb9siCljveVhkCgtouVmV6Pw7aw");
        verify(userRepository).findById(id);

    }

    @Test
    void checkPendingSellerDetails() {
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

        this.undertest.pendingSellerDetails(id,"Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjaGF5Iiwicm9sZSI6InNlbGxlciIsImV4cCI6MTYzMjkyNTkzNiwiaWF0IjoxNjMyOTIyMzM2fQ.urWWNBtfuwQ_iJT1tFFNTEeXnAo6JPH2FvVWvBApYVpE0ws0N8S1Y-n1wZuXb9siCljveVhkCgtouVmV6Pw7aw");
        verify(userRepository).findById(id);

    }

    @Test
    void checkDeletePendingSeller() {
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

        this.undertest.deletePendingSeller(id,"Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjaGF5Iiwicm9sZSI6InNlbGxlciIsImV4cCI6MTYzMjkyNTkzNiwiaWF0IjoxNjMyOTIyMzM2fQ.urWWNBtfuwQ_iJT1tFFNTEeXnAo6JPH2FvVWvBApYVpE0ws0N8S1Y-n1wZuXb9siCljveVhkCgtouVmV6Pw7aw");
        verify(userRepository).findById(id);

    }

    @Test
    void checkApproveSeller() {
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

        this.undertest.approveSeller(id,"Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjaGF5Iiwicm9sZSI6InNlbGxlciIsImV4cCI6MTYzMjkyNTkzNiwiaWF0IjoxNjMyOTIyMzM2fQ.urWWNBtfuwQ_iJT1tFFNTEeXnAo6JPH2FvVWvBApYVpE0ws0N8S1Y-n1wZuXb9siCljveVhkCgtouVmV6Pw7aw");
        verify(userRepository).findById(id);


    }
}