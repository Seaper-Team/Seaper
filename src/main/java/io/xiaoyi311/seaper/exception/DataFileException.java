package io.xiaoyi311.seaper.exception;

import lombok.extern.slf4j.Slf4j;

/**
 * 数据文件错误
 * @author Xiaoyi311-
 */
@Slf4j
public class DataFileException extends Exception{
    /**
     * 实例化
     */
    public DataFileException(String msg){
        super(msg);
    }
}
