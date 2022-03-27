package samples;

import java.awt.AWTException;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Screenshot {

	public static void main(String[] args) {
		  GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		    GraphicsDevice[] gDevs = ge.getScreenDevices();

		    for (GraphicsDevice gDev : gDevs) {
		        DisplayMode mode = gDev.getDisplayMode();
		        Rectangle bounds = gDev.getDefaultConfiguration().getBounds();
		        System.out.println(gDev.getIDstring());
		        System.out.println("Min : (" + bounds.getMinX() + "," + bounds.getMinY() + ") ;Max : (" + bounds.getMaxX()
		                + "," + bounds.getMaxY() + ")");
		        System.out.println("Width : " + mode.getWidth() + " ; Height :" + mode.getHeight());

		        try {
		            Robot robot = new Robot();

		            BufferedImage image = robot.createScreenCapture(new Rectangle((int) bounds.getMinX(),
		                    (int) bounds.getMinY(), (int) bounds.getWidth(), (int) bounds.getHeight()));
		            ImageIO.write(image, "png",
		                    new File("src/resources/screen_" + gDev.getIDstring().replace("\\", "") + ".png"));

		        } catch (AWTException e) {
		            e.printStackTrace();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }

		    }

	}

}
