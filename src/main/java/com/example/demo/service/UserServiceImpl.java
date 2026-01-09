package com.example.demo.service;


import com.example.demo.dto.user.UserDTO;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.UserBaseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserBaseRepository userBaseRepository;

    @Override
    public void saveUser(UserEntity user) {
        userBaseRepository.save(user);
    }

    @Override
    public Optional<UserEntity> getUserByUsrId(UserDTO userDTO) {
        return userBaseRepository.findByUsrId(userDTO.getUsrId());
    }

    @Override
    public Optional<UserEntity> getUserByUsrNo(UserDTO userDTO) {
        return userBaseRepository.findByUsrNo(userDTO.getUsrNo());
    }
}
