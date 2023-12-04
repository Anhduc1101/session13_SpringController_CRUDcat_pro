package com.ra.model.dao.product;

import com.ra.model.entity.Product;

import java.util.List;

public interface ProductDAO {
    List<Product> findAll();
    void delete(Integer id);
    boolean save(Product product);
    Product findById(Integer id);
    List<Product> findByName(String name);
}
