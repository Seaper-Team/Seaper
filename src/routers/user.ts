/**
 * 用户路由
 * @author Xiaoyi311
 */

import express, { NextFunction, Request, Response } from "express";
import userManager from "../services/userManager";
import i18n from "../utils/i18n";
import User from "../models/User";

//初始化路由解析器
const router = express.Router();

//初始化用户系统
router.post("/init", (req: Request, res: Response, next: NextFunction) => {
    //是否初始化过
    if(userManager.users.size > 0){
        res.locals.data = i18n.msg("user.initiated");
        next();
        return;
    }

    //检验数据
    const user: User = new User(req.body.username, req.body.password);
    const check = user.check();
    if(check != "OK"){
        res.locals.data = i18n.msg(check);
        next();
        return;
    }

    //创建用户
    userManager.create(user, userManager.system);
    res.locals.data = "OK";
    next();
});

//获取是否初始化用户系统
router.get("/init", (req: Request, res: Response, next: NextFunction) => {
    res.locals.data = userManager.users.size > 0;
    next();
});

export default router;