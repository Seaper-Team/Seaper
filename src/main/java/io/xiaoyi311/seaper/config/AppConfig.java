package io.xiaoyi311.seaper.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

/**
 * 程序基本数据
 * @author Xiaoyi311
 */
@Configuration
public class AppConfig {
    /**
     * 新建一个资源加载器
     */
    @Bean
    public ResourceLoader resloader(){
        return new DefaultResourceLoader();
    }
}
