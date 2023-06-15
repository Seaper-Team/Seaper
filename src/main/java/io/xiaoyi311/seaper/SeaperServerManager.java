package io.xiaoyi311.seaper;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import io.xiaoyi311.seaper.config.AppConfig;
import io.xiaoyi311.seaper.model.UserConfig;
import io.xiaoyi311.seaper.service.LangManager;
import io.xiaoyi311.seaper.service.UserManager;
import io.xiaoyi311.seaper.utils.ConfigUtil;
import io.xiaoyi311.seaper.utils.EnvCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * Seaper 主类
 * @author Xiaoyi311
 */
@SpringBootApplication
@Slf4j
public class SeaperServerManager {
	/**
	 * 版本号
	 */
	public static Integer version = 10;

	/**
	 * 配置读取
	 */
	public static AnnotationConfigApplicationContext config = new AnnotationConfigApplicationContext(AppConfig.class);

	/**
	 * 配置文件
	 */
	public static UserConfig userConfig;

	/**
	 * 启动时间戳
	 */
	public static long runTime = 0;

	/**
	 * 入口点
	 */
	public static void main(String[] args) {
		//初始化
		if (!EnvCheckUtil.check()) {
			return;
		}

		userConfig = ConfigUtil.load("config", UserConfig.class, new UserConfig());
		if(userConfig.debug){
			log.warn("WARRING! YOU ARE ENABLED THE DEBUG MODE!!");
			debugMode();
		}
		LangManager.initLanguage();
		UserManager.initUsers();

		runTime = System.currentTimeMillis();
		SpringApplication.run(SeaperServerManager.class, args);
	}

	/**
	 * 调试模式
	 */
	private static void debugMode() {
		LoggerContext loggerContext= (LoggerContext) LoggerFactory.getILoggerFactory();
		List<Logger> loggerList = loggerContext.getLoggerList();
		for(Logger logger : loggerList){
			logger.setLevel(Level.DEBUG);
		}
	}

	/**
	 * 启动配置
	 */
	@Bean
	public TomcatServletWebServerFactory servletContainer(){
		UserConfig config = userConfig;
		return new TomcatServletWebServerFactory(config == null ? 8080 : config.port) ;
	}
}
