/**
 * 权限校验
 * @author Xiaoyi311
 */

import { NextFunction, Request, Response } from "express";
import userManager from "../services/userManager";
import User from "../models/User";

//参数
interface PermissionParameter{
    permission: Array<string>
}

//中间件
export default function (params: PermissionParameter) {
    return (req: Request, res: Response, next: NextFunction) => {
        //获取真实用户
        const user: User | undefined = userManager.getUserFromSession(req.session);

        //是否用户不存在
        if(user == undefined){
            next(new Error("SEAPER_PERMISSION_ERROR"));
            return;
        }

        //应用用户信息
        res.locals.user = user;

        //删除都拥有的权限
        const testPermission: string[] = params.permission.filter((v) =>
            user.permissions.every((e) => e != v)
        );

        //是否已没有权限
        if(testPermission.length == 0){
            next();
            return;
        }

        //泛指符处理，得到拥有的所有权限
        const haveNodes: string[] = [];
        for (let i = 0; i < user.permissions.length; i++) {
            const e: string = user.permissions[i];
            if(e.endsWith("*")){
                //如果拥有全部权限
                if(e == "*"){
                    next();
                    return;
                }

                //加入拥有权限列表
                haveNodes.push(e.replace(".*", ""));
            }
        }

        //判断权限
        let isOk = true;
        for (let i = 0; i < params.permission.length; i++) {
            const e: string = params.permission[i];
            let success = false;

            //遍历拥有的权限
            for (let y = 0; y < haveNodes.length; y++) {
                const e1: string = haveNodes[i];

                //被检测的权限是否在拥有的权限范围内
                if(e.startsWith(e1)){
                    success = true;
                    break;
                }
            }

            //是否找到对应权限
            if(!success){
                isOk = false;
                break;
            }
        }

        //是否成功
        next(isOk ? undefined : new Error("SEAPER_PERMISSION_ERROR"));
    };
}