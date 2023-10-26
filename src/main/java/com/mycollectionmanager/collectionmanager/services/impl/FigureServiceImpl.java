package com.mycollectionmanager.collectionmanager.services.impl;

import com.mycollectionmanager.collectionmanager.domain.dto.figure.FigureDTO;
import com.mycollectionmanager.collectionmanager.domain.entities.Category;
import com.mycollectionmanager.collectionmanager.domain.entities.Figure;
import com.mycollectionmanager.collectionmanager.repositories.FigureRepository;
import com.mycollectionmanager.collectionmanager.services.CategoryService;
import com.mycollectionmanager.collectionmanager.services.FigureService;
import com.mycollectionmanager.collectionmanager.services.UserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FigureServiceImpl implements FigureService {

    private final FigureRepository figureRepository;
    private final UserService userService;
    private final CategoryService categoryService;

    public FigureServiceImpl(FigureRepository figureRepository, UserService userService, CategoryService categoryService) {
        this.figureRepository = figureRepository;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @Override
    public List<Figure> findAll() {
        return figureRepository.findAll();
    }

    @Override
    @Transactional
    public Figure save(FigureDTO figure) {
        return null;
    }

    private List<Category> getCategories(List<Long> categoryIds) {
        List<Category> categories = new ArrayList<>();
        for (Long categoryId : categoryIds) {
            Optional<Category> category = categoryService.findById(categoryId);
            if (category.isPresent()) {
                categories.add(category.get());
            } else {
                throw new RuntimeException("Invalid categories");
            }
        }
        return categories;
    }

    @Override
    public Figure findById(Long id) {
        Optional<Figure> requestFigure = figureRepository.findById(id);

        return requestFigure.orElse(null);
    }

    @Override
    public boolean existsById(Long id) {
        return figureRepository.existsById(id);
    }

    @Override
    public void delete(Long id) {
        figureRepository.deleteById(id);
    }
}
