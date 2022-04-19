package neurosky.outpup;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import Business.Constants;
import Business.ExprerimentCSVWritter;

public class EEGDataManager {
	private HashMap<Date, EEGRaw> EEGRawMap;
	
//	private static EEGDataManager instance = null;
//	
//	public static EEGDataManager getInstance()
//	{
//		if(instance == null)
//		{
//			instance = new EEGDataManager();
//		}
//		return instance;
//	}
	
	public EEGDataManager()
	{
		EEGRawMap = new HashMap<>();
	}

	public HashMap<Date, EEGRaw> getEEGRawMap() {
		return EEGRawMap;
	}
	
	public void AddEEGMap(EEGAction eegAction,
				Date timeStamp, 
				Integer poorSignalLevel,
				
				Integer blinkStrength,
				
				Integer attentionLevel,
				Integer meditationLevel,
				
				Integer delta, 
				Integer theta, 
				Integer low_alpha,
				Integer high_alpha, 
				Integer low_beta, 
				Integer high_beta, 
				Integer low_gamma, 
				Integer mid_gamma) {
		if(!EEGRawMap.containsKey(timeStamp))
		{
			EEGRawMap.put(timeStamp, new EEGRaw(timeStamp));
		}
		EEGRaw eegRawMap = EEGRawMap.get(timeStamp);
		
		if(eegAction == EEGAction.POOR_SIGNAL_LEVEL)
		{
			eegRawMap.setPoorSignalLevel(poorSignalLevel);
			
		}else if(eegAction == EEGAction.BLINK_STRENGTH)
		{
			eegRawMap.setBlinkStrength(blinkStrength);
		}else if(eegAction == EEGAction.E_SENSE)
		{
			eegRawMap.setAttentionLevel(attentionLevel);
			eegRawMap.setMeditationLevel(meditationLevel);
		}else if(eegAction == EEGAction.EEG_POWER)
		{
			eegRawMap.setDelta(delta);
			eegRawMap.setTheta(theta);
			eegRawMap.setLow_alpha(low_alpha);
			eegRawMap.setHigh_alpha(high_alpha);
			eegRawMap.setLow_beta(low_beta);
			eegRawMap.setHigh_beta(high_beta);
			eegRawMap.setLow_gamma(low_gamma);
			eegRawMap.setMid_gamma(mid_gamma);
		}
		
	}
	
	public void StoreNeuroSkyData(String fileName, String path)
	{
		List<String> headers = new LinkedList<String>();
		headers.add("Time stamp");
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
		headers.add("Mid Beta");
		
		
		List<List<String>> dataList = new LinkedList<>();
		Map<Date, EEGRaw> treeMap = new TreeMap<>(EEGRawMap);
		for (Entry<Date, EEGRaw> pair : treeMap.entrySet()) {
			List<String> data = new LinkedList<String>();
			data.add(Constants.DATE_FORMATE.format(pair.getKey()));
			EEGRaw raw = pair.getValue();
			data.add(rawDataToString(raw.getPoorSignalLevel()));
			data.add(rawDataToString(raw.getBlinkStrength()));
			data.add(rawDataToString(raw.getAttentionLevel()));
			data.add(rawDataToString(raw.getMeditationLevel()));
			data.add(rawDataToString(raw.getDelta()));
			data.add(rawDataToString(raw.getTheta()));
			data.add(rawDataToString(raw.getLow_alpha()));
			data.add(rawDataToString(raw.getHigh_alpha()));
			data.add(rawDataToString(raw.getLow_beta()));
			data.add(rawDataToString(raw.getHigh_beta()));
			data.add(rawDataToString(raw.getLow_gamma()));
			data.add(rawDataToString(raw.getMid_gamma()));
			dataList.add(data);
		 }
		
		
		
		ExprerimentCSVWritter neuroskyCSVWritter = new ExprerimentCSVWritter(headers, dataList, path, fileName);
		neuroskyCSVWritter.WriteData();
	}
	
	private static String rawDataToString(Integer data)
	{
		if(data == null)
			return "";
		else
			return data.toString();
	}
	

}
