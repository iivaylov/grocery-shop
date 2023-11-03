package com.groceryshop.groceryshop.services.mappers;

import com.groceryshop.groceryshop.dtos.UserDTO;
import com.groceryshop.groceryshop.models.User;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserDTOMapper implements Function<User, UserDTO> {
    @Override
    public UserDTO apply(User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName()
        );
    }
}
