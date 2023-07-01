/**
 * 用户管理服务
 * @author Xiaoyi311
 */

import User from "../models/User";
import fs from "fs";
import path from "path";
import logger from "../utils/logger";
import i18n from "../utils/i18n";
import { Session } from "express-session";

export default new class UserManager {
    /**
     * 用户数据目录
     */
    USER_PATH: string = path.resolve(process.cwd(), "data/users/");

    /**
     * 系统用户
     */
    system: User = new User("SeaperSystem", "none", [], "!!-Seaper-System-User-!!");

    /**
     * 用户表
     */
    users: Map<String, String> = new Map();

    /**
     * 初始化用户管理服务
     */
    initUser(){
        logger.log(i18n.msg("console.init.user-start"), "UserManager");

        //检测用户数据目录
        if(!fs.existsSync(this.USER_PATH)){
            fs.mkdirSync(this.USER_PATH);
        }

        //读取用户数据目录
        const userData = fs.readdirSync(this.USER_PATH);
        for (let i = 0; i < userData.length; i++) {
            //JSON 数据
            if(userData[i].endsWith(".json")){
                const user: User = User.fromJSON(this.getUserFromUUID(userData[i].replace(".json", ""), true));
                this.users.set(user.username, user.uuid);
            }
        }

        logger.log(i18n.msg("console.init.user-over", this.users.size), "UserManager");
    }

    /**
     * 从用户名获取用户
     */
    getUserFromUsername(username: string): User | undefined{
        //UUID
        const uuid: String | undefined = this.users.get(username);

        //是否存在
        if(uuid == undefined){
            return undefined;
        }

        //获取用户
        return this.getUserFromUUID(uuid.valueOf());
    }

    /**
     * 从用户名获取用户
     */
    getUserFromSession(session: any): User | undefined{
        //UUID
        let uuid: any | undefined = session.auth;
        if(uuid){
            uuid = uuid.uuid;
        }

        //是否不存在
        if(!uuid){
            return undefined;
        }

        //获取用户
        return this.getUserFromUUID(uuid);
    }

    /**
     * 从 UUID 读取用户
     */
    getUserFromUUID(uuid: string, init?: boolean): User | undefined{
        //路径
        const file: any = path.resolve(
            this.USER_PATH,
            uuid + ".json"
        );

        //是否存在
        if(!fs.existsSync(file)){
            return undefined;
        }

        //读取数据
        return JSON.parse(
            fs.readFileSync(
                file,
                "utf-8"
            )
        );
    }

    /**
     * 创建用户
     */
    create(user: User, who: User){
        this.set(user, who);
        this.users.set(user.uuid, user.username);
    }

    /**
     * 写入用户
     */
    set(user: User, who: User){
        const file_path = path.resolve(this.USER_PATH, user.uuid + ".json");

        //是否不存在
        if(!fs.existsSync(file_path)){
            logger.log(i18n.msg("console.createUser", who.uuid, who.username, user.uuid, user.username), "UserManager");
        }

        fs.writeFileSync(file_path, JSON.stringify(user));
    }

    /**
     * 登录用户
     */
    login(user: User, session: any): boolean{
        //真实用户
        const relUser = this.getUserFromUsername(user.username);

        //是否登录失败
        if(relUser == undefined || relUser.password != user.password){
            return false;
        }

        //登录成功
        session.auth = {
            login: true,
            uuid: relUser.uuid,
            username: relUser.username,
            password: relUser.password
        };
        session.resetMaxAge();
        logger.log(i18n.msg("console.loginUser", relUser.uuid, relUser.username), "UserManager");
        return true;
    }
}