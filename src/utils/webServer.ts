/**
 * 网页服务器
 * @author Xiaoyi311
 */

import path from 'path';
import i18n from './i18n';
import logger from './logger';
import express, { NextFunction, Request, Response } from 'express'
import responseRewrite from '../middlewares/responseRewrite';
import router from '../routers/router';
import bodyParser from 'body-parser';

export default new class WebServerManager {
    /**
     * 网页服务器
     */
    server = express();

    /**
     * 初始化网页服务器
     */
    initServer(port: number): void{
        logger.log(i18n.msg("console.init.server-start"), "WebServerManager");

        //应用前端路径
        this.server.use(express.static(path.resolve(process.cwd(), "public/")));

        //Body JSON 解析器
        this.server.use(bodyParser.json());

        //应用 API 路由
        this.server.use('/api', router);

        //页面未找到
        this.server.use((req: Request, res: Response, next: NextFunction) => {
            //是否没有任何数据返回
            if(res.locals.data == undefined){
                res.status(404);
                res.locals.data = i18n.msg("errorStatus.404");
            }
            next();
        });

        //应用返回统一化
        this.server.use(responseRewrite);

        //启动监听服务
        this.server.listen(port, async () => {
            logger.log(i18n.msg("console.init.server-over", port), "WebServerManager");
        })
    }
}