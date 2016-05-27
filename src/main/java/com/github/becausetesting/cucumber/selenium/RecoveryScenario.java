/**
 * Project Name:PAF_HC
 * File Name:RecoveryScenario.java
 * Package Name:com.hp.utility
 * Date:Aug 22, 20139:23:06 AM
 * Copyright (c) 2013, alterhu2020@gmail.com All Rights Reserved.
 *
 */

package com.github.becausetesting.cucumber.selenium;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * ClassName:RecoveryScenario
 * 
 * Date: Aug 22, 2013 9:23:06 AM
 * 
 * @author huchan
 * 
 * @since JDK 1.6
 * @see
 * @version $Revision: 1.0 $
 * @description : still in developing
 */
public class RecoveryScenario implements Thread.UncaughtExceptionHandler {

	// private WebDriver driver;

	private static Logger logger = LogManager.getLogger(RecoveryScenario.class);

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		// TODO Auto-generated method stub
		logger.info("Trigger default RecoveryScenario: "+t.getName()+",Exception: ",e);
	}

	

}
