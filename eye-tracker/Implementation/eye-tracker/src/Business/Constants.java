package Business;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

public class Constants {
	public final static String BASE_PATH = "/resources/questions/";
	public final static String FILE_EXTENSION = ".JPG";
	public final static int qtyQuestions = 16;
	public final static String BASE_OUTPUT_PATH = "output\\";
	public final static DateFormat DATE_FORMATE = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss.sss");
	public final static DateFormat DATE_FORMATE2 = new SimpleDateFormat("yyyy MM dd HH'h'mm'm'ss's'");
	
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
		DateFormat dateFormate = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
		String newDate = dateFormate.format(date);
	    System.out.println("++++++++++++++++++++++++++++"+newDate);
	    return newDate;
		
	}
	
	
}
