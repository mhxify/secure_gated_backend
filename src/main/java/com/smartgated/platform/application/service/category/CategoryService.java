package com.smartgated.platform.application.service.category;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.smartgated.platform.application.usecase.category.CategoryUseCase;
import com.smartgated.platform.domain.model.category.Category;
import com.smartgated.platform.infrastructure.repository.category.CategoryRepository;
import com.smartgated.platform.presentation.dto.category.create.request.CreateCategoryRequest;
import com.smartgated.platform.presentation.dto.category.create.response.CreateCategoryResponse;
import com.smartgated.platform.presentation.dto.category.get.GetCategory;
import com.smartgated.platform.presentation.dto.category.update.UpdateCategoryRequest;

@Service
public class CategoryService implements CategoryUseCase{

    private final CategoryRepository categoryRepository;
    
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void deleteCategory(UUID categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + categoryId));

        categoryRepository.deleteById(category.getCategoryId());
    }


    @Override
    public void updateCategory(UUID categoryId, UpdateCategoryRequest request) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + categoryId));

        category.setCategoryName(request.getCategoryName());
        categoryRepository.save(category);
    }

    @Override
    public CreateCategoryResponse createCategory(CreateCategoryRequest request) {
        Category category = new Category();
        category.setCategoryName(request.getCategoryName());
        category.setCreatedAt(LocalDateTime.now());

        Category savedCategory = categoryRepository.save(category);

        CreateCategoryResponse response = new CreateCategoryResponse();
        response.setCategoryId(savedCategory.getCategoryId());
        response.setCategoryName(savedCategory.getCategoryName());

        return response;
    }


    @Override
    public List<GetCategory> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(category -> {
                    GetCategory getCategory = new GetCategory();
                    getCategory.setCategoryId(category.getCategoryId());
                    getCategory.setCategoryName(category.getCategoryName());
                    return getCategory;
                })
                .collect(Collectors.toList());
    }


}
