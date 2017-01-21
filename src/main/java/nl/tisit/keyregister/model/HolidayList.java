package nl.tisit.keyregister.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HolidayList {
	private List<LocalDate> holidayDates = new ArrayList<LocalDate>();
	private List<LocalDate> holidaysNorthDates = new ArrayList<LocalDate>();
	private List<LocalDate> holidaysMiddleDates = new ArrayList<LocalDate>();
	private List<LocalDate> holidaysSouthDates = new ArrayList<LocalDate>();

	public Boolean isHoliday(LocalDate date) {
		return holidayDates.contains(date);
	}

	public Boolean isHolidaysNorth(LocalDate date) {
		return holidaysNorthDates.contains(date);
	}

	public Boolean isHolidaysMiddle(LocalDate date) {
		return holidaysMiddleDates.contains(date);
	}

	public Boolean isHolidaysSouth(LocalDate date) {
		return holidaysSouthDates.contains(date);
	}

	public Integer holidaysCount(LocalDate date) {
		return ((holidaysNorthDates.contains(date)?1:0)+(holidaysMiddleDates.contains(date)?1:0)+(holidaysSouthDates.contains(date)?1:0));	
	}
	
	public List<LocalDate> getHolidayDates() {
		return holidayDates;
	}

	public void setHolidayDates(List<LocalDate> holidayDates) {
		this.holidayDates = holidayDates;
	}

	public List<LocalDate> getHolidaysNorthDates() {
		return holidaysNorthDates;
	}

	public void setHolidaysNorthDates(List<LocalDate> holidaysNorthDates) {
		this.holidaysNorthDates = holidaysNorthDates;
	}

	public List<LocalDate> getHolidaysMiddleDates() {
		return holidaysMiddleDates;
	}

	public void setHolidaysMiddleDates(List<LocalDate> holidaysMiddleDates) {
		this.holidaysMiddleDates = holidaysMiddleDates;
	}

	public List<LocalDate> getHolidaysSouthDates() {
		return holidaysSouthDates;
	}

	public void setHolidaysSouthDates(List<LocalDate> holidaysSouthDates) {
		this.holidaysSouthDates = holidaysSouthDates;
	}
}
