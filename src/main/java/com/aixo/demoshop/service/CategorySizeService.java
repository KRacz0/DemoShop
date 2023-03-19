package com.aixo.demoshop.service;

import com.aixo.demoshop.model.CategorySize;
import com.aixo.demoshop.repository.CategorySizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategorySizeService {
    @Autowired
    CategorySizeRepository categorySizeRepository;
    public List<CategorySize> getAllCategorySize(){
        return categorySizeRepository.findAll();
    }
    public void addCategorySize(CategorySize categorySize){
        categorySizeRepository.save(categorySize);
    }
    public void removeCategorySizeById(int id){
        categorySizeRepository.deleteById(id);
    }
    public Optional<CategorySize> getCategorySizeById(int id){
        return categorySizeRepository.findById(id);
    }
}
