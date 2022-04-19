package neurosky;

import java.awt.Component;
import java.awt.Container;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JToolBar;

import Business.Constants;
 
public class NeuroskyPlayer{
	
	private static JButton recordButton;
	private static JButton stopButton;
	
	private static Image recordImage;
	private static Image pauseImage;
	private static Image stopImage;
	private static ThinkGearSocket controller;
	
	
    public static void addComponentsToPane(Container pane) {
    	controller = new ThinkGearSocket();
    	
        pane.setLayout(new BoxLayout(pane, BoxLayout.X_AXIS));
 
        recordButton = addAButton(recordImage, pane);
        recordButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(controller.isRunning()) {
					controller.pause();
					recordButton.setIcon(new ImageIcon(recordImage));
				}
				else {
					try {
						controller.start();
						recordButton.setIcon(new ImageIcon(pauseImage));
					} catch (ConnectException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
				
			}
		});
        stopButton = addAButton(stopImage, pane);
        stopButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.stop();
				controller.getEEGDataManager().StoreNeuroSkyData(Calendar.getInstance().getTimeInMillis()+"_neurosky_output.csv", null);
				recordButton.setIcon(new ImageIcon(recordImage));
				
				
			}
		});
    }
 
    private static JButton addAButton(Image icon, Container container) {
        JButton button = new JButton();
        button.setIcon(new ImageIcon(icon));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(button);
        return button;
    }
 
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
    	try {
			recordImage = ImageIO.read(new File("E:\\Google Drive\\Doutorado\\Ecomp\\dev\\doctorate-degree\\eye-tracker\\Implementation\\eye-tracker\\src\\resources\\Record-32.png"));
			pauseImage = ImageIO.read(new File("E:\\Google Drive\\Doutorado\\Ecomp\\dev\\doctorate-degree\\eye-tracker\\Implementation\\eye-tracker\\src\\resources\\Pause-32.png"));
			stopImage = ImageIO.read(new File("E:\\Google Drive\\Doutorado\\Ecomp\\dev\\doctorate-degree\\eye-tracker\\Implementation\\eye-tracker\\src\\resources\\Stop-32.png"));
			 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    
    
        //Create and set up the window.
        JFrame frame = new JFrame("BoxLayoutDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Set up the content pane.
        addComponentsToPane(frame.getContentPane());
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
 
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

	
}