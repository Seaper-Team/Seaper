/**
 * 用户路由
 * @author Xiaoyi311
 */

import express, { NextFunction, Request, Response } from "express";
import userManager from "../services/userManager";
import i18n from "../utils/i18n";
import User from "../models/User";
import userAuth from "./userAuth";
import valid from "../middlewares/valid";
import permission from "../middlewares/permission";

//初始化路由解析器
const router = express.Router();

//初始化用户系统
router.post("/init",
valid({body: {username: String, password: String}}),
(req: Request, res: Response, next: NextFunction) => {
    //是否初始化过
    if(userManager.users.size > 0){
        res.locals.data = i18n.msg("user.initiated");
        next();
        return;
    }

    //获取用户
    const user: User = new User(req.body.username, req.body.password, ["*"]);

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

//获取用户信息
router.get("/info", 
permission({permission: ["user.info"]}),
(req: Request, res: Response, next: NextFunction) => {
    res.locals.data = res.locals.user;
    next();
});

//验证路由
router.use("/auth", userAuth);

export default router;