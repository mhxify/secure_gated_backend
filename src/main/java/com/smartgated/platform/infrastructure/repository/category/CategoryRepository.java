package com.smartgated.platform.infrastructure.repository.category;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartgated.platform.domain.model.category.Category;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    Category findByCategoryName(String categoryName);
}
