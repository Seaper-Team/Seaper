/**
 * 错误数据重写
 * @author Xiaoyi311
 */

import { NextFunction, Request, Response } from "express"

export default (err: Error, req: Request, res: Response, next: NextFunction) => {
    res.status(500);
    res.locals.data = err.message;
    next();
}