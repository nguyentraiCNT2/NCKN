package com.nckhntu.eventunivercity_v2_be.Service.IMPL;

import com.nckhntu.eventunivercity_v2_be.Entity.Category;
import com.nckhntu.eventunivercity_v2_be.Model.DTO.CategoryDTO;
import com.nckhntu.eventunivercity_v2_be.Repository.CategoryRepository;
import com.nckhntu.eventunivercity_v2_be.Service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceIMPL implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryServiceIMPL(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CategoryDTO> getAllCategories(Pageable pageable) {
        try {
            List<CategoryDTO> categoryDTOS = new ArrayList<>();
            List<Category> categoryList = categoryRepository.findAll(pageable).getContent();
            if (categoryList == null)
                throw new RuntimeException("Không có thể loại nào");
            categoryList.stream()
                    .forEach(category -> categoryDTOS.add(modelMapper.map(category, CategoryDTO.class)));
            return categoryDTOS;
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        try {
            List<CategoryDTO> categoryDTOS = new ArrayList<>();
            List<Category> categoryList = categoryRepository.findAll();
            if (categoryList == null)
                throw new RuntimeException("Không có thể loại nào");
            categoryList.stream()
                    .forEach(category -> categoryDTOS.add(modelMapper.map(category, CategoryDTO.class)));
            return categoryDTOS;
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<CategoryDTO> getCategoriesByName(String categoryName, Pageable pageable) {
        try {
            List<CategoryDTO> categoryDTOS = new ArrayList<>();
            String name = "%" + categoryName + "%";
            List<Category> categoryList = categoryRepository.findByName(name,pageable);
            if (categoryList != null)
                throw new RuntimeException("Không tìm thấy kết quả phù hợp");
            categoryList.stream()
                    .forEach(category -> categoryDTOS.add(modelMapper.map(category, CategoryDTO.class)));
            return categoryDTOS;
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public CategoryDTO getCategoryById(Long id) {
        try {
            Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy thể loại có id là "+id));
            return modelMapper.map(category, CategoryDTO.class);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void createCategory(CategoryDTO categoryDTO) {
        try {
            Category category = modelMapper.map(categoryDTO, Category.class);
            category.setCreatedAt(Timestamp.from(Instant.now()));
            categoryRepository.save(category);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void updateCategory(CategoryDTO categoryDTO) {
        try {
            Category category = categoryRepository.findById(categoryDTO.getId()).orElseThrow(() -> new RuntimeException("Không tìm thấy thể loại có id là "+categoryDTO.getId()));
                category.setName(categoryDTO.getName());
                category.setDescription(categoryDTO.getDescription());
            category.setUpdatedAt(Timestamp.from(Instant.now()));
            categoryRepository.save(category);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void deleteCategory(Long id) {
        try {
            Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy thể loại có id là "+id));
            categoryRepository.delete(category);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public int totalCategories() {
        return (int) categoryRepository.count();
    }

}
