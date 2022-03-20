package Business;

public class Question {
	
	private String paht;
	private Character answer;
	
	public Question(String paht, Character answer) {
		super();
		this.paht = paht;
		this.answer = answer;
	}
	public String getPaht() {
		return paht;
	}
	public Character getAnswer() {
		return answer;
	}
	
}
