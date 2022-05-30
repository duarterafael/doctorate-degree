package Business.triangulation;

import java.util.List;

import com.theeyetribe.client.data.GazeData;

import neurosky.outpup.EEGRaw;

public class TriangaulationRaw {
	private GazeData gazeData;
	private EEGRaw eegRaw;
	private int calculateX;
	private int calculateY;
	private List<AreaOfInterest> AIOList;
	
	public TriangaulationRaw(GazeData gazeData, EEGRaw eegRaw, List<AreaOfInterest> AIOList, int calculateX, int calculateY) {
		super();
		this.gazeData = gazeData;
		this.eegRaw = eegRaw;
		this.AIOList = AIOList;
		this.calculateX = calculateX;
		this.calculateY = calculateY;
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

	public int getCalculateX() {
		return calculateX;
	}

	public void setCalculateX(int calculateX) {
		this.calculateX = calculateX;
	}

	public int getCalculateY() {
		return calculateY;
	}

	public void setCalculateY(int calculateY) {
		this.calculateY = calculateY;
	}
	
	
}
