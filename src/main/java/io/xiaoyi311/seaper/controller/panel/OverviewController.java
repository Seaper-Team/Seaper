package io.xiaoyi311.seaper.controller.panel;

import io.xiaoyi311.seaper.annotation.Permission;
import io.xiaoyi311.seaper.service.SystemManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 面板仪表盘页面
 * @author Xioyi311
 */
@RestController
@RequestMapping("/api/panel/overview")
public class OverviewController {
    /**
     * 面板状态
     * @api {GET} /panel/overview/status 查看当前面板所在主机状态
     * @apiName 查看当前面板所在主机状态
     * @apiUse ResData
     * @apiGroup Panel
     * @apiPermission panel.status
     * @apiDescription 查看目前面板所在主机的系统信息及其运行数据
     * @apiSuccess {Object} data 成功应返回状态数据
     * @apiSuccessExample {json} 成功
     *  HTTP/1.1 200 OK
     *  {
     *      "status": 200,
     *      "time": 0,
     *      "data": {
     *          "infomation": "Windows 11; amd64; 10.0; ",
     *          "appUser": "Admin",
     *          "cpu": 0.09,
     *          "ram": 0.94,
     *          "loginSuccessTime": 1,
     *          "loginBadTime": 0,
     *          "runTime": 0,
     *          "apiReqTime": 5
     *      }
     *  }
     * @apiVersion 0.0.2
     */
    @GetMapping("/status")
    @Permission("panel.status")
    public SystemManager.SystemStatus status(){
        return SystemManager.getStatus();
    }
}
