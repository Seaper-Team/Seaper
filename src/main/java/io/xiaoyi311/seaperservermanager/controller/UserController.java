package io.xiaoyi311.seaperservermanager.controller;

import io.xiaoyi311.seaperservermanager.exception.BadRequestException;
import io.xiaoyi311.seaperservermanager.exception.DataFileException;
import io.xiaoyi311.seaperservermanager.model.User;
import io.xiaoyi311.seaperservermanager.service.LangManager;
import io.xiaoyi311.seaperservermanager.service.UserManager;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * 用户类引导
 * @author Xioyi311
 */
@RestController
@RequestMapping("/api/user")
public class UserController {
    /**
     * 初始化
     * @api {POST} /user/init 初始化用户系统
     * @apiName 初始化用户系统
     * @apiUse ResData
     * @apiUse UserData
     * @apiGroup User
     * @apiPermission none
     * @apiDescription 用于初始化整个用户系统, 通常用在第一次启动 Seaper, 新建的账户为超级管理员账户
     * @apiBody {String} username 要注册的用户名
     * @apiBody {String} password 要注册的密码 (应为MD5)
     * @apiParamExample {json} 参数请求示例
     *  {
     *      "username":"test"
     *      "password":"testPassword"
     *  }
     * @apiError Initiated Seaper 已经初始化过
     * @apiErrorExample {json} 错误-已初始化
     *  HTTP/1.1 400 Bad Request
     *  {
     *      "status":400,
     *      "time":0,
     *      "data":"Seaper Initiated!"
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
    @PostMapping("/init")
    public String init(@Valid @RequestBody User user, BindingResult errors) throws DataFileException, BadRequestException {
        //是否初始化过了
        if(UserManager.users.size() > 0){
            throw new BadRequestException(LangManager.msg("user.initiated"));
        }

        //是否校验失败
        if(errors.hasErrors()){
            throw new BadRequestException(LangManager.msg(Objects.requireNonNull(errors.getFieldError()).getDefaultMessage()));
        }

        //新建用户
        UserManager.create(user.username, user.password);
        return "OK";
    }
}
