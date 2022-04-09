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
				new Question("Esta � apenas uma checagem da calibra��o dos equipamentos\r\n"
				, "a) Leia as instru��es abaixo\r\n"
						+ "b) Conte cinco segundos antes de ir para o pr�ximo numero.\r\n"
						+ "c) Olhe os n�meros em sequencia.\r\n"
						+ "d) Ao chegar no �ltimo n�mero pressione umas das seguintes teclas: A, B, C ou D.\r\n"
						,'D');
				questions.add(question0);
		
		Question question1 = 
		new Question("1)	O setor de Atendimento participa do processo..."
		, "a)	... se o propriet�rio estiver cadastrado.\r\n"
		+ "b)	... se o propriet�rio estiver alugado.\r\n"
		+ "c)	... se o im�vel estiver cadastrado.\r\n"
		+ "d)	... se o propriet�rio solicitar a valida��o.", 
		'D');
		questions.add(question1);
		
		Question question2 = 
		new Question("2)	Quem inicia o processo..."
		, "a)	... setor jur�dico.\r\n"
		+ "b)	... o propriet�rio.\r\n"
		+ "c)	... setor de atendimento.\r\n"
		+ "d)	... setor de corretagem.", 
		'B');
		questions.add(question2);
		
		Question question3 = 
		new Question("3)	As atividades de cadastramento do propriet�rio e de cadastramento de Im�vel..."
		, "a)	... n�o depende de nenhum evento para serem executadas.\r\n"
		+ "b)	... s�o executadas de forma excludentes (uma ou outra).\r\n"
		+ "c)	... podem ser executadas simultaneamente ou excludentemente.\r\n"
		+ "d)	... s�o executadas simultaneamente.", 
		'D');
		questions.add(question3);
		
		Question question4 = 
		new Question("4)	A atividade respons�vel pela verifica��o da documenta��o..."
		, "a)	... � executada apenas uma vez no processo.\r\n"
		+ "b)	... pode ser executada v�rias vezes durante o processo.\r\n"
		+ "c)	... depende de uma condi��o para ser executada pela primeira vez.\r\n"
		+ "d)	... n�o � necess�ria para a execu��o do processo.", 
		'B');
		questions.add(question4);
		
		Question question5 = 
		new Question("5)	A verifica��o da documenta��o..."
		, "a)	... � executada apenas ap�s a atividade \"Cadastrar Propriet�rio\".\r\n"
		+ "b)	... � executada apenas ap�s a atividade \"Cadastrar Im�vel\".\r\n"
		+ "c)	... depende da execu��o de ambas as atividades \"Cadastrar Propriet�rio\" e \"Cadastrar Im�vel\".\r\n"
		+ "d)	... n�o depende da execu��o de ambas as atividades \"Cadastrar Propriet�rio\" e \"Cadastrar Im�vel\".", 
		'C');
		questions.add(question5);

		Question question6 = 
		new Question("6)	Se a documenta��o estiver irregular..."
		, "a)	 ... a atividade \"Verificar Documenta��o\" � executada.\r\n"
		+ "b)	 ... o processo � cancelado em seguida.\r\n"
		+ "c)	 ... a atividade \"Agendar Avalia��o\" � executada.\r\n"
		+ "d)	 ... o propriet�rio � notificado.", 
		'D');
		questions.add(question6);

		Question question7 = 
		new Question("7)	Se a documenta��o estiver regular..."
		, "a)	 ... a atividade \"Verificar Documenta��o\" � executada.\r\n"
		+ "b)	 ... o processo � cancelado em seguida.\r\n"
		+ "c)	 ... a atividade \"Agendar Avalia��o\" � executada.\r\n"
		+ "d)	 ... o propriet�rio � notificado.", 
		'C');
		questions.add(question7);

		Question question8 = 
		new Question("8)	Quem � respons�vel por informar data de avalia��o?"
		, "a)	Setor de atendimento.\r\n"
		+ "b)	Setor jur�dico.\r\n"
		+ "c)	Setor de corretagem.\r\n"
		+ "d)	O propriet�rio.", 
		'A');
		questions.add(question8);

		Question question9 = 
		new Question("9)	A corretagem ap�s realizar a avalia��o do im�vel..."
		, "a)	... verifica novamente a documenta��o.\r\n"
		+ "b)	... encerra o processo.\r\n"
		+ "c)	... sugere valor de venda e/ou de aluguel.\r\n"
		+ "d)	... notifica ao cliente o valor da avalia��o.", 
		'C');
		questions.add(question9);

		Question question10 = 
		new Question("10)	De quantas formas podem ser encerrado o processo?"
		, "a)	Uma.\r\n"
		+ "b)	Tr�s.\r\n"
		+ "c)	Duas.\r\n"
		+ "d)	Quatro.", 
		'C');
		questions.add(question10);				

		Question question11 = 
		new Question("11)	Em todo o processo, quantas poss�veis mensagens o propriet�rio pode receber?"
		, "a)	Uma mensagem.\r\n"
		+ "b)	Tr�s mensagens.\r\n"
		+ "c)	Duas mensagens.\r\n"
		+ "d)	N mensagens.", 
		'D');
		questions.add(question11);				

		Question question12 = 
		new Question("12)	Quais informa��es s�o necess�rias para a avalia��o do im�vel?"
		, "a)	Apenas as informa��es do propriet�rio.\r\n"
		+ "b)	Apenas as informa��es do im�vel.\r\n"
		+ "c)	As informa��es do im�vel e do propriet�rio.\r\n"
		+ "d)	Os dados da imobili�ria.", 
		'C');
		questions.add(question12);				

		Question question13 = 
		new Question("13)	O setor jur�dico � respons�vel por quantas atividades?"
		, "a)	Tr�s atividades.\r\n"
		+ "b)	Uma atividade.\r\n"
		+ "c)	Oito atividades.\r\n"
		+ "d)	Quatro atividades.", 
		'B');
		questions.add(question13);	
		
		Question question14 = 
		new Question("14)	O que gera a interrup��o do processo?"
		, "a)	O propriet�rio passar mais de 30 dias sem responder a notifica��o de irregularidade na documenta��o. \r\n"
		+ "b)	 A documenta��o est� irregular.\r\n"
		+ "c)	O propriet�rio n�o ser cadastrado.\r\n"
		+ "d)	O im�vel n�o ser cadastrado.", 
		'A');
		questions.add(question14);	
				
		Question question15 = 
		new Question("15)	Em quais momentos o processo pode ser interrompido/encerrado sem sucesso?"
		, "a)	Se a documenta��o estiver irregular.\r\n"
		+ "b)	Ap�s a notifica��o da data de avalia��o.\r\n"
		+ "c)	Ap�s 30 dias sem resposta ou ap�s sugerir os valores.\r\n"
		+ "d)	Ap�s a solicita��o de avalia��o.", 
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
