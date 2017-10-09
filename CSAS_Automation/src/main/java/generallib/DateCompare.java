package generallib;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.sql.rowset.FilteredRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;

//import com.sun.rowset.FilteredRowSetImpl;

import db.filters.EqualsFilter;
import lib.DB;
import lib.Stock;

public class DateCompare 

{
	static boolean isBothEqual = false;
	static SimpleDateFormat formatter;
	private static FilteredRowSet rowset1;
  
	
//	public void formatDate(){
//		formatter = new SimpleDateFormat("dd-MMM-yyyy"); 
//	}
	
	 public static Date RandomDategenerate() {
		 
		 	Calendar cal = Calendar.getInstance();
			Date dMin = cal.getTime();
			cal.add(Calendar.YEAR, 1);
			Date dMax = cal.getTime();
		 
	        long MILLIS_PER_DAY = 1000*60*60*24;
	        GregorianCalendar s = new GregorianCalendar();
	        s.setTimeInMillis(dMin.getTime());
	        GregorianCalendar e = new GregorianCalendar();
	        e.setTimeInMillis(dMax.getTime());
	       
	        long endL   =  e.getTimeInMillis() +  e.getTimeZone().getOffset(e.getTimeInMillis());
	        long startL = s.getTimeInMillis() + s.getTimeZone().getOffset(s.getTimeInMillis());
	        long dayDiff = (endL - startL) / MILLIS_PER_DAY;
	       
	        Calendar cal1 = Calendar.getInstance();
	        cal1.setTime(dMin);
	        cal1.add(Calendar.DATE, new Random().nextInt((int)dayDiff));          
	        return cal1.getTime();
	    }
	   
	 static ResultSet getColumnData(String gc_id){
		 String GC_ID = gc_id.split("-")[0];
		 ResultSet rowset = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryToGetPayrollDate")[1], GC_ID);//returns payrolldate column with associated gc_id
		 return rowset;
	 }
	 
	 
	 public static Date getUniquedate(String gc_id) throws SQLException{
		 
		 Date date = null;
		 RowSetFactory rsf = RowSetProvider.newFactory();
	     FilteredRowSet rowset1 = rsf.createFilteredRowSet();
		 rowset1.populate(getColumnData(gc_id));
		 do{
			 date = RandomDategenerate();
			 EqualsFilter equalsFilter = new EqualsFilter(date, "PAYROLL_DATE");
		 rowset1.setFilter(equalsFilter);
		 
		 }while(DB.getRecordSetCount(rowset1)>0);
		 
		return date; 
		 
	 }
	
	public static boolean compareAnyDateWithCurrentDate(Date dateTobeCompared,String... formatTobeCompared)
	{
		if(formatTobeCompared.length != 0)
		{
			formatter = new SimpleDateFormat(formatTobeCompared[0]);
		}else{
			formatter = new SimpleDateFormat("dd-MMM-yyyy");
		}
		
		Calendar calender = Calendar.getInstance();
		String comparedDate = formatter.format(dateTobeCompared);
		String currentDate=formatter.format(calender.getTime());
		
		if(comparedDate.equalsIgnoreCase(currentDate))
		{
			isBothEqual = true;
		}else{
			isBothEqual = false;
		}
		return isBothEqual;
	}
	
	public static boolean compareAnyDateWithCurrentDate(String dateTobeCompared,String... formatTobeCompared) throws ParseException
	{
		if(formatTobeCompared.length != 0)
		{
			formatter = new SimpleDateFormat(formatTobeCompared[0]);
		}else{
			formatter = new SimpleDateFormat("dd-MMM-yyyy");
		}
		//formatter = new SimpleDateFormat("dd-MMM-yyyy");
		
		Date firstDatetoComapre = formatter.parse(dateTobeCompared);
		
		Calendar calender = Calendar.getInstance();
		String comparedDate = formatter.format(firstDatetoComapre);
		String currentDate=formatter.format(calender.getTime());
		
		if(comparedDate.equalsIgnoreCase(currentDate))
		{
			isBothEqual = true;
		}else{
			isBothEqual = false;
		}
		return isBothEqual;
	}
	
	
	public static boolean compareTwoDates(Date firstDatetoComapre,Date secondDatetoCompare)
	{
		formatter = new SimpleDateFormat("dd-MMM-yyyy");
		String firstDate = formatter.format(firstDatetoComapre);
		String secondDate = formatter.format(secondDatetoCompare);
		
		if(firstDate.equalsIgnoreCase(secondDate))
		{
			isBothEqual = true;
		}else{
			isBothEqual = false;
		}
		return isBothEqual;
	}
	
	public static boolean compareTwoDates(String firstDateStringtoComapre,String secondDateStringtoCompare) throws ParseException
	{
		formatter = new SimpleDateFormat("dd-MMM-yyyy");
		
		Date firstDatetoComapre = formatter.parse(firstDateStringtoComapre);
		Date secondDatetoCompare = formatter.parse(secondDateStringtoCompare);
		
		String firstDate = formatter.format(firstDatetoComapre);
		String secondDate = formatter.format(secondDatetoCompare);
		
		if(firstDate.equalsIgnoreCase(secondDate))
		{
			isBothEqual = true;
		}else{
			isBothEqual = false;
		}
		return isBothEqual;
	}
	
	/*
	Method to Format DATE to "dd-mmm-yyyy" form
	*/
	public static Date FormatDate(String dateTobeFormatted) throws ParseException{
		formatter = new SimpleDateFormat("dd-MMM-yyyy");
		Date firstDate = formatter.parse(dateTobeFormatted);
		return firstDate;
	}
	
	
	/*
	Format DATE to given form
	*/	
	
	public static Date FormatDate1(String dateTobeFormatted, String newFormat) throws ParseException{
		formatter = new SimpleDateFormat(newFormat);
		Date newDate = formatter.parse(dateTobeFormatted);
		return newDate;
	}
}
