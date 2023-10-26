package com.mycollectionmanager.collectionmanager.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAuthTokenDTO {
    private Long id;
    private String username;
    private String token;
}
