package com.reunion.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.reunion.common.FullTime;

/**
 * @author k.azangue
 */
/**
 * @author Kevin Azangue
 */
public final class CalendarUtils {
	private static DateFormat df;
	private static Instant instant;

	/**
	 * Convert from actual date to a String with a defined format
	 *
	 * @return string
	 */
	public static String getCalendarToday() {
		df = new SimpleDateFormat("EEE, d. MMM yyyy");
		return df.format(new Date());
	}

	/**
	 * Convert from actual date to a String with a defined format
	 *
	 * @return
	 */
	public static String getActualWeekDay() {
		df = new SimpleDateFormat("EEEE");
		return df.format(new Date());
	}

	/**
	 * @param date
	 * @return
	 */
	public static String monthOfDate(Date date) {
		if (date != null) {
			df = new SimpleDateFormat("MMMM");
			return df.format(date);
		}
		return null;
	}

	/**
	 * @return
	 */
	public static String actualMonthToString() {
		return monthOfDate(new Date());
	}

	/**
	 * @param localDate
	 * @return
	 */
	public static String monthOfLocalDate(LocalDate localDate) {
		if (localDate != null) {
			instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
			return monthOfDate(Date.from(instant));
		}
		return null;
	}

	/**
	 * Convert from actual date to a String with a defined format
	 *
	 * @return
	 */
	public static String getDayToday() {
		df = new SimpleDateFormat("d");
		return df.format(new Date());
	}

	/**
	 * Convert from actual date to a String with a defined format
	 *
	 * @return
	 */
	public static String getActualMonthYear() {
		df = new SimpleDateFormat("MMMM yyyy");
		return df.format(new Date());
	}

	/**
	 * Convert from actual date to a String with a defined format in Month and
	 * Year
	 *
	 * @param date
	 * @return
	 */
	public static String getMonthYear(Date date) {

		if (date != null) {
			df = new SimpleDateFormat("MMMM yyyy");
			return df.format(date);
		}
		return null;

	}

	/**
	 * Convert from Date to String with a defined format "12:59"
	 *
	 * @param date
	 * @return
	 */
	public static String getHourMinute(Date date) {
		if (date != null) {
			df = new SimpleDateFormat("HH:mm");
			return df.format(date);
		}
		return null;
	}

	/**
	 * Convert from Date to a String with a defined format "Mo. 01.01.14"
	 *
	 * @param date
	 * @return String
	 */
	public static String getShortDayMonthYear(Date date) {
		if (date != null) {
			DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, Locale.GERMANY);
			// df = new SimpleDateFormat("E. dd.MM.YY");
			return df.format(date);

		}
		return null;
	}


	/**
	 * Convert from Date to a String with a defined format "Mo. 01.01.14"
	 *
	 * @param date
	 * @return String
	 */
	public static String getShortDayMonthYear(LocalDate ldate) {
		if (ldate != null) {
			DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, Locale.GERMANY);
			// df = new SimpleDateFormat("E. dd.MM.YY");
			return df.format(localDateToDate(ldate));

		}
		return null;
	}


	/**
	 * Convert from LocalDate to a String with a defined format "Mo. 01.01.14"
	 *
	 * @param date
	 * @return String
	 */
	public static String getLocalDateShortDayMonthYear(LocalDate lDate) {
		Date date = localDateToDate(lDate);
		if (date == null) {
			return null;
		}
		return getShortDayMonthYear(date);
	}

	/**
	 * Convert from Date to a String with a defined format "Mo. 01.01.14  12:59"
	 *
	 * @param date
	 * @return String
	 */

	public static String getCompleteDateShort(Date date) {
		if (date != null) {
			df = new SimpleDateFormat("E. dd.MM.YY  HH:mm");
			return df.format(date);
		}
		return null;
	}

	/**
	 * Convert from LocalDateTime to a String with a defined format "Mo. 01.01.14  12:59"
	 *
	 * @param date
	 * @return String
	 */

	public static String getCompleteDateShort(LocalDateTime date) {
		if (date != null) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E. dd.MM.YY  HH:mm");
			return date.format(formatter);
		}
		return null;
	}

	/**
	 * Convert from LocaleDate to Date
	 *
	 * @param localDate
	 * @return
	 */
	public static Date localDateToDate(LocalDate localDate) {
		if (localDate != null) {
			instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
			return Date.from(instant);
		}
		return null;
	}

	/**
	 * Convert from Date to LocalDate
	 *
	 * @param date
	 * @return LocalDate
	 */
	public static LocalDate DateToLocalDate(Date date) {
		if (date != null) {
			instant = Instant.ofEpochMilli(date.getTime());
			return LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
		}
		return null;
	}

	/**
	 * @param date
	 * @return
	 */
	public static LocalTime DateToLocaltime(Date date) {
		if (date != null) {
			instant = Instant.ofEpochMilli(date.getTime());
			return LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalTime();
		}
		return null;
	}

	/**
	 * @param localDateTime
	 * @return
	 */
	public static Date LocaldatetimeToDate(LocalDateTime localDateTime) {
		if (localDateTime != null) {
			return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
		}
		return null;
	}

	/**
	 * calculates the Time difference between two dates. Returns values as day,
	 * hour, minutes and seconds,
	 *
	 * @param dateLater
	 *        ,java.util.Date
	 * @param dateBefore
	 *        ,java.util.Date
	 * @return Fulltime
	 * @see class FullTime
	 */
	public static FullTime calcTimeDifference(Date dateLater, Date dateBefore) {
		long diff = dateLater.getTime() - dateBefore.getTime();
		return new FullTime(diff / (24 * 60 * 60 * 1000), diff / (60 * 60 * 1000) % 24, diff / (60 * 1000) % 60, (diff / 1000) % 60);
	}

	/**
	 * return the date at the midgnight time.
	 *
	 * @param date
	 * @return 2013-08-10 00:00:00
	 */
	public static Date getMidnightOfTheDate(Date date) {
		if (date != null) {
			LocalTime lt = LocalTime.of(0, 0, 0);
			LocalDateTime l = LocalDateTime.of(DateToLocalDate(date), lt);
			return LocaldatetimeToDate(l);
		}
		return null;
	}

	/**
	 * return the date at the end.
	 *
	 * @param date
	 * @return 2013-08-10 23:59:59
	 */
	public static Date getEndOfTheDate(Date date) {
		if (date != null) {
			LocalTime lt = LocalTime.of(23, 59, 59);
			LocalDateTime l = LocalDateTime.of(DateToLocalDate(date), lt);
			return LocaldatetimeToDate(l);
		}
		return null;
	}

	/**
	 * Date1 before date2.
	 *
	 * @param date1
	 *        the date1
	 * @param date2
	 *        the date2
	 * @param ignoreTime
	 *        ignore the time
	 * @return true, if successful, false if not or one or both given dates are
	 *         null
	 */
	public static boolean date1BeforeDate2(Date date1, Date date2, boolean ignoreTime) {
		if ((date1 != null) && (date2 != null)) {
			if (ignoreTime) {
				return DateToLocalDate(date1).isBefore(DateToLocalDate(date2));
			} else {
				return DateToLocalDate(date1).isBefore(DateToLocalDate(date2));
			}
		} else {
			return false;
		}
	}

	/**
	 * test if one date is between 2 dates
	 *
	 * @param date1
	 *        starttime
	 * @param date2
	 *        endtime
	 * @param between
	 *        time to test
	 * @return boolean
	 */
	public static boolean dateIsBetween2Dates(Date date1, Date date2, Date between) {
		if ((date1 != null) && (date2 != null) && (between != null)) {
			return DateToLocalDate(date1).isBefore(DateToLocalDate(between)) && DateToLocalDate(between).isBefore(DateToLocalDate(date2));
		}
		return false;
	}

	/**
	 * Date to local date time.
	 *
	 * @param date
	 *        the date
	 * @return the local date time, if date is null it returns null
	 */
	public static LocalDateTime dateToLocalDateTime(Date date) {
		if (date != null) {
			return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
		} else {
			return null;
		}
	}

	public static boolean isDayOfWeek(LocalDate localDate, DayOfWeek dayOfWeek) {
		return localDate.getDayOfWeek() == DayOfWeek.SUNDAY;
	}

	/**
	 * @param minStep
	 *        (>0)
	 * @return
	 */
	public static LocalTime getLocalTimeMinStep(int minStep) {
		if (minStep <= 0)
			return null;

		Date date = new Date();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		int minute = calendar.get(Calendar.MINUTE);
		int mod = minute % minStep;

		calendar.add(Calendar.MINUTE, mod < 1 ? -mod : (minStep - mod));

		LocalTime localTime = LocalTime.of(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
		if (localTime.isAfter(LocalTime.of(19, 00))) {
			localTime = LocalTime.of(18, 45);
		} else if (localTime.isBefore(LocalTime.of(7, 00))) {
			localTime = LocalTime.of(7, 00);
		}

		return localTime;
	}

	public static boolean skontoExpiered(LocalDate billncomedate, int skontoDays) {
		/*
		 * Rechnungseingang
		 */
		LocalDate start = billncomedate;
		LocalDate stop = start;
		int addDay = 0;

		/*
		 * find sunndays and count days to extend
		 */
		while (!start.isEqual(stop.plusDays(skontoDays))) {
			if (start.getDayOfWeek().getValue() == 7)
				addDay++;
			start = start.plusDays(1);
		}

		LocalDate end = start.plusDays(skontoDays + addDay);
		int i = 0;

		while (!start.isEqual(end)) {
			System.out.println(start.getDayOfWeek() + "Count of days" + i);
			start = start.plusDays(1);
			i++;
		}
		return start.isAfter(LocalDate.now());
	}

	/**
	 * return the actual Week of the given year.
	 *
	 * @param localDate
	 *        the date to get the week.
	 * @return the week number of the year.
	 */
	public static int getWeekOfYear(LocalDate localDate) {
		Date date = new Date();
		if (localDate != null) {
			date = localDateToDate(localDate);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			return cal.get(Calendar.WEEK_OF_YEAR);
		}

		return 0;
	}


	/**
	 * Gets the file formatted date.
	 *
	 * @param date
	 *            the date
	 * @return the file formatted date
	 */
	public static String getFileFormattedDate(Date date) {
		String returnValue = "";

		if (date != null) {
			df = new SimpleDateFormat("yyyyMMdd");

			returnValue = df.format(date);
		}

		return returnValue;
	}

	/**
	 * @param start
	 * @param end
	 * @param minDay
	 * @param maxDay
	 * @return
	 */
	public static boolean daysBetween(Date start, Date end, int minDay, int maxDay) {
		LocalDate max = DateToLocalDate(start);
		LocalDate min = DateToLocalDate(end);

		long dateMax = max.toEpochDay();
		long dateMin = min.toEpochDay();

		if ((dateMax - dateMin) >= minDay && (dateMax - dateMin) <= maxDay) {
			return true;
		}
		return false;
	}



	/**
	 * Convert date to string in form "01.01.1900"
	 *
	 * @param date the date to be converted.
	 * @return string value of the given date.
	 */
	public static String convertDateToStringDayMonthYear(Date date) {
		String returnValue = "";
		if(date != null) {
			SimpleDateFormat smp = new SimpleDateFormat("dd.MM.yyyy");
			returnValue = smp.format(date);
		}
		return returnValue;
	}

	public static LocalDate getNextMonth(LocalDate date){
		return date.plusMonths(1);
	}
	public static LocalDate getPreviousMonth(LocalDate date){
		return date.minusMonths(1);
	}

	public static long daysDifference(LocalDate startDate, LocalDate endDate){
		LocalDate template = LocalDate.from(startDate);
		return template.until(endDate, ChronoUnit.DAYS);
	}

	public static long monthsDifference(LocalDate startDate, LocalDate endDate){
		LocalDate template = LocalDate.from(startDate);
		return template.until(endDate, ChronoUnit.MONTHS);
	}

	public static long yearsDifference(LocalDate startDate, LocalDate endDate){
		LocalDate template = LocalDate.from(startDate);
		return template.until(endDate, ChronoUnit.YEARS);
	}

	public static int dayOfMonth(LocalDate date){
		return date.getDayOfMonth();
	}

	public static int lastDayOfMonth(LocalDate date){
		return date.withDayOfMonth(date.lengthOfMonth()).getDayOfMonth();
	}


	/**
	 * Subtracts working days from given day
	 * Working days mean Saturday, Sunday excluded
	 *
	 * @param date Date from which subtract workdays
	 * @param workdays Number of workdays to subtract
	 * @return LocalDate minus workdays
	 */
	public static LocalDate subtractBusinessDaysFromLocalDate(LocalDate date, int workdays){
		    if (workdays < 1) {
		        return date;
		    }
		    LocalDate result = date;
		    int addedDays = 0;
		    while (addedDays < workdays) {
		        result = result.minusDays(1);
		        if (!(result.getDayOfWeek() == DayOfWeek.SATURDAY ||
		              result.getDayOfWeek() == DayOfWeek.SUNDAY)) {
		            ++addedDays;
		        }
		    }
		    return result;
	}

	/**
	 * Subtracts working days from given day
	 * Working days mean Saturday, Sunday excluded
	 *
	 * @param date Date from which subtract workdays
	 * @param workdays Number of workdays to subtract
	 * @return LocalDate minus workdays
	 */
	public static LocalDate addBusinessDaysFromLocalDate(LocalDate date, int workdays){
		    if (workdays < 1) {
		        return date;
		    }
		    LocalDate result = date;
		    int addedDays = 0;
		    while (addedDays < workdays) {
		        result = result.plusDays(1);
		        if (!(result.getDayOfWeek() == DayOfWeek.SATURDAY ||
		              result.getDayOfWeek() == DayOfWeek.SUNDAY)) {
		            ++addedDays;
		        }
		    }
		    return result;
	}
}
