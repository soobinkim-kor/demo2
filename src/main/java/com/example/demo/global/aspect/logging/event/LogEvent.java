package com.example.demo.global.aspect.logging.event;

import com.example.demo.global.aspect.logging.dto.LogData;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogEvent {

    private LogData logData;
}
