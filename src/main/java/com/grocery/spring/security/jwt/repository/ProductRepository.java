package com.grocery.spring.security.jwt.repository;

import com.grocery.spring.security.jwt.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByUserid(Long userid);


    Optional<Product> findByUserid(Long userid);
    boolean existsById(Long user_id);

    Product findByIdAndUserid(Long id, Long userid);

}
