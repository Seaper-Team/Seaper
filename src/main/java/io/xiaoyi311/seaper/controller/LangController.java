package io.xiaoyi311.seaper.controller;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import io.xiaoyi311.seaper.exception.BadRequestException;
import io.xiaoyi311.seaper.service.LangManager;
import io.xiaoyi311.seaper.service.UserManager;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static io.xiaoyi311.seaper.service.LangManager.msg;

/**
 * 国际化类引导
 * @author Xiaoyi311
 */
@RestController
@RequestMapping("/api/lang")
public class LangController {
    /**
     * 初始化
     * @api {POST} /lang/init 初始化国际化系统
     * @apiName 初始化国际化系统
     * @apiUse ResData
     * @apiGroup Lang
     * @apiPermission none
     * @apiDescription 用于初始化整个国际化系统, 通常用在第一次启动 Seaper, 设置的语言为默认语言，需在初始化用户系统前执行
     * @apiBody {String} lang 语言代码
     * @apiParamExample {json} 参数请求示例
     *  {
     *      "lang":"zh_CN"
     *  }
     * @apiError Initiated Seaper 已经初始化过
     * @apiErrorExample {json} 错误-已初始化
     *  HTTP/1.1 400 Bad Request
     *  {
     *      "status":400,
     *      "time":0,
     *      "data":"Seaper Initiated!"
     *  }
     * @apiError Initiated 语言代码未找到
     * @apiErrorExample {json} 错误-语言代码未找到
     *  HTTP/1.1 400 Bad Request
     *  {
     *      "status":400,
     *      "time":0,
     *      "data":"Language Not Found"
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
    public String init(@RequestBody Map<String, String> args) throws BadRequestException {
        //是否初始化过了
        if(UserManager.users.size() > 0){
            throw new BadRequestException(msg("user.initiated"));
        }

        //判断语言是否存在
        String lang = args.get("lang");
        if(lang == null || !LangManager.langInfos.containsKey(lang)){
            throw new BadRequestException(msg("lang.notFound"));
        }

        //设置语言
        LangManager.setLang(lang);
        return "OK";
    }

    /**
     * 获取前端语言数据
     * @api {GET} /lang/get
     * @apiName 获取前端语言数据
     * @apiUse ResData
     * @apiGroup Lang
     * @apiPermission none
     * @apiDescription 用于前端语言初始化，获取目前语言
     * @apiSuccess {String} data 成功应返回前端语言的 json
     * @apiSuccessExample {json} 成功
     *  HTTP/1.1 200 OK
     *  {
     *      "status":200,
     *      "time":0,
     *      "data":{}
     *  }
     * @apiVersion 0.0.1
     */
    @GetMapping("/get")
    public JSONObject get(){
        //获取语言
        return LangManager.getFrontLang();
    }

    /**
     * 获取前端语言数据
     * @api {GET} /lang/get
     * @apiName 获取前端语言数据
     * @apiUse ResData
     * @apiGroup Lang
     * @apiPermission none
     * @apiDescription 用于前端语言初始化，获取目前语言
     * @apiSuccess {String} data 成功应返回前端语言的 json
     * @apiSuccessExample {json} 成功
     *  HTTP/1.1 200 OK
     *  {
     *      "status":200,
     *      "time":0,
     *      "data":{}
     *  }
     * @apiVersion 0.1.0
     */
    @GetMapping("/list")
    public JSONArray list(){
        //获取语言
        return LangManager.getList();
    }
}
