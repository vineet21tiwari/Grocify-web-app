package com.grocery.spring.security.jwt.controllers;

import com.grocery.spring.security.jwt.models.Product;
import com.grocery.spring.security.jwt.models.User;
import com.grocery.spring.security.jwt.payload.request.user_request.EditAccountDetailsRequest;
import com.grocery.spring.security.jwt.payload.response.auth_response.MessageResponse;
import com.grocery.spring.security.jwt.payload.response.user_response.AccountDetailsResponse;
import com.grocery.spring.security.jwt.payload.response.user_response.ShopsResponse;
import com.grocery.spring.security.jwt.repository.ProductRepository;
import com.grocery.spring.security.jwt.repository.UserRepository;
import com.grocery.spring.security.jwt.security.jwt.JwtUtils;
import com.grocery.spring.security.jwt.security.services.RefreshTokenService;
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
import java.util.Optional;
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


    @GetMapping("/buyer")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> accountDetails() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        User user = userRepository.findByUsername(username).get();
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new AccountDetailsResponse(user.getId(),user.getUsername(),user.getFirst_name(),
                user.getLast_name(),user.getEmail(),user.getMobile(),user.getAddress(),
                user.getAccount_status(),user.getActive(),roles));
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
