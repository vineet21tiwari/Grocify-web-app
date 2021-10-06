package com.grocery.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.grocery.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findById(Long id);
  //Optional<User> findByUsername(String username);

  List<User> findAllByRole(String role);

  //Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);

  List<User> findAllByRoleAndAddress(String role, String address);


  boolean existsByUsername(String username);

  Optional<User> findByUsername(String username);

  @Query("select u from User u where u.role = ?1 and u.account_status = ?2")
  List<User> findAllByRoleAndAccount_status(String role, String account_status);

  List<User> findAllByRoleAndActive(String role,String active);



}
