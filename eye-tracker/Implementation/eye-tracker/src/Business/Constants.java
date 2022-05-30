package Business;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

public class Constants {
	public final static String BASE_PATH = "/resources/questions/";
	public final static String FILE_EXTENSION = ".JPG";
	public final static int qtyQuestions = 15;
	public final static String BASE_OUTPUT_PATH = "output\\";
	public final static DateFormat DATE_FORMATE = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss");
	public final static DateFormat DATE_FORMATE2 = new SimpleDateFormat("yyyy MM dd HH'h'mm'm'ss's'");
	public final static DateFormat DATE_FORMATE3 = new SimpleDateFormat("HH_mm_ss");
	
	public static long calulateDuration (Calendar startDate, Calendar endDate)
	{
		// Calucalte time difference
        // in milliseconds
		 long difference_In_Time = endDate.getTimeInMillis() - startDate.getTimeInMillis();
		 return difference_In_Time;
	     // Calucalte time difference in
	     // seconds, minutes, hours, years,
	     // and days
//	     return difference_In_Time / 1000;
	}
	
	public static String getTimeStamp(Date date)
	{
		DateFormat dateFormate = new SimpleDateFormat("HH:mm:ss");
		String newDate = dateFormate.format(date);
	    return newDate;
		
	}
	
	
}
