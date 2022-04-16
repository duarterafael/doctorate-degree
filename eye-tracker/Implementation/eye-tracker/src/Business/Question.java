package Business;

import java.util.Calendar;

public class Question {
	
	private String statement;
	private String altenatives;
	private String paht;
	private Character answer;
	private Character response;
	private Calendar startDate;
	private Calendar endDate; 
	
	public Question(String statement, String altenatives, Character answer) {
		super();
		this.statement = statement;
		this.altenatives = altenatives;
		this.answer = answer;
		this.startDate = startDate;
	}

	public String getStatement() {
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}

	public String getAltenatives() {
		return altenatives;
	}

	public void setAltenatives(String altenatives) {
		this.altenatives = altenatives;
	}

	public String getPaht() {
		return paht;
	}

	public void setPaht(String paht) {
		this.paht = paht;
	}

	public Character getAnswer() {
		return answer;
	}

	public void setAnswer(Character answer) {
		this.answer = answer;
	}

	public Character getResponse() {
		return response;
	}

	public void setResponse(Character response) {
		this.response = response;
	}

	public Calendar getStartDate() {
		return startDate;
	}

	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

	public Calendar getEndDate() {
		return endDate;
	}

	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}
	
	
	
	
	
	
}
