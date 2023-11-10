package com.test.mytest2.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.test.mytest2.entity.Goods;
import com.test.mytest2.mapper.GoodsMapper;
import com.test.mytest2.service.GoodsService;
import org.springframework.stereotype.Service;

/**
 *
 */

@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {

}


