package com.example.checkrecognzer.repositoties;


import com.example.checkrecognzer.entities.ProductCheckEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCheckRepository extends JpaRepository<ProductCheckEntity, Long> {
    @Query("SELECT DISTINCT pc FROM ProductCheckEntity pc " +
            "LEFT JOIN FETCH pc.productItems")
    List<ProductCheckEntity> findAllWithItems();
}
