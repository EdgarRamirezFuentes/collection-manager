package com.mycollectionmanager.collectionmanager.mappers.user;

import com.mycollectionmanager.collectionmanager.domain.dto.user.UserDetailDTO;
import com.mycollectionmanager.collectionmanager.domain.entities.User;
import com.mycollectionmanager.collectionmanager.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class UserDetailMapper implements Mapper<User, UserDetailDTO> {

    private final ModelMapper modelMapper;

    public UserDetailMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        // Setting to allow map without the password token.
        // https://modelmapper.org/user-manual/configuration/#matching-strategies
        this.modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
    }


    @Override
    public UserDetailDTO mapTo(User user) {
        return modelMapper.map(user, UserDetailDTO.class);
    }

    @Override
    public User mapFrom(UserDetailDTO userDetailDTO) {
        return modelMapper.map(userDetailDTO, User.class);
    }
}
