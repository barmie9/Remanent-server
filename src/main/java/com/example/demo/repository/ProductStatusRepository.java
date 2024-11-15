package com.example.demo.repository;

import com.example.demo.model.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductStatusRepository extends JpaRepository<ProductStatus,Long> {

//    Optional<List<ProductStatus>> findAllByOrderByName();
}
