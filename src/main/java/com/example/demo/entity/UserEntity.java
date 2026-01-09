package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners( value = AuditingEntityListener.class)
@Table(name = "USER_BASE")
public class UserEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USR_NO", nullable = false)
    private Long usrNo;

    @Column(name = "USR_ID", nullable = false)
    private String usrId;

    @Column(name = "USR_NM", nullable = false)
    private String usrNm;

    @Column(name = "USR_PWD")
    private String usrPwd;

    @Column(name = "USR_ROLE")
    private String role;

    @Column(name = "USR_EMAIL")
    private String usrEmail;

    @Column(name = "USR_BIRTH")
    private Date usrBirth;

    @Column(name = "USR_PWD_UPD_DTM")
    private Date usrPwdUpdDtm;
}
