package com.groceryshop.groceryshop.repositories;

import com.groceryshop.groceryshop.models.User;

import java.util.Optional;

public interface UserDAO {

    Optional<User> selectUserById(Integer userId);

    void insertUser(User user);

    void updateUser(User user);

    void deleteUser(User user);
}
