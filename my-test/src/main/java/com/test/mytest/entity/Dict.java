package com.test.mytest.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Objects;

@Data
public class Dict {


    @ExcelProperty("关键词1")
    private String keyword1;


    @ExcelProperty("关键词2")
    private String keyword2;

    @ExcelProperty("关键词3")
    private String keyword3;

    @ExcelProperty("标签值")
    private String tag;


}
