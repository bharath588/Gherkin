package org.bdd.psc.pageobjects;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestExamples {

	public static void main(String[] args) {
		
		LocalDate date=LocalDate.of(2018,05,06);
		//LocalDate date=LocalDate.of(05,21,2018);
		System.out.println("hi");
        for(int i=0;i<date.lengthOfMonth();i++){
        	System.out.println(date.getDayOfWeek().toString());
            if("Sunday".equalsIgnoreCase(date.getDayOfWeek().toString())){
            	System.out.println("Match found");

                break;
            }else{
                date=date.plusDays(1);
            }
        }

        //System.out.println(date.getDayOfMonth());

	}

}
