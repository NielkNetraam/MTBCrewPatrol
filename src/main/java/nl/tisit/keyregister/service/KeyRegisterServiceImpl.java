package nl.tisit.keyregister.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.tisit.keyregister.model.Holiday;
import nl.tisit.keyregister.model.HolidayList;
import nl.tisit.keyregister.model.Holidays;
import nl.tisit.keyregister.repository.HolidayRepository;
import nl.tisit.keyregister.repository.HolidaysRepository;

@Service
public class KeyRegisterServiceImpl implements KeyRegisterService {
	@Autowired
	protected HolidayRepository holidayRepository;
	@Autowired
	protected HolidaysRepository holidaysRepository;
	
	@Override
	public HolidayList getHolidayList() {
		
		List<LocalDate> holidayDates = new ArrayList<LocalDate>();
		List<LocalDate> holidaysNorthDates = new ArrayList<LocalDate>();
		List<LocalDate> holidaysMiddleDates = new ArrayList<LocalDate>();
		List<LocalDate> holidaysSouthDates = new ArrayList<LocalDate>();

		for (Holiday holiday : holidayRepository.findAll()) 
			holidayDates.add(holiday.getHoliday());
		
		for (Holidays holidays : holidaysRepository.findAll()) {
			LocalDate current = holidays.getStart();
			LocalDate end = holidays.getEnd().plusDays(1);

			while (current.isBefore(end)) {
				switch (holidays.getRegion()) {
				case NORTH:
					holidaysNorthDates.add(current);
					break;
				case MIDDLE:
					holidaysMiddleDates.add(current);
					break;
				case SOUTH:
					holidaysSouthDates.add(current);
					break;
				default:
					holidaysNorthDates.add(current);
					holidaysMiddleDates.add(current);
					holidaysSouthDates.add(current);
				}
			
				current = current.plusDays(1);
			}
		}

		HolidayList holidayList = new HolidayList();
		
		holidayList.setHolidayDates(holidayDates);
		holidayList.setHolidaysNorthDates(holidaysNorthDates);
		holidayList.setHolidaysMiddleDates(holidaysMiddleDates);
		holidayList.setHolidaysSouthDates(holidaysSouthDates);

		return holidayList;
	}
}
