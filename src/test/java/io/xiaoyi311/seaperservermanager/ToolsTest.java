package io.xiaoyi311.seaperservermanager;

import io.xiaoyi311.seaperservermanager.model.UserConfig;
import io.xiaoyi311.seaperservermanager.service.LangManager;
import io.xiaoyi311.seaperservermanager.utils.ConfigUtil;
import io.xiaoyi311.seaperservermanager.utils.EnvCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;

/**
 * 工具测试
 * @author Xiaoyi311
 */
@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("工具")
class ToolsTest {
	/**
	 * 环境检测
	 */
	@Test
	@Order(0)
	@DisplayName("环境变量")
	void env() throws IOException {
		log.info("ParentPath: " + SeaperServerManager.config.getBean(ResourceLoader.class).getResource("file:").getFile().getCanonicalPath());

		EnvCheckUtil.check();
	}

	@DisplayName("国际化")
	@Test
	void lang(){
		UserConfig config = new UserConfig();
		config.lang = "en_US";
		SeaperServerManager.userConfig = config;
		LangManager.initLanguage();
		log.info(LangManager.msg("console.loginUser", "Xiaoyi311", "Test"));
	}

	@DisplayName("配置")
	@Test
	void config(){
		UserConfig userConfig = ConfigUtil.load("test_config", UserConfig.class, new UserConfig());
		log.info("端口: " + userConfig.port);

		userConfig.port++;
		ConfigUtil.set("test_config", userConfig);

		userConfig = ConfigUtil.load("test_config", UserConfig.class, new UserConfig());
		log.info("修改后端口: " + userConfig.port);
	}
}
