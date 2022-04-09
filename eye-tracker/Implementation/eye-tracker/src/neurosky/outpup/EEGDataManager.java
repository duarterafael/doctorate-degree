package neurosky.outpup;

import java.util.Date;
import java.util.HashMap;

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

}
