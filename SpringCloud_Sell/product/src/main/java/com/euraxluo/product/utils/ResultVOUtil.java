package com.euraxluo.product.utils;

import com.euraxluo.product.VO.ResultVO;

/**
 * product
 * com.euraxluo.product.utils
 * ResultUtil
 * 2020/6/14 18:41
 * author:Euraxluo
 * TODO
 */
public class ResultVOUtil {

    public static ResultVO success(Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setData(object);
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        return resultVO;
    }
}