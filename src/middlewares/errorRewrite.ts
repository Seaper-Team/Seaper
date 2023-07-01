/**
 * 错误数据重写
 * @author Xiaoyi311
 */

import { NextFunction, Request, Response } from "express"
import i18n from "../utils/i18n";

export default (err: Error, req: Request, res: Response, next: NextFunction) => {
    if(err.message == "SEAPER_PARAMS_ERROR"){
        res.status(400);
        res.locals.data = i18n.msg("errorStatus.400");
        next();
        return;
    }

    if(err.message == "SEAPER_PERMISSION_ERROR"){
        res.status(403);
        res.locals.data = i18n.msg("errorStatus.403");
        next();
        return;
    }

    res.status(500);
    res.locals.data = err.message;
    next();
}