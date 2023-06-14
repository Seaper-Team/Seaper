package io.xiaoyi311.seaper.controller;

import io.xiaoyi311.seaper.annotation.Permission;
import io.xiaoyi311.seaper.annotation.UserData;
import io.xiaoyi311.seaper.exception.BadRequestException;
import io.xiaoyi311.seaper.exception.DataFileException;
import io.xiaoyi311.seaper.exception.ErrorPageException;
import io.xiaoyi311.seaper.model.PageData;
import io.xiaoyi311.seaper.model.User;
import io.xiaoyi311.seaper.service.LangManager;
import io.xiaoyi311.seaper.service.UserManager;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
        UserManager.create(user.username, user.password, "Seaper User Manager");
        return "OK";
    }

    /**
     * 是否初始化
     * @api {GET} /user/init 是否已初始化用户系统
     * @apiName 是否已初始化用户系统
     * @apiUse ResData
     * @apiUse UserData
     * @apiGroup User
     * @apiPermission none
     * @apiDescription 查看 Seaper 是否已经初始化
     * @apiSuccess {String} data 成功应返回 boolean，已初始化为 true，未初始化反之
     * @apiSuccessExample {json} 成功
     *  HTTP/1.1 200 OK
     *  {
     *      "status":200,
     *      "time":0,
     *      "data":true
     *  }
     * @apiVersion 0.0.3
     */
    @GetMapping("/init")
    public boolean isInit(){
        return UserManager.users.size() > 0;
    }

    /**
     * 用户列表
     * @api {GET} /user/list 查看用户列表
     * @apiName 查看用户列表
     * @apiUse ResData
     * @apiUse PageData
     * @apiGroup User
     * @apiPermission user.list
     * @apiDescription 查看目前所有存在的用户列表，可按照页数返回
     * @apiSuccess {String} data 成功应返回页数格式
     * @apiSuccessExample {json} 成功
     *  HTTP/1.1 200 OK
     *  {
     *      "status":200,
     *      "time":0,
     *      "data":{
     *          "total":1,
     *          "page":1,
     *          "data": [{
     *              "uuid": "00000000-0000-0000-0000-000000000000",
     *              "username": "test",
     *              "password": "testPassword",
     *              "registerTime": 0,
     *              "permissions": []
     *          }]
     *      }
     *  }
     * @apiVersion 0.0.2
     */
    @GetMapping("/list")
    @Permission("user.list")
    public PageData list(@Valid PageData.ReqPageData args, BindingResult errors) throws BadRequestException, ErrorPageException {
        //是否校验失败
        if(errors.hasErrors()){
            throw new BadRequestException(LangManager.msg(Objects.requireNonNull(errors.getFieldError()).getDefaultMessage()));
        }

        //返回最终数据
        return UserManager.getPageList(args.show, args.page);
    }

    /**
     * 删除用户
     * @api {DELETE} /user/remove 删除存在的用户
     * @apiName 删除存在的用户
     * @apiUse ResData
     * @apiGroup User
     * @apiPermission user.remove
     * @apiDescription 根据 UUID 删除一位用户
     * @apiQuery {String} uuid 要删除用户的 UUID
     * @apiSuccess {String} data 成功应返回 OK
     * @apiSuccessExample {json} 成功
     *  HTTP/1.1 200 OK
     *  {
     *      "status":200,
     *      "time":0,
     *      "data":"OK"
     *  }
     * @apiError UserNotFound 指定目标未找到
     * @apiErrorExample {json} 错误-目标未找到
     *  HTTP/1.1 400 Bad Request
     *  {
     *      "status":400,
     *      "time":0,
     *      "data":"User Not Found!"
     *  }
     * @apiError UserCantDelete 用户无法删除
     * @apiErrorExample {json} 错误-无法删除
     *  HTTP/1.1 400 Bad Request
     *  {
     *      "status":400,
     *      "time":0,
     *      "data":"User Cant Delete!"
     *  }
     * @apiVersion 0.0.2
     */
    @DeleteMapping("/remove")
    @Permission("user.remove")
    public String remove(@UserData User user, String uuid) throws BadRequestException, DataFileException {
        //该用户是否为当前账户
        if(Objects.equals(user.uuid, uuid)){
            throw new BadRequestException(LangManager.msg("user.cantDelete"));
        }

        //尝试删除用户
        if(!UserManager.remove(uuid, user.uuid)){
            throw new BadRequestException(LangManager.msg("user.notFound"));
        }

        return "OK";
    }

    /**
     * 新建用户
     * @api {PUT} /user/create 新建一个用户
     * @apiName 新建一个用户
     * @apiUse ResData
     * @apiUse UserData
     * @apiGroup User
     * @apiPermission user.create
     * @apiDescription 根据用户信息新建一个用户
     * @apiBody {Array} [permissions] 拥有的权限
     * @apiParamExample {json} 参数请求示例
     *  {
     *      "username":"test"
     *      "password":"testPassword",
     *      "permission": ["*"]
     * @apiError UsernameExists 用户名已存在
     * @apiErrorExample {json} 错误-用户名已存在
     *  HTTP/1.1 400 Bad Request
     *  {
     *      "status":400,
     *      "time":0,
     *      "data":"Username Exists!"
     *  }
     * @apiSuccess {String} data 成功应返回 OK
     * @apiSuccessExample {json} 成功
     *  HTTP/1.1 200 OK
     *  {
     *      "status":200,
     *      "time":0,
     *      "data":"OK"
     *  }
     * @apiVersion 0.0.2
     */
    @PutMapping("/create")
    @Permission("user.create")
    public String create(@UserData User user, @Valid @RequestBody User createUser, BindingResult errors) throws BadRequestException, DataFileException {
        //是否校验失败
        if(errors.hasErrors()){
            throw new BadRequestException(LangManager.msg(Objects.requireNonNull(errors.getFieldError()).getDefaultMessage()));
        }

        //是否用户名重复
        if(UserManager.users.containsValue(createUser.username)){
            throw new BadRequestException(LangManager.msg("user.usernameExists"));
        }

        //防止固定数据篡改
        createUser.permissions = createUser.permissions == null ? new ArrayList<>() : createUser.permissions;
        createUser.registerTime = System.currentTimeMillis();
        createUser.uuid = UUID.randomUUID().toString();

        //新建用户
        UserManager.create(createUser, user.uuid);
        return "OK";
    }
}
