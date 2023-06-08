package io.xiaoyi311.seaper.service;

import io.xiaoyi311.seaper.SeaperServerManager;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;

import java.util.Properties;

/**
 * 系统数据管理器
 * @author Xiaoyi311
 */
public class SystemManager {
    /**
     * API 请求次数
     */
    public static Integer apiReqTime = 0;

    /**
     * 系统信息
     */
    static Properties properties = System.getProperties();

    /**
     * 系统其他信息
     */
    static SystemInfo info = new SystemInfo();

    /**
     * 获取系统信息
     * @return 系统信息
     */
    public static SystemStatus getStatus() {
        return new SystemStatus(
                getInfomation(),
                properties.getProperty("user.name"),
                getCpu(),
                getRam()
        );
    }

    /**
     * 获取 RAM 占用
     * @return RAM 占用
     */
    private static double getRam() {
        GlobalMemory memory = info.getHardware().getMemory();
        long totalByte = memory.getTotal();
        long acaliableByte = memory.getAvailable();
        return Double.parseDouble(String.format("%.2f", (totalByte-acaliableByte) * 1.0 / totalByte));
    }

    /**
     * 获取 CPU 占用
     * @return CPU 占用
     */
    private static double getCpu() {
        CentralProcessor processor = info.getHardware().getProcessor();
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        long[] ticks = processor.getSystemCpuLoadTicks();
        long nice = ticks[CentralProcessor.TickType.NICE.getIndex()] - prevTicks[CentralProcessor.TickType.NICE.getIndex()];
        long irq = ticks[CentralProcessor.TickType.IRQ.getIndex()] - prevTicks[CentralProcessor.TickType.IRQ.getIndex()];
        long softirq = ticks[CentralProcessor.TickType.SOFTIRQ.getIndex()] - prevTicks[CentralProcessor.TickType.SOFTIRQ.getIndex()];
        long steal = ticks[CentralProcessor.TickType.STEAL.getIndex()] - prevTicks[CentralProcessor.TickType.STEAL.getIndex()];
        long cSys = ticks[CentralProcessor.TickType.SYSTEM.getIndex()] - prevTicks[CentralProcessor.TickType.SYSTEM.getIndex()];
        long user = ticks[CentralProcessor.TickType.USER.getIndex()] - prevTicks[CentralProcessor.TickType.USER.getIndex()];
        long iowait = ticks[CentralProcessor.TickType.IOWAIT.getIndex()] - prevTicks[CentralProcessor.TickType.IOWAIT.getIndex()];
        long idle = ticks[CentralProcessor.TickType.IDLE.getIndex()] - prevTicks[CentralProcessor.TickType.IDLE.getIndex()];
        long totalCpu = user + nice + cSys + idle + iowait + irq + softirq + steal;
        return Double.parseDouble(String.format("%.2f", 1.0 - (idle * 1.0 / totalCpu)));
    }

    /**
     * 获取主机信息
     * @return 主机信息
     */
    private static String getInfomation() {
        StringBuilder sb = new StringBuilder();
        sb.append(properties.getProperty("os.name")).append("; ");
        sb.append(properties.getProperty("os.arch")).append("; ");
        sb.append(properties.getProperty("os.version")).append("; ");
        return sb.toString();
    }


    /**
     * 系统状态数据
     */
    public static class SystemStatus{
        /**
         * 主机信息
         */
        public String infomation;

        /**
         * 启动用户
         */
        public String appUser;

        /**
         * CPU 使用百分比
         */
        public double cpu;

        /**
         * CPU 使用百分比
         */
        public double ram;

        /**
         * 登录成功次数
         */
        public int loginSuccessTime = UserManager.loginSuccessTimes;

        /**
         * 登录失败次数
         */
        public int loginBadTime = UserManager.loginBadTimes;

        /**
         * 运行时间
         */
        public long runTime = System.currentTimeMillis() - SeaperServerManager.runTime;

        /**
         * API 请求次数
         */
        public int apiReqTime = SystemManager.apiReqTime;

        /**
         * 实例化
         */
        public SystemStatus(String info, String appUser, double cpu, double ram){
            infomation = info;
            this.appUser = appUser;
            this.cpu = cpu;
            this.ram = ram;
        }
    }
}
