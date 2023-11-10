package com.test.mytest2;

import com.alibaba.excel.EasyExcel;
import com.test.mytest2.entity.Goods;
import com.test.mytest2.listener.GoodsDataListener;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MyTest2ApplicationTests {

    @Test
    void contextLoads() {

        String fileName = "E:\\microserve\\oj-backend-microservice-master (2)\\my-test-2\\src\\main\\java\\com\\test\\mytest2\\file\\haman_2023102801.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        // 参数一：读取的excel文件路径
        // 参数二：读取sheet的一行，将参数封装在DemoData实体类中
        // 参数三：读取每一行的时候会执行DemoDataListener监听器
        EasyExcel.read(fileName, Goods.class, new GoodsDataListener()).sheet().doRead();

    }

}
