package com.groceryshop.groceryshop.services.mappers;

import com.groceryshop.groceryshop.dtos.UserDTO;
import com.groceryshop.groceryshop.models.UserEntity;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserDTOMapper implements Function<UserEntity, UserDTO> {
    @Override
    public UserDTO apply(UserEntity userEntity) {
        return new UserDTO(
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getFirstName(),
                userEntity.getLastName()
        );
    }
}
