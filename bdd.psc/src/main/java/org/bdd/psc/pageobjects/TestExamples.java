package org.bdd.psc.pageobjects;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestExamples {

	public static void main(String[] args) {
		//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
		
		LocalDate start = LocalDate.now();
		LocalDate end = LocalDate.now().plusMonths(18)
				.with(TemporalAdjusters.lastDayOfMonth());
		List<String> dates = Stream
				.iterate(start, date -> date.plusDays(1))
				.limit(ChronoUnit.DAYS.between(start, end))
				.map(element->(String)element.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")))
				.collect(Collectors.toList());
		System.out.println(dates);
	}

}
