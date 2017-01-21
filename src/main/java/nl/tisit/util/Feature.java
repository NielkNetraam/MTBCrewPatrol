package nl.tisit.util;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class Feature {
	public static Integer shiftDayOfWeek(LocalDate date) {
		return ((date.getDayOfWeek().getValue()+3)%7+1);
	}

	public static Integer shiftMonth(LocalDate date) {
		return ((date.getMonthValue()+6-1)%12+1);
	}

	public static Integer shiftDayOfYear(LocalDate date) {
		return ((date.getDayOfYear()-1+183)%366+1);

	}
	
	public static LocalDate startSummerTime(Integer year) {
		LocalDate date = LocalDate.of(year, 3, 31);
		
		while (!date.getDayOfWeek().equals(DayOfWeek.SUNDAY)) 
			date = date.minusDays(1);
		
		return date;	
	}

	public static LocalDate endSummerTime(Integer year) {
		LocalDate date = LocalDate.of(year, 10, 31);
		
		while (!date.getDayOfWeek().equals(DayOfWeek.SUNDAY)) 
			date = date.minusDays(1);
		
		return date;		
	}

	public static Boolean isSummerTime(LocalDate date) {
		if (date.getMonthValue() < 3 || date.getMonthValue() > 10)
			return false;
		else if (date.getMonthValue() > 3 && date.getMonthValue() < 10)
			return true;
		else if (date.getMonthValue() == 3) 
			return !date.isBefore(startSummerTime(date.getYear()));
		else 
			return date.isBefore(endSummerTime(date.getYear()));	
	}
}
