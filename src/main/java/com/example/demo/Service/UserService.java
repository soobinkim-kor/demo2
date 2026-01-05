package com.example.demo.Service;

import com.example.demo.DTO.UserDTO;
import com.example.demo.Entity.UserBase;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    void saveUser(UserBase user);
    UserBase getUserByUsrId(UserDTO userDTO);
    UserBase getUserByUsrNo(UserDTO userDTO);
}
