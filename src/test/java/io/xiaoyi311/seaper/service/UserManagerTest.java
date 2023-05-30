package io.xiaoyi311.seaper.service;

import io.xiaoyi311.seaper.exception.DataFileException;
import io.xiaoyi311.seaper.interceptor.PermissionInterceptor;
import io.xiaoyi311.seaper.model.TempSession;
import io.xiaoyi311.seaper.model.User;
import io.xiaoyi311.seaper.utils.PermissionUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

/**
 * 用户管理器测试
 * @author Xiaoyi311
 */
@SpringBootTest
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("用户管理器")
public class UserManagerTest {
    /**
     * 创建用户
     */
    @Test
    @Order(0)
    @DisplayName("创建用户")
    void createUser() throws DataFileException {
        User user = new User(
                UUID.randomUUID().toString(),
                "test",
                "testPass",
                PermissionUtil.Default
        );
        UserManager.create(user);

        log.info("UUID: " + user.uuid);
        log.info("用户名: " + user.username);
        log.info("密码: " + user.password);
        log.info("注册时间: " + user.registerTime);
    }

    /**
     * 登录用户
     */
    @Test
    @Order(1)
    @DisplayName("登录用户")
    void loginUser() throws Exception {
        User user = new User();
        user.username = "test";
        user.password = "testPass";

        UserManager.login(user, TempSession.temp);
        if(!UserManager.isLogin(TempSession.temp)){
            throw new Exception("登陆失败");
        }
    }

    /**
     * 查看用户信息
     */
    @Test
    @Order(2)
    @DisplayName("用户信息")
    void userinfo() throws Exception {
        User user = UserManager.getBySession(TempSession.temp);

        if(user == null){
            throw new Exception("获取用户 Session 失败");
        }

        log.info("UUID: " + user.uuid);
        log.info("用户名: " + user.username);
        log.info("密码: " + user.password);
        log.info("注册时间: " + user.registerTime);
    }

    /**
     * 权限测试
     */
    @Test
    @Order(3)
    @DisplayName("权限测试")
    void userPermission() throws Exception {
        if(!PermissionInterceptor.checkPermissions(TempSession.temp, new String[]{})){
            throw new Exception("权限测验不通过");
        }
    }

    /**
     * 修改用户信息
     */
    @Test
    @Order(4)
    @DisplayName("修改用户信息")
    void changeUserinfo() throws DataFileException {
        User user = UserManager.getBySession(TempSession.temp);
        assert user != null;
        user.permissions.add("test.fuck");
        user.permissions.add("test2.*");
        UserManager.set(user);
        log.info("修改成功");
    }

    /**
     * 特殊权限测试
     */
    @Test
    @Order(5)
    @DisplayName("特殊权限测试")
    void userNoPermission() throws Exception {
        if(PermissionInterceptor.checkPermissions(TempSession.temp, new String[]{"test.fuck1"})){
            throw new Exception("权限测验通过");
        }
    }

    /**
     * 修改后特殊权限测试
     */
    @Test
    @Order(6)
    @DisplayName("修改后特殊权限测试")
    void userChangePermission() throws Exception {
        if(!PermissionInterceptor.checkPermissions(TempSession.temp, new String[]{"test.fuck"})){
            throw new Exception("权限测验不通过");
        }
    }

    /**
     * 泛指修改后特殊权限测试
     */
    @Test
    @Order(6)
    @DisplayName("泛指修改后特殊权限测试")
    void userAllChangePermission() throws Exception {
        if(!PermissionInterceptor.checkPermissions(TempSession.temp, new String[]{"test2.a"})){
            throw new Exception("权限测验不通过");
        }
    }

    /**
     * 登出用户
     */
    @Test
    @Order(7)
    @DisplayName("登出用户")
    void logoutUser() throws Exception {
        TempSession.temp.invalidate();

        if(UserManager.isLogin(TempSession.temp)){
            throw new Exception("退出登录失败");
        }
    }
}
