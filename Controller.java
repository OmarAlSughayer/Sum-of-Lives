// Omar A. AlSughayer
// Start Date: 08/27/2017 
// Last modification: 08/27/2017

import java.awt.*;
import java.util.*;
import java.io.*;
import javax.swing.JFrame;

/**
* Controller manages in instance of Processor and of GridGUI to visualize a game of SSA
*/
public class Controller extends Canvas {

	// TODO: make it so that the screen adjusts size according to how many pixels there are with a min and a max
	public static final int FRAME_LENGTH = 1000; // length of the output screen, currently always a square
	public static final int GRID_SIZE = 250; // size of the game grid, currently always a square

	public static void main(String[] args) throws InterruptedException{
		//setup the graphics frame
		JFrame frame = new JFrame("Subset Sum Atutomaton");
		frame.setSize(FRAME_LENGTH, FRAME_LENGTH);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Processor world = makeProcessor();

		// set up and add the graphics component to the frame
		GridGUI window = new GridGUI(world, FRAME_LENGTH);
		frame.add(window);

		// keep updating the window until the user interrupts (just press the goddamn x button, I am not
		//			 making an even listener just so you can be lazy!)
		while(true){
			Thread.sleep(500);
			window.repaint();
		}
	}

	private static Processor makeProcessor(){

		//  You are my world
		// New world, the door is open
		// You are inside
		// One step apart
		Processor world;
		
		////////////////////////////////////////////////////////////////////////////
		// different world and tests //////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////


		// #01 a zero grid with a 2x2 matrix of 4's in the middle gives a flower effect
		// create the test grid 
		/*int[][] grid = new int[GRID_SIZE][GRID_SIZE];
		for(int i = 0; i < GRID_SIZE; i++){
			for(int j = 0; j < GRID_SIZE; j++){
				// grid 2
				grid[i][j] = 0;
			}
		}

		grid[GRID_SIZE/2][GRID_SIZE/2] = 4; grid[GRID_SIZE/2 + 1][GRID_SIZE/2] = 4;
		grid[GRID_SIZE/2 + 1][GRID_SIZE/2 + 1] = 4; grid[GRID_SIZE/2][GRID_SIZE/2 + 1] = 4;
		
		world = new Processor(grid, 4, 3, 4, 30);*/

		////////////////////////////////////////////////////////////////////////

		// #02 a randomized grid with (3, 1, 999) as parameters give a whorly effect
		// create the processor object
		world = new Processor(GRID_SIZE, GRID_SIZE, 3, 1, 999, 30);

		///////////////////////////////////////////////////////////////////////

		// #03 

		return world;
	}

}