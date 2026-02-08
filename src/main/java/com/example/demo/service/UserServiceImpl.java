package com.example.demo.service;


import com.example.demo.dto.user.UserDTO;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public void saveUser(UserEntity user) {
        userRepository.save(user);
    }

    @Override
    public Optional<UserEntity> getUserByUsrId(UserDTO userDTO) {
        return userRepository.findByUsrId(userDTO.getUsrId());
    }

    @Override
    public Optional<UserEntity> getUserByUsrNo(UserDTO userDTO) {
        return userRepository.findByUsrNo(userDTO.getUsrNo());
    }
}
