package com.example.demo.entity;

import jakarta.persistence.Column;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class BaseEntity {
    @Column(name = "SYS_REG_DTM")
    @CreatedDate
    private LocalDateTime sysRegDtm;

    @Column(name = "SYS_UPD_DTM")
    @DateTimeFormat
    private LocalDateTime sysUpdDtm;

    @Column(name = "SYS_DEL_DTM")
    @DateTimeFormat
    private LocalDateTime sysDelDtm;

    @Column(name = "SYS_DEL_YN")
    @DateTimeFormat
    private Boolean sysDelYn;
}
