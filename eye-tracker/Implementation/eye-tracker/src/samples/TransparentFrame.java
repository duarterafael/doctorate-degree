package samples;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Polygon;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.sql.Savepoint;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;

import Business.Constants;
import Business.ExprerimentCSVWritter;

public class TransparentFrame extends JFrame {

	private JPanel contentPane;
	public static final int WIDTH_SCREEN = 1920;
	public static final int HEIGHT_SCREEN = 1080;
	private static final Color BG_COLOR_SCREEN = new Color(0.0f, 0.0f, 0.0f, 0.0f);
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TransparentFrame frame = new TransparentFrame();
					frame.setVisible(true);
					// showOnScreen( 1, frame );
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void showOnScreen(int screen, JFrame frame) {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] gs = ge.getScreenDevices();

		if (screen > -1 && screen < gs.length) {
			gs[screen].setFullScreenWindow(frame);
		} else if (gs.length > 0) {
			gs[0].setFullScreenWindow(frame);
		} else {
			throw new RuntimeException("No Screens Found");
		}
	}

	/**
	 * Create the frame.
	 */
	public TransparentFrame() {
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, WIDTH_SCREEN, HEIGHT_SCREEN);
		setLocationRelativeTo(null);
		setBackground(new Color(1.0f, 1.0f, 1.0f, 0.1f));

		DrawingPanel dp = new DrawingPanel();
		setContentPane(dp);
		addKeyListener(dp);

	}

	/**
	 * This is another class in this program, responsible for the panel where
	 * drawing will happen.
	 */
	class DrawingPanel extends JPanel implements MouseListener, MouseMotionListener, KeyListener {
		/**
		 * This is how "constants" are defined in java.
		 */
		private final Dimension MIN_DIM = new Dimension(300, 300);
		private final Dimension MAX_DIM = new Dimension(800, 800);
		private final Dimension PREF_DIM = new Dimension(500, 500);

		private boolean polygonIsNowComplete = false;
		private String currentAIOName = null;

		/**
		 * The 'dummy' point tracking the mouse.
		 */
		private final Point trackPoint = new Point();

		/**
		 * The list of points making up a polygon.
		 */
		private List<Point> points = new LinkedList<>();
		private Map<String, List<Point>> savedPoints = new TreeMap<>();

		/**
		 * The only constructor needed for this class.
		 */
		DrawingPanel() {
			super();
			setBackground(BG_COLOR_SCREEN);
			setBorder(new LineBorder(Color.YELLOW, 2));
			addMouseListener(this);
			addMouseMotionListener(this);
		}

		/**
		 * This is where all the drawing action happens.
		 */
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
		
			if(!points.isEmpty()) {
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
			}
			else {
				g.clearRect(0, 0, WIDTH_SCREEN, HEIGHT_SCREEN);
				setOpaque(false);
				
			
			}
			
//    		else
//    			draw(g, prevPoint, trackPoint);
		}

		/**
		 * This method is required by the MouseListener interface, and is the only one
		 * that we really care about.
		 */
		public void mouseClicked(MouseEvent evt) {
//			System.out.println("evt.getPoint().getX() " + evt.getPoint().getX());
//			System.out.println("evt.getPoint().getY() " + evt.getPoint().getY());
//			System.out.println("evt.getXOnScreen() " + evt.getXOnScreen());
//			System.out.println("evt.getYOnScreen() " + evt.getYOnScreen());
//			System.out.println("evt.getX() " + evt.getX());
//			System.out.println("evt.getY() " + evt.getY());
//			System.out.println("__________________ ");

			int x = evt.getX();
			int y = evt.getY();
	
			switch (evt.getClickCount()) {
			case 1: // single-click
				if (polygonIsNowComplete) {
//    				points.clear();
//    				polygonIsNowComplete = false;
//					int[] xpoints = new int[points.size()];
//					int[] ypoints = new int[points.size()];
//					for (int i = 0; i < points.size(); i++) {
//						xpoints[i] = points.get(i).x;
//						ypoints[i] = points.get(i).y;
//					}
//
//					Polygon p = new Polygon(xpoints, ypoints, points.size());
//					if (p.contains(new Point(x, y))) {
//						System.out.println("Insade");
//					} else {
//						System.out.println("Outside");
//					}

				} else {
					addPoint(new Point(x, y));
					repaint();

				}
				break;

			case 2: // double-click
				polygonIsNowComplete = true;
				repaint();
				break;

			default: // ignore anything else
				break;
			}
		}
		
		private void addPoint(Point currentPoint)
		{
			for (Point point : points) {
				if(currentPoint.x == point.x && currentPoint.y == point.y)
					return;
			}
			points.add(currentPoint);
			System.out.println(currentPoint.x+", "+currentPoint.y);

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
//				int[] xpoints = new int[points.size()];
//				int[] ypoints = new int[points.size()];
//				for (int i = 0; i < points.size(); i++) {
//					xpoints[i] = points.get(i).x;
//					ypoints[i] = points.get(i).y;
//				}
//
//				Polygon p = new Polygon(xpoints, ypoints, points.size());
//				if (p.contains(new Point(evt.getX(), evt.getY()))) {
//					System.out.println("Insade");
//				} else {
//					System.out.println("Outside");
//				}
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

			g.setColor(Color.red.darker());
			g.drawLine(x1, y1, x2, y2);

			g.setColor(Color.green);
			g.fillRect(x1 - 4, y1 - 4, 8, 8);

			g.setColor(Color.blue);
			g.fillOval(x2 - 2, y2 - 2, 4, 4);
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

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyReleased(KeyEvent e) {
			String response = KeyEvent.getKeyText(e.getKeyCode());
			if(response.equals("F1"))
			{
				System.out.println("F1 - Help");
				System.out.println("F2 - Get current AIO Name");
				System.out.println("F3 - Get current mapping");
				System.out.println("F4 - Get all mapping");
				System.out.println("F5 - Clear AIO Name");
				System.out.println("F6 - Remove last point");
				System.out.println("F7 - Clear Mappin");
				System.out.println("F8 - Save Mapping");
				System.out.println("F9 - Confirm to save Mappin");
			}
			else if(response.equals("F2"))
			{
				System.out.println("Current AIO Name: "+currentAIOName);
			}
			else if(response.equals("F3"))
			{
				printPoins(points);
			}else if(response.equals("F4"))
			{
				printAllMapping();
			}else if(response.equals("F5"))
			{
				currentAIOName = null;
				System.out.println("Clear AIO namem.");
			}else if(response.equals("F6"))
			{
				removeLastPoint();
				printPoins(points);
			}
			else if(response.equals("F7"))
			{
				clearMapping();
			}else if(response.equals("F8"))
			{
				saveMapping();
			}
			else if(response.equals("F9"))
			{
				storeMapping();
			}else if(isValidName(response))
			{
				if(currentAIOName == null)
				{
					currentAIOName = "";
				}
				currentAIOName += response;
			}
			
		}
		
		private void printPoins(List<Point> points)
		{
			for (Point point : points) {
				System.out.print(point.getX()+","+point.getY()+" ");
			}
			System.out.println("");
		}
		
		public void saveMapping()
		{
			if(currentAIOName == null)
			{
				System.out.println("ERRO: Please define a name to AIO.");
			}else if(!polygonIsNowComplete)
			{
				System.out.println("ERRO: Please double click to finalize de polygon.");
			}else if(savedPoints.containsKey(currentAIOName)){
				System.out.println("ERRO: There is a AIO whith this name.");
			}
			else
			{
				printPoins(points);
				savedPoints.put(currentAIOName, new LinkedList<Point>());
				savedPoints.get(currentAIOName).addAll(points);
				currentAIOName = null;
				clearMapping();
				System.out.println("Mapping saved");
			}
		}
		
		public void removeLastPoint() {
			if(points.size() >= 1)
			{
				points.remove(points.size()-1);
				polygonIsNowComplete = false;
				repaint();
			}
			
		}

		public boolean isValidName(String s) {
			return s != null && s.length() == 1 && s.matches("^[a-zA-Z0-9]*$");
		}

		private void clearMapping() {
			points.clear();
			polygonIsNowComplete = false;
			repaint();
		}
		
		private void printAllMapping()
		{
			for (Entry<String, List<Point>> item : savedPoints.entrySet()) {
				System.out.print(item.getKey());
				printPoins(item.getValue());
				System.out.println();
			}
					
		}
		
		private void storeMapping()
		{
			List<String> headers = new LinkedList<String>();
			headers.add("AIO Name");
			
			
			
			List<List<String>> dataList = new LinkedList<List<String>>();
			for (Entry<String, List<Point>> item : savedPoints.entrySet()) {
				List<String> data = new LinkedList<String>();
				data.add(item.getKey());
				for (Point point : item.getValue()) {
					data.add(point.x + ","+point.y);
				}
				dataList.add(data);
			}
			String csvPath = (new File("E:\\Google Drive\\Doutorado\\Ecomp\\dev\\doctorate-degree\\eye-tracker\\Implementation\\eye-tracker")).getAbsolutePath();
			ExprerimentCSVWritter exprerimentCSVWritter = new ExprerimentCSVWritter(headers, dataList, csvPath, "MappingAIO"+(Calendar.getInstance().getTimeInMillis())+".csv");
			exprerimentCSVWritter.WriteData();
					
		}
	}

}