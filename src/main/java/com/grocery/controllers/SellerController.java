package com.grocery.controllers;

import java.util.List;
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


	@GetMapping("/seller")
	@PreAuthorize("hasRole('SELLER')")
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

	@PutMapping("/seller")
	@PreAuthorize("hasRole('SELLER')")
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

	@PutMapping("/seller/delete")
	@PreAuthorize("hasRole('SELLER')")
	public ResponseEntity<?> deleteAccount(){
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();
		User user = userRepository.findByUsername(username).get();
		user.setActive("0");

		userRepository.save(user);
		return ResponseEntity.ok(new MessageResponse("User Deleted successfully!"));

	}

	@PostMapping("/seller/items")
	@PreAuthorize("hasRole('SELLER')")
	public ResponseEntity<?> addItem(@Valid @RequestBody AddItemRequest addItemRequest){
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();
		User user = userRepository.findByUsername(username).get();

		Product product = new Product(addItemRequest.getShopname(),addItemRequest.getItemname(),
				addItemRequest.getItemprice(),user.getId());
		productRepository.save(product);

		return ResponseEntity.ok(new MessageResponse("Item added successfully!"));
	}

	@GetMapping("/seller/items")
	@PreAuthorize("hasRole('SELLER')")
	public ResponseEntity<?> showItemsDetails() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();
		User user = userRepository.findByUsername(username).get();
		List<Product> product = productRepository.findAllByUserid(user.getId());
		return ResponseEntity.ok(new ShowItemDetailsResponse(product));
	}

	@GetMapping("/seller/items/{id}")
	@PreAuthorize("hasRole('SELLER')")
	public ResponseEntity<?> showItem(@PathVariable("id") Long id) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();
		User user = userRepository.findByUsername(username).get();
		Product product = productRepository.findByIdAndUserid(id,user.getId());
		return ResponseEntity.ok(new ShowItemResponse(product.getItemname(), product.getItemprice()));
	}

	@PutMapping("/seller/items/{id}")
	@PreAuthorize("hasRole('SELLER')")
	public ResponseEntity<?> editItemDetails(@PathVariable("id") Long id,@Valid @RequestBody EditItemDetailsRequest editItemDetailsRequest) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();
		User user = userRepository.findByUsername(username).get();
		Product product = productRepository.findById(id).get();
		product.setItemname(editItemDetailsRequest.getItemname());
		product.setItemprice(editItemDetailsRequest.getItemprice());
		productRepository.save(product);
		return ResponseEntity.ok(new MessageResponse("Item updated successfully!"));
	}

	@DeleteMapping ("/seller/items/{id}")
	@PreAuthorize("hasRole('SELLER')")
	public ResponseEntity<?> deleteItem(@PathVariable("id") Long id) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();
		User user = userRepository.findByUsername(username).get();
		Product product = productRepository.findById(id).get();
		if(product!=null){
		productRepository.delete(product);
			return ResponseEntity.ok(new MessageResponse("Item deleted successfully!"));}
		else
			return ResponseEntity.ok(new MessageResponse("Item Not Found!"));
	}
}
