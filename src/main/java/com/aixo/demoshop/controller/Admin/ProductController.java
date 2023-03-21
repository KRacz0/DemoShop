package com.aixo.demoshop.controller.Admin;

import com.aixo.demoshop.dto.ProductDTO;
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

@Controller
public class ProductController {
    public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/productImages";
    @Autowired
    ProductService productService;

    @Autowired
    CategorySizeService categorySizeService;

    @Autowired
    CategoryService categoryService;

    @GetMapping("/admin/products")
    public String product(Model model) {
        model.addAttribute("products", productService.getAllProduct());
        return "products";
    }

    ///
    @GetMapping("/admin/products/add")
    public String productAddGet(Model model) {
        model.addAttribute("productDTO", new ProductDTO());
        model.addAttribute("categories", categoryService.getAllCategory());
        model.addAttribute("categoriesSize", categorySizeService.getAllCategorySize());
        return "productsAdd";
    }

    ///
    @PostMapping("/admin/products/add")
    public String productAddPost(@ModelAttribute("productDTO")ProductDTO productDTO,
                                 @RequestParam("productImage") MultipartFile file,
                                 @RequestParam("imgName")String imgName) throws IOException {
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

    ///
    @GetMapping("admin/product/delete/{id}")
    public String deleteProduct(@PathVariable long id) {
        productService.removeProductById(id);
        return "redirect:/admin/products";
    }

    ///
    @GetMapping("admin/product/update/{id}")
    public String updateProductGet(@PathVariable long id, Model model) {
        Product product = productService.getProductById(id).get();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setCategoryId((product.getCategory().getId()));
        productDTO.setCategorySizeId(product.getCategorySize().getId());
        productDTO.setPrice(product.getPrice());
        productDTO.setRestock(product.getRestock());
        productDTO.setDescription(product.getDescription());
        productDTO.setImageName(product.getImageName());

        model.addAttribute("categories", categoryService.getAllCategory());
        model.addAttribute("categoriesSize", categorySizeService.getAllCategorySize());
        model.addAttribute("productDTO", productDTO);

        return "productsAdd";
    }
}
