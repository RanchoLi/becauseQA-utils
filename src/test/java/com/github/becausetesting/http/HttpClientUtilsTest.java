package com.github.becausetesting.http;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HttpClientUtilsTest {

	private TempHttpClientUtils httpClientUtils;

	@Before
	public void setUp() throws Exception {
		httpClientUtils = new TempHttpClientUtils();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		httpClientUtils.getRequest("https://selenium-release.storage.googleapis.com/?delimiter=/&prefix=");
	}

}
