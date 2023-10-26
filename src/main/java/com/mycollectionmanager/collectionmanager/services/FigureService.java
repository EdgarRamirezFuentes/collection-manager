package com.mycollectionmanager.collectionmanager.services;

import com.mycollectionmanager.collectionmanager.domain.dto.figure.FigureDTO;
import com.mycollectionmanager.collectionmanager.domain.entities.Category;
import com.mycollectionmanager.collectionmanager.domain.entities.Figure;

import java.util.List;
import java.util.Optional;

public interface FigureService {
    public List<Figure> findAll();
    public Figure save(FigureDTO figure);
    public Figure findById(Long id);
    public boolean existsById(Long id);
    public void delete(Long id);
}
