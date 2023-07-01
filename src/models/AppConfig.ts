/**
 * 程序配置
 * @author Xiaoyi311
 */

export default class AppConfig{
    /**
     * 端口
     */
    port: number = 8088;

    /**
     * 默认语言
     */
    lang: string = "zh_CN";

    /**
     * 登录失败封禁时长 (秒)
     */
    loginStopTime: number = 60 * 60;

    /**
     * 登录失败尝试次数
     */
    loginTryTime: number = 5;
}