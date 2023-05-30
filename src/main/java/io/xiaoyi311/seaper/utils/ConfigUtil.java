package io.xiaoyi311.seaper.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import io.xiaoyi311.seaper.SeaperServerManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * 配置文件工具
 * @author Xiaoyi311
 */
@Slf4j
public class ConfigUtil {
    /**
     * 配置集目录
     */
    static Resource configsFolder = SeaperServerManager.config.getBean(ResourceLoader.class).getResource("file:data/configs");

    /**
     * 加载配置文件
     */
    public static <T> T load(String filename, Class<T> config, T def){
        log.info("Loading Config...");

        //检查文件
        Path configpath = check(filename);
        if(configpath == null){
            return def;
        }

        //检查存在
        if(!Files.exists(configpath)){
            try {
                Files.createFile(configpath);
                set(filename, def);
                log.debug("Create Config File");
            } catch (IOException e) {
                log.error("Create config file error: " + e.getMessage());
                return def;
            }
        }

        //实例化
        try {
            return JSONObject.parseObject(
                    Files.readString(configpath),
                    config
            );
        } catch (IOException e) {
            log.error("Read config file error: " + e.getMessage());
            return def;
        }
    }

    /**
     * 写入配置文件
     */
    public static <T> void set(String filename, T config){
        Path configPath = check(filename);
        if(configPath == null){
            return;
        }

        //实例化
        try {
            Files.write(
                    configPath,
                    JSON.toJSONString(config).getBytes()
            );
        } catch (IOException e) {
            log.error("Write config file error: " + e.getMessage());
        }
    }

    /**
     * 文件基本检测
     * @param filename 文件名
     * @return 文件路径
     */
    static Path check(String filename){
        //获取目录
        Path configpath;
        try {
            configpath = Path.of(
                    configsFolder.getFile().getCanonicalPath(),
                    filename + ".json"
            );
        } catch (IOException e) {
            log.error("get config files error: " + e.getMessage());
            return null;
        }

        return configpath;
    }
}
