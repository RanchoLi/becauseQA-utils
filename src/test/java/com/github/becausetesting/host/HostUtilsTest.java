package com.github.becausetesting.host;

import static org.junit.Assert.*;

import org.junit.Test;

public class HostUtilsTest {

	@Test
	public void test() {
		String username=HostUtils.getCurrentUserName();
		System.out.println(username);
	}

}
