package com.groceryshop.groceryshop.services.mappers;

import com.groceryshop.groceryshop.dtos.UserDTO;
import com.groceryshop.groceryshop.models.User;

import java.util.function.Function;

public class UserDTOMapper implements Function<User, UserDTO> {
    @Override
    public UserDTO apply(User user) {
        return new UserDTO(
                user.getId()
        );
    }
}
