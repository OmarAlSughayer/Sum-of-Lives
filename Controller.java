// Omar A. AlSughayer
// Start Date: 08/27/2017 

import java.util.*;

import java.awt.Canvas;

import java.io.File;
import java.io.IOException;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.stream.FileImageOutputStream;


/**
* Controller manages in instance of Processor and of GridGUI to visualize a game of SSA
*/
public class Controller extends Canvas {

	// TODO: make it so that the screen adjusts size according to how many pixels there are with a min and a max
	public static final int FRAME_LENGTH = 1000; // length of the output screen, currently always a square
	public static final int GRID_SIZE = 250; // size of the game grid, currently always a square
	public static final int WORLD_CHOICE = 2; // which test world is chosen
	public static final int DELAY = 100; // delay between each two frames  

	public static void main(String[] args) throws InterruptedException{
		//setup the graphics frame
		JFrame frame = new JFrame("Subset Sum Atutomaton");
		frame.setSize(FRAME_LENGTH, FRAME_LENGTH);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// choose the world you want to display
		Processor world = makeProcessor(WORLD_CHOICE);

		// set up and add the graphics component to the frame
		GridGUI window = new GridGUI(world, FRAME_LENGTH, DELAY);

		frame.add(window);

		// add an actionlistener to creat a gif of the animation after closing
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent event) {
				// get the sequence of bufferedImages then save it as a gif
				BufferedImage[] sequence = window.getSequence(); 
				try{ 
					creatGIF(sequence); 
				} 
				catch(IOException e) { 
					System.out.println("could not create gif");
				};
			}
		});
	}
	
	/** writes the sequence of buffered images passed as a gif and saves it in the current directory
	* @param sequence, a BufferedImage array containing each frame of the desired gif in order
	* @post an animated gif of sequence have been saved to the current directory under output.gif
	*/
	private static void creatGIF(BufferedImage[] sequence) throws IOException {
		// if the frame was interrupted before it starts animating then the sequence will be empty
		if(sequence.length != 0){ 
			// create a new BufferedOutputStream with the last argument
			ImageOutputStream output = new FileImageOutputStream(new File("output.gif"));
      
			// create a gif sequence with the type of the first image, 1 second
			// between frames, which loops continuously
			GifSequenceWriter writer = new GifSequenceWriter(output, sequence[0].getType(), 1, false);
			int count = 0;
			// write out the images in the sequence to the output
			for(BufferedImage frame : sequence) {
				System.out.println(count++);
				writer.writeToSequence(frame);
			}
      
			// close everything
			writer.close();
			output.close();

		} else {
			System.out.println("cannot write an empty sequence");
		}
		
	}

	/** offers multiple 'interesting' choices for Processors 
	*	@param choice the number of the desired world, currently worlds 1 through 4 are available
	*	@return a Processor object with the appropriate setup corresponding to "choice"
	*	@throws IllegalArgumentException if a non-existing choice number was passed
	*/
	private static Processor makeProcessor(int choice){

		//  You are my world
		// New world, the door is open
		// You are inside
		// One step apart
		Processor world = null; // default vaulue
		
		////////////////////////////////////////////////////////////////////////////
		// different world and tests //////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////

		switch(choice){
			case 1: // #01 a zero grid with a 2x2 matrix of 4's in the middle gives a flower effect
				int[][] grid = new int[GRID_SIZE][GRID_SIZE];
				// populate the grid with all zeros
				for(int i = 0; i < GRID_SIZE; i++){
					for(int j = 0; j < GRID_SIZE; j++){
						grid[i][j] = 0;
					}
				}

				// add one square of 4's in the middle
				grid[GRID_SIZE/2][GRID_SIZE/2] = 4; grid[GRID_SIZE/2 + 1][GRID_SIZE/2] = 4;
				grid[GRID_SIZE/2 + 1][GRID_SIZE/2 + 1] = 4; grid[GRID_SIZE/2][GRID_SIZE/2 + 1] = 4;
		
				world = new Processor(grid, 4, 3, 4, 30);
				break;  

			////////////////////////////////////////////////////////////////////////////////////////
			
			case 2: // #02 a randomized grid with (3, 1, 999) as parameters, gives a whorly effect
				world = new Processor(GRID_SIZE, GRID_SIZE, 3, 1, 999, 30);
				break;

			////////////////////////////////////////////////////////////////////////////////////////
			
			case 3: // #03 a randomized grid with (2, 5, 8) as parameters, gives a siezure effect
				world = new Processor(GRID_SIZE, GRID_SIZE, 2, 5, 8, 30);
				break;

			///////////////////////////////////////////////////////////////////////////////////////
			
			case 4: // #04
				world = new Processor(GRID_SIZE, GRID_SIZE, 4, 3, 5, 30);
				break;

			///////////////////////////////////////////////////////////////////////////////////////

			default: throw new IllegalArgumentException();
		}


		return world;
	}

}