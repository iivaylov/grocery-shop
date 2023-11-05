package com.groceryshop.groceryshop.repositories.jpa;

import com.groceryshop.groceryshop.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
}
