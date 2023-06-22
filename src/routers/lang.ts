/**
 * 国际化路由
 * @author Xiaoyi311
 */

import express, { NextFunction, Request, Response } from "express";
import userManager from "../services/userManager";
import i18n from "../utils/i18n";

//初始化路由解析器
const router = express.Router();

//语言初始化
router.post("/init", (req: Request, res: Response, next: NextFunction) => {
    //是否初始化过
    if(userManager.users.size > 0){
        res.locals.data = i18n.msg("user.initiated");
        next();
        return;
    }

    //语言是否存在
    if(!i18n.langData.has(req.body.lang)){
        res.locals.data = i18n.msg("lang.notFound");
        next();
        return;
    }

    //设置语言
    i18n.set(req.body.lang, userManager.system);
    res.locals.data = "OK";
    next();
});

//获取前端语言数据
router.get("/get", (req: Request, res: Response, next: NextFunction) => {
    res.locals.data = i18n.frontLang();
    next();
});

//获取前端语言列表
router.get("/list", (req: Request, res: Response, next: NextFunction) => {
    res.locals.data = i18n.langList();
    next();
});

export default router;