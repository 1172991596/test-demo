package com.test.mytest2.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 商品
 * @TableName goods
 */
@TableName(value ="goods")
@Data
public class Goods implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 标题
     */
    private Long batch_no;

    /**
     * 详情
     */
    private String platform;

    /**
     * 地址，存储到oss
     */
    private String page_url;

    /**
     * 封面地址，存储到oss
     */
    private Long sku_id;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}