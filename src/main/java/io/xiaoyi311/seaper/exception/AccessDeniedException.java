package io.xiaoyi311.seaper.exception;

import io.xiaoyi311.seaper.model.ErrorData;

/**
 * 权限不足错误
 * @author Xiaoyi311
 * @apiDefine PermissionError
 * @apiError PermissionError 权限不足
 * @apiErrorExample {json} 错误-权限不足
 *  HTTP/1.1 403 Forbidden
 *  {
 *      "status":403,
 *      "time":0,
 *      "data":"Permission Denied!"
 *  }
 */
public class AccessDeniedException extends BaseException{
    /**
     * 错误数据
     */
    public AccessDeniedData errorInfo;

    /**
     * 实例化
     * @param permissions 需要的权限
     */
    public AccessDeniedException(String[] permissions){
        this.errorInfo = new AccessDeniedData(permissions);
    }

    /**
     * 权限不足错误数据
     */
    public static class AccessDeniedData{
        /**
         * 需要的权限
         */
        public String[] permissions;

        /**
         * 错误信息
         */
        public String msg = ErrorData.C403.data;

        /**
         * 实例化
         * @param permissions 错误权限
         */
        public AccessDeniedData(String[] permissions){
            this.permissions = permissions;
        }
    }
}
