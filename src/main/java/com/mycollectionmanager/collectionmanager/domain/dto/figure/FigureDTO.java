package com.mycollectionmanager.collectionmanager.domain.dto.figure;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FigureDTO {
    private Long id;
    private String name;
    private String image;
    private List<Long> categories;
    private Long user;
}
