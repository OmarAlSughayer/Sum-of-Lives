// Omar A. AlSughayer
// Start Date: 08/27/2017 

import java.util.*;

import java.io.File;
import java.io.IOException;
import javax.imageio.*;
import javax.imageio.metadata.*;
import javax.imageio.stream.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.imageio.ImageIO;

/**
* Controller manages in instance of Processor and of GridGUI to visualize a game of SSA
*/
public class GridGUI extends JPanel implements ActionListener {

	// TODO: make it so that the screen adjusts size according to how many pixels there are
	public final int FRAME_LENGTH; // length of the output screen, currently always a square
	public static Processor engine; // the processor object
	public Timer timer; // the timer object to conrtol repainting
	public int frameNum; // the number of the current frame
	public ArrayList<BufferedImage> sequence; 

	/** a constructer that uses 
	*	@param p: the Processor object (SSA world) to be animated and displayed
	*	@param length: length of the output screen
	*	@param delay: the delay between frames in milliseconds, basically 1/fps
	*/
	public GridGUI(Processor p, int length, int delay){
		// assign the variables
		engine = p;
		FRAME_LENGTH = length;
		// use this object as the timer's Actionlistenr (where the ActionEvent can be found)
		timer = new Timer(delay, this); 
		frameNum = 0;
		sequence = new ArrayList<BufferedImage>();
	}
	
	// overriding the paint object 
	public void paintComponent(Graphics g){
		super.paintComponent(g);

		// compute the length of one side of a grid's square
		int sideLength = FRAME_LENGTH / engine.getRow();
		// advance the grid once
		int[][] grid = engine.step();

		// loop over all cells in the gird
		for(int i = 0; i < grid.length; i++){
			for(int j = 0; j < grid[i].length; j++){
				int value = grid[i][j];

				// transform the value in the grid to an RGB coloring then draw it
				Color c = getColor(value);
				g.setColor(c);
				g.fillRect(i*sideLength, j*sideLength, sideLength, sideLength);
			}
		}

		// invoke the timer to wait a bit then call the actionlistener
		timer.start();
	}

	// repaints the whole panel 
	public void actionPerformed(ActionEvent e){
		// save the previous panel
		save();

		// keep updating the window until the user interrupts (just press the goddamn x button,
		//	I am not	making an even listener just so you can be lazy!)
		repaint();
	}

	// saves the current panel to the sequence then as a .png pic
	public void save()
	{
		// create a bufferedImage to draw into 
		BufferedImage bImg = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D td = bImg.createGraphics();
		// paint inside the 2d object
		this.paintAll(td);

		// add the bufferedImage to the sequence
		sequence.add(bImg);
		// save the frame as a png image
		//savePNG(bImg);
	}

	/** saves the current panel to the current directory as a .png pic
	*	@param bImg: the bufferedImage object to be saved as a .png
	*	@throws IOException if saving failed for any reason
	*
	*	@post bImg is saved into current directory as 'fram#.png'
	*/
	private void savePNG(BufferedImage bImg){
		// try to save the image as a PNG
		try {
			ImageIO.write(bImg, "png", new File("./frame" + ++frameNum + ".png"));
		} catch (IOException e) {
			// print the error	
			System.out.println(e);
		}
	}

	// returns an array of bufferedImages representing every frame in the grid
	//public BufferedImage[] getBuff(){
	public BufferedImage[] getSequence(){
		return sequence.toArray(new BufferedImage[sequence.size()]);
	}

	/** assigns a single, unique, RGB color to each integer, so that closer integers have
	*		closer colors on a rainbow scale
	*	@param value: an integer between 0 and the maximum number in this object's Processor
	*	@return an RGB color that corresponds to value on a rainbow scale
	*/
	private Color getColor(int value){
		
		// strap on boys lemme explaing this one
		/* any color is represented by a combination of intensities of Red, Green, and Blue
		*	In the rainbow scale, you can observe that one of the three colors is always FF (255)
		*											   another is 00 (0)
		*												while the third is a value from 00-FF (0-255)
		*	The rainbow scale starts at pure red (255, 0, 0) then progresses to yellow (255, 255, 0)
		*	Between these two, the value for green increases from 0 to 255 gradually.
		*	Then after yellow the scale lowers the red values slowly until it hits 0 at pure green (0, 255, 0).
		*	Going as such defines 6 periods in which the scale goes from one primary color to a secondary one then back again.
		*	By knowing which period we are in, we can decide which
		*		color should be 255, which should be 0, and which should be scaling up or down. 
		*	By knowing how much percent of the current period have we covered yet, we can figure out 
		*		the exact value the scaled number should be at. 
		*	This way we get the values for Red, Green, and Blue
		*	The ambiguity of this explanation is left for the reader to decipher as an exercise
		*/

		// RGB values
		int red = 0;
		int green = 0;
		int blue = 0;
		
		// scale the numbers from min (0) to max (engine.getMax()) over the rainbow scale where 0 is pure RED
		// and max is PURPLE then shows where exactly on both lines are we
		double ratio = value / (1.0 * engine.getMax());
		 // multiplying by 6 makes the integer part the period we are in and the fractional part
		 // the percentage completed of the current period
		double filler = ratio * 6;
		// the integer (whole number) part and the fractional part turned into a hex number represented in dec
		int whole = (int) (Math.floor(filler));
		int fracHex = (int) (255 * (filler - whole));

		// each case is a period
		switch(whole){
			case 0: // RED to YELLOW
				red = 255; green = fracHex; blue = 0;
				break;
			case 1: // YELLOW to GREEN
				red = 255 - fracHex; green = 255; blue = 0;
				break;
			case 2: // GREEN to... blue?
				red = 0; green = 255; blue = fracHex;
			case 3: 
				red = 0; green = 255 - fracHex; blue = 255;
				break;
			case 4: // I don't know what color really
				red = fracHex; green = 0; blue = 255;
				break;
			case 5: // I am bad with colors ok? I figured out the numbers but you go give them names
				red = 255; green = 0; blue = 255 - fracHex;
				break;

		}

		// create and return the color
		return new Color(red, green, blue);
	}
  
}