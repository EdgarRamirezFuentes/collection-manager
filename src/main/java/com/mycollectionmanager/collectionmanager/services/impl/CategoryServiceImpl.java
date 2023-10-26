package com.mycollectionmanager.collectionmanager.services.impl;

import com.mycollectionmanager.collectionmanager.domain.entities.Category;
import com.mycollectionmanager.collectionmanager.repositories.CategoryRepository;
import com.mycollectionmanager.collectionmanager.services.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Category> findAllByUserId(Long id) {
        return categoryRepository.findAllByUserId(id);
    }

    @Override
    public List<Category> findAllByNameContainingAndUserId(String name, Long id) {
        return categoryRepository.findAllByNameContainingAndUserId(name, id);
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return categoryRepository.existsById(id);
    }

    @Override
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}
