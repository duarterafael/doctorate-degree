package Business;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controllers.DataController;
import controllers.ImageEditor;
import controllers.MovieController;
import views.ActionToolBar;
import views.MainFrame;
import views.TopMenu;

public class ScreenCaptureManager {

	private boolean recording;
	private DataController gazeController;
	private MovieController movieController;
	private ImageEditor imageEditor;

	private CaptureLoop captureLoop;
	private boolean paused;
	private long pauseStart;
	private long pausedTime;

	public String workingDirectory;

	// fields used in capturing screen
	private Robot robot;
	private Rectangle screenRect;
	
	public static GraphicsDevice getOnScreen( int screen) {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] gs = ge.getScreenDevices();
		
		if(screen > -1 && screen < gs.length) {
		  return  gs[screen];
		}else if(gs.length > 0) {
		   return gs[0];
		}else {
		    throw new RuntimeException("No Screens Found");
		}
	}

	public ScreenCaptureManager(DataController gazeController, MovieController movieController) {

		this.gazeController = gazeController;
		this.movieController = movieController;
		this.imageEditor = new ImageEditor(gazeController);
		this.paused = false;
		
		GraphicsDevice gDev = getOnScreen(1);
		Rectangle bounds = gDev.getDefaultConfiguration().getBounds();

		this.screenRect = new Rectangle((int) bounds.getMinX(),
                (int) bounds.getMinY(), (int) bounds.getWidth(), (int) bounds.getHeight());
		try {
			this.robot = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		recording = false;
		loadSettings();

		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				endRecording();
			}
		});
	}

	

	public BufferedImage captureScreen() {
		return robot.createScreenCapture(screenRect);
	}

	public void startRecording() {
		if (!recording) {
			if (!paused) {
				pausedTime = 0;
				String currentTime = getCurrentTime();

				if (workingDirectory == null) {
					movieController.startRecording(currentTime);
					gazeController.startRecording(currentTime);
				} else {
					movieController.startRecording(workingDirectory + "\\_" + currentTime);
					gazeController.startRecording(workingDirectory + "\\_" + currentTime);
				}

			} else {
				pausedTime += System.nanoTime() - pauseStart;
				gazeController.pauseRecording();
				paused = false;

			}

			recording = true;

			captureLoop = new CaptureLoop();
		
			captureLoop.start();

		}
	}

	public void pauseRecording() {
		if (recording) {
			recording = false;
			paused = true;
			pauseStart = System.nanoTime();

			gazeController.pauseRecording();
		}
	}

	public void endRecording() {
		if (recording || paused) {
			recording = false;

			try {
				captureLoop.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			movieController.endRecording();			

			gazeController.endRecording();
			
		}
	}

	private class CaptureLoop extends Thread {

		@Override
		public void run() {
			while (recording) {

				BufferedImage screenshot = captureScreen();
				imageEditor.addCurrentEyePosition(screenshot);

				movieController.encodeImage(screenshot, pausedTime);
			}
		}
	}
	
	public static String getCurrentTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy MM dd HH'h'mm'm'ss's'");
		Date date = new Date();

		return dateFormat.format(date);
	}

	public boolean isCapturing() {
		return recording;
	}

	public void setCapturing(boolean recording) {
		this.recording = recording;
	}

	public String getWorkingDirectory() {
		return workingDirectory;
	}

	public void setWorkingDirectory(String workingDirectory) {
		this.workingDirectory = workingDirectory;
		saveSettings();
	}

	public void loadSettings() {
		File file = new File("settings.txt");

		if (file.exists() && !file.isDirectory()) {
			try {
				Scanner scanner = new Scanner(file);
				while (scanner.hasNext()) {
					if (scanner.next().equals("workingDirectory")) {
						scanner.next();
						workingDirectory = scanner.nextLine();
						workingDirectory = workingDirectory.trim();
					}
				}
				scanner.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void saveSettings() {
		File file = new File("settings.txt");
		try {
			FileWriter fw = new FileWriter(file, false);
			fw.write("workingDirectory = " + workingDirectory);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void showOnScreen( int screen, JFrame frame ) {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] gs = ge.getScreenDevices();
		
		if(screen > -1 && screen < gs.length) {
		    gs[screen].setFullScreenWindow(frame);
		}else if(gs.length > 0) {
		    gs[0].setFullScreenWindow(frame);
		}else {
		    throw new RuntimeException("No Screens Found");
		}
	}

}
