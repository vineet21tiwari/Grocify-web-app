package com.grocery.controllers;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.grocery.models.Product;
import com.grocery.models.User;
import com.grocery.payload.request.user_request.AddItemRequest;
import com.grocery.payload.request.user_request.EditItemDetailsRequest;
import com.grocery.payload.request.user_request.EditAccountDetailsRequest;
import com.grocery.payload.response.user_response.AccountDetailsResponse;
import com.grocery.payload.response.auth_response.MessageResponse;
import com.grocery.payload.response.user_response.ShowItemDetailsResponse;
import com.grocery.payload.response.user_response.ShowItemResponse;
import com.grocery.repository.CartRepository;
import com.grocery.repository.ProductRepository;
import com.grocery.repository.UserRepository;
import com.grocery.security.jwt.JwtUtils;
import com.grocery.security.services.RefreshTokenService;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("")
public class SellerController {
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

	@Autowired
	CartRepository cartRepository;

	public SellerController(AuthenticationManager authenticationManager,
							JwtUtils jwtUtils,
							UserRepository userRepository,
							ProductRepository productRepository,CartRepository cartRepository) {
		this.authenticationManager = authenticationManager;
		this.jwtUtils = jwtUtils;
		this.userRepository = userRepository;
		this.productRepository = productRepository;
		this.cartRepository = cartRepository;
	}



	@GetMapping("/seller")
	@PreAuthorize("hasRole('SELLER')")
	public ResponseEntity<?> accountDetails() {
		if(SecurityContextHolder.getContext().getAuthentication()!=null) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();
		User user = userRepository.findByUsername(username).get();
			if (Objects.equals(user.getActive(), "1")) {
				List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
						.collect(Collectors.toList());

				return ResponseEntity.ok(new AccountDetailsResponse(user.getId(),user.getUsername(),user.getFirst_name(),
						user.getLast_name(),user.getEmail(),user.getMobile(),user.getAddress(),
						user.getAccount_status(),user.getActive(),roles));}
			else
				return ResponseEntity.badRequest().body(new MessageResponse("Your account has deleted"));
		}
		else
			return ResponseEntity.notFound().build();

	}

	@PutMapping("/seller")
	@PreAuthorize("hasRole('SELLER')")
	public ResponseEntity<?> updateDetails(@Valid @RequestBody EditAccountDetailsRequest editAccountDetailsRequest){
		if(SecurityContextHolder.getContext().getAuthentication()!=null) {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String username = userDetails.getUsername();
			User user = userRepository.findByUsername(username).get();
			if (Objects.equals(user.getActive(), "1")) {
				user.setUsername(editAccountDetailsRequest.getUsername());
				user.setFirst_name(editAccountDetailsRequest.getFirst_name());
				user.setLast_name(editAccountDetailsRequest.getLast_name());
				user.setEmail(editAccountDetailsRequest.getEmail());
				user.setAddress(editAccountDetailsRequest.getAddress());
				user.setMobile(editAccountDetailsRequest.getMobile());
				userRepository.save(user);
				return ResponseEntity.ok(new MessageResponse("User Updated successfully!"));}
			else
				return ResponseEntity.badRequest().body(new MessageResponse("Your account has deleted"));
		}
		else
			return ResponseEntity.notFound().build();
	}

	@PutMapping("/seller/delete")
	@PreAuthorize("hasRole('SELLER')")
	public ResponseEntity<?> deleteAccount(){
		if(SecurityContextHolder.getContext().getAuthentication()!=null) {
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

	@PostMapping("/seller/items")
	@PreAuthorize("hasRole('SELLER')")
	public ResponseEntity<?> addItem(@Valid @RequestBody AddItemRequest addItemRequest){
		if(SecurityContextHolder.getContext().getAuthentication()!=null) {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String username = userDetails.getUsername();
			User user = userRepository.findByUsername(username).get();
			if (Objects.equals(user.getActive(), "1")) {
				Product product = new Product(addItemRequest.getShopname(),addItemRequest.getItemname(),
						addItemRequest.getItemprice(),user.getId());
				productRepository.save(product);

				return ResponseEntity.ok(new MessageResponse("Item added successfully!"));}
			else
				return ResponseEntity.badRequest().body(new MessageResponse("Your account has deleted"));
		}
		else
			return ResponseEntity.notFound().build();
	}

	@GetMapping("/seller/items")
	@PreAuthorize("hasRole('SELLER')")
	public ResponseEntity<?> showItemsDetails() {
		if(SecurityContextHolder.getContext().getAuthentication()!=null) {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String username = userDetails.getUsername();
			User user = userRepository.findByUsername(username).get();
			if (Objects.equals(user.getActive(), "1")) {
				List<Product> product = productRepository.findAllByUserid(user.getId());
				return ResponseEntity.ok(new ShowItemDetailsResponse(product));}
			else
				return ResponseEntity.badRequest().body(new MessageResponse("Your account has deleted"));
		}
		else
			return ResponseEntity.notFound().build();
	}

	@GetMapping("/seller/items/{id}")
	@PreAuthorize("hasRole('SELLER')")
	public ResponseEntity<?> showItem(@PathVariable("id") Long id) {
		if(SecurityContextHolder.getContext().getAuthentication()!=null) {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String username = userDetails.getUsername();
			User user = userRepository.findByUsername(username).get();
			if (Objects.equals(user.getActive(), "1")) {
				Product product = productRepository.findByIdAndUserid(id,user.getId()).get();
				return ResponseEntity.ok(new ShowItemResponse(product.getItemname(), product.getItemprice()));}
			else
				return ResponseEntity.badRequest().body(new MessageResponse("Your account has deleted"));
			}
		else
			return ResponseEntity.notFound().build();
	}

	@PutMapping("/seller/items/{id}")
	@PreAuthorize("hasRole('SELLER')")
	public ResponseEntity<?> editItemDetails(@PathVariable("id") Long id,@Valid @RequestBody EditItemDetailsRequest editItemDetailsRequest) {
		if(SecurityContextHolder.getContext().getAuthentication()!=null) {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String username = userDetails.getUsername();
			User user = userRepository.findByUsername(username).get();
			if (Objects.equals(user.getActive(), "1")) {
				Product product = productRepository.findByIdAndUserid(id, user.getId()).get();
				product.setItemname(editItemDetailsRequest.getItemname());
				product.setItemprice(editItemDetailsRequest.getItemprice());
				productRepository.save(product);
				return ResponseEntity.ok(new MessageResponse("Item updated successfully!"));
			}
			else
				return ResponseEntity.badRequest().body(new MessageResponse("Your account has deleted"));
		}
		else
			return ResponseEntity.notFound().build();
	}

	@DeleteMapping ("/seller/items/{id}")
	@PreAuthorize("hasRole('SELLER')")
	public ResponseEntity<?> deleteItem(@PathVariable("id") Long id) {
		if(SecurityContextHolder.getContext().getAuthentication()!=null) {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String username = userDetails.getUsername();
			User user = userRepository.findByUsername(username).get();
			if (Objects.equals(user.getActive(), "1")) {
				Product product = productRepository.findById(id).get();
				if ((Objects.equals(product.getUserid(), user.getId())) && product != null) {
					productRepository.delete(product);
					return ResponseEntity.ok(new MessageResponse("Item deleted successfully!"));
				}
				else
					return ResponseEntity.notFound().build();
			}
			else
				return ResponseEntity.badRequest().body(new MessageResponse("Your account has deleted"));
		}
		return ResponseEntity.notFound().build();
	}
}
