package io.xiaoyi311.seaper.config;

import io.xiaoyi311.seaper.interceptor.PermissionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
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
     * 注解拦截器
     */
    @Autowired
    PermissionInterceptor annotationInterceptor;

    /**
     * 注入注解拦截器
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(annotationInterceptor).addPathPatterns("/**");
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
}
