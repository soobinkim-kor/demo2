package com.example.demo.service;


import com.example.demo.dto.UserDTO;
import com.example.demo.entity.UserBase;
import com.example.demo.repository.UserBaseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserBaseRepository userBaseRepository;

    @Override
    public void saveUser(UserBase user) {
        userBaseRepository.save(user);
    }

    @Override
    public Optional<UserBase> getUserByUsrId(UserDTO userDTO) {
        return userBaseRepository.findByUsrId(userDTO.getUsrId());
    }

    @Override
    public Optional<UserBase> getUserByUsrNo(UserDTO userDTO) {
        return userBaseRepository.findByUsrNo(userDTO.getUsrNo());
    }
}
