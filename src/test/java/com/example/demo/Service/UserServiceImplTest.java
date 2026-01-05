package com.example.demo.Service;

import com.example.demo.DTO.UserDTO;
import com.example.demo.Entity.UserBase;
import com.example.demo.Repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void getUserByUsrId() {
        // given
        UserDTO userDTO = UserDTO.builder().usrId("shb03207").build();

        UserBase mockUser = UserBase.builder().usrId("shb03207").usrNm("수빈").build();

        when(userRepository.findByUsrId("shb03207")).thenReturn(mockUser);

        // when
        UserBase result = userService.getUserByUsrId(userDTO);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getUsrId()).isEqualTo("shb03207");
        assertThat(result.getUsrNm()).isEqualTo("수빈");

        // repository 호출 검증
        verify(userRepository, times(1)).findByUsrId("shb03207");
    }
}