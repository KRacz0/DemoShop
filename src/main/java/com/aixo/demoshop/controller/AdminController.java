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
    @GetMapping("/admin")
    public String adminHome() {
        return "adminHome";
    }
}