package Business.triangulation;

import java.util.List;

import com.theeyetribe.client.data.GazeData;

import neurosky.outpup.EEGRaw;

public class TriangaulationRaw {
	private GazeData gazeData;
	private EEGRaw eegRaw;
	private List<AreaOfInterest> AIOList;
	
	public TriangaulationRaw(GazeData gazeData, EEGRaw eegRaw, List<AreaOfInterest> AIOList) {
		super();
		this.gazeData = gazeData;
		this.eegRaw = eegRaw;
		this.AIOList = AIOList;
	}
	
	public TriangaulationRaw(EEGRaw eegRaw) {
		super();
		this.eegRaw = eegRaw;
	}
	
	public TriangaulationRaw(GazeData gazeData, List<AreaOfInterest> AIOList) {
		super();
		this.gazeData = gazeData;
		this.AIOList = AIOList;
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

	public List<AreaOfInterest> getAIOList() {
		return AIOList;
	}

	public void setAIOList(List<AreaOfInterest> aIOList) {
		AIOList = aIOList;
	}
	
	
	
	
}
