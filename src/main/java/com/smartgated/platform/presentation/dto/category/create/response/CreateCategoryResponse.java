package com.smartgated.platform.presentation.dto.category.create.response;

import java.util.UUID;

public class CreateCategoryResponse {
    private UUID categoryId;
    private String categoryName;

    public UUID getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    
}
