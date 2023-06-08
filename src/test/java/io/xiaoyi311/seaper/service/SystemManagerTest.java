package io.xiaoyi311.seaper.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
@DisplayName("系统信息管理器")
public class SystemManagerTest {
    /**
     * 获取系统信息
     */
    @Test
    @DisplayName("获取系统信息")
    void getInfo(){
        SystemManager.SystemStatus status = SystemManager.getStatus();

        log.info("系统信息：" + status.infomation);
        log.info("运行用户：" + status.appUser);
        log.info("运行时长：" + status.runTime);
        log.info("CPU 占用：" + status.cpu);
        log.info("RAM 占用：" + status.ram);
        log.info("API 请求次数：" + status.apiReqTime);
        log.info("登录成功次数：" + status.loginSuccessTime);
        log.info("登录失败次数：" + status.loginBadTime);
    }
}
