package com.mycollectionmanager.collectionmanager.mappers.user;

import com.mycollectionmanager.collectionmanager.domain.dto.user.UserDTO;
import com.mycollectionmanager.collectionmanager.domain.entities.User;
import com.mycollectionmanager.collectionmanager.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements Mapper<User, UserDTO> {

    private final ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        // Setting to allow map without the password token.
        // https://modelmapper.org/user-manual/configuration/#matching-strategies
        this.modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
    }

    /**
     * Maps an User object to a UserDTO object
     * @param user The User object that will be mapped.
     * @return a UserDTO object, which was mapped from the User object.
     */
    public UserDTO mapTo(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    /**
     * Maps an UserDTO object to a User Object
     * @param userDTO the user object that will be mapped.
     * @return a User object, which contains was mapped from the UserDTO object.
     */
    public User mapFrom(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }
}
