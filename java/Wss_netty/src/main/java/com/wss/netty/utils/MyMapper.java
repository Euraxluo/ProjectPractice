package com.wss.netty.utils;



import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * Wss_netty
 * com.wss.netty.utils
 * Mapper
 * 2019/10/11 19:37
 * author:Euraxluo
 * TODO
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
    //TODO
    //FIXME 特别注意，该接口不能被扫描到，否则会出错
}
