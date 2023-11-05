package com.groceryshop.groceryshop.repositories;

import com.groceryshop.groceryshop.models.UserEntity;

import java.util.Optional;

public interface UserDAO {

    Optional<UserEntity> selectUserById(int userId);

    Optional<UserEntity> selectUserByUsername(String username);

    void insertUser(UserEntity userEntity);

    void updateUser(UserEntity userEntity);

    void deleteUser(UserEntity userEntity);
}
