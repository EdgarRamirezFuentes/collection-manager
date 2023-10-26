package com.mycollectionmanager.collectionmanager.domain.dto.figure;

import com.mycollectionmanager.collectionmanager.domain.dto.category.CategoryDetailDTO;
import com.mycollectionmanager.collectionmanager.domain.dto.user.UserDetailDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FigureDetailDTO {
    private Long id;
    private String name;
    private String image;
    private List<CategoryDetailDTO> categories;
    private UserDetailDTO user;
}
