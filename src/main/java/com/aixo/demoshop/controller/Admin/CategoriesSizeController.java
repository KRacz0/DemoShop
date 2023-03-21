package com.aixo.demoshop.controller.Admin;

import com.aixo.demoshop.model.CategorySize;
import com.aixo.demoshop.service.CategorySizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class CategoriesSizeController {
    @Autowired
    CategorySizeService categorySizeService;

    @GetMapping("/admin/categoriesSize")
    public String getCatSize(Model model){
        model.addAttribute("categories_size", categorySizeService.getAllCategorySize());
        return "categoriesSize";
    }
    @GetMapping("/admin/categoriesSize/add")
    public String getCatSizeAdd(Model model){
        model.addAttribute("categorySize", new CategorySize());
        return "categoriesSizeAdd";
    }

    @PostMapping("/admin/categoriesSize/add")
    public String postCatSizeAdd(@ModelAttribute("category_size") CategorySize categorySize) {
        categorySizeService.addCategorySize(categorySize);
        return "redirect:/admin/categoriesSize";
    }
    @GetMapping("/admin/categoriesSize/delete/{id}")
    public String deleteCatSize(@PathVariable int id) {
        categorySizeService.removeCategorySizeById(id);
        return "redirect:/admin/categoriesSize";
    }
    @GetMapping("/admin/categoriesSize/update/{id}")
    public String updateCatSize(@PathVariable int id, Model model) {
        Optional<CategorySize> categorySize = categorySizeService.getCategorySizeById(id);
        if(categorySize.isPresent()) {
            model.addAttribute("categorySize", categorySize.get());
            return "categoriesSizeAdd";
        } else
            return "error";
    }
}
