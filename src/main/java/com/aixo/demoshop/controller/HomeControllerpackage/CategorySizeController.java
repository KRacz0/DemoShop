package com.aixo.demoshop.controller.HomeControllerpackage;

import com.aixo.demoshop.service.CategoryService;
import com.aixo.demoshop.service.CategorySizeService;
import com.aixo.demoshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
@Controller
public class CategorySizeController {
    @Autowired
    CategorySizeService categorySizeService;
    @Autowired
    ProductService productService;
    @GetMapping("/shop/categorySize/{id}")
    public String shopByCategorySize(Model model, @PathVariable int id) {
        model.addAttribute("categoriesSize", categorySizeService.getAllCategorySize());
        model.addAttribute("products", productService.getAllProductsByCategorySizeId(id));
        return "shop";
    }

}
