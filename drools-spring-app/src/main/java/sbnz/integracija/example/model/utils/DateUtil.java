package sbnz.integracija.example.model.utils;

import java.time.LocalDate;

public class DateUtil {
	
	public static int getDaysElapsed(LocalDate first) {
	    LocalDate today = LocalDate.now();
	    long daysElapsed = java.time.temporal.ChronoUnit.DAYS.between( first , today );	    
	    return (int) daysElapsed;
	}
}
