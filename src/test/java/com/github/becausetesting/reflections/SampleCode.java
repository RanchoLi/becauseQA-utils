package com.github.becausetesting.reflections;

import org.omg.CORBA.SystemException;

public class SampleCode {

	private int a=3;
	
	public void getvalue(){
		System.out.println(getA());
	}
	
	public void setvalue(){
		setA(4);
		System.out.println("a="+a);
	}

	public int getA() {
		return a;
	}

	public void setA(int a) {
		this.a = a;
	}
	
}
