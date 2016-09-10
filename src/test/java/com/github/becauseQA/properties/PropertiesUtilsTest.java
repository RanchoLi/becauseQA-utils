package com.github.becauseQA.properties;

import java.io.File;
import org.junit.Test;

public class PropertiesUtilsTest {

	@Test
	public void test() {
		
		PropertiesUtils.setXmlPropertiesFile(new File("test.xml"));
		String propertyString = PropertiesUtils.getPropertyString("name");
		//PropertiesUtils.setPropertyString("name", "alterhu");
		System.out.println(propertyString);
	}

}
