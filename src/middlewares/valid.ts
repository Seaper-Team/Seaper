/**
 * 类型校验
 * @author Xiaoyi311 unitwk
 * Copyright (C) 2022 MCSManager <mcsmanager-dev@outlook.com>
 * 由 Xiaoyi311 修改并适配 Express
 */

import { NextFunction, Request, Response } from "express";

//参数
interface ValidParameter{
    params?: any;
    query?: any;
    body?: any;
}

//验证
function check(target: any, parameter: any) {
    if (target) {
        for (const key in parameter) {
            const typeVal = parameter[key];

            if (target[key] == null || target[key] === "") return false;

            if (typeVal === Number) {
                target[key] = Number(target[key]);
                if (isNaN(target[key])) return false;
                continue;
            }

            if (typeVal === String) {
                target[key] = String(target[key]);
                continue;
            }

            if (typeVal === Date) {
                const r = new Date(target[key]).toString();
                if (r == "Invalid Date" || r == null) return false;
                target[key] = new Date(target[key]);
                continue;
            }

            if (typeVal === Array && !(target[key] instanceof Array)) {
                const object = JSON.parse(target[key]);
                if (!(object instanceof Array)) return false;
                target[key] = object;
            }

            if (typeVal === Object) {
                if (!target[key]) return false;
            }
        }
        return true;
    }
    return false;
}

//中间件
export default function (params: ValidParameter) {
    return (req: Request, res: Response, next: NextFunction) => {
        try {
            let success = true;
            if (params["params"] && !check(req.params, params["params"])) success = false;
            if (params["query"] && !check(req.query, params["query"])) success = false;
            if (params["body"] && !check(req.body, params["body"])) success = false;
            if (success) return next();
        } catch (err) {}
        next(new Error("SEAPER_PARAMS_ERROR"));
        return;
    };
}