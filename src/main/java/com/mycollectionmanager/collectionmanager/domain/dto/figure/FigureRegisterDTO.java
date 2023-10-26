package com.mycollectionmanager.collectionmanager.domain.dto.figure;

import com.mycollectionmanager.collectionmanager.domain.dto.user.UserDetailDTO;
import com.mycollectionmanager.collectionmanager.domain.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FigureRegisterDTO {
    private Long id;
    private String name;
    private String image;
    private List<Category> categories;
    private UserDetailDTO user;
}
