package com.grocery.controllers;

import com.grocery.models.Product;
import com.grocery.models.User;
import com.grocery.payload.request.auth_request.SignupRequest;
import com.grocery.payload.request.user_request.AddItemRequest;
import com.grocery.payload.request.user_request.EditAccountDetailsRequest;
import com.grocery.payload.request.user_request.EditItemDetailsRequest;
import com.grocery.repository.CartRepository;
import com.grocery.repository.ProductRepository;
import com.grocery.repository.RoleRepository;
import com.grocery.repository.UserRepository;
import com.grocery.security.jwt.JwtUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static net.bytebuddy.matcher.ElementMatchers.any;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.client.ExpectedCount.never;

@ExtendWith({MockitoExtension.class})
class SellerControllerTest {
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
    private SellerController undertest;

    @Mock
    JwtUtils jwtUtils;

    @Mock
    PasswordEncoder encoder;

    private AuthController undertest1;


    @BeforeEach
    void setUp() {
        this.undertest = new SellerController(this.authenticationManager,this.jwtUtils,this.userRepository,
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
    void checkAccountDetails() {
        Long id = 1L;
        this.undertest.accountDetails();
        this.userRepository.findById(id);
        ArgumentCaptor<Long> id1 = ArgumentCaptor.forClass(Long.class);
        verify(userRepository).findById(id1.capture());
        Long capturedvalue = id1.getValue();
        assertThat(capturedvalue).isEqualTo(1);
    }

    @Test
    void checkUpdateDetails() {
        Long id = 1L;
        EditAccountDetailsRequest editAccountDetailsRequest = new EditAccountDetailsRequest();
        editAccountDetailsRequest.setUsername("sunil");
        editAccountDetailsRequest.setEmail("sunil@gmail.com");
        editAccountDetailsRequest.setFirst_name("sunil");
        editAccountDetailsRequest.setLast_name("tomar");
        editAccountDetailsRequest.setAddress("jabalpur");
        editAccountDetailsRequest.setMobile("89822210101");
        undertest.updateDetails(editAccountDetailsRequest);
        this.userRepository.findByUsername("sunil");
        verify(userRepository).findByUsername("sunil");

    }

    @Test
    void checkDeleteAccount() {
        Long id = 1L;
        this.undertest.deleteAccount();
        this.userRepository.deleteById(id);
        //then
        ArgumentCaptor<Long> studentArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(userRepository).deleteById(studentArgumentCaptor.capture());
        Long capturedvalue = studentArgumentCaptor.getValue();
        assertThat(capturedvalue).isEqualTo(id);

    }

    @Test
    void checkAddItem() {
        Long id = 1L;
        AddItemRequest addItemRequest = new AddItemRequest();
        addItemRequest.setShopname("bikaner");
        addItemRequest.setItemname("paneer");
        addItemRequest.setItemprice("150");

        undertest.addItem(addItemRequest);
        Product product = new Product(addItemRequest.getShopname(),addItemRequest.getItemname(),
                addItemRequest.getItemprice(),id);
        this.productRepository.save(product);

        ArgumentCaptor<Product> itemArgumentCaptor = ArgumentCaptor.forClass(Product.class);
        verify(productRepository).save(itemArgumentCaptor.capture());
        Product capturedvalue = itemArgumentCaptor.getValue();
        assertThat(capturedvalue).isEqualTo(product);

    }

    @Test
    void checkShowItemsDetails() {
        this.undertest.showItemsDetails();
        this.productRepository.findAllByUserid(1L);
        verify(productRepository).findAllByUserid(1L);

    }

    @Test
    void checkShowItem() {
        Long id = 1L;
        this.undertest.showItem(id);
        this.productRepository.findByIdAndUserid(1L,1L);
        verify(productRepository).findByIdAndUserid(1L,1L);

    }

    @Test
    void checkEditItemDetails() {
        Long id = 1L;
        EditItemDetailsRequest editItemDetailsRequest = new EditItemDetailsRequest();

        editItemDetailsRequest.setItemname("rice");
        editItemDetailsRequest.setItemprice("350");
        this.undertest.editItemDetails(id,editItemDetailsRequest);
        Product product = new Product();
        product.setItemprice(editItemDetailsRequest.getItemprice());
        product.setItemprice(editItemDetailsRequest.getItemprice());
        product.setShopname("bikaner");
        product.setUserid(1L);
        this.productRepository.save(product);

        Optional<Product> products = productRepository.findById(id);
        ArgumentCaptor<Product> productsEntityArgumentCaptor = ArgumentCaptor.forClass(Product.class);
        verify(productRepository).save(productsEntityArgumentCaptor.capture());
        Product capturedvalue = productsEntityArgumentCaptor.getValue();
        assertThat(capturedvalue).isNotEqualTo(products);


    }

    @Test
    void checkDeleteItem() {
        Long id = 1L;
        this.undertest.deleteItem(id);
        this.productRepository.deleteById(id);
        //then
        ArgumentCaptor<Long> item = ArgumentCaptor.forClass(Long.class);
        verify(productRepository).deleteById(item.capture());
        Long capturedvalue = item.getValue();
        assertThat(capturedvalue).isEqualTo(id);


    }
}