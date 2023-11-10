package com.test.mytest2.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 返回给前端
 *
 */

@Data
public class GoodsVO implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * pageUrl
     */
    private String pageUrl;

    /**
     * batchNo
     */
    private Long batchNo;

    /**
     *  sku_id
     */
    private Long sku_id;

    /**
     * platform
     */
    private String platform;


}
