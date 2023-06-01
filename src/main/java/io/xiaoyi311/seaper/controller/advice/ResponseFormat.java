package io.xiaoyi311.seaper.controller.advice;

import io.xiaoyi311.seaper.exception.AccessDeniedException;
import io.xiaoyi311.seaper.exception.BadRequestException;
import io.xiaoyi311.seaper.exception.BaseException;
import io.xiaoyi311.seaper.model.ErrorData;
import io.xiaoyi311.seaper.model.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 返回数据统一格式
 * @author Xiaoyi311
 */
@RestControllerAdvice
@Slf4j
public class ResponseFormat implements ResponseBodyAdvice<Object> {
    /**
     * 判断是否转换数据
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    /**
     * 重写数据
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        //状态码矫正
        if(body instanceof ResponseData<?>){
            response.setStatusCode(HttpStatusCode.valueOf(((ResponseData<?>) body).getStatus()));
            return body;
        }

        return new ResponseData<>(200, body);
    }

    /**
     * 请求拒绝处理
     */
    @ExceptionHandler(value = BadRequestException.class)
    @ResponseBody
    public ResponseData<String> badRequest(BadRequestException e){
        return new ResponseData<>(400, e.data.toString());
    }

    /**
     * 权限错误处理
     */
    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseBody
    public ResponseData<AccessDeniedException.AccessDeniedData> permissionDenied(AccessDeniedException e){
        return new ResponseData<>(403, e.errorInfo);
    }

    /**
     * 请求方式错误处理
     */
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public ResponseData<String> permissionDenied(){
        return new ResponseData<>(405, ErrorData.C405.data);
    }

    /**
     * 页面未找到错误处理
     */
    @ExceptionHandler(value = NoHandlerFoundException.class)
    @ResponseBody
    public ResponseData<String> pageNotFound(){
        return new ResponseData<>(404, ErrorData.C404.data);
    }

    /**
     * 其他程序正常错误处理
     */
    @ExceptionHandler(BaseException.class)
    @ResponseBody
    public ResponseData<Object> otherAppException(BaseException e){
        return new ResponseData<>(200, e.data);
    }

    /**
     * 其他错误处理
     */
    @ExceptionHandler
    @ResponseBody
    public ResponseData<String> otherException(Exception e){
        log.error(e.getMessage());
        e.printStackTrace();
        return new ResponseData<>(500, e.getMessage());
    }
}
