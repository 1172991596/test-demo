package com.test.mytest.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.test.mytest.entity.Dict;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import static com.alibaba.fastjson.JSON.*;

// 如果没有特殊说明，下面的案例将默认使用这个监听器
@Data
public class DictDataListener extends AnalysisEventListener<Dict> {

    List<Dict> list = new ArrayList<Dict>();

    /**
     * 如果使用了spring,请使用这个构造方法。每次创建Listener的时候需要把spring管理的类传进来
     */
    public DictDataListener() {}

    /**
     * 这个每一条数据解析都会来调用
     *
     * @param data
     * @param context
     */
    @Override
    public void invoke(Dict data, AnalysisContext context) {
        System.out.println("解析到一条数据:{}"+ JSON.toJSONString(data));
        list.add(data);
    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        System.out.println(JSON.toJSONString(list));
    }
}
