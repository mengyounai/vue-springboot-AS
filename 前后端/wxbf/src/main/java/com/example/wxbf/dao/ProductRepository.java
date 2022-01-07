package com.example.wxbf.dao;

import com.example.wxbf.po.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

//     Product findProductsByProductKey(String ProductKey);

}
