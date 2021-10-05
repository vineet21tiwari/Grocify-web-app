package com.grocery.controllers;

import com.grocery.models.Product;
import com.grocery.models.Role;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
        List<User> user = userRepository.findAllByRoleAndActive("1","1");
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
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return ResponseEntity.ok(new AccountDetailsResponse(user.getId(), user.getUsername(), user.getFirst_name(),
                user.getLast_name(), user.getEmail(), user.getMobile(), user.getAddress(), user.getAccount_status(),
                user.getActive(), roles));
    }

    @PutMapping("/admin/buyers/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deletebuyer(@PathVariable("id") Long id) {
        User user = userRepository.findById(id).get();
        user.setActive("0");
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User Deleted successfully!"));
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
    @GetMapping("/admin/sellers/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> sellerDetails(@PathVariable("id") Long id) {
        User user = userRepository.findById(id).get();
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return ResponseEntity.ok(new AccountDetailsResponse(user.getId(), user.getUsername(), user.getFirst_name(),
                user.getLast_name(), user.getEmail(), user.getMobile(), user.getAddress(), user.getAccount_status(),
                user.getActive(), roles));

    }
    @PutMapping("/admin/sellers/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteSeller(@PathVariable("id") Long id) {
        User user = userRepository.findById(id).get();
        user.setActive("0");
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User Deleted successfully!"));
    }

    @GetMapping("/admin/sellers/items/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> sellerItemDetails(@PathVariable("id") Long id) {
        User user = userRepository.findById(id).get();
        List<Product> product = productRepository.findAllByUserid(id);
        return ResponseEntity.ok(product);
    }



    @GetMapping("/admin/pending")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> allPendingSellers() {
        List<User> user = userRepository.findAllByRoleAndAccount_status("2","0");
        List<AllUsersResponse> response=new ArrayList<AllUsersResponse>();;
        for(int i=0;i< user.size();i++){
            response.add(new AllUsersResponse(user.get(i).getId(),user.get(i).getUsername(),user.get(i).getFirst_name(),
                    user.get(i).getLast_name()));
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/admin/pending/items/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> pendingSellerItemDetails(@PathVariable("id") Long id) {
        User user = userRepository.findById(id).get();
        List<Product> product = productRepository.findAllByUserid(id);
        return ResponseEntity.ok(product);
    }
    @GetMapping("/admin/pending/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> pendingSellerDetails(@PathVariable("id") Long id) {
        User user = userRepository.findById(id).get();
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return ResponseEntity.ok(new AccountDetailsResponse(user.getId(), user.getUsername(), user.getFirst_name(),
                user.getLast_name(), user.getEmail(), user.getMobile(), user.getAddress(), user.getAccount_status(),
                user.getActive(), roles));

    }
    @PutMapping("/admin/pending/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deletePendingSeller(@PathVariable("id") Long id) {
        User user = userRepository.findById(id).get();
        user.setActive("0");
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User Deleted successfully!"));
    }

    @PutMapping("/admin/approve/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> approveSeller(@PathVariable("id") Long id) {
        User user = userRepository.findById(id).get();
        user.setAccount_status("1");
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User Approved successfully!"));
    }




}
