package com.mycollectionmanager.collectionmanager.services;

import com.mycollectionmanager.collectionmanager.domain.entities.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    public List<Category> findAll();
    public Optional<Category> findById(Long id);
    public List<Category> findAllByUserId(Long id);
    public List<Category> findAllByNameContainingAndUserId(String name, Long id);
    public Category save(Category category);
    public boolean existsById(Long id);
    public void delete(Long id);
}
