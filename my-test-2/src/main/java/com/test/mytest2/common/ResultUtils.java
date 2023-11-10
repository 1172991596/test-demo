package com.test.mytest2.common;

import com.test.mytest2.model.GoodsVO;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * 返回工具类
 *
 */
public class ResultUtils {

    /**
     * 成功
     *
     * @param data
     * @return
     */
    public static <T>BaseResponse<T> success(T data) {
        return new BaseResponse<>(data, true);
    }

    /**
     * 失败
     *
     * @param
     * @return
     */
    public static BaseResponse error() {
        return new BaseResponse<>(false);
    }

}
