package com.aixo.demoshop.repository;

import com.aixo.demoshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByCategory_Id(int id);
    List<Product> findAllByCategorySize_Id(int id);

}
