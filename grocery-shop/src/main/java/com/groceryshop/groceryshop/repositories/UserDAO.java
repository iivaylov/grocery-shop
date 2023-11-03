package com.groceryshop.groceryshop.repositories;

import com.groceryshop.groceryshop.models.User;

import java.util.Optional;

public interface UserDAO {

    Optional<User> selectUserById(int userId);

    Optional<User> selectUserByUsername(String username);

    void insertUser(User user);

    void updateUser(User user);

    void deleteUser(User user);
}
