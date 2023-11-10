package com.test.mytest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyTestApplication {
    //本项目在csvAnalyze中加载，修改文件打开路径为相对路径
    public static void main(String[] args) {
        SpringApplication.run(MyTestApplication.class, args);
    }

}
