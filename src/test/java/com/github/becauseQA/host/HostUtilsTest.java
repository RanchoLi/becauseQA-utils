package com.github.becauseQA.host;

import org.junit.Test;

public class HostUtilsTest {

	@Test
	public void test() {
		String username=HostUtils.getCurrentUserName();
		System.out.println(username);
		
		String pingIPAddress = HostUtils.pingIPAddress("www.google.com", 3000);
		System.out.println(pingIPAddress);
	}

}
