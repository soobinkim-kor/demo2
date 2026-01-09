package com.example.demo.service;

import com.example.demo.dto.user.UserDTO;
import com.example.demo.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {
    void saveUser(UserEntity user);
    Optional<UserEntity> getUserByUsrId(UserDTO userDTO);
    Optional<UserEntity> getUserByUsrNo(UserDTO userDTO);
}
