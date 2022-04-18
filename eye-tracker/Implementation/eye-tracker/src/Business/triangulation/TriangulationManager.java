package Business.triangulation;

import java.util.Date;
import java.util.HashMap;
import com.theeyetribe.client.data.GazeData;
import neurosky.outpup.EEGRaw;

public class TriangulationManager {
	HashMap<Date, TriangaulationRaw> triangalation;
	private static TriangulationManager instance;
	
	public static TriangulationManager GetInscance()
	{
		if(instance == null)
		{
			instance = new TriangulationManager();
		}
		return instance;
	}
	
	private TriangulationManager()
	{
		triangalation = new HashMap<>();
	}

	public HashMap<Date, TriangaulationRaw> getTriangalation() {
		return triangalation;
	}
	
	
	public void AddTriangulation(Date timeStamp,GazeData gazeData, EEGRaw eegRaw) {
		if(!triangalation.containsKey(timeStamp))
		{
			triangalation.put(timeStamp, new TriangaulationRaw(gazeData, eegRaw));
		}else 
		{
			TriangaulationRaw triangaulationRaw = triangalation.get(timeStamp);
			if(gazeData != null)
			{
				triangaulationRaw.setGazeData(gazeData);
			}
			
			if(eegRaw != null)
			{
				triangaulationRaw.setEegRaw(eegRaw);
			}
		}
		
		TriangaulationRaw x = triangalation.get(timeStamp);
		if(x.getEegRaw() != null && x.getGazeData() != null)
		{
			System.out.println("Trigangulation!!!!!");
		}
	}
	
}
