/**
 * 国际化
 * @author Xiaoyi311
 */

import fs from "fs";
import path from "path";
import logger from "./logger";
import User from "../models/User";

export default new class I18nManager {
    /**
     * 语言路径
     */
    I18N_PATH: string = path.resolve(process.cwd(), "data/langs/");

    /**
     * 语言数据表
     */
    langData: Map<string, any> = new Map<string, any>();

    /**
     * 默认语言
     */
    lang: any;

    /**
     * 初始化国际化
     */
    initI18n(lang: string, version: number): void{
        console.log("[I18nManager] Initiating I18n...");

        //是否存在语言文件夹
        if(!fs.existsSync(this.I18N_PATH)){
            console.log("[I18nManager] Create I18n Folder")
            fs.mkdirSync(this.I18N_PATH);
        }

        //设置默认语言
        const langData = fs.readdirSync(this.I18N_PATH);
        for (let i = 0; i < langData.length; i++) {
            const code = langData[i].replace(".json", "");
            this.langData.set(code, this.get(code));

            //默认语言
            if(code == lang){
                this.lang = this.get(code);
                const banner = 
`<======== Seaper Lang Information ========>
Name: %s
Author: %s
Version: %s
<======== Seaper Lang Information ========>`;
                console.log(banner, this.lang.Name, this.lang.Author, this.lang.Version);

                //版本低
                if(this.lang.Version < version){
                    console.warn("WARRING! LANG VERSION LOW!!!!!")
                }
            }
        }

        //没有语言使用
        if(this.lang == undefined){
            console.warn("[I18nManager] WARRING! NO LANG USED NOW!!!!!!!!!")
        }

        console.log("[I18nManager] I18n Initiated!");
    }

    /**
     * 读取语言文件数据
     */
    get(code: string): any{
        return JSON.parse(fs.readFileSync(path.resolve(this.I18N_PATH, code + ".json"), "utf-8"));
    }

    /**
     * 获取翻译结果
     */
    msg(node: string, ...args: any[]){
        //读取语言
        const nodes = node.split(".");
        let result = this.lang.backend;
        for (let i = 0; i < nodes.length; i++) {
            result = result[nodes[i]];
        }

        //填充参数
        for (let i = 0; i < args.length; i++) {
            result = result.replace("{" + i + "}", args[i]);
        }

        return result;
    }

    /**
     * 设置默认语言
     */
    set(code: string, who: User){
        this.lang = this.get(code);
        logger.log(this.msg("console.langChange", who.uuid, who.username, code), "I18nManager");
    }

    /**
     * 获取前端语言
     */
    frontLang(): any{
        return this.lang.front;
    }

    /**
     * 获取语言数据列表
     */
    langList(): Array<any>{
        const infos = new Array<any>();
        this.langData.forEach((value: any, key: string) => {
            infos.push({
                code: key,
                name: value.Name
            });
        });
        return infos;
    }
}