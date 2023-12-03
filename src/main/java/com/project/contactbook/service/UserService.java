package com.project.contactbook.service;

import com.project.contactbook.dto.AuthDTO;
import com.project.contactbook.dto.UserDTO;

public interface UserService {
    UserDTO registerUser(UserDTO userDTO);
    AuthDTO login(UserDTO userDTO);
}
