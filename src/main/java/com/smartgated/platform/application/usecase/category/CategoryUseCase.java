package com.smartgated.platform.application.usecase.category;

import java.util.List;
import java.util.UUID;

import com.smartgated.platform.presentation.dto.category.create.request.CreateCategoryRequest;
import com.smartgated.platform.presentation.dto.category.create.response.CreateCategoryResponse;
import com.smartgated.platform.presentation.dto.category.get.GetCategory;
import com.smartgated.platform.presentation.dto.category.update.UpdateCategoryRequest;

public interface CategoryUseCase {

    CreateCategoryResponse createCategory(CreateCategoryRequest request);

    void updateCategory(UUID categoryId, UpdateCategoryRequest request);

    void deleteCategory(UUID categoryId);

    List<GetCategory> getAllCategories();
}
