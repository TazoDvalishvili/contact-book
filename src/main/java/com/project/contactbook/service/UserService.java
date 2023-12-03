package com.project.contactbook.service;

import com.project.contactbook.dto.AuthDTO;
import com.project.contactbook.dto.UserDTO;
import com.project.contactbook.model.User;

public interface UserService {
    UserDTO registerUser(UserDTO userDTO);
    AuthDTO login(UserDTO userDTO);

    User getCurrentUser();
}
