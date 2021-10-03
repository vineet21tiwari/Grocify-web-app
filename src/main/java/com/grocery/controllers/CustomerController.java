package com.grocery.controllers;

import com.grocery.models.User;
import com.grocery.payload.request.user_request.EditAccountDetailsRequest;
import com.grocery.payload.response.auth_response.MessageResponse;
import com.grocery.payload.response.user_response.AccountDetailsResponse;
import com.grocery.payload.response.user_response.ShopsResponse;
import com.grocery.repository.ProductRepository;
import com.grocery.repository.UserRepository;
import com.grocery.security.jwt.JwtUtils;
import com.grocery.security.services.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("")
public class CustomerController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    public CustomerController(AuthenticationManager authenticationManager,
                              JwtUtils jwtUtils, RefreshTokenService refreshTokenService,
                              UserRepository userRepository, ProductRepository productRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.refreshTokenService = refreshTokenService;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @GetMapping("/buyer")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> accountDetails() {
        if(SecurityContextHolder.getContext().getAuthentication()!=null){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        User user = userRepository.findByUsername(username).get();
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new AccountDetailsResponse(user.getId(),user.getUsername(),user.getFirst_name(),
                user.getLast_name(),user.getEmail(),user.getMobile(),user.getAddress(),
                user.getAccount_status(),user.getActive(),roles));}

        else
            return ResponseEntity.ok(new MessageResponse("Username not found!"));

    }


    @PutMapping("/buyer")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> updateDetails(@Valid @RequestBody EditAccountDetailsRequest editAccountDetailsRequest){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        User user = userRepository.findByUsername(username).get();
        user.setUsername(editAccountDetailsRequest.getUsername());
        user.setFirst_name(editAccountDetailsRequest.getFirst_name());
        user.setLast_name(editAccountDetailsRequest.getLast_name());
        user.setEmail(editAccountDetailsRequest.getEmail());
        user.setAddress(editAccountDetailsRequest.getAddress());
        user.setMobile(editAccountDetailsRequest.getMobile());
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User Updated successfully!"));
    }


    @PutMapping("/buyer/delete")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> deleteAccount(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        User user = userRepository.findByUsername(username).get();
        user.setActive("0");
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User Deleted successfully!"));
    }


   @GetMapping("/buyer/shops")
   @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> showShops() {
       UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       String username = userDetails.getUsername();
       User user = userRepository.findByUsername(username).get();
       List<User> sellers = (List<User>) userRepository.findAllByRoleAndAddress("2",user.getAddress());
       //List<ShopsResponse> shopsResponse;
       List<ShopsResponse> shopsResponse = new ArrayList<ShopsResponse>();


       for (int i = 0; i < sellers.size(); i++) {
           ShopsResponse response = new ShopsResponse(sellers.get(i).getId(), sellers.get(i).getShopname(),
                   sellers.get(i).getMobile(),sellers.get(i).getFirst_name(),sellers.get(i).getLast_name());
           shopsResponse.add(response);
       }

       return ResponseEntity.ok(shopsResponse);

   }



}
