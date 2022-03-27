import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;
import javax.swing.JLabel;

import views.ExperimentScreen;

public class StartProject {
	public static void main(String args[]) {
		ExperimentScreen experimentScreen = new ExperimentScreen();
		showOnScreen( 1, experimentScreen.frame );	
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
