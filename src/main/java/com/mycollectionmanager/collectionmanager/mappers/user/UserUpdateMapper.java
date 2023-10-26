package com.mycollectionmanager.collectionmanager.mappers.user;

import com.mycollectionmanager.collectionmanager.domain.dto.user.UserUpdateUsernameDTO;
import com.mycollectionmanager.collectionmanager.domain.entities.User;
import com.mycollectionmanager.collectionmanager.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class UserUpdateMapper implements Mapper<User, UserUpdateUsernameDTO> {

    private final ModelMapper modelMapper;

    public UserUpdateMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        // Setting to allow map without the password token.
        // https://modelmapper.org/user-manual/configuration/#matching-strategies
        this.modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
    }

    @Override
    public UserUpdateUsernameDTO mapTo(User user) {
        return modelMapper.map(user, UserUpdateUsernameDTO.class);
    }

    @Override
    public User mapFrom(UserUpdateUsernameDTO userUpdateUsernameDTO) {
        return modelMapper.map(userUpdateUsernameDTO, User.class);
    }
}
