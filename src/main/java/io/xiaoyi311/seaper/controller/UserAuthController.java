package io.xiaoyi311.seaper.controller;

import io.xiaoyi311.seaper.model.User;
import io.xiaoyi311.seaper.service.LangManager;
import io.xiaoyi311.seaper.service.UserManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * 用户验证类引导
 * @author Xiaoyi311
 */
@RestController
@RequestMapping("/api/user/auth")
public class UserAuthController {
    /**
     * 登录
     * @api {POST} /user/auth/login 用户登录
     * @apiName 用户登陆账号
     * @apiUse ResData
     * @apiUse UserData
     * @apiGroup User
     * @apiPermission none
     * @apiDescription 用于用户登录操作，所有管理操作的基础
     * @apiBody {String} username 用户名
     * @apiBody {String} password 密码 (应为MD5)
     * @apiParamExample {json} 参数请求示例
     *  {
     *      "username":"test"
     *      "password":"testPassword"
     *  }
     * @apiError LoginError 用户名或密码错误
     * @apiErrorExample {json} 错误-用户名或密码错误
     *  HTTP/1.1 400 Bad Request
     *  {
     *      "status":400,
     *      "time":0,
     *      "data":"Username or password error!"
     *  }
     * @apiSuccess {String} data 成功应返回 OK
     * @apiSuccessExample {json} 成功
     *  HTTP/1.1 200 OK
     *  {
     *      "status":200,
     *      "time":0,
     *      "data":"OK"
     *  }
     * @apiVersion 0.0.1
     */
    @PostMapping("/login")
    public String login(@Valid @RequestBody User user, BindingResult errors, HttpServletRequest request) {
        //是否校验失败
        if(errors.hasErrors()){
            return LangManager.msg(Objects.requireNonNull(errors.getFieldError()).getDefaultMessage());
        }

        //是否登录失败
        return UserManager.login(user, request.getSession()) ? "OK" : LangManager.msg("user.loginRefuse");
    }

    /**
     * 信息
     * @api {GET} /user/auth/info 用户信息
     * @apiName 查看用户信息
     * @apiUse ResData
     * @apiUse PermissionError
     * @apiGroup User
     * @apiPermission 已登录
     * @apiDescription 用于测试用户系统是否正常，后期会弃用！
     * @apiSuccess {String} data 返回登录的用户信息
     * @apiSuccessExample {json} 成功
     *  HTTP/1.1 200 OK
     *  {
     *      "status":200,
     *      "time":0,
     *      "data":{
     *          "uuid": "00000000-0000-0000-0000-000000000000",
     *          "username": "test",
     *          "password": "testPassword",
     *          "registerTime": 0,
     *          "permissions": []
     *      }
     *  }
     * @apiDeprecated 此方法已替换为 /user/info
     * @apiVersion 0.0.1
     */
    public void info(){}

    /**
     * 登出
     * @api {GET} /user/auth/logout 用户登出
     * @apiName 用户退出登录
     * @apiUse ResData
     * @apiGroup User
     * @apiDescription 退出目前登录状态
     * @apiSuccess {String} data 返成功应返回 OK
     * @apiSuccessExample {json} 成功
     *  HTTP/1.1 200 OK
     *  {
     *      "status":200,
     *      "time":0,
     *      "data":"OK"
     *  }
     * @apiVersion 0.0.1
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        request.getSession().invalidate();
        return "OK";
    }
}
