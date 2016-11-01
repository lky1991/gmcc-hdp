package com.ftpService.util;

import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
* @ClassName: SpringContextHolder
* @Description: 以静态变量保存Spring ApplicationContext, 可在代码任何地方任何时候中取出ApplicaitonContext
* @author wally@hudongpai.com
* @date 2016-6-27 上午11:02:54
*/

@SuppressWarnings({"rawtypes","unchecked"})
public class SpringContextHolder implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	/**
	* @Title: setApplicationContext
	* @Description: 实现ApplicationContextAware接口的context注入函数, 将其存入静态变量.
	* @param applicationContext    
	 */
	public void setApplicationContext(ApplicationContext applicationContext) {
		SpringContextHolder.applicationContext = applicationContext;
		System.out.println("-----配置文件已经加载-----");
	}

	/**
	* @Title: getApplicationContext
	* @Description: 取得存储在静态变量中的ApplicationContext.
	 */
	public static ApplicationContext getApplicationContext() {
		checkApplicationContext();
		return applicationContext;

	}

	
	/**
	* @Title: getBean
	* @Description: T从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
	* @param name
	 */
	public static <T> T getBean(String name) {
		checkApplicationContext();
		return (T) applicationContext.getBean(name);
	}
		
	/**
	* @Title: getBean
	* @Description: 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.如果有多个Bean符合Class, 取出第一个.
	* @param clazz
	* @return   
	* @return     返回类型
	 */
	public static <T> T getBean(Class<T> clazz) {
		checkApplicationContext();
		
		Map beanMaps = applicationContext.getBeansOfType(clazz);
		if (beanMaps != null && !beanMaps.isEmpty()) {
			return (T) beanMaps.values().iterator().next();
		} else {
			return null;
		}
	}

	private static void checkApplicationContext() {
		if (applicationContext == null) {
			throw new IllegalStateException("applicaitonContext未注入,请在applicationContext.xml中定义SpringContextHolder");
		}
	}
}