package io.xiaoyi311.seaperservermanager.model;

import lombok.Data;

/**
 * 统一返回数据格式
 * @author Xiaoyi311
 * @apiDefine ResData
 * @apiSuccess {number} status 状态码, 正常为 200
 * @apiSuccess {number} time 时间戳, 为返回时间
 */
@Data
public class ResponseData<T> {
    /**
     * 状态码
     */
    public int status;
    /**
     * 时间戳
     */
    public long time;
    /**
     * 数据
     */
    public T data;

    /**
     * 创建返回数据
     * @param status 状态码
     * @param data 数据
     */
    public ResponseData(int status, T data) {
        this.status = status;
        this.time = System.currentTimeMillis();
        this.data = data;
    }
}