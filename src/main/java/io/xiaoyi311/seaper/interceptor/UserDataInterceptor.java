package io.xiaoyi311.seaper.interceptor;

import io.xiaoyi311.seaper.annotation.UserData;
import io.xiaoyi311.seaper.exception.DataFileException;
import io.xiaoyi311.seaper.service.UserManager;
import jakarta.servlet.http.HttpSession;
import org.apache.catalina.User;
import org.apache.catalina.connector.RequestFacade;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 用户数据获取注解处理拦截器
 * @author Xiaoyi311
 */
@Component
public class UserDataInterceptor implements HandlerMethodArgumentResolver {
    /**
     * 是否注解匹配
     * @return 是否匹配
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(UserData.class);
    }

    /**
     * 重写参数
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory){
        RequestFacade a = webRequest.getNativeRequest(RequestFacade.class);

        //是否读取失败
        if (a == null){
            return null;
        }

        //尝试返回用户信息
        try {
            return UserManager.getBySession(a.getSession());
        } catch (DataFileException e) {
            return null;
        }
    }
}
