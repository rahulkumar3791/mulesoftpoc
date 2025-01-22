package com.zenni.escher.request;

import java.io.InputStream;
import java.util.Properties;

public class PropertyAccess {

	public String getProperty(String key) {
		Properties prop = new Properties();
		try {
			InputStream input = getClass().getResourceAsStream("/config.properties");
			prop.load(input);

			InputStream input1 = getClass()
					.getResourceAsStream("/resources-" + prop.getProperty("env") + ".properties");
			prop.load(input1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prop.getProperty(key);
	}
}
