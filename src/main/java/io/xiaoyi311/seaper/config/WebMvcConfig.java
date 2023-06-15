package io.xiaoyi311.seaper.config;

import io.xiaoyi311.seaper.interceptor.PermissionInterceptor;
import io.xiaoyi311.seaper.interceptor.UserDataInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * WebMvc 配置
 * @author Xiaoyi311
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    /**
     * 注解拦截器1
     */
    @Autowired
    PermissionInterceptor annotationInterceptor1;

    /**
     * 注解拦截器2
     */
    @Autowired
    UserDataInterceptor annotationInterceptor2;

    /**
     * 注入注解拦截器
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(annotationInterceptor1).addPathPatterns("/**");
        super.addInterceptors(registry);
    }

    /**
     * 字符集纠正
     */
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {

        for (HttpMessageConverter<?> converter : converters) {
            if(converter instanceof StringHttpMessageConverter){
                ((StringHttpMessageConverter) converter).setDefaultCharset(StandardCharsets.UTF_8);
            }
        }
    }

    /**
     * JSON 返回序列化
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(0, new MappingJackson2HttpMessageConverter());
    }

    /**
     * 添加参数修改器
     */
    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(annotationInterceptor2);
    }

    /**
     * 读取静态资源
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("file:public/");
        super.addResourceHandlers(registry);
    }

    /**
     * 重定向首页
     */
    @Override
    protected void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:index.html");
        super.addViewControllers(registry);
    }
}
