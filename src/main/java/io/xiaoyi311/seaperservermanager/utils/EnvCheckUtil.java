package io.xiaoyi311.seaperservermanager.utils;

import io.xiaoyi311.seaperservermanager.SeaperServerManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.IOException;

/**
 * 环境监测
 * @author Xiaoyi311
 */
@Slf4j
public class EnvCheckUtil {
    /**
     * 是否通过验证
     */
    static boolean isSucceed = true;

    /**
     * 检测
     * @return 是否检测成功
     */
    public static boolean check(){
        log.info("Checking Environment...");

        checkFolder("data");
        checkFolder("data/configs");
        checkFolder("data/users");
        checkFolder("data/langs");

        if(!isSucceed){
            log.error("Environmental detection failed. Please fix the environmental issue based on the above information!");
        }
        return isSucceed;
    }

    /**
     * 检测目录权限
     */
    static void checkFolder(String path){
        //是否前面已经失败
        if(!isSucceed){
            return;
        }

        //资源路径
        Resource folderRes = SeaperServerManager.config.getBean(ResourceLoader.class).getResource("file:" + path);

        //检查存在
        File folder;
        if(!folderRes.exists()){
            try {
                folder = folderRes.getFile();
                folder.mkdir();
                log.info("Create Folder " + path);
            } catch (Exception e) {
                log.error("Unable to create new " + path + " directory, please check read and write permissions!");
                isSucceed = false;
                return;
            }
        } else {
            try {
                folder = folderRes.getFile();
            } catch (IOException e) {
                log.error("Unable to read " + path + " directory, Please check if the directory is abnormal!");
                isSucceed = false;
                return;
            }
        }

        //检查权限
        if(!folder.canRead() || !folder.canWrite()){
            log.error("Directory " + path + " has incorrect permissions. Please add read and write permissions for this directory!");
            isSucceed = false;
        }
    }
}
