package com.github.becausetesting.random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.becausetesting.lang.RandomUtils;

public class RandomUtilsTest {

	private RandomUtils RandomUtilsTest;
	@Before
	public void setUp() throws Exception {
		RandomUtilsTest=new RandomUtils();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetRandomNumber() {
		int randomNumber = RandomUtilsTest.getRandomNumber(1, 3);
		System.out.println(randomNumber);
	}

	@Test
	public void testGetGUID() {
		String guid = RandomUtilsTest.getGUID();
		System.out.println("guid is: "+guid);
	}

	@Test
	public void testGetMaxNumber() {
		int maxNumber = RandomUtilsTest.getMinNumber2(new int[]{1,30,4,2,50,3,300});
		//assertEquals(30, maxNumber);
		System.out.println("min is:"+maxNumber); 
	}
	@Test
	public void testGetRandomString() {
		
	}

	
}
