package com.grocery.models;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(	name = "users", 
		uniqueConstraints = { 
			@UniqueConstraint(columnNames = "username"),
			@UniqueConstraint(columnNames = "email") 
		})
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 20)
	@Column(name = "username")
	private String username;

	@NotBlank
	@Size(max = 50)
	@Column(name = "email")
	private String email;

	@NotBlank
	@Size(max = 120)
	@Column(name = "password")
	private String password;

	@NotBlank
	@Size(max = 50)
	@Column(name = "first_name")
	private String first_name;

	@NotBlank
	@Size(max = 50)
	@Column(name = "last_name")
	private String last_name;

	@NotBlank
	@Size(max = 200)
	@Column(name = "address")
	private String address;

	@NotBlank
	@Size(max = 15)
	@Column(name = "mobile")
	private String mobile;


	@Column(name = "active")
	private String active;

	@Column(name = "account_status")
	private String account_status;

	@Column(name="role")
	private String role;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "user_roles", 
				joinColumns = @JoinColumn(name = "user_id"), 
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	@Column(name="shopname")
	private String shopname;

	public User() {
	}

	public User(String username, String email,
				String password, String first_name, String last_name,
				String address, String mobile, String active,
				String account_status, String role) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.first_name = first_name;
		this.last_name = last_name;
		this.address = address;
		this.mobile = mobile;
		this.active = active;
		this.account_status = account_status;
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getAccount_status() {
		return account_status;
	}

	public void setAccount_status(String account_status) {
		this.account_status = account_status;
	}

	public String getShopname() {
		return shopname;
	}

	public void setShopname(String shopname) {
		this.shopname = shopname;
	}
}
