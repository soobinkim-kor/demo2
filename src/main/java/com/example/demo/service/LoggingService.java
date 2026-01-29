package com.example.demo.service;

import com.example.demo.global.aspect.logging.dto.LogData;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Service;

@Service
public class LoggingService {
    public void saveSuccess(ProceedingJoinPoint jp, long start) {
        System.out.println("logged");
        // 로그 엔티티 생성 후 DB 저장
    }

    public void saveFail(ProceedingJoinPoint jp, Exception e, long start) {
        // 실패 로그 저장
    }

    public void save(LogData logData){
        System.out.println("logged");
    }
}
