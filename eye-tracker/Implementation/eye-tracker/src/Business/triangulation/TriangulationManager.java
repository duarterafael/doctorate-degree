package Business.triangulation;

import java.awt.MouseInfo;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import com.theeyetribe.client.data.GazeData;

import Business.Constants;
import Business.ExprerimentCSVWritter;
import neurosky.outpup.EEGDataManager;
import neurosky.outpup.EEGRaw;

public class TriangulationManager {
	TreeMap<String, TriangaulationRaw> triangalation;
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
		triangalation = new TreeMap<>();
	}

	public TreeMap<String, TriangaulationRaw> getTriangalation() {
		return triangalation;
	}
	
	
	public void AddTriangulation(Date timeStamp,GazeData gazeData, EEGRaw eegRaw) {
		String truncateTimeStamp = Constants.getTimeStamp(timeStamp);
		if(!triangalation.containsKey(truncateTimeStamp))
		{
			triangalation.put(truncateTimeStamp, new TriangaulationRaw(gazeData, eegRaw));
		}else 
		{
			TriangaulationRaw triangaulationRaw = triangalation.get(truncateTimeStamp);
			if(gazeData != null)
			{
				triangaulationRaw.setGazeData(gazeData);
			}
			
			if(eegRaw != null)
			{
				triangaulationRaw.setEegRaw(eegRaw);
			}
			if(triangaulationRaw.getEegRaw() != null && triangaulationRaw.getGazeData() != null)
			{
				System.out.println("-------------------------------->Trigangulation!!!!!");
			}
		}
		
	}
	

	
	public void StoreTriangulatipm(String fileName, String path)
	{
		List<String> headers = new LinkedList<String>();
		headers.add("Key");
		
		headers.add("Time Stamp Eye tracking");
		headers.add("x");
		headers.add("y");
		headers.add("is Fixated");
		headers.add("Left Pupil Diameter");
		headers.add("Right Pupil Diameter");
		
		headers.add("Time Stamp Neurosky");
		headers.add("Poor Signal Level");
		headers.add("Blink Strength");
		headers.add("Attention Level");
		headers.add("Meditation Level");
		headers.add("Delta");
		headers.add("Theta");
		headers.add("Low Alpha");
		headers.add("High Alpha");
		headers.add("Low Beta");
		headers.add("High Beta");
		headers.add("Low Gamma");
		headers.add("High Beta");
		
		
		List<List<String>> dataList = new LinkedList<>();
		Map<String, TriangaulationRaw> treeMap = new TreeMap<>(triangalation);
		for (Entry<String, TriangaulationRaw> pair : treeMap.entrySet()) {
			List<String> data = new LinkedList<String>();
			data.add(pair.getKey());
			GazeData gazeDataRaw = pair.getValue().getGazeData();
			if(gazeDataRaw != null)
			{
				data.add(gazeDataRaw.timeStampString);
				data.add(Double.toString(gazeDataRaw.smoothedCoordinates.x));
				data.add(Double.toString(gazeDataRaw.smoothedCoordinates.y));
				data.add(gazeDataRaw.isFixated+"");
				data.add("" + gazeDataRaw.leftEye.pupilSize);
				data.add("" + gazeDataRaw.rightEye.pupilSize);
			}else
			{
				data.add("");
				data.add("");
				data.add("");
				data.add("");
				data.add("");
				data.add("");
			}
			EEGRaw eegRaw = pair.getValue().getEegRaw();
			if(eegRaw != null)
			{
				
				data.add(Constants.DATE_FORMATE.format(eegRaw.getTimeStamp()));
				data.add(EEGDataManager.rawDataToString(eegRaw.getPoorSignalLevel()));
				data.add(EEGDataManager.rawDataToString(eegRaw.getBlinkStrength()));
				data.add(EEGDataManager.rawDataToString(eegRaw.getAttentionLevel()));
				data.add(EEGDataManager.rawDataToString(eegRaw.getMeditationLevel()));
				data.add(EEGDataManager.rawDataToString(eegRaw.getDelta()));
				data.add(EEGDataManager.rawDataToString(eegRaw.getTheta()));
				data.add(EEGDataManager.rawDataToString(eegRaw.getLow_alpha()));
				data.add(EEGDataManager.rawDataToString(eegRaw.getHigh_alpha()));
				data.add(EEGDataManager.rawDataToString(eegRaw.getLow_beta()));
				data.add(EEGDataManager.rawDataToString(eegRaw.getHigh_beta()));
				data.add(EEGDataManager.rawDataToString(eegRaw.getLow_gamma()));
				data.add(EEGDataManager.rawDataToString(eegRaw.getMid_gamma()));
			}else
			{
				data.add("");
				data.add("");
				data.add("");
				data.add("");
				data.add("");
				data.add("");
				data.add("");
				data.add("");
				data.add("");
				data.add("");
				data.add("");
				data.add("");
				data.add("");
			}
			
		
			dataList.add(data);
		 }
		
		
		
		ExprerimentCSVWritter neuroskyCSVWritter = new ExprerimentCSVWritter(headers, dataList, path, fileName);
		neuroskyCSVWritter.WriteData();
	}
	
	

	
	
}
