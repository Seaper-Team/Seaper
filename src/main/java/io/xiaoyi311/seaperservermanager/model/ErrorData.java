package io.xiaoyi311.seaperservermanager.model;

import io.xiaoyi311.seaperservermanager.service.LangManager;

/**
 * 错误数据
 * @author Xiaoyi311
 */
public enum ErrorData{
    /**
     * 权限不足
     */
    C403(LangManager.msg("errorStatus.403")),
    /**
     * 页面未找到
     */
    C404(LangManager.msg("errorStatus.404")),

    /**
     * 请求不支持
     */
    C405(LangManager.msg("errorStatus.405"));

    /**
     * 实例化
     */
    ErrorData(String data){
        this.data = data;
    }

    /**
     * 错误信息
     */
    public final String data;
}
