package com.example.wxbf.service.impl;

import com.example.wxbf.dao.ProductRepository;
import com.example.wxbf.po.Product;
import com.example.wxbf.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public List<Product> saveAll() {

        return null;
    }
}
