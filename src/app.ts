/**
 * Seaper 后端入口点
 * @author Xiaoyi311
 */

import config from './utils/config';

//Banner 展示
const banner: string = 
`
     _________
    /   _____/ ____ _____  ______   ___________
    \\_____  \\_/ __ \\\\__  \\ \\____ \\_/ __ \\_  __ \\
     /        \\ ___/ / __ \\ |  |_ > >___/ |  |\\/
    /_______  /\\___  >____  /   __/ \\___  >__|
            \\/     \\/     \\/|__|        \\/
====================================================
<--  Seaper Server Manager Powered By Xiaoyi311  -->
Version: %s
Author: Xiaoyi311
Studio: SkyWorldStudio
====================================================`;
console.log(banner, config.package().version);

//检测前端文件夹
import path from "path";
import fs from "fs";
if(!fs.existsSync(path.resolve(process.cwd(), "public/"))){
    console.log("[Development Mode] Hei! You Shouldn't Do This! This is Development Mode!");
    console.log("[Development Mode] Please Download Seaper Production On https://project.skyworldstudio.top/seaper!");
    console.log("[Development Mode] If You Want Run Seaper in Development Mode, Please Create \"public\" folder in project folder!");
    process.exit();
}

//检测数据文件夹
const DATA_PATH: string = path.resolve(process.cwd(), "data/");
if(!fs.existsSync(DATA_PATH)){
    fs.mkdirSync(DATA_PATH);
}

//初始化用户配置
import AppConfig from './models/AppConfig';
config.initConfig();
const userConfig = config.get("app", new AppConfig());

//初始化国际化
import i18n from './utils/i18n';
i18n.initI18n(userConfig.lang, Number.parseInt(config.package().version.replace(".", "")));

//初始化日志系统
import logger from './utils/logger';
logger.initLogger();

//初始化用户管理服务
import userManager from './services/userManager';
userManager.initUser(userConfig.loginStopTime, userConfig.loginTryTime);

//初始化网页服务器
import server from './utils/webServer';
server.initServer(userConfig.port);