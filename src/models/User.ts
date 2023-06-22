/**
 * 用户
 * @author Xiaoyi311
 */

import { randomUUID } from "crypto";

export default class User {
    /**
     * UUID
     */
    uuid: string;

    /**
     * 用户名
     */
    username: string;
    
    /**
     * 密码
     */
    password: string;
    
    /**
     * 注册时间
     */
    registerTime: number;
    
    /**
     * 权限
     */
    permissions: Array<string>;

    /**
     * 实例化
     */
    constructor(username: string, password: string);
    constructor(username: string, password: string, uuid: string);
    constructor(username: string, password: string, uuid: string, permissions: Array<string>, registerTime: number);
    constructor(username: string, password: string, uuid?: string, permissions?: Array<string>, registerTime?: number){
        this.uuid = uuid == undefined ? randomUUID() : uuid;
        this.username = username;
        this.password = password;
        this.registerTime = registerTime == undefined ? new Date().getTime() : registerTime;
        this.permissions = permissions == undefined ? new Array<string>() : permissions;
    }

    /**
     * 从 JSON 生成
     */
    static fromJSON(data: any): User{
        return new User(
            data.username,
            data.password,
            data.uuid,
            data.permissions,
            data.registerTime
        );
    }

    /**
     * 校验数据
     */
    check(): string{
        if(this.username == undefined){
            return "user.usernameNull";
        }
        if(this.password == undefined){
            return "user.passwordNull";
        }
        if(this.password.length != 32){
            return "user.passwordNotMd5";
        }

        return "OK";
    }
}