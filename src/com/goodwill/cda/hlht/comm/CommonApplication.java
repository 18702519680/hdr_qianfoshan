package com.goodwill.cda.hlht.comm;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.ContextLoader;

/**
 * 公共 获取 spring application 方法
 * @Description
 * 类描述：
 * @author liuzhi
 * @Date 2017年12月6日
 * @modify
 * 修改记录：
 *
 */
public class CommonApplication {

	private static ApplicationContext app = null;

	public static ApplicationContext getApplicationContext() {
		if (app != null) {
			return app;
		}
		ApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		if (wac == null) {
			wac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		}
		app = wac;
		return app;
	}

}
