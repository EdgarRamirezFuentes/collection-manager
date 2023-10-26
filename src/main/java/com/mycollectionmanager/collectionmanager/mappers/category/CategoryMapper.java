package com.mycollectionmanager.collectionmanager.mappers.category;

import com.mycollectionmanager.collectionmanager.domain.dto.category.CategoryDTO;
import com.mycollectionmanager.collectionmanager.domain.entities.Category;
import com.mycollectionmanager.collectionmanager.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper implements Mapper<Category, CategoryDTO> {

    private final ModelMapper modelMapper;

    public CategoryMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * Map a Category object to a CategoryDTO object.
     * @param category The Category object that will be mapped.
     * @return a CategoryDTO object, which was mapped from the Category object.
     */
    @Override
    public CategoryDTO mapTo(Category category) {
        return modelMapper.map(category, CategoryDTO.class);
    }

    /**
     * Map a CategoryDTO object to a Category object.
     * @param categoryDTO The Category object that will be mapped.
     * @return a Category object, which was mapped from the CategoryDTO object.
     */
    @Override
    public Category mapFrom(CategoryDTO categoryDTO) {
        return modelMapper.map(categoryDTO, Category.class);
    }
}
