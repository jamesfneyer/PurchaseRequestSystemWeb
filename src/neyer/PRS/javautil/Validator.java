package neyer.PRS.javautil;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class Validator {
	
	public static double getValidDouble(String p){
		double price = 0.0;
		try{
			price = Double.parseDouble(p);
		}
		catch(Exception e){
			price = 0.0;
		}
		if(price<0.0){
			price = 0.0;
		}
		return price;
	}
	
	public static Date getDate(String dateNeeded){
		String[] dateN = dateNeeded.split("-");
		Calendar submittedDateCal = Calendar.getInstance(); 
		boolean isValid = false;
		int subYear = Integer.parseInt(dateN[0]);
		int todayYear = submittedDateCal.get(Calendar.YEAR);
		int subMonth = Integer.parseInt(dateN[1]);
		int todayMonth = submittedDateCal.get(Calendar.MONTH);
		int subDay = Integer.parseInt(dateN[2]);
		int todayDay = submittedDateCal.get(Calendar.DAY_OF_MONTH);
		Calendar neededD = new GregorianCalendar(subYear,subMonth,subDay);
		Date neededDate = neededD.getTime();
		if(subYear>todayYear){
			if(subMonth>=todayMonth){
				if(subMonth == todayMonth){
					if(subDay > todayDay)
						isValid = true;
				}
				else if(subMonth > todayMonth){
					isValid = true;
				}
			}
		}
		if(isValid = false)
			return null;
		else
			return neededDate;
	}
}