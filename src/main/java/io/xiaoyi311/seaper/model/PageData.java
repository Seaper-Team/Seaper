package io.xiaoyi311.seaper.model;

import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 页面数据
 * @author Xiaoyi311
 * @apiDefine PageData
 * @apiParam {Number} [show] 每页展示几个数据，默认为 8
 * @apiParam {Number} [page] 查看第几页，默认第一页
 */
@Data
@NoArgsConstructor
public class PageData {
    /**
     * 总页面
     */
    public Integer total;

    /**
     * 当前页面
     */
    public Integer page;

    /**
     * 数据
     */
    public List<?> data;

    /**
     * 实例化
     */
    public PageData(Integer total, Integer page, List<?> data){
        this.total = total;
        this.page = page;
        this.data = data;
    }

    /**
     * 页面数据请求格式
     */
    @Data
    @NoArgsConstructor
    public static class ReqPageData {
        /**
         * 一页展示多少个数据
         */
        @Min(value = 1, message = "other.pageShowTooSmall")
        public Integer show = 8;

        /**
         * 查看第几页
         */
        @Min(value = 1, message = "other.pageTooSmall")
        public Integer page = 1;
    }
}
