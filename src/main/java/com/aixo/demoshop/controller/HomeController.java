package com.aixo.demoshop.controller;

import com.aixo.demoshop.global.GlobalCart;
import com.aixo.demoshop.service.CategoryService;
import com.aixo.demoshop.service.CategorySizeService;
import com.aixo.demoshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    CategorySizeService categorySizeService;
    @Autowired
    ProductService productService;

    @GetMapping({"/", "/home"})
    public String home(Model model){
        model.addAttribute("cartCount", GlobalCart.cart.size());
        return "index";
    }
    @GetMapping("/shop")
    public String shop(Model model){
        model.addAttribute("categories", categoryService.getAllCategory());
        model.addAttribute("categoriesSize", categorySizeService.getAllCategorySize());
        model.addAttribute("products", productService.getAllProduct());
        model.addAttribute("cartCount", GlobalCart.cart.size());
        return "shop";
    }
    @GetMapping("/shop/viewProduct/{id}")
    public String viewProduct(Model model, @PathVariable int id) {
        model.addAttribute("product", productService.getProductById(id).get());
        model.addAttribute("cartCount", GlobalCart.cart.size());
        return "viewProduct";
    }
}
