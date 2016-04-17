/**
 * Project Name:commons
 * File Name:WeatherWebServiceSoapProxyTest.java
 * Package Name:cn.com.WebXml
 * Date:2016年4月16日下午6:20:22
 * Copyright (c) 2016, alterhu2020@gmail.com All Rights Reserved.
 *
*/

package cn.com.WebXml;

import static org.junit.Assert.*;

import java.rmi.RemoteException;

import org.junit.Before;
import org.junit.Test;

import becausetesting.webserivce.WebXml.WeatherWebServiceSoapProxy;

/**
 * ClassName:WeatherWebServiceSoapProxyTest  
 * Function: TODO ADD FUNCTION.  
 * Reason:	 TODO ADD REASON.  
 * Date:     2016年4月16日 下午6:20:22 
 * @author   Administrator
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
public class WeatherWebServiceSoapProxyTest {

	private WeatherWebServiceSoapProxy proxy;
	@Before
	public void setUp() throws Exception {
		proxy=new WeatherWebServiceSoapProxy();
	}

	@Test
	public void testGetSupportProvince() {
		try {
			String[] supportProvince = proxy.getSupportProvince();
			for(int k=0;k<supportProvince.length;k++){
				System.out.println(supportProvince[k]);
			}
			
			String[] supportCity = proxy.getSupportCity("直辖市");
			for(int j=0;j<supportCity.length;j++){
				System.out.println("support: "+supportCity[j]);
			}
			
			String[] weatherbyCityName = proxy.getWeatherbyCityName("上海");
			for(int j=0;j<weatherbyCityName.length;j++){
				System.out.println("Weather: "+weatherbyCityName[j]);
			}
		} catch (RemoteException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
	}

	@Test
	public void testGetWeatherbyCityName() {
		//fail("Not yet implemented");
	}

	@Test
	public void testGetWeatherbyCityNamePro() {
		//fail("Not yet implemented");
	}

}

