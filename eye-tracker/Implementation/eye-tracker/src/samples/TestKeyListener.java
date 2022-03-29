package samples;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class TestKeyListener  implements KeyListener{
	public static JFrame frame;
	public static void main(String[] args) {
		TestKeyListener text = new TestKeyListener();
		
	}
	
	public TestKeyListener()
	{
		frame = new JFrame();
		frame.setBackground(Color.WHITE);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.setTitle("PhD experiment APP");
		frame.setLocation(0, 0);
		frame.add(new JLabel("Test Key loger"));
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);
		frame.setVisible(true);
		frame.setBackground(Color.WHITE);
		frame.addKeyListener(this);
	}
	

	@Override
	public void keyTyped(KeyEvent e) {
		displayInfo(e, "keyTyped: ");
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		displayInfo(e, "keyPressed: ");
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		displayInfo(e, "keyReleased: ");
		
	}
	 private void displayInfo(KeyEvent e, String keyStatus){
		 String response = KeyEvent.getKeyText(e.getKeyCode()).toUpperCase();

			System.out.println(keyStatus + " = " + response);
	 }

}
