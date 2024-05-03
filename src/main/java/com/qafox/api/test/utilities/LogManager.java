package com.qafox.api.test.utilities;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.qafox.api.test.testdata.DataConstants;

public class LogManager {

	public static Logger getLogger(Class object) {
		PropertyConfigurator.configure(DataConstants.log_properties_path);
		return Logger.getLogger(object);
	}
}