/**
 * 配置解析
 * @author Xiaoyi311
 */

import log from './logger';
import fs from "fs";
import path from "path";

export default new class ConfigManager {
    /**
     * 配置数据目录
     */
    CONFIG_PATH: string = path.resolve(process.cwd(), "data/configs/");

    /**
     * 初始化配置
     */
    initConfig(): void{
        console.log("[ConfigManager] Initiating Config...");
        
        //检测配置文件目录
        if (!fs.existsSync(this.CONFIG_PATH)){
            console.log("Create Config Folder");
            fs.mkdirSync(this.CONFIG_PATH);
        }

        console.log("[ConfigManager] Config Initiated!");
    }

    /**
     * 读取配置
     */
    get <T> (name: string, target: T): T{
        //文件名
        const filename: string = path.resolve(this.CONFIG_PATH, name + ".json");

        //检测配置文件
        if (!fs.existsSync(filename)){
            log.logger.log("Create Config [" + name + ".json]");
            fs.writeFileSync(filename, JSON.stringify(target));
        }

        //读取文件
        return JSON.parse(fs.readFileSync(filename, "utf-8"));
    }

    /**
     * 包信息
     */
    package(): any {
        return JSON.parse(fs.readFileSync(path.resolve(process.cwd(), "package.json"), "utf-8"));
    }
}