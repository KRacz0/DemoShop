package com.aixo.demoshop.controller;

import com.aixo.demoshop.dto.ProductDTO;
import com.aixo.demoshop.model.Category;
import com.aixo.demoshop.model.CategorySize;
import com.aixo.demoshop.model.Product;
import com.aixo.demoshop.service.CategoryService;
import com.aixo.demoshop.service.CategorySizeService;
import com.aixo.demoshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Controller
public class AdminController {
    public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/productImages";
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;

    @Autowired
    CategorySizeService categorySizeService;

    @GetMapping("/admin")
    public String adminHome() {
        return "adminHome";
    }
    //Categories
    @GetMapping("/admin/categories")
    public String getCat(Model model) {
        model.addAttribute("categories", categoryService.getAllCategory());
        return "categories";
    }

    @GetMapping("/admin/categories/add")
    public String getCatAdd(Model model) {
        model.addAttribute("category", new Category());
        return "categoriesAdd";
    }

    @PostMapping("/admin/categories/add")
    public String postCatAdd(@ModelAttribute("category") Category category) {
        categoryService.addCategory(category);
        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categories/delete/{id}")
    public String deleteCat(@PathVariable int id) {
        categoryService.removeCategoryById(id);
        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categories/update/{id}")
    public String updateCat(@PathVariable int id, Model model) {
        Optional<Category> category = categoryService.getCategoryById(id);
        if (category.isPresent()) {
            model.addAttribute("category", category.get());
            return "categoriesAdd";
        } else
            return "error";
    }

    //CategorySize
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


    //Product Section
    @GetMapping("/admin/products")
    public String product(Model model) {
        model.addAttribute("products", productService.getAllProduct());
        return "products";
    }

    @GetMapping("/admin/products/add")
    public String productAddGet(Model model) {
        model.addAttribute("productDTO", new ProductDTO());
        model.addAttribute("categories", categoryService.getAllCategory());
        model.addAttribute("categoriesSize", categorySizeService.getAllCategorySize());
        return "productsAdd";
    }
    @PostMapping("/admin/products/add")
    public String productAddPost(@ModelAttribute("productDTO")ProductDTO productDTO,
                                 @RequestParam("productImage")MultipartFile file,
                                 @RequestParam("imgName")String imgName) throws IOException{
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setCategory(categoryService.getCategoryById(productDTO.getCategoryId()).get());
        product.setCategorySize(categorySizeService.getCategorySizeById(productDTO.getCategorySizeId()).get());
        product.setPrice(productDTO.getPrice());
        product.setRestock(productDTO.getRestock());
        product.setDescription(productDTO.getDescription());

        String imageUUID;
        if(!file.isEmpty()){
            imageUUID = file.getOriginalFilename();
            Path fileNameAndPath = Paths.get(uploadDir, imageUUID);
            Files.write(fileNameAndPath, file.getBytes());
        } else {
            imageUUID = imgName;
        }
        product.setImageName(imageUUID);
        productService.addProduct(product);

        return "redirect:/admin/products";
    }

    @GetMapping("admin/product/delete/{id}")
    public String deleteProduct(@PathVariable long id) {
        productService.removeProductById(id);
        return "redirect:/admin/products";
    }
    @GetMapping("admin/product/update/{id}")
    public String updateProductGet(@PathVariable long id, Model model) {
        Product product = productService.getProductById(id).get();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setCategoryId((product.getCategory().getId()));
        productDTO.setCategorySizeId(product.getCategorySize().getId());
        productDTO.setPrice(product.getPrice());
        productDTO.setRestock((product.getRestock()));
        //DOKOŃCZYĆ

        return "productsAdd";
    }
}