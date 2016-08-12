package com.github.becauseQA.time;

import java.time.LocalDateTime;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TimeUtilsTest {

	//private TimeUtils time;
	@Before
	public void setUp() throws Exception {
		//time=new TimeUtils();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetCurrentTime() {
		LocalDateTime currentTime = TimeUtils.getCurrentTime();
		System.out.println("current time is:"+currentTime);
	}

	@Test
	public void testCurrentDate(){
		LocalDateTime currentUTCTime = TimeUtils.getCurrentUTCTime();
		System.out.println("Current time UTC format is:"+currentUTCTime); 
	}
	@Test
	public void testDateTimeString() {
		String dateTimeString = TimeUtils.dateTimeString(LocalDateTime.now(), "yyyy/MM/dd HH:mm:ss:n");
		System.out.println(dateTimeString);
	}

	@Test
	public void testString2DateTime() {
		LocalDateTime string2DateTime = TimeUtils.string2DateTime(LocalDateTime.now().toString(), "yyyy-MM-dd'T'HH:mm:ss.SSS");
		System.out.println(string2DateTime);
	}

	@Test
	public void testWhichdayOfWeek() {
		String whichdayOfWeek = TimeUtils.whichdayOfWeek(LocalDateTime.now());
		System.out.println(whichdayOfWeek);
	}

}
