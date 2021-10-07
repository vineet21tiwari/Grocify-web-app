package com.grocery.repository;

import com.grocery.models.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepositoryTest {
    @Autowired
    private ProductRepository undertest;

    @AfterEach
    void tearDown() {
        undertest.deleteAll();
    }


    @Test
    void checkFindByIdAndUserid() {
        //given
        Product product = new Product("bikaner","rice","400",1L);
        undertest.save(product);
        Long id = 1L;

        //when
        boolean exists = undertest.existsByIdAndUserid(id,1L);

        //then
        assertThat(exists).isTrue();
    }
}
