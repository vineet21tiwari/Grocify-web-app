package com.grocery.repository;

import com.grocery.models.Cart;
import com.grocery.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUserid(Long id);

    Optional<Cart> findById(Long id);

    List<Cart> findAllByUserid(Long id);

    Optional<Cart> findByIdAndUserid(Long id, Long userid);
}
