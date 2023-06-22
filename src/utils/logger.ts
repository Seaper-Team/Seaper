/**
 * 日志系统
 * @author Xiaoyi311
 */

import path from 'path';
import * as log4js from "log4js";
import i18n from './i18n';

export default new class LoggerManager {
    /**
     * 日志
     */
    logger = log4js.getLogger();

    /**
     * 初始化日志系统
     */
    initLogger() : void{
        console.log("[Logger] " + i18n.msg("console.init.logger-start"));
    
        //初始化 log4js
        log4js.configure({
            appenders: {
                console: {
                    type: 'stdout',
                    layout: {
                        type: "pattern",
                        pattern: "%[[%d{yyyy/MM/dd hh:mm:ss}] [%p] [%X{class}]%]: %m"
                    },
                },
                file: {
                    type: 'dateFile',
                    pattern: "yyyy-MM-dd.log",
                    filename: path.resolve(process.cwd(), "data/logs/seaper"),
                    alwaysIncludePattern: true
                }
            },
            categories: {
                default: {
                    appenders: ["console", "file"],
                    level: "info",
                    enableCallStack: true
                }
            }
        });
    
        this.log(i18n.msg("console.init.logger-over"), "LoggerManager");
    }

    /**
     * 输出信息
     */
    log(msg: string, from: string){
        this.logger.addContext("class", from);
        this.logger.log(msg);
    }
}