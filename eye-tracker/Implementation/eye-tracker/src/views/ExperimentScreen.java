package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Business.Experiment;
import Business.ModelType;
import Business.Question;

public class ExperimentScreen {

	private static final List<String> validResponses=new LinkedList<String>(){{add("A");add("B");add("C");add("D");}};

	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	private static final String DEFUALT_MODEL_TYPE = "Select a model type";
	private Experiment experiment;
	private JPanel firstPanel;
	private JPanel secondPanel;
	private int questionsIndex = 0;
	private JLabel statementLabel;
	private JLabel altenativesLabel;
	private JLabel imageLabel;
	private Font defaultFont;

	public ExperimentScreen() {
		frame = new JFrame();
		frame.setBackground(Color.WHITE);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.setTitle("PhD experiment APP");
		frame.setLocation(0, 0);
		frame.add(firstPanel());
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);
		frame.setVisible(true);
		frame.setBackground(Color.WHITE);
		defaultFont = new Font("Arial", Font.CENTER_BASELINE, 25);
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

		comboBoxmodelTypes.setSelectedIndex(2); // REMOVER THIS
		textParticipantId.setText("Rafael Duarte");// REMOVE THIS

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
					experiment = new Experiment(
							ModelType.getByDescription(comboBoxmodelTypes.getSelectedItem().toString()),
							textParticipantId.getText());
					firstPanel.setVisible(false);
					secondPanel();
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
		try {
			String path = experiment.getQuestions().get(index).getPaht();
			image = new ImageIcon(ImageIO.read(getClass().getResource(path)));
			if (index == 0) {
				imageLabel = new JLabel();
			}
			imageLabel.setIcon(image);
			secondPanel.add(imageLabel, BorderLayout.CENTER);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

	public void secondPanel() {
		secondPanel = new JPanel(new BorderLayout());
		setImage(questionsIndex);

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

				if (validResponses.contains(response)) {
					if (questionsIndex < experiment.getQuestions().size()) {
						//System.out.println("keyPressed=" + response);

						experiment.getResponses().add(response.charAt(0));

						questionsIndex++;

						if (questionsIndex < experiment.getQuestions().size()) {
							setImage(questionsIndex);
						} else {
							int score = experiment.getScore();
							JOptionPane.showMessageDialog(frame,
									"End of experiment.\r\n" + "Thank you so much for contributing to our experiment!",
									"End experiment message", JOptionPane.WARNING_MESSAGE);

						}

					}
				}
			}
		};

		secondPanel.addKeyListener(listener);
		secondPanel.setFocusable(true);
		frame.add(secondPanel);
	}

	private void setQuestions(int index) {
		Question currentQuetion = experiment.getQuestions().get(index);
		statementLabel.setText(currentQuetion.getStatement());
		String alternativas = "<html>" + currentQuetion.getAltenatives().replace("\r\n", "<br/>") + "</html>";
		altenativesLabel.setText(alternativas);
	}

}
