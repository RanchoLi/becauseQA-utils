package com.github.becausetesting.time;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TimeUtilsTest {

	private TimeUtils time;
	@Before
	public void setUp() throws Exception {
		time=new TimeUtils();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetCurrentTime() {
		LocalDateTime currentTime = time.getCurrentTime();
		System.out.println(currentTime);
	}

	@Test
	public void testDateTimeString() {
		String dateTimeString = time.dateTimeString(LocalDateTime.now(), "yyyy/MM/dd HH:mm:ss:n");
		System.out.println(dateTimeString);
	}

	@Test
	public void testString2DateTime() {
		LocalDateTime string2DateTime = time.string2DateTime(LocalDateTime.now().toString(), "yyyy-MM-dd'T'HH:mm:ss.SSS");
		System.out.println(string2DateTime);
	}

	@Test
	public void testWhichdayOfWeek() {
		String whichdayOfWeek = time.whichdayOfWeek(LocalDateTime.now());
		System.out.println(whichdayOfWeek);
	}

}
