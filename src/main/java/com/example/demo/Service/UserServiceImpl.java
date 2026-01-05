package com.example.demo.Service;


import com.example.demo.DTO.UserDTO;
import com.example.demo.Entity.UserBase;
import com.example.demo.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public void saveUser(UserBase user) {
        userRepository.save(user);
    }

    @Override
    public UserBase getUserByUsrId(UserDTO userDTO) {
        return userRepository.findByUsrId(userDTO.getUsrId());
    }

    @Override
    public UserBase getUserByUsrNo(UserDTO userDTO) {
        return userRepository.findByUsrNo(userDTO.getUsrNo());
    }
}
