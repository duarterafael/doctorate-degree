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
		feedQuestions();
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
	
	private void feedQuestions()
	{
		Question question0 = 
				new Question("Esta é apenas uma checagem da calibração dos equipamentos\r\n"
				, "a) Leia as instruções abaixo\r\n"
						+ "b) Conte cinco segundos antes de ir para o próximo numero.\r\n"
						+ "c) Olhe os números em sequencia.\r\n"
						+ "d) Ao chegar no último número pressione umas das seguintes teclas: A, B, C ou D.\r\n"
						,'D');
				questions.add(question0);
		
		Question question1 = 
		new Question("1)	O setor de Atendimento participa do processo..."
		, "a)	... se o proprietário estiver cadastrado.\r\n"
		+ "b)	... se o proprietário estiver alugado.\r\n"
		+ "c)	... se o imóvel estiver cadastrado.\r\n"
		+ "d)	... se o proprietário solicitar a validação.", 
		'D');
		questions.add(question1);
		
		Question question2 = 
		new Question("2)	Quem inicia o processo..."
		, "a)	... setor jurídico.\r\n"
		+ "b)	... o proprietário.\r\n"
		+ "c)	... setor de atendimento.\r\n"
		+ "d)	... setor de corretagem.", 
		'B');
		questions.add(question2);
		
		Question question3 = 
		new Question("3)	As atividades de cadastramento do proprietário e de cadastramento de Imóvel..."
		, "a)	... não depende de nenhum evento para serem executadas.\r\n"
		+ "b)	... são executadas de forma excludentes (uma ou outra).\r\n"
		+ "c)	... podem ser executadas simultaneamente ou excludentemente.\r\n"
		+ "d)	... são executadas simultaneamente.", 
		'D');
		questions.add(question3);
		
		Question question4 = 
		new Question("4)	A atividade responsável pela verificação da documentação..."
		, "a)	... é executada apenas uma vez no processo.\r\n"
		+ "b)	... pode ser executada várias vezes durante o processo.\r\n"
		+ "c)	... depende de uma condição para ser executada pela primeira vez.\r\n"
		+ "d)	... não é necessária para a execução do processo.", 
		'B');
		questions.add(question4);
		
		Question question5 = 
		new Question("5)	A verificação da documentação..."
		, "a)	... é executada apenas após a atividade \"Cadastrar Proprietário\".\r\n"
		+ "b)	... é executada apenas após a atividade \"Cadastrar Imóvel\".\r\n"
		+ "c)	... depende da execução de ambas as atividades \"Cadastrar Proprietário\" e \"Cadastrar Imóvel\".\r\n"
		+ "d)	... não depende da execução de ambas as atividades \"Cadastrar Proprietário\" e \"Cadastrar Imóvel\".", 
		'C');
		questions.add(question5);

		Question question6 = 
		new Question("6)	Se a documentação estiver irregular..."
		, "a)	 ... a atividade \"Verificar Documentação\" é executada.\r\n"
		+ "b)	 ... o processo é cancelado em seguida.\r\n"
		+ "c)	 ... a atividade \"Agendar Avaliação\" é executada.\r\n"
		+ "d)	 ... o proprietário é notificado.", 
		'D');
		questions.add(question6);

		Question question7 = 
		new Question("7)	Se a documentação estiver regular..."
		, "a)	 ... a atividade \"Verificar Documentação\" é executada.\r\n"
		+ "b)	 ... o processo é cancelado em seguida.\r\n"
		+ "c)	 ... a atividade \"Agendar Avaliação\" é executada.\r\n"
		+ "d)	 ... o proprietário é notificado.", 
		'C');
		questions.add(question7);

		Question question8 = 
		new Question("8)	Quem é responsável por informar data de avaliação?"
		, "a)	Setor de atendimento.\r\n"
		+ "b)	Setor jurídico.\r\n"
		+ "c)	Setor de corretagem.\r\n"
		+ "d)	O proprietário.", 
		'A');
		questions.add(question8);

		Question question9 = 
		new Question("9)	A corretagem após realizar a avaliação do imóvel..."
		, "a)	... verifica novamente a documentação.\r\n"
		+ "b)	... encerra o processo.\r\n"
		+ "c)	... sugere valor de venda e/ou de aluguel.\r\n"
		+ "d)	... notifica ao cliente o valor da avaliação.", 
		'C');
		questions.add(question9);

		Question question10 = 
		new Question("10)	De quantas formas podem ser encerrado o processo?"
		, "a)	Uma.\r\n"
		+ "b)	Três.\r\n"
		+ "c)	Duas.\r\n"
		+ "d)	Quatro.", 
		'C');
		questions.add(question10);				

		Question question11 = 
		new Question("11)	Em todo o processo, quantas possíveis mensagens o proprietário pode receber?"
		, "a)	Uma mensagem.\r\n"
		+ "b)	Três mensagens.\r\n"
		+ "c)	Duas mensagens.\r\n"
		+ "d)	N mensagens.", 
		'D');
		questions.add(question11);				

		Question question12 = 
		new Question("12)	Quais informações são necessárias para a avaliação do imóvel?"
		, "a)	Apenas as informações do proprietário.\r\n"
		+ "b)	Apenas as informações do imóvel.\r\n"
		+ "c)	As informações do imóvel e do proprietário.\r\n"
		+ "d)	Os dados da imobiliária.", 
		'C');
		questions.add(question12);				

		Question question13 = 
		new Question("13)	O setor jurídico é responsável por quantas atividades?"
		, "a)	Três atividades.\r\n"
		+ "b)	Uma atividade.\r\n"
		+ "c)	Oito atividades.\r\n"
		+ "d)	Quatro atividades.", 
		'B');
		questions.add(question13);	
		
		Question question14 = 
		new Question("14)	O que gera a interrupção do processo?"
		, "a)	O proprietário passar mais de 30 dias sem responder a notificação de irregularidade na documentação. \r\n"
		+ "b)	 A documentação está irregular.\r\n"
		+ "c)	O proprietário não ser cadastrado.\r\n"
		+ "d)	O imóvel não ser cadastrado.", 
		'A');
		questions.add(question14);	
				
		Question question15 = 
		new Question("15)	Em quais momentos o processo pode ser interrompido/encerrado sem sucesso?"
		, "a)	Se a documentação estiver irregular.\r\n"
		+ "b)	Após a notificação da data de avaliação.\r\n"
		+ "c)	Após 30 dias sem resposta ou após sugerir os valores.\r\n"
		+ "d)	Após a solicitação de avaliação.", 
		'C');
		questions.add(question15);	
		
		for(int i = 0; i < Constants.qtyQuestions; i++)
		{
			String path = Constants.BASE_PATH+modelType+"/"+modelType+"_"+i+Constants.FILE_EXTENSION;
			questions.get(i).setPaht(path);
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
