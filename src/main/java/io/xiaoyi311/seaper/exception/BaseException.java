package io.xiaoyi311.seaper.exception;

/**
 * 错误基类
 * @author Xiaoyi311
 */
public class BaseException extends Exception {
    /**
     * 错误数据
     */
    public Object data;

    /**
     * 错误码
     */
    public Integer status = 400;
}
