package com.github.becausetesting.time;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.MonthDay;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.becausetesting.time.TimeUtils.UNIT;

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
		System.out.println(currentTime);
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
		LocalDateTime now = LocalDateTime.now();
		
		LocalDateTime plus = now.plus(-1, ChronoUnit.YEARS);
		long dateDiff = TimeUtils.dateDiff(UNIT.DAYS,plus, now);
		
		
		System.out.println(whichdayOfWeek);
	}

}
