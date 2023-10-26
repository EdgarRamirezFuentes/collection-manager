package com.mycollectionmanager.collectionmanager.repositories;

import com.mycollectionmanager.collectionmanager.domain.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    public List<Category> findAllByUserId(Long id);

    public List<Category> findAllByNameContainingAndUserId(String name, Long id);
}
