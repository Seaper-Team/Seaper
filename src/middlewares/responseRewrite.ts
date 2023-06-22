/**
 * 返回数据重写
 * @author Xiaoyi311
 */

import { NextFunction, Request, Response } from "express"

export default (req: Request, res: Response, next: NextFunction) => {
    res.json({
        status: res.statusCode,
        time: new Date().getTime(),
        data: res.locals.data
    });

    next();
}