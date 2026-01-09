package com.example.demo.repository;

import com.example.demo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserBaseRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByUsrId(String usrId);

    Optional<UserEntity> findByUsrNo(Long usrNo);

    Optional<UserEntity> findByUsrIdAndUsrPwd(String usrId, String password);

    boolean existsByUsrId(String usrId);
}