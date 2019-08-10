package com.thebe.webService.util;

import java.io.FileReader;
import java.util.Properties;

/**
 * Class to read address rest configuration via file
 * @author Thebe.Alfarisi
 * @since Dec, 21st 2018
 * @version 1.0
 *
 */
public class RestAddressConf {
	
	private static final String configAddress = "rest.conf";
	
	/**
	 * Method to get Web Service Address based on configuration file
	 * @param restConfName URL address
	 * @return Retrieved address from configuration file 
	 * @throws Exception
	 */
	public static String getRestAddress(String restConfName) throws Exception {
		String result = "";
		
		try {
			FileReader propFile = new FileReader(configAddress);
			if (propFile != null) {
				Properties prop = new Properties();
				prop.load(propFile);
				
				result = prop.getProperty(restConfName) == null ? "" : prop.getProperty(restConfName); 
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return result;
	}

}
