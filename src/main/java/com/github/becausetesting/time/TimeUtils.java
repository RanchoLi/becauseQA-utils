package com.github.becausetesting.time;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class TimeUtils {

	
	
	public LocalDateTime getCurrentTime(){
		return LocalDateTime.now();
	}
	
	
	public String dateTimeString(LocalDateTime date,String pattern){
		return date.format(DateTimeFormatter.ofPattern(pattern));
	}
	
	public LocalDateTime string2DateTime(String datetime,String pattern){
		return LocalDateTime.parse(datetime, DateTimeFormatter.ofPattern(pattern));
	}
	
	public String whichdayOfWeek(LocalDateTime datetime){
		String displayName = datetime.getDayOfWeek().getDisplayName(TextStyle.FULL_STANDALONE, Locale.CHINA);
		return displayName;
	}
}
