package com.aixo.demoshop.controller;

import com.aixo.demoshop.service.CategoryService;
import com.aixo.demoshop.service.CategorySizeService;
import com.aixo.demoshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
            return "index";
    }
}
