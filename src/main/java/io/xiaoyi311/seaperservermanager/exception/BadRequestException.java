package io.xiaoyi311.seaperservermanager.exception;

/**
 * 请求无法完成错误
 * @author Xiaoyi311
 */
public class BadRequestException extends Exception{
    /**
     * 拒绝信息
     */
    public String errorInfo;

    /**
     * 实例化
     * @param errorInfo 拒绝原因
     */
    public BadRequestException(String errorInfo){
        this.errorInfo = errorInfo;
    }
}
