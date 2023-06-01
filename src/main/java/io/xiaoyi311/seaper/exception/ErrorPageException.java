package io.xiaoyi311.seaper.exception;

import io.xiaoyi311.seaper.service.LangManager;

/**
 * 页码错误
 * @author Xiaoyi311
 */
public class ErrorPageException extends BaseException{
    /**
     * 实例化
     * @param page 目前页码
     * @param total 总共页码
     */
    public ErrorPageException(Integer page, Integer total){
        this.data = new ErrorPageData(page, total);
    }

    /**
     * 页码错误信息
     */
    public static class ErrorPageData{
        /**
         * 目前页码
         */
        public Integer page;

        /**
         * 总页数
         */
        public Integer total;

        /**
         * 错误信息
         */
        public String msg = LangManager.msg("other.pageNotFound");

        /**
         * 实例化
         * @param page
         * @param total
         */
        public ErrorPageData(Integer page, Integer total){
            this.page = page;
            this.total = total;
        }
    }
}
