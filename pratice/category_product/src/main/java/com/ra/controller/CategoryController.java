package com.ra.controller;

import com.ra.model.entity.Category;
import com.ra.model.service.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/category")
    public String index(Model model) {
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categoryList", categories);
        return "category/index";
    }

    @GetMapping("category-add")
    public String add(Model model) {
        Category cat = new Category();
        model.addAttribute("category",cat);
        return "category/add";
    }

    @PostMapping("/create-category")
    public String create(@ModelAttribute("category") Category category, RedirectAttributes redirectAttributes){
        categoryService.save(category);
        redirectAttributes.addFlashAttribute("mess","Add Success");
        return "redirect:/category";
    }

    @GetMapping("category/edit/{id}")
    public String edit(@PathVariable("id") Integer id,Model model){
        System.out.println(id);
        Category cat = categoryService.findById(id);
        model.addAttribute("category",cat);
        return "category/edit";
    }

    @PostMapping("category-update")
    public String update(@ModelAttribute("category") Category category,RedirectAttributes redirectAttributes){
        categoryService.save(category);
        redirectAttributes.addFlashAttribute("mess","Edit Success");
        return "redirect:/category";
    }

    @GetMapping("category/changeStatus/{id}")
    public String changeStatus(@PathVariable("id") Integer id){
        categoryService.changeStatus(id);
        return "redirect:/category";
    }
}


