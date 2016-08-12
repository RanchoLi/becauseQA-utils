package com.github.becauseQA.time;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public class TimeUtils {

	/*
	 * LocalDateTime ==Year +Month /YearMonth MonthDay DayOfWeek
	 * 
	 * 整个地球分为二十四时区，每个时区都有自己的本地时间。在国际无线电通信场合， 为了统一起见，使用一个统一的时间，称为通用协调时(UTC,
	 * Universal Time Coordinated)。 UTC与格林尼治平均时(GMT, Greenwich Mean
	 * Time)一样，都与英国伦敦的本地时相同。在本文中，UTC与GMT含义完全相同。 如果要把这个时间转化为UTC，可以使用一下公式： UTC +
	 * 时区差 ＝ 本地时间 时区差东为正，西为负。在此，把东八区时区差记为 +0800， UTC + (＋0800) = 本地（北京）时间 (1)
	 * 那么，UTC = 本地时间（北京时间)）- 0800 (2)
	 * 
	 * PST(pacific standard time,US) UTC is ahead 7 hours for PST time
	 * DST(daytime saving time)
	 * 
	 * What is UTC time? Prior to 1972, this time was called Greenwich Mean Time
	 * (GMT) but is now referred to as Coordinated Universal Time or Universal
	 * Time Coordinated (UTC). It is a coordinated time scale, maintained by the
	 * Bureau International des Poids et Mesures (BIPM). It is also known a
	 * "Z time" or "Zulu Time".
	 * 
	 * Is GMT and UTC the same thing? But GMT is a time zone and UTC is a time
	 * standard. The Royal Observatory in Greenwich, London, United Kingdom.
	 * Although GMT and UTC share the same current time in practice, there is a
	 * basic difference between the two: GMT is a time zone officially used in
	 * some European and African countries.
	 * 
	 */
	public static enum UNIT {
		YEAR, MONTH, WEEK, DAYS, HOURS, MINUTES, SECONDS, NANOSECONDS;
	}

	public static LocalDateTime getCurrentTime() {
		return LocalDateTime.now();
	}

	public static LocalDateTime getCurrentUTCTime() {
		return LocalDateTime.now(Clock.systemUTC());
		
		// return LocalDateTime.now(ZoneOffset.UTC);
	}

	public static LocalDateTime ofDateTime(int year, int month, int day) {
		return LocalDateTime.of(year, month, day, 0, 0);
	}

	public static LocalDateTime ofDateTime(Instant instant) {
		LocalDateTime ofInstant = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
		return ofInstant;
	}

	public static String dateTimeString(LocalDateTime date, String pattern) {
		return date.format(DateTimeFormatter.ofPattern(pattern));
	}

	public static LocalDateTime string2DateTime(String datetime, String pattern) {
		return LocalDateTime.parse(datetime, DateTimeFormatter.ofPattern(pattern));
	}

	public static long getSeconds(LocalDateTime dateTime) {
		// long epochSecond =
		// dateTime.atZone(ZoneId.systemDefault()).toEpochSecond();
		long epochSecond = dateTime.atZone(ZoneOffset.UTC).toEpochSecond();
		return epochSecond;
	}

	public static Instant toInstant(LocalDateTime dateTime) {
		Instant instant = dateTime.toInstant(ZoneOffset.UTC);
		return instant;
	}

	/**
	 * whichdayOfWeek: TODO
	 *
	 * @author Administrator
	 * @param datetime
	 *            LocalDateTime parameter.
	 * @return the return string week.
	 * @since JDK 1.8
	 */
	public static String whichdayOfWeek(LocalDateTime datetime) {
		String displayName = datetime.getDayOfWeek().getDisplayName(TextStyle.FULL_STANDALONE, Locale.CHINA);
		return displayName;
	}

	/**
	 * dateAdd:
	 *
	 * @author Administrator
	 * @param unit
	 *            the unit like week or minute.
	 * @param amount
	 *            the date amount.
	 * @param dateTime
	 *            the LocalDateTime object.
	 * @return localdatetime object.
	 * @since JDK 1.8
	 */
	public static LocalDateTime dateAdd(UNIT unit, long amount, LocalDateTime dateTime) {
		LocalDateTime localDateTime = LocalDateTime.now();
		switch (unit) {
		case YEAR:
			localDateTime = dateTime.plus(amount, ChronoUnit.YEARS);
			break;
		case MONTH:
			localDateTime = dateTime.plus(amount, ChronoUnit.MONTHS);
			break;
		case DAYS:
			localDateTime = dateTime.plus(amount, ChronoUnit.DAYS);
			break;
		case HOURS:
			localDateTime = dateTime.plus(amount, ChronoUnit.HOURS);
			break;
		case MINUTES:
			localDateTime = dateTime.plus(amount, ChronoUnit.MINUTES);
			break;
		case SECONDS:
			localDateTime = dateTime.plus(amount, ChronoUnit.SECONDS);
			break;
		case NANOSECONDS:
			localDateTime = dateTime.plus(amount, ChronoUnit.NANOS);
			break;
		case WEEK:
			localDateTime = dateTime.plus(amount, ChronoUnit.WEEKS);
			break;
		default:
			break;
		}

		return localDateTime;
	}

	public static long dateDiff(UNIT unit, LocalDateTime start, LocalDateTime end) {
		long amount = 0l;
		// Duration between = Duration.between(start, end);
		switch (unit) {
		case YEAR:
			amount = ChronoUnit.YEARS.between(start, end);
			break;
		case MONTH:
			amount = ChronoUnit.MONTHS.between(start, end);
			break;
		case WEEK:
			amount = ChronoUnit.WEEKS.between(start, end);
			break;
		case DAYS:
			amount = ChronoUnit.DAYS.between(start, end);
			break;
		case HOURS:
			amount = ChronoUnit.HOURS.between(start, end);
			break;
		case MINUTES:
			amount = ChronoUnit.MINUTES.between(start, end);

			// start.until(end, ChronoUnit.MINUTES);
			// TimeUnit.MINUTES.toHours(1);
			break;
		case SECONDS:
			amount = ChronoUnit.SECONDS.between(start, end);
			break;
		case NANOSECONDS:
			amount = ChronoUnit.NANOS.between(start, end);
			break;
		default:
			break;
		}
		return amount;
	}

}
