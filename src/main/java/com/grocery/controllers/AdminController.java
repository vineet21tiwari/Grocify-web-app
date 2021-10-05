package com.grocery.controllers;

import com.grocery.models.User;
import com.grocery.payload.response.admin_response.AllUsersResponse;
import com.grocery.payload.response.auth_response.MessageResponse;
import com.grocery.payload.response.user_response.AccountDetailsResponse;
import com.grocery.repository.CartRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("")
public class AdminController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    ProductRepository productRepository;

    @GetMapping("/admin/buyers")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> aLLBuyers() {
        List<User> user = userRepository.findAllByRole("1");
        List<AllUsersResponse> response=new ArrayList<AllUsersResponse>();;
        for(int i=0;i< user.size();i++){
            response.add(new AllUsersResponse(user.get(i).getId(),user.get(i).getUsername(),user.get(i).getFirst_name(),
                    user.get(i).getLast_name()));
        }
        return ResponseEntity.ok(response);
    }
    @GetMapping("/admin/sellers")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> aLLSellers() {
        List<User> user = userRepository.findAllByRoleAndAccount_status("2","1");
        List<AllUsersResponse> response=new ArrayList<AllUsersResponse>();;
        for(int i=0;i< user.size();i++){
            response.add(new AllUsersResponse(user.get(i).getId(),user.get(i).getUsername(),user.get(i).getFirst_name(),
                    user.get(i).getLast_name()));
        }
        return ResponseEntity.ok(response);
    }
    @GetMapping("/admin/pending")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> aLLPendingSellers() {
        List<User> user = userRepository.findAllByRoleAndAccount_status("2","0");
        List<AllUsersResponse> response=new ArrayList<AllUsersResponse>();;
        for(int i=0;i< user.size();i++){
            response.add(new AllUsersResponse(user.get(i).getId(),user.get(i).getUsername(),user.get(i).getFirst_name(),
                    user.get(i).getLast_name()));
        }
        return ResponseEntity.ok(response);
    }
    @GetMapping("/admin/buyers/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> buyerDetails(@PathVariable("id") Long id) {
        User user = userRepository.findById(id).get();
        return ResponseEntity.ok(user);
    }

    @GetMapping("/admin/sellers/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> sellerDetails(@PathVariable("id") Long id) {
        User user = userRepository.findById(id).get();
        return ResponseEntity.ok(user);

    }

}
