package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.UserBase;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {
    void saveUser(UserBase user);
    Optional<UserBase> getUserByUsrId(UserDTO userDTO);
    Optional<UserBase> getUserByUsrNo(UserDTO userDTO);
}
