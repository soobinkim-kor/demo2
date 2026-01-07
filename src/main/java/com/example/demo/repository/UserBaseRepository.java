package com.example.demo.repository;

import com.example.demo.entity.UserBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserBaseRepository extends JpaRepository<UserBase,Long> {
    Optional<UserBase> findByUsrId(String usrId);

    Optional<UserBase> findByUsrNo(Long usrNo);

    Optional<UserBase> findByUsrIdAndUsrPwd(String usrId, String password);

    boolean existsByUsrId(String usrId);
}