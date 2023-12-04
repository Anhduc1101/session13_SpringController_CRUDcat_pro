package com.ra.model.dao.category;

import com.ra.model.entity.Category;

import java.util.List;

public interface CategoryDAO {
    List<Category> findAll();
    Category findById(Integer id);
    boolean save(Category category);
    void changeStatus(Integer id);
}
