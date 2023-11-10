package com.test.mytest2.task;

import com.alibaba.excel.EasyExcel;
import com.test.mytest2.entity.Goods;
import com.test.mytest2.listener.GoodsDataListener;
import com.test.mytest2.service.GoodsService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.annotation.Resource;
import java.util.List;

/**
 * 定时任务将excel转至数据库
 */
@Component
@EnableScheduling
public class ExcelToDBTask {


    @Resource
    private GoodsService goodsService;


    /**
     * 定时任务
     */
    @Scheduled(cron = "0/1200 * * * * ?")
    public void excelToDB() {
        System.out.println("定时任务开始");
        String fileName = "E:\\microserve\\oj-backend-microservice-master (2)\\my-test-2\\src\\main\\java\\com\\test\\mytest2\\file\\haman_2023102801.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        // 参数一：读取的excel文件路径
        // 参数二：读取sheet的一行，将参数封装在DemoData实体类中
        // 参数三：读取每一行的时候会执行DemoDataListener监听器
        GoodsDataListener listener = new GoodsDataListener();
        EasyExcel.read(fileName, Goods.class, listener).sheet().doRead();
        List<Goods> list = listener.getList();
        boolean b = goodsService.saveOrUpdateBatch(list);
        if (!b){
            System.out.println("插入失败！！！");
        }
    }



}
