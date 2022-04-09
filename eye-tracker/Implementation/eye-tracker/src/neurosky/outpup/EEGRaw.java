package neurosky.outpup;

import java.util.Date;


public class EEGRaw {
	private Date TimeStamp;
	private Integer poorSignalLevel;
	private Integer blinkStrength;
	private Integer attentionLevel;
	private Integer meditationLevel;
	private Integer delta; 
	private Integer theta; 
	private Integer low_alpha; 
	private Integer high_alpha; 
	private Integer low_beta;
	private Integer high_beta; 
	private Integer low_gamma; 
	private Integer mid_gamma;
	
	
	public EEGRaw(Date TimeStamp)
	{
		this.TimeStamp = TimeStamp;
	}
	
	public Date getTimeStamp() {
		return TimeStamp;
	}
	public void setTimeStamp(Date timeStamp) {
		TimeStamp = timeStamp;
	}
	public Integer getPoorSignalLevel() {
		return poorSignalLevel;
	}
	public void setPoorSignalLevel(Integer poorSignalLevel) {
		this.poorSignalLevel = poorSignalLevel;
	}
	public Integer getBlinkStrength() {
		return blinkStrength;
	}
	public void setBlinkStrength(Integer blinkStrength) {
		this.blinkStrength = blinkStrength;
	}
	public Integer getAttentionLevel() {
		return attentionLevel;
	}
	public void setAttentionLevel(Integer attentionLevel) {
		this.attentionLevel = attentionLevel;
	}
	public Integer getMeditationLevel() {
		return meditationLevel;
	}
	public void setMeditationLevel(Integer meditationLevel) {
		this.meditationLevel = meditationLevel;
	}
	public Integer getDelta() {
		return delta;
	}
	public void setDelta(Integer delta) {
		this.delta = delta;
	}
	public Integer getTheta() {
		return theta;
	}
	public void setTheta(Integer theta) {
		this.theta = theta;
	}
	public Integer getLow_alpha() {
		return low_alpha;
	}
	public void setLow_alpha(Integer low_alpha) {
		this.low_alpha = low_alpha;
	}
	public Integer getHigh_alpha() {
		return high_alpha;
	}
	public void setHigh_alpha(Integer high_alpha) {
		this.high_alpha = high_alpha;
	}
	public Integer getLow_beta() {
		return low_beta;
	}
	public void setLow_beta(Integer low_beta) {
		this.low_beta = low_beta;
	}
	public Integer getHigh_beta() {
		return high_beta;
	}
	public void setHigh_beta(Integer high_beta) {
		this.high_beta = high_beta;
	}
	public Integer getLow_gamma() {
		return low_gamma;
	}
	public void setLow_gamma(Integer low_gamma) {
		this.low_gamma = low_gamma;
	}
	public Integer getMid_gamma() {
		return mid_gamma;
	}
	public void setMid_gamma(Integer mid_gamma) {
		this.mid_gamma = mid_gamma;
	}
	
	@Override
	public String toString() {
		return TimeStamp+";"
				+poorSignalLevel == null ? "" : poorSignalLevel + ";"+
				+blinkStrength == null ? "" : blinkStrength + ";"+
				+attentionLevel == null ? "" : attentionLevel + ";"+
				+meditationLevel == null ? "" : meditationLevel + ";"+
				+theta == null ? "" : theta + ";"+
				+low_alpha == null ? "" : low_alpha + ";"+
				+high_alpha == null ? "" : high_alpha + ";"+
				+low_beta == null ? "" : low_beta + ";"+
				+high_beta == null ? "" : high_beta + ";"+
				+low_gamma == null ? "" : low_gamma + ";"+
				+mid_gamma == null ? "" : mid_gamma + "";
	}
	
	
	

}
