package io.xiaoyi311.seaper.exception;

/**
 * 请求无法完成错误
 * @author Xiaoyi311
 */
public class BadRequestException extends BaseException{
    /**
     * 实例化
     * @param errorInfo 拒绝原因
     */
    public BadRequestException(String errorInfo){
        this.data = errorInfo;
    }
}
