package com.mycollectionmanager.collectionmanager.domain.dto.user;

import com.mycollectionmanager.collectionmanager.domain.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private Role role;
}
