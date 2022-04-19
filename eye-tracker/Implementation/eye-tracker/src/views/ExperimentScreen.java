package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Business.Constants;
import Business.Experiment;
import Business.ExprerimentCSVWritter;
import Business.ModelType;
import Business.ScreenCaptureManager;
import Business.triangulation.TriangaulationRaw;
import Business.triangulation.TriangulationManager;
import controllers.DataController;
import controllers.MovieController;
import neurosky.ThinkGearSocket;
import neurosky.outpup.EEGDataManager;
import neurosky.outpup.EEGRaw;

public class ExperimentScreen {

	private static final List<String> validResponses=new LinkedList<String>(){{add("A");add("B");add("C");add("D");}};

	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	private static final String DEFUALT_MODEL_TYPE = "Select a model type";
	private Experiment experiment;
	private JPanel firstPanel;
	private int questionsIndex = 0;
	private JLabel imageLabel;
	private ScreenCaptureManager screenCaptureManager;
	private ExprerimentCSVWritter exprerimentCSVWritter;
	private File rootFile = null;
	private String exemperimentDataTime = ScreenCaptureManager.getCurrentTime();
	
	private File currentSubDir;
	private ThinkGearSocket thinkGearSocket;

	public ExperimentScreen() {
		
		DataController gc = new DataController();
		MovieController mc = new MovieController();
		screenCaptureManager = new ScreenCaptureManager(gc, mc);
		frame = new JFrame();

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.setTitle("PhD experiment APP");
		frame.setLocation(0, 0);
		frame.setSize(200, 200);
		frame.add(firstPanel());
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);
		frame.setVisible(true);
	}

	private JPanel firstPanel() {
		Font font1 = new Font("SansSerif", Font.BOLD, 30);
		JLabel labelParticipantId = new JLabel("Participant ID: ");
		labelParticipantId.setFont(font1);

		JLabel labelModelType = new JLabel("Model Type: ");
		labelModelType.setFont(font1);

		JTextField textParticipantId = new JTextField(21);
		textParticipantId.setFont(font1);

		String[] modelTypes = { DEFUALT_MODEL_TYPE, ModelType.WITH_GUIDELINES_AND_COLORFUL.getdescription(),
				ModelType.WITH_GUIDELINES_AND_BLACK_AND_WHITE.getdescription(),
				ModelType.NO_GUIDELINES_AND_COLORFUL.getdescription(),
				ModelType.NO_GUIDELINES_AND_BLACK_AND_WHITE.getdescription() };

		JComboBox comboBoxmodelTypes = new JComboBox(modelTypes);
		comboBoxmodelTypes.setFont(font1);

		//comboBoxmodelTypes.setSelectedIndex(1); // REMOVER THIS
		//textParticipantId.setText("Rafael Duarte");// REMOVE THIS

		JButton buttonLogin = new JButton("Start");
		
		buttonLogin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (textParticipantId.getText().isEmpty() || textParticipantId.getText().isBlank()) {
					JOptionPane.showMessageDialog(frame, "Please enter a paticipant ID", "Error message",
							JOptionPane.ERROR_MESSAGE);
				} else if (comboBoxmodelTypes.getSelectedIndex() == 0) {
					JOptionPane.showMessageDialog(frame, "Please select a model type", "Error message",
							JOptionPane.ERROR_MESSAGE);
				} else {
					screenCaptureManager.workingDirectory = Constants.BASE_OUTPUT_PATH+textParticipantId.getText()+ "_" + comboBoxmodelTypes.getSelectedItem().toString()+"\\"+exemperimentDataTime;
					rootFile = new File(screenCaptureManager.workingDirectory);
					rootFile.mkdirs();
					
					experiment = new Experiment(ModelType.getByDescription(comboBoxmodelTypes.getSelectedItem().toString()), textParticipantId.getText());
					
					firstPanel.setVisible(false);
					
					KeyListener listener = new KeyListener() {
						@Override
						public void keyTyped(KeyEvent e) {
						}

						@Override
						public void keyPressed(KeyEvent e) {
							
						}

						@Override
						public void keyReleased(KeyEvent e) {
							String response = KeyEvent.getKeyText(e.getKeyCode()).toUpperCase();
							System.out.println(">>>>>>>>>>>>>>"+response);
							if (validResponses.contains(response)) {
								if (questionsIndex < experiment.getQuestions().size()) {
									
									experiment.getQuestions().get(questionsIndex).setResponse(response.charAt(0));
									String fileName = "Neuroskyoutput_"+questionsIndex+".csv";
									questionsIndex++;
									if (questionsIndex < experiment.getQuestions().size()) {
										screenCaptureManager.endRecording();
										thinkGearSocket.pause();

										for (Entry<Date, EEGRaw> pair : thinkGearSocket.getEEGDataManager().getEEGRawMap().entrySet()) {
											TriangulationManager.GetInscance().AddTriangulation(pair.getKey(), null, pair.getValue());
										}
										thinkGearSocket.getEEGDataManager().StoreNeuroSkyData(fileName, currentSubDir.getAbsolutePath());
										thinkGearSocket.getEEGDataManager().getEEGRawMap().clear();
									
										
										setCurrentOutputDir();
										screenCaptureManager.startRecording();
										
										thinkGearSocket.pause();
										
										setImage(questionsIndex);
									} else {
										experiment.setEndDate(Calendar.getInstance());
										experiment.getQuestions().get(questionsIndex-1).setEndDate(experiment.getEndDate());
										experiment.getQuestions().get(questionsIndex-1).setResponse(response.charAt(0));
										
										screenCaptureManager.endRecording();
										storeExperimentData();
										
										thinkGearSocket.stop();

										for (Entry<Date, EEGRaw> pair : thinkGearSocket.getEEGDataManager().getEEGRawMap().entrySet()) {
											TriangulationManager.GetInscance().AddTriangulation(pair.getKey(), null, pair.getValue());
										}
										thinkGearSocket.getEEGDataManager().StoreNeuroSkyData(fileName, currentSubDir.getAbsolutePath());
										thinkGearSocket.getEEGDataManager().getEEGRawMap().clear();
										for (Entry<Date, TriangaulationRaw> pair : (TriangulationManager.GetInscance()).getTriangalation().entrySet() ) {
											if(pair.getValue().getEegRaw() != null && pair.getValue().getGazeData() != null) {
												System.out.println(pair.getKey());
												
											}
										}

										try {
											imageLabel.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/resources/tks.jpg"))));
										} catch (IOException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
									    
									}

								}
							}
						}
					};
					
					frame.addKeyListener(listener);
					frame.setFocusable(true);
					
					setCurrentOutputDir();
					screenCaptureManager.startRecording();
					thinkGearSocket = new ThinkGearSocket();
					try {
						thinkGearSocket.start();
					} catch (ConnectException e1) {
						System.out.print("Erro to start thinkGearSocket. Question Id "+questionsIndex);
						e1.printStackTrace();
					}
					setImage(questionsIndex);
					
				}
			}
		});

		buttonLogin.setFont(new Font("SansSerif", Font.BOLD, 100));
		firstPanel = new JPanel(new GridBagLayout());

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(10, 10, 10, 10);

		constraints.gridx = 0;
		constraints.gridy = 0;
		firstPanel.add(labelParticipantId, constraints);

		constraints.gridx = 1;
		firstPanel.add(textParticipantId, constraints);

		constraints.gridx = 0;
		constraints.gridy = 1;
		firstPanel.add(labelModelType, constraints);

		constraints.gridx = 1;
		firstPanel.add(comboBoxmodelTypes, constraints);

		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 2;
		constraints.anchor = GridBagConstraints.CENTER;
		firstPanel.add(buttonLogin, constraints);

		return firstPanel;

	}

	private void setImage(int index) {
		ImageIcon image = null;
		if(index == 0)
		{
			experiment.setStartDate(Calendar.getInstance());
			experiment.getQuestions().get(index).setStartDate(experiment.getStartDate());
		}else if(questionsIndex < experiment.getQuestions().size())
		{
			experiment.getQuestions().get((index-1)).setEndDate(Calendar.getInstance());
			experiment.getQuestions().get(index).setStartDate(experiment.getQuestions().get((index-1)).getEndDate());
		}
		
		try {
			String path = experiment.getQuestions().get(index).getPaht();
			image = new ImageIcon(ImageIO.read(getClass().getResource(path)));
			if (index == 0) {
				imageLabel = new JLabel();
			}
			imageLabel.setIcon(image);
			frame.add(imageLabel, BorderLayout.CENTER);
			frame.setFocusable(true);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	private void setCurrentOutputDir()
	{
		String subDir = (questionsIndex+1)+"_"+ScreenCaptureManager.getCurrentTime();
		currentSubDir = new File(rootFile.getAbsolutePath()+"\\"+subDir);
		currentSubDir.mkdirs();
		screenCaptureManager.workingDirectory = currentSubDir.getAbsolutePath();
	}

	public void secondPanel() {
		
	}
	
	
	private void storeExperimentData()
	{
		List<String> headers = new LinkedList<String>();
		headers.add("Start Date");
		headers.add("End Date");
		headers.add("Time duration (s)");
		headers.add("Participant Id");
		headers.add("Model Tyle");
		headers.add("Total Score");
		
		List<String> data = new LinkedList<String>();
		data.add(Constants.DATE_FORMATE.format(experiment.getStartDate().getTime()));
		data.add(Constants.DATE_FORMATE.format(experiment.getEndDate().getTime()));
		data.add(""+Constants.calulateDuration(experiment.getStartDate(), experiment.getEndDate()));
		
		data.add(experiment.getParticipant().getID());
		data.add(experiment.getModelType().getdescription());
		data.add(experiment.getScore()+"");
		
		for(int i = 0; i < Constants.qtyQuestions; i++) {
			String headersQuestionSufix = "Q"+(i+1);
			
			headers.add(headersQuestionSufix+" Response");
			data.add(experiment.getQuestions().get(i).getResponse().toString());
			
			headers.add(headersQuestionSufix+" Answer ");
			data.add(experiment.getQuestions().get(i).getAnswer().toString());
			
			headers.add(headersQuestionSufix + " Score");
			String questionScore = experiment.getQuestions().get(i).getResponse() == experiment.getQuestions().get(i).getAnswer() ? "1" : "0";
			data.add(questionScore);
			
			headers.add(headersQuestionSufix+" Start Date ");
			data.add(Constants.DATE_FORMATE.format(experiment.getQuestions().get(i).getStartDate().getTime()));
			
			headers.add(headersQuestionSufix+" End Date");
			data.add(Constants.DATE_FORMATE.format(experiment.getQuestions().get(i).getEndDate().getTime()));
			
			headers.add(headersQuestionSufix+" Time duration (s) ");
			data.add(""+Constants.calulateDuration(experiment.getQuestions().get(i).getStartDate(), experiment.getQuestions().get(i).getEndDate()));
			
		}
		
		headers.add("Output data");
		data.add(rootFile.getAbsolutePath());
		
		List<List<String>> dataList = Arrays.asList(data);
		String csvPath = rootFile.getParentFile().getParentFile().getAbsolutePath();
		exprerimentCSVWritter = new ExprerimentCSVWritter(headers, dataList, csvPath, "Experimento.csv");
		exprerimentCSVWritter.WriteData();
				
	}
	

}
