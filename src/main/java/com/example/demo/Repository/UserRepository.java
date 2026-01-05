package com.example.demo.Repository;

import com.example.demo.Entity.UserBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserBase,Long> {
    UserBase findByUsrId(String usrId);

    UserBase findByUsrNo(Long usrNo);
}