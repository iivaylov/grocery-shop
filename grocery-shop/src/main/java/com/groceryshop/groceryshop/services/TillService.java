package com.groceryshop.groceryshop.services;

import com.groceryshop.groceryshop.dtos.UserDTO;

public interface TillService {

    String calculateUserBill(UserDTO userDTO);
}
