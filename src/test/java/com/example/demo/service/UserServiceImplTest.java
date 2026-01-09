package com.example.demo.service;

import com.example.demo.dto.user.UserDTO;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.UserBaseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserBaseRepository userBaseRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void getUserByUsrId() {
        // given
        UserDTO userDTO = UserDTO.builder().usrId("shb03207").build();

        UserEntity mockUser = UserEntity.builder().usrId("shb03207").usrNm("수빈").build();

        when(userBaseRepository.findByUsrId("shb03207")).thenReturn(Optional.ofNullable(mockUser));

        // when
        UserEntity result = userService.getUserByUsrId(userDTO).orElse(null);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getUsrId()).isEqualTo("shb03207");
        assertThat(result.getUsrNm()).isEqualTo("수빈");

        // repository 호출 검증
        verify(userBaseRepository, times(1)).findByUsrId("shb03207");
    }
}