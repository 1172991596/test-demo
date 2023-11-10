package com.test.mytest2.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.test.mytest2.common.BaseResponse;
import com.test.mytest2.common.ResultUtils;
import com.test.mytest2.entity.Goods;
import com.test.mytest2.model.GoodsVO;
import com.test.mytest2.service.GoodsService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.lang3.StringUtils;
import javax.annotation.Resource;
import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/breakPriceUrls")
public class GoodsController {

    @Resource
    private GoodsService goodsService;

    /**
     * 使用锁进行同步，可以使用redis的分布式锁，这里直接并发量不大，性能要求不高使用synchronized进行互斥
     * @param platform
     * @param count
     * @param batchNo
     * @return
     */
    @GetMapping("")
    public synchronized BaseResponse<List<GoodsVO>> getGoodsInfo(@RequestParam String platform, @RequestParam(defaultValue = "10") Long count, @RequestParam String batchNo) {
        if (StringUtils.isAnyBlank(platform, batchNo)) {
            return ResultUtils.error();
        }
        //Wrapper对象
        QueryWrapper goodsQueryWrapper = new QueryWrapper();
        goodsQueryWrapper.eq("platform", platform);
        goodsQueryWrapper.eq("batch_no", batchNo);
        Page<Goods> videoPage = goodsService.page(new Page<>(0, count),goodsQueryWrapper);
        List<Goods> goods = new ArrayList<>();
        for (Goods good : videoPage.getRecords()){
            goods.add(good);
        }
        //创建VO对象
        List<GoodsVO> goodsVOs = new ArrayList<>();
        for (Goods good : goods){
            GoodsVO goodsVO = new GoodsVO();
            goodsVO.setId(good.getId());
            goodsVO.setBatchNo(good.getBatch_no());
            goodsVO.setPageUrl(good.getPage_url());
            goodsVO.setPlatform(good.getPlatform());
            goodsVO.setSku_id(good.getSku_id());
            goodsVOs.add(goodsVO);
        }
        return ResultUtils.success(goodsVOs);
    }
}
