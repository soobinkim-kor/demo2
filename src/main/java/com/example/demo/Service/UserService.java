package com.example.demo.Service;

import com.example.demo.DTO.UserDTO;
import com.example.demo.Entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    void saveUser(User user);
    User getUserByUsrId(UserDTO userDTO);
}
