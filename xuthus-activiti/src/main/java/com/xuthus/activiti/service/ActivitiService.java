package com.xuthus.activiti.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ActivitiService {
    public void activiti() {
        System.out.println("任务已经执行.....................................");
    }

    public List<String> user() {
        return Arrays.asList("xiaoming", "xiaohong");
    }
}
