package com.acme.tvshows.util;

import java.io.InputStream;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.Properties;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeanFactory {
	private static Logger logger = LoggerFactory.getLogger(BeanFactory.class);
	private static final String PROPERTIES_EXTENSION = ".properties";
	private static Map<Class<?>,Object> singletons = new HashMap<Class<?>,Object>();

	public static <T> T getInstance(Class<T> clazz) {
		T result = null;
		try {
			boolean isSingleton = clazz.isAnnotationPresent(Singleton.class);
			if (isSingleton && singletons.containsKey(clazz)) {
				result = clazz.cast(singletons.get(clazz));
			} else {
				result = clazz.newInstance();
				if (clazz.isAnnotationPresent(Configured.class)) {
					configure(result);
				}
				if (isSingleton) {
					registerSingleton(clazz, result);
				}
			}
		} catch (ReflectiveOperationException e) {
			logger.error("Can't load instance for class " + clazz.getName(), e);
		}
		return result;
	}

	private static synchronized <T> void registerSingleton(Class<T> clazz, T singleton) throws ReflectiveOperationException {
		if (!singletons.containsKey(clazz)) {
			singletons.put(clazz, singleton);
		}
	}

	private static void configure(Object bean) throws ReflectiveOperationException {
		BeanUtils.populate(bean, loadConfigurationProperties(bean));
	}

	private static Map<String,String> loadConfigurationProperties(Object bean) throws InstantiationException {
		final String resourcePath = bean.getClass().getSimpleName() + PROPERTIES_EXTENSION;
		Properties props = new Properties();
		try (InputStream is = bean.getClass().getResourceAsStream(resourcePath)) {
			if (is == null) {
				logger.warn("No properties file for class {}", bean.getClass().getName());
			} else {
				props.load(is);
			}
		} catch (IOException e) {
			throw new InstantiationException("Can't load properties file for class " + resourcePath);
		}
		Map<String,String> result = new HashMap<String,String>();
		for (String key : props.stringPropertyNames()) {
			result.put(key, props.getProperty(key));
		}
		return result;
	}
}
