package com.github.becausetesting.exceptions;

import org.junit.Test;

public class NullPointerExceptionTest {

	String test[]={"1","ewewe"};
	@Test
	public void test1(){
		try{
		System.out.println(test[4]);
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		System.out.println("not continue the test,only use the try catch statement ");
	}
}
