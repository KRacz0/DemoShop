package com.aixo.demoshop.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class CategorySize {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "category_size_id")
    private int id;
    private String name;
}
