package com.grocery.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.grocery.models.*;
import com.grocery.payload.request.auth_request.LoginRequest;
import com.grocery.payload.request.auth_request.SignupRequest;
import com.grocery.payload.request.auth_request.TokenRefreshRequest;
import com.grocery.payload.request.user_request.SellerSignupAddItem;
import com.grocery.payload.response.user_response.SellerSignupResponse;
import com.grocery.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.grocery.exception.TokenRefreshException;
import com.grocery.payload.response.auth_response.JwtResponse;
import com.grocery.payload.response.auth_response.MessageResponse;
import com.grocery.payload.response.auth_response.TokenRefreshResponse;
import com.grocery.repository.RoleRepository;
import com.grocery.repository.UserRepository;
import com.grocery.security.jwt.JwtUtils;
import com.grocery.security.services.RefreshTokenService;
import com.grocery.security.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  ProductRepository productRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

  @Autowired
  RefreshTokenService refreshTokenService;

  public AuthController(UserRepository userRepository) {

  }

    @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    User user = userRepository.findByUsername(loginRequest.getUsername()).get();
    if(Objects.equals(user.getActive(), "0"))
      return ResponseEntity.ok(new MessageResponse("User Account Deleted!"));

    if(Objects.equals(user.getAccount_status(), "0"))
      return ResponseEntity.ok(new MessageResponse("Your account is not approved yet, please wait " +
              "for approval from Admin"));

    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

    String jwt = jwtUtils.generateJwtToken(userDetails);

    List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
            .collect(Collectors.toList());

    RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

    return ResponseEntity.ok(new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(),
        userDetails.getUsername(),userDetails.getFirst_name(),roles));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
    }

    else if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
    }

    else {
      // Create new user's account
      String role = "";
      Set<Role> roles = new HashSet<>();

      List<String> strRoles = signUpRequest.getRole();
      for (int i = 0; i < strRoles.size(); i++) {
        if (Objects.equals(strRoles.get(i), "seller")) {
          role = "2";
          Role userRole = roleRepository.findByName(ERole.ROLE_SELLER)
                  .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(userRole);
        } else {
          role = "1";
          Role userRole = roleRepository.findByName(ERole.ROLE_CUSTOMER)
                  .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(userRole);

        }
      }
    /*Set<Role> roles = new HashSet<>();

    if (strRoles == null) {
      Role userRole = roleRepository.findByName(ERole.ROLE_CUSTOMER)
          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
      role="1";
    } else {
      strRoles.forEach(role1 -> {
        switch (role1) {
        case "seller":
          Role sellerRole = roleRepository.findByName(ERole.ROLE_SELLER)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(sellerRole);


          break;
        default:
          Role userRole = roleRepository.findByName(ERole.ROLE_CUSTOMER)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(userRole);
          role.set("1");
        }
      });
    }*/
      User user = new User(signUpRequest.getUsername(),
              signUpRequest.getEmail(),
              encoder.encode(signUpRequest.getPassword()),
              signUpRequest.getFirst_name(),
              signUpRequest.getLast_name(),
              signUpRequest.getAddress(),
              signUpRequest.getMobile(),
              signUpRequest.getActive(),
              signUpRequest.getAccount_status(),
              role);

      user.setRoles(roles);
      userRepository.save(user);
      return ResponseEntity.ok(new SellerSignupResponse(user.getId(), "User registered successfully!"));
    }
  }

  @PostMapping("/signup/seller/{id}")
  public ResponseEntity<?> sellerSignupAddItem(@PathVariable("id") Long id,@Valid @RequestBody SellerSignupAddItem sellerSignupAddItem) {
    Product product = new Product(sellerSignupAddItem.getShopname(),sellerSignupAddItem.getItemname(),
            sellerSignupAddItem.getItemprice(),id);
    User user = userRepository.findById(id).get();
    user.setShopname(sellerSignupAddItem.getShopname());
    userRepository.save(user);
    productRepository.save(product);

    return ResponseEntity.ok(new MessageResponse("Register Successfully, please wait for approval from Admin."));
  }


  @PostMapping("/refreshtoken")
  public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
    String requestRefreshToken = request.getRefreshToken();

    return refreshTokenService.findByToken(requestRefreshToken)
        .map(refreshTokenService::verifyExpiration)
        .map(RefreshToken::getUser)
        .map(user -> {
          String token = jwtUtils.generateTokenFromUsername(user.getUsername());
          return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
        })
        .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
            "Refresh token is not in database!"));
  }


}
