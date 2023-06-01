package io.xiaoyi311.seaper.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 页面数据工具
 * @author Xiaoyi311
 */
public class PageUtil {
    /**
     * 获取总页数
     * @param show 一个页面展示多少元素
     * @param data 数据集合
     * @return 总页数
     */
    public static <T> Integer total(Integer show, T[] data){
        return (int) Math.ceil((double) data.length / show);
    }

    /**
     * 获取页面数据
     * @param page 要查看的页面
     * @param show 一个页面展示多少元素
     * @param data 数据集合
     * @return 最终数据，如果页面不存在返回 Null
     */
    public static <T> List<T> get(Integer page, Integer show, T[] data){
        //判断页数是否满足
        if(total(show, data) < page){
            return null;
        }

        //获取基本数值、索引
        int overIndex = Math.min(page * show, data.length);
        int startIndex = show * (page - 1);

        //返回最终数据
        return new ArrayList<>(Arrays.asList(data).subList(startIndex, overIndex));
    }
}
