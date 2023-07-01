/**
 * 用户验证路由
 * @author Xiaoyi311
 */

import express, { NextFunction, Request, Response } from "express";
import User from "../models/User";
import i18n from "../utils/i18n";
import userManager from "../services/userManager";
import valid from "../middlewares/valid";

//初始化路由解析器
const router = express.Router();

//用户登录
router.post("/login",
valid({body: {username: String, password: String}}),
(req: Request, res: Response, next: NextFunction) => {
    //获取用户
    const user: User = new User(req.body.username, req.body.password);

    //登录
    res.locals.data = userManager.login(user, req.session, "127.0.0.2") ? "OK" : i18n.msg("user.loginRefuse");
    next();
    return;
});

//用户登出
router.get("/logout", (req: Request, res: Response, next: NextFunction) => {
    req.session.destroy(() => {});
    res.locals.data = "OK";
    next();
    return;
});

export default router;