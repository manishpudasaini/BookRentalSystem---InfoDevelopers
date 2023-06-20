package com.bookrentalsystem.bks.repo;

import com.bookrentalsystem.bks.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CategoryRepo extends JpaRepository<Category,Short> {
    Optional<Category> findCategoryByNameAndDeletedIsFalse(String name);
    @Query(nativeQuery = true,value = "select * from category where category.category_name=?1 and category.deleted=true")
    Optional<Category> findCategoryByNameAndDeletedIsTrue(String name);
}
