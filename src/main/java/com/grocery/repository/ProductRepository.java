package com.grocery.repository;

import com.grocery.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByUserid(Long userid);
    Optional<Product> findById(Long id);


    Optional<Product> findByUserid(Long userid);
    boolean existsById(Long user_id);

    Optional<Product> findByIdAndUserid(Long id, Long userid);

    boolean existsByUserid(long l);

    boolean existsByIdAndUserid(long l, long l1);
}
