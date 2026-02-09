package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PrometheusTestController {

    @RequestMapping(  "/test/jvm")
    public void cpuTest() {
        List<byte[]> list = new ArrayList<>();
        while (true) {
            list.add(new byte[1024 * 1024]); // 1MB 계속 생성
        }
    }

    @RequestMapping(  "/test/npe")
    public void errorTest() {
        List<byte[]> list = null;
        list.get(0);
    }

    @RequestMapping("/test/gc")
    public void gcTest() {
        for (int i = 0; i < 1_000_000; i++) {
            new Object();
        }
    }
}