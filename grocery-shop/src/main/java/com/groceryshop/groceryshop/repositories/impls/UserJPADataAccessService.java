package com.groceryshop.groceryshop.repositories.impls;

import com.groceryshop.groceryshop.models.UserEntity;
import com.groceryshop.groceryshop.repositories.UserDAO;
import com.groceryshop.groceryshop.repositories.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserJPADataAccessService implements UserDAO {

    private final UserRepository userRepository;

    @Autowired
    public UserJPADataAccessService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<UserEntity> selectUserById(int userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Optional<UserEntity> selectUserByUsername(String username) {
        return userRepository.findAll()
                .stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
    }

    @Override
    public void insertUser(UserEntity userEntity) {
        userRepository.save(userEntity);
    }

    @Override
    public void updateUser(UserEntity userEntity) {
        userRepository.save(userEntity);
    }

    @Override
    public void deleteUser(UserEntity userEntity) {
        userRepository.delete(userEntity);
    }
}
