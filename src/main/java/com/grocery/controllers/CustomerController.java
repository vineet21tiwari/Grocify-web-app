package com.grocery.controllers;

import com.grocery.models.Cart;
import com.grocery.models.Product;
import com.grocery.models.User;
import com.grocery.payload.request.user_request.AddToCartRequest;
import com.grocery.payload.request.user_request.EditAccountDetailsRequest;
import com.grocery.payload.response.auth_response.MessageResponse;
import com.grocery.payload.response.user_response.AccountDetailsResponse;
import com.grocery.payload.response.user_response.ShopsResponse;
import com.grocery.payload.response.user_response.ShowCartItemsResponse;
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

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    CartRepository cartRepository;

    @Autowired
    ProductRepository productRepository;

    public CustomerController(AuthenticationManager authenticationManager,
                              JwtUtils jwtUtils,
                              UserRepository userRepository, ProductRepository productRepository,
                              CartRepository cartRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
    }



    @GetMapping("/buyer")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> accountDetails() {
        if(SecurityContextHolder.getContext().getAuthentication()!=null) {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = userDetails.getUsername();
            User user = userRepository.findByUsername(username).get();
            if (Objects.equals(user.getActive(), "1")) {
                List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                        .collect(Collectors.toList());

                return ResponseEntity.ok(new AccountDetailsResponse(user.getId(), user.getUsername(), user.getFirst_name(),
                        user.getLast_name(), user.getEmail(), user.getMobile(), user.getAddress(),
                        user.getAccount_status(), user.getActive(), roles));
            }
            else
                return ResponseEntity.badRequest().body(new MessageResponse("Your account has deleted"));

        }
        else
            return ResponseEntity.notFound().build();

    }


    @PutMapping("/buyer")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> updateDetails(@Valid @RequestBody EditAccountDetailsRequest editAccountDetailsRequest){
        if(SecurityContextHolder.getContext().getAuthentication()!=null && editAccountDetailsRequest!=null) {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = userDetails.getUsername();
            User user;
            if(userRepository.findByUsername(username)!=null){
            user = userRepository.findByUsername(username).get();}
            else
                user=null;
            if (Objects.equals(user.getActive(), "1")) {
                user.setUsername(editAccountDetailsRequest.getUsername());
                user.setFirst_name(editAccountDetailsRequest.getFirst_name());
                user.setLast_name(editAccountDetailsRequest.getLast_name());
                user.setEmail(editAccountDetailsRequest.getEmail());
                user.setAddress(editAccountDetailsRequest.getAddress());
                user.setMobile(editAccountDetailsRequest.getMobile());
                userRepository.save(user);
                return ResponseEntity.ok(new MessageResponse("User Updated successfully!"));
            }
            else
                return ResponseEntity.badRequest().body(new MessageResponse("Your account has deleted"));

        }
        else
            return ResponseEntity.notFound().build();
    }


    @PutMapping("/buyer/delete")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> deleteAccount(){
        if(SecurityContextHolder.getContext().getAuthentication()!=null){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        User user = userRepository.findByUsername(username).get();
        if (Objects.equals(user.getActive(), "1")) {
        user.setActive("0");
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User Deleted successfully!"));}
        else
            return ResponseEntity.badRequest().body(new MessageResponse("Your account has deleted"));
        }
        else
            return ResponseEntity.notFound().build();
    }


   @GetMapping("/buyer/shops")
   @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> showShops() {
       if(SecurityContextHolder.getContext().getAuthentication()!=null) {
           UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
           String username = userDetails.getUsername();
           User user = userRepository.findByUsername(username).get();
           if (Objects.equals(user.getActive(), "1")) {
               List<User> sellers = (List<User>) userRepository.findAllByRoleAndAddress("2", user.getAddress());
               //List<ShopsResponse> shopsResponse;
               List<User> user1 = new ArrayList<User>();
               if (user != null) {
                   for (int i = 0; i < sellers.size(); i++) {
                       if (Objects.equals(sellers.get(i).getActive(), "1") && Objects.equals(sellers.get(i).getAccount_status(), "1")) {
                           user1.add(sellers.get(i));
                       }
                   }
               }

               List<ShopsResponse> shopsResponse = new ArrayList<ShopsResponse>();

               for (int i = 0; i < user1.size(); i++) {
                   ShopsResponse response = new ShopsResponse(user1.get(i).getId(), user1.get(i).getShopname(),
                           user1.get(i).getMobile(), user1.get(i).getFirst_name(), user1.get(i).getLast_name());
                   shopsResponse.add(response);
               }
               return ResponseEntity.ok(shopsResponse);
           }
           else
               return ResponseEntity.badRequest().body(new MessageResponse("Your account has deleted"));
       }
       else
           return ResponseEntity.notFound().build();

   }

    @GetMapping("/buyer/shops/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> showShopDetail(@PathVariable("id") Long id) {
        if(SecurityContextHolder.getContext().getAuthentication()!=null) {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = userDetails.getUsername();
            User user = userRepository.findByUsername(username).get();
            if (Objects.equals(user.getActive(), "1")) {
                List<User> sellers = (List<User>) userRepository.findAllByRoleAndAddress("2", user.getAddress());
                //List<ShopsResponse> shopsResponse;
                List<User> user1 = new ArrayList<User>();
                if (user != null) {
                    for (int i = 0; i < sellers.size(); i++) {
                        if (Objects.equals(sellers.get(i).getActive(), "1") && Objects.equals(sellers.get(i).getAccount_status(), "1")) {
                            user1.add(sellers.get(i));
                        }
                    }
                }
                for (int i = 0; i < user1.size(); i++) {
                    if(Objects.equals(user1.get(i).getId(), id)){
                        List<Product> product = productRepository.findAllByUserid(id);
                        return ResponseEntity.ok(product);
                    }
                }
                return ResponseEntity.ok(new MessageResponse("Item not found!"));

            }
            else
                return ResponseEntity.badRequest().body(new MessageResponse("Your account has deleted"));
        }
        else
            return ResponseEntity.notFound().build();
    }

    @PostMapping("/buyer/cart/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> addToCart(@PathVariable("id") Long id,@RequestBody AddToCartRequest addToCartRequest) {
        if(SecurityContextHolder.getContext().getAuthentication()!=null) {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = userDetails.getUsername();
            User user = userRepository.findByUsername(username).get();
            if (Objects.equals(user.getActive(), "1")) {
                Product product = productRepository.findById(id).get();
                Cart cart = new Cart(product.getShopname(), product.getItemname(), product.getItemprice(), user.getId(), addToCartRequest.getQuantity());
                cartRepository.save(cart);
                return ResponseEntity.ok(new MessageResponse("Item Added to Cart!"));
            }
            else
                return ResponseEntity.badRequest().body(new MessageResponse("Your account has deleted"));

        }
        else
            return ResponseEntity.notFound().build();
    }

    @GetMapping("/buyer/cart/items")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> showCartItems() {
        if(SecurityContextHolder.getContext().getAuthentication()!=null) {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = userDetails.getUsername();
            User user = userRepository.findByUsername(username).get();
            if (Objects.equals(user.getActive(), "1")) {
                List<Cart> cart = cartRepository.findAllByUserid(user.getId());
                Long total = 0L;
                for(int i=0;i< cart.size();i++){
                    Long item_price = Long.parseLong(cart.get(i).getItemprice());
                    Long quantity = Long.parseLong(cart.get(i).getQuantity());
                    total = total + item_price*quantity;
                }
                ShowCartItemsResponse show = new ShowCartItemsResponse(cart,total);
                return ResponseEntity.ok(show);
            }
            else
                return ResponseEntity.badRequest().body(new MessageResponse("Your account has deleted"));
        }
        else
            return ResponseEntity.notFound().build();
    }

    @DeleteMapping ("/buyer/cart/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> deleteItemFromCart(@PathVariable("id") Long id) {
        if(SecurityContextHolder.getContext().getAuthentication()!=null) {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = userDetails.getUsername();
            User user = userRepository.findByUsername(username).get();
            Cart cart = cartRepository.findByIdAndUserid(id, user.getId()).get();
            if (cart != null) {
                cartRepository.delete(cart);
                return ResponseEntity.ok(new MessageResponse("Item deleted successfully!"));
            } else
                return ResponseEntity.notFound().build();
        }
        else
            return ResponseEntity.notFound().build();
    }






}
