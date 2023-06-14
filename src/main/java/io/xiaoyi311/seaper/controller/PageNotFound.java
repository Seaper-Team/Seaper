package io.xiaoyi311.seaper.controller;

import io.xiaoyi311.seaper.model.ErrorData;
import io.xiaoyi311.seaper.model.ResponseData;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 页面未找到错误
 * @author Xiaoyi311
 */
@Controller
public class PageNotFound implements ErrorController {
    /**
     * 错误捕获
     */
    @RequestMapping(value = {"/error"})
    @ResponseBody
    public ResponseData<String> error(){
        return new ResponseData<>(404, ErrorData.C404.data);
    }
}
