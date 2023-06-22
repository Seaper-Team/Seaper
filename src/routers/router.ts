/**
 * 主路由
 * @author Xiaoyi311
 */

import express from "express";
import lang from "./lang";
import user from "./user";

//初始化路由解析器
const router = express.Router();

//国际化路由
router.use("/lang", lang);

//用户路由
router.use("/user", user);

export default router;