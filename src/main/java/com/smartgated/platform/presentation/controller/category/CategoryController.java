package com.smartgated.platform.presentation.controller.category;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartgated.platform.application.usecase.category.CategoryUseCase;
import com.smartgated.platform.presentation.dto.category.create.request.CreateCategoryRequest;
import com.smartgated.platform.presentation.dto.category.create.response.CreateCategoryResponse;
import com.smartgated.platform.presentation.dto.category.get.GetCategory;
import com.smartgated.platform.presentation.dto.category.update.UpdateCategoryRequest;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    
    private final CategoryUseCase categoryUseCase;

    public CategoryController(CategoryUseCase categoryUseCase) {
        this.categoryUseCase = categoryUseCase;
    }

    @PostMapping
    public ResponseEntity<CreateCategoryResponse> createCategory(
        @RequestBody CreateCategoryRequest request
    ) {
        CreateCategoryResponse response = categoryUseCase.createCategory(request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{categoryId}")
    public ResponseEntity<Void> updateCategory(
        @PathVariable UUID categoryId,
        @RequestBody UpdateCategoryRequest request
    ) {
        categoryUseCase.updateCategory(categoryId, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID categoryId) {
        categoryUseCase.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/all")
    public ResponseEntity<List<GetCategory>> getAllCategories() {
        return ResponseEntity.ok(categoryUseCase.getAllCategories());
    }
    
}
