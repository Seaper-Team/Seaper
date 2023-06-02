package io.xiaoyi311.seaper.controller;

import io.xiaoyi311.seaper.annotation.Permission;
import io.xiaoyi311.seaper.exception.BadRequestException;
import io.xiaoyi311.seaper.exception.DataFileException;
import io.xiaoyi311.seaper.exception.ErrorPageException;
import io.xiaoyi311.seaper.model.PageData;
import io.xiaoyi311.seaper.model.User;
import io.xiaoyi311.seaper.service.LangManager;
import io.xiaoyi311.seaper.service.UserManager;
import io.xiaoyi311.seaper.utils.PageUtil;
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
     *          "data":[{
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

        //获取用户 UUID 数据
        Object[] uuids = UserManager.users.keySet().toArray();
        int total = PageUtil.total(args.show, uuids);
        List<Object> uuidList = PageUtil.get(args.page, args.show, uuids);

        //判断页面是否不存在
        if(uuidList == null){
            throw new ErrorPageException(args.page, total);
        }

        //转换所有数据
        uuidList.replaceAll(o -> {
            try {
                return UserManager.getByUUID(o.toString());
            } catch (DataFileException e) {
                return new User(o.toString(), "error", "error", new ArrayList<>());
            }
        });

        //返回最终数据
        return new PageData(
                total,
                args.page,
                uuidList
        );
    }
}
