package com.mycollectionmanager.collectionmanager.repositories;

import com.mycollectionmanager.collectionmanager.domain.entities.Category;
import com.mycollectionmanager.collectionmanager.domain.entities.Figure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FigureRepository extends JpaRepository<Figure, Long> {
    public List<Figure> findAllByUserId(Long id);
}
