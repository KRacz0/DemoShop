package com.aixo.demoshop.dto;

import com.aixo.demoshop.model.Category;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class ProductDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private int categoryId;
    private int categorySizeId;
    private double price;
    private double restock;
    private String description;
    private String imageName;
}
