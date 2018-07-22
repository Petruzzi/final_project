package com.iktpreobuka.final_project.controllers.util;

import java.util.Date;

public class DateValidation {
	public static String dateValidate(String date) throws Exception{
		String[] arr=date.split("-");
		Integer day=Integer.parseInt(arr[0]);
		Integer month=Integer.parseInt(arr[1]);
		Integer year=Integer.parseInt(arr[2]);
		
		if(month>12 || month<1)
			return "Date can have max 12 months.";
		
		if(month==1 || month==3 || month==5 || month==7 || month==8 || month==10 || month==12 ) 
			if(day>31 || day<1) 
				return month+". month can have max 31 days";
			
		if(month==4 || month==6 || month==9 || month==11)
			if(day>30 || day<1) 
				return month+". month can have max 30 days";
		
		if(month==2)
			if(year%4==0) {
				if(day>29 || day<1)
					return "2. month can have max 29 days in leap years.";
			}else 
				if(day>28 || day<1)
					return "2. month can have max 28 days in common years.";
			
		return null;
	}
	
	@SuppressWarnings("deprecation")
	public static String checkStartSemesterEndDates(Date start,Date semester,Date end)throws Exception{
		if(start.after(semester) || end.before(semester)) 
			return "Dates must be set in chronological order.";

		if(start.getYear()+1!=end.getYear()) 
			return "End date must be one year higher than the starting date.";
		
		return null;
	}
	
	
	
	
//	public static void main(String[] args) {
//		Date d1=new Date(1519862400000l); 
//		Date d2=new Date(1519862500000l); 
//		Date d3=new Date(1275782400000l); 
//		//System.out.println(DateValidation.checkStartSemesterEndDates(d1, d2, d3));
//		
//		
//		
//		
//		Calendar d=Calendar.getInstance();
//		
//		d.setTime(d3);
//		int a=d.get(Calendar.YEAR);
//		System.out.println(a);
//		
//	}
}
