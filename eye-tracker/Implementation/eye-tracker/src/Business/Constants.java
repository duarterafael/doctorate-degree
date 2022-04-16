package Business;

import java.util.Calendar;

public class Constants {
	public final static String BASE_PATH = "/resources/questions/";
	public final static String FILE_EXTENSION = ".JPG";
	public final static int qtyQuestions = 16;
	public final static String BASE_OUTPUT_PATH = "output\\";
	
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
	
	
}
