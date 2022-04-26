package samples;

import java.awt.BorderLayout;

//
//Polygons.java
//Polygons
//
//Created by Wagner on Mon Feb 23 2004.
//Copyright (c) 2004 W.L.Truppel. All rights reserved.
//

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * A class for a program that draws polygons as the user clicks around.
 * 
 * @author Wagner Truppel
 */
public class Polygons {
	/**
	 * This is where execution of the program starts.
	 * 
	 * @param args A list of strings.
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		JFrame frame = new JFrame("Polygons");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//frame.setUndecorated(true);
		frame.setBackground(Color.BLUE);
		frame.setVisible(true);
		//frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(0, 0);
		//frame.setContentPane(new DrawingPanel());
		frame.pack();
		//showOnScreen( 1, frame );	
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

/**
 * This is another class in this program, responsible for the panel where
 * drawing will happen.
 */
class DrawingPanel extends JPanel implements MouseListener, MouseMotionListener {
	/**
	 * This is how "constants" are defined in java.
	 */
	private static final Dimension MIN_DIM = new Dimension(300, 300);
	private static final Dimension MAX_DIM = new Dimension(800, 800);
	private static final Dimension PREF_DIM = new Dimension(500, 500);

	private boolean polygonIsNowComplete = false;

	/**
	 * The 'dummy' point tracking the mouse.
	 */
	private final Point trackPoint = new Point();

	/**
	 * The list of points making up a polygon.
	 */
	private List<Point> points = new LinkedList<>();

	/**
	 * The only constructor needed for this class.
	 */
	DrawingPanel() {
		super();
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	/**
	 * This is where all the drawing action happens.
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		int numPoints = points.size();
		if (numPoints == 0)
			return; // nothing to draw

		Point prevPoint = (Point) points.get(0);

		// draw polygon
		Iterator it = points.iterator();
		while (it.hasNext()) {
			Point curPoint = (Point) it.next();
			draw(g, prevPoint, curPoint);
			prevPoint = curPoint;
		}

		// now draw tracking line or complete the polygon
		if (polygonIsNowComplete)
			draw(g, prevPoint, (Point) points.get(0));
		else
			draw(g, prevPoint, trackPoint);
	}
	


	/**
	 * This method is required by the MouseListener interface, and is the only one
	 * that we really care about.
	 */
	public void mouseClicked(MouseEvent evt) {
		System.out.println("evt.getPoint().getX() "+evt.getPoint().getX());
		System.out.println("evt.getPoint().getY() "+evt.getPoint().getY());
		System.out.println("evt.getXOnScreen() "+evt.getXOnScreen());
		System.out.println("evt.getYOnScreen() "+evt.getYOnScreen());
		System.out.println("evt.getX() "+evt.getX());
		System.out.println("evt.getY() "+evt.getY());
		System.out.println("__________________ ");
		
		int x = evt.getX();
		int y = evt.getY();

		switch (evt.getClickCount()) {
		case 1: // single-click
			if (polygonIsNowComplete) {
//				points.clear();
//				polygonIsNowComplete = false;
				int[] xpoints = new int[points.size()];
				int[] ypoints = new int[points.size()];
				for (int i = 0; i < points.size(); i++) {
					xpoints[i] = points.get(i).x;
					ypoints[i] = points.get(i).y;
				}
				 
				Polygon p = new Polygon(xpoints, ypoints, points.size());
				if(p.contains(new Point(x, y)))
				{
					System.out.println("Insade");
				}else
				{
					System.out.println("Outside");
				}
						
			}else
			{
				points.add(new Point(x, y));
				repaint();
					
			}
			break;

		case 2: // double-click
			polygonIsNowComplete = true;
			points.add(new Point(x, y));
			repaint();
			break;

		default: // ignore anything else
			break;
		}
	}

	/**
	 * This method is required by the MouseMotionListener interface, and is the only
	 * one that we really care about.
	 */
	public void mouseMoved(MouseEvent evt) {
		trackPoint.x = evt.getX();
		trackPoint.y = evt.getY();
		repaint();
		if (polygonIsNowComplete) {
			int[] xpoints = new int[points.size()];
			int[] ypoints = new int[points.size()];
			for (int i = 0; i < points.size(); i++) {
				xpoints[i] = points.get(i).x;
				ypoints[i] = points.get(i).y;
			}
			 
			Polygon p = new Polygon(xpoints, ypoints, points.size());
			if(p.contains(new Point(evt.getX(), evt.getY())))
			{
				System.out.println("Insade");
			}else
			{
				System.out.println("Outside");
			}
		}
	
	}

	/**
	 * Utility method used to draw points and lines.
	 */
	private void draw(Graphics g, Point p1, Point p2) {
		int x1 = p1.x;
		int y1 = p1.y;

		int x2 = p2.x;
		int y2 = p2.y;
		
		// draw the line first so that the points
		// appear on top of the line ends, not below
		g.setColor(Color.red.darker());
		g.drawLine(x1, y1, x2, y2);
		//g.drawLine(x1 + 4, y1 + 4, x2 + 4, y2 + 4);
		//g.drawLine(x1 + 5, y1 + 5, x2 + 5, y2 + 5);

		g.setColor(Color.green);
		g.fillRect(x1-4, y1-4, 8, 8);

		g.setColor(Color.blue);
		g.fillOval(x2-2, y2-2, 4, 4);
	}

	/**
	 * This method is used to draw this panel with the correct minimum size.
	 */
	public Dimension getMinimumSize() {
		return MIN_DIM;
	}

	/**
	 * This method is used to draw this panel with the correct preferred size.
	 */
	public Dimension getPreferredSize() {
		return PREF_DIM;
	}

	/**
	 * This method is required by the MouseMotionListener interface, but we don't
	 * really listen to this kind of event.
	 */
	public void mouseDragged(MouseEvent evt) {
		/* do nothing */ }

	/**
	 * This method is required by the MouseListener interface, but we don't really
	 * listen to this kind of event.
	 */
	public void mousePressed(MouseEvent evt) {
		/* do nothing */ }

	/**
	 * This method is required by the MouseListener interface, but we don't really
	 * listen to this kind of event.
	 */
	public void mouseReleased(MouseEvent evt) {
		/* do nothing */ }

	/**
	 * This method is required by the MouseListener interface, but we don't really
	 * listen to this kind of event.
	 */
	public void mouseEntered(MouseEvent evt) {
		/* do nothing */ }

	/**
	 * This method is required by the MouseListener interface, but we don't really
	 * listen to this kind of event.
	 */
	public void mouseExited(MouseEvent evt) {
		/* do nothing */ }
}
