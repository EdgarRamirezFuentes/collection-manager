package com.mycollectionmanager.collectionmanager.domain.dto.category;

import com.mycollectionmanager.collectionmanager.domain.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDetailDTO {
    private Long id;
    private String name;
}
