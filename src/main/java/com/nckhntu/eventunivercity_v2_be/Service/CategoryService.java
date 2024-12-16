package com.nckhntu.eventunivercity_v2_be.Service;

import com.nckhntu.eventunivercity_v2_be.Model.DTO.CategoryDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getAllCategories(Pageable pageable);
    List<CategoryDTO> getAllCategories();
    List<CategoryDTO> getCategoriesByName(String categoryName, Pageable pageable);
    CategoryDTO getCategoryById(Long id);
    void createCategory(CategoryDTO categoryDTO);
    void updateCategory(CategoryDTO categoryDTO);
    void deleteCategory(Long id);
    int totalCategories();
}
