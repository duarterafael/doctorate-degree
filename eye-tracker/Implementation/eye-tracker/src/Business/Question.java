package Business;

public class Question {
	
	private String statement;
	private String altenatives;
	private String paht;
	private Character answer;
	
	public Question(String statement, String altenatives, Character answer) {
		super();
		this.statement = statement;
		this.altenatives = altenatives;
		this.answer = answer;
	}
	
	public Character getAnswer() {
		return answer;
	}
	public String getStatement() {
		return statement;
	}
	public String getAltenatives() {
		return altenatives;
	}
	
	

	public void setPaht(String paht) {
		this.paht = paht;
	}

	public String getPaht() {
		return paht;
	}
	
	
	
	
}
