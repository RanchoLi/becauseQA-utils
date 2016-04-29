package com.github.becausetesting.apache.commons;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.List;

import org.junit.Test;

import com.github.becausetesting.http.TempHttpClientUtils;
import com.sun.jna.Platform;

import jxl.common.Logger;

public class FieldUtilsTest {

	private static Logger logger = Logger.getLogger(FieldUtilsTest.class);

	@Test
	public void testGetFieldClassOfQString()
			throws IllegalArgumentException, IllegalAccessException, InstantiationException {
		Class clazz = TempHttpClientUtils.class;
		Field field = FieldUtils.getDeclaredField(clazz, "DEFAULT_REQUEST_TIMEOUT", true);
		
		FieldUtils.writeStaticField(field, 323);
		
		Object object = field.get(null);
		logger.info(object);

	}

	@Test(expected = IllegalAccessException.class)
	public void testReadFieldFieldObject() throws IllegalArgumentException, IllegalAccessException, InstantiationException {
		// fail("Not yet implemented");
		// private memmbers
		List<Field> allFieldsList = FieldUtils.getAllFieldsList(Platform.class);
		for (Field fieldobj : allFieldsList) {
			logger.info(fieldobj.get(null));
		}
	}

	@Test
	public void testWriteStaticFieldFieldObject() {
		// fail("Not yet implemented");
	}

	@Test
	public void testWriteFieldFieldObjectObject() {
		// fail("Not yet implemented");
		//FieldUtils.writeField(field, target, value);
	}

	@Test
	public void testWriteFieldObjectStringObject() {
		//fail("Not yet implemented");
	}

}
