package Business;

import java.util.LinkedList;

public class Experiment {
	private ModelType modelType;
	private Participant participant;
	private LinkedList<Question> questions;
	private LinkedList<Character> responses;
	
	public Experiment(ModelType modelType, String paticipantId) {
		super();
		this.modelType = modelType;
		this.participant = new Participant(paticipantId);
		this.questions = new LinkedList<>();
		this.responses = new LinkedList<>();
		feedQuestions(this.modelType, getAnswers());
	}

	public ModelType getModelType() {
		return modelType;
	}

	public Participant getParticipant() {
		return participant;
	}

	public LinkedList<Question> getQuestions() {
		return questions;
	}

	public LinkedList<Character> getResponses() {
		return responses;
	}
	
	private void feedQuestions(ModelType modelType, LinkedList<Character> answers)
	{
		
		for(int i = 0; i < Constants.qtyQuestions; i++)
		{
			String path = Constants.BASE_PATH+modelType+"/"+modelType+"_"+i+Constants.FILE_EXTENSION;
			questions.add(new Question(path, answers.get(i)));
		}
	}
	
	public int getScore()
	{
		int score = 0;
		for(int i = 0; i < questions.size(); i++)
		{
			if(this.questions.get(i).getAnswer().charValue() == this.responses.get(i).charValue())
			{
				score++;
			}
		}
		return score;
	}
	
	public LinkedList<Character> getAnswers()
	{
		LinkedList<Character> answers = new LinkedList<Character>();
		answers.add('D');//1
		answers.add('B');//2
		answers.add('D');//3
		answers.add('B');//4
		answers.add('C');//5
		answers.add('D');//6
		answers.add('C');//7
		answers.add('A');//8
		answers.add('C');//9
		answers.add('C');//10
		answers.add('D');//11
		answers.add('C');//12
		answers.add('B');//13
		answers.add('A');//14
		answers.add('C');//15
		return answers;
	}
	
	public String toStringResponses()
	{
		String returnedresponses = "";
		if(responses == null || responses.isEmpty())
		{
			returnedresponses = "NO RESPONSES";
		}else
		{
			int questionID = 1;
			for (Character response : responses) {
				returnedresponses += " "+questionID + ") "+response;
				questionID++;
			}
		}
		return returnedresponses;
	}
	
}
