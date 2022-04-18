package Business.triangulation;

import com.theeyetribe.client.data.GazeData;

import neurosky.outpup.EEGRaw;

public class TriangaulationRaw {
	private GazeData gazeData;
	private EEGRaw eegRaw;
	
	public TriangaulationRaw(GazeData gazeData, EEGRaw eegRaw) {
		super();
		this.gazeData = gazeData;
		this.eegRaw = eegRaw;
	}
	
	public TriangaulationRaw(EEGRaw eegRaw) {
		super();
		this.eegRaw = eegRaw;
	}
	
	public TriangaulationRaw(GazeData gazeData) {
		super();
		this.gazeData = gazeData;
	}
	
	public GazeData getGazeData() {
		return gazeData;
	}
	public void setGazeData(GazeData gazeData) {
		this.gazeData = gazeData;
	}
	public EEGRaw getEegRaw() {
		return eegRaw;
	}
	public void setEegRaw(EEGRaw eegRaw) {
		this.eegRaw = eegRaw;
	}
	
	
}
