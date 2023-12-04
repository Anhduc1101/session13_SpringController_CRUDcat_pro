package com.ra.controller;

import com.ra.model.entity.Category;
import com.ra.model.entity.Product;
import com.ra.model.service.category.CategoryService;
import com.ra.model.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProductController {
    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @RequestMapping("/product")
    public String index(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "product/index-pro";
    }

    @GetMapping("product-add")
    public String add(Model model) {
        Product product = new Product();
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("product", product);
        return "product/add-pro";
    }

    @PostMapping("/create-product")
    public String add_product(@ModelAttribute("product") Product product) {
        productService.save(product);
        return "redirect:/product";
    }

    @GetMapping("product/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Product pro = productService.findById(id);
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("pro", pro);
        return "product/edit-pro";
    }

    @PostMapping("update-product")
    public String updateProduct(@ModelAttribute("product") Product product) {
        productService.save(product);
        return "redirect:/product";
    }

    @GetMapping("product/delete/{id}")
    public String deleteProduct(@PathVariable("id") Integer id, Model model) {
        productService.delete(id);
        return "redirect:/product";
    }
}
