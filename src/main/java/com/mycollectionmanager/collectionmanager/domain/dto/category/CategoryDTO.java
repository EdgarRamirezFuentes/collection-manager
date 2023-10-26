package com.mycollectionmanager.collectionmanager.domain.dto.category;

import com.mycollectionmanager.collectionmanager.domain.entities.User;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDTO {
    private Long id;
    private String name;
    private User user;
}
