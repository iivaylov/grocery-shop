package com.groceryshop.groceryshop.repositories.jpa;

import com.groceryshop.groceryshop.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
