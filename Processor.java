// Omar A. AlSughayer
// Start Date: 08/27/2017 
// Last modification: 08/27/2017

import java.awt.*;
import java.util.*;
import java.io.*;

/**
* Processor manages an instance of the Subset Sum Automata (SSA) problem, a combination of the subset sum
*     problem and Conway's Game of Life. 
* Every SSA is defined with a triple tulip (TARGET_OFFSET, REWARD, PENALTY), e.g. (8, 1, -1), and played in a grid. 
* Cells are populated with integers. 
* Each step of the game, every individual cell is considered. If a non-empty subset sum of its neighboring
*	 values adds to (TARGET_OFFSET + cellValue) then the cell's value is increased by REWARD, otherwise it is
*	 decresed by PENALTY. 
*/
public class Processor
{
   public final int ROWS; // number of rows in the grid
   public final int COLUMNS; // number of columns in the grid
   public final int TARGET_OFFSET; // target value for neighboring sum
   public final int REWARD; // given reward for correct sum
   public final int PENALTY; // penalty 
   public final int MAX; // max allowed value in the grid, the minimum is always zero

   public static int[][] grid; 

   // constructor that initializes a random grid
   public Processor(int rows, int columns, int targetOffest, int reward, int penalty, int max){
      // set all values
      ROWS = rows;
      COLUMNS = columns;
      TARGET_OFFSET = targetOffest;
      REWARD = reward;
      PENALTY = penalty;
	  MAX = max;

      // initalize the grid then populate it with random values 
      grid = new int[ROWS][COLUMNS];
      populate();
   }

   // constructor that uses a passed grid
   public Processor(int[][] grid, int targetOffest, int reward, int penalty, int max){
	  // set all values
	  this.grid = grid; // TODO: deep-copy the grid instead 
      ROWS = grid.length;
      COLUMNS = grid[0].length;
      TARGET_OFFSET = targetOffest;
      REWARD = reward;
      PENALTY = penalty;
	  MAX = max;
   }

   // populates the grid randomly and uniformly with integer values in the range [0, MAX] 
   // TODO: make it so that numbers are not assigned uniformly
   private void populate(){
      // loop over the 2d array
      for(int i = 0; i < grid.length; i++){
         for(int j = 0; j < grid[i].length; j++){
            // uniformly choose a random value in the range [0, MAX]
            grid[i][j] = (int)(Math.random() * (MAX + 1));
         }
      }
   }

   // advances the game by one step then returns the grid after the fact
   public int[][] step(){
      int[][] newGrid = new int[ROWS][COLUMNS];

      // loop over the 2d array
      for(int i = 0; i < grid.length; i++){
         for(int j = 0; j < grid[i].length; j++){
			// check if the nieghbors have a subset that sums to TARGET
            if(neighboringSum(i, j, TARGET_OFFSET + grid[i][j])){
				// values don't go below zero, with no maximum
               newGrid[i][j] = Math.max(grid[i][j] + REWARD, 0);
            }
            else{
				// values don't go below zero, with no maximum
				newGrid[i][j] = Math.max(grid[i][j] - PENALTY, 0);
            }
         }
      }

      // reassign the grid then return it
      grid = newGrid;
      return grid;
   }

   // returns returns true iff the neighbors of cell [row][column] has non-empty subset-sum equal to sum
   // the grid is now defined as infinite in all directions, meaning that the left-most cells are neighbors
   //     to the right-most cells; and the top-most neighbors the bottom-most. So in reality, the grid 
   //     exists on the surface of torus, as appose to 2d space. This is intentional.  
   // TODO: add the option to process a normal grid. 
   private boolean neighboringSum(int row, int column, int sum){
      int[] shifts = {-1, 0, 1}; // to get the neighbors of a cell you shit its coordinates by these values
      int[] neighbors = new int[8]; // list of neighboring values
	  int ind = 0; // the least fancy way of keepign track of index

      // loop over all the neighbors of the cell **PLUS ITSELF**
      for(int rd: shifts){
         for(int cd: shifts){
            // to simulate a torus effect, use the given formulas
            // this gurnatees that if row = 0 then the upper neighbor is in the row (ROWS + -1)%ROWS = (ROWS - 1)
            // and similarly for shiting too far left, right, or diagonally.
			if(!(rd == 0 && cd == 0)) 
				neighbors[ind++] = grid[(ROWS + row + rd) % ROWS][(COLUMNS + column + cd) % COLUMNS];
         }
      }

      // use a helper method to check for the existence of such subset
      return isSubsetSum(neighbors, neighbors.length, sum);
   }

	/** comment out if you want to use the slower method (god knows why)
		a recursive method to solve the subset sum problem in exponential time
		remember that the subset sum problem is NP-Complete so a polynomial time
		solution (most likely) doesn't exist.
   // Returns true iff set[] has as non-empty set that sums up to sum
	private boolean isSubsetSum(int[] set, int n, int sum){
	   // a non-empty subset have been found
	   if (n != set.length && sum == 0)
		 return true;
	   // all subsets have been attempted with no solution at one branch
	   if (n == 0 && sum != 0)
		 return false;
 
	   // If last element is greater than sum, then ignore it
	   if (set[n-1] > sum)
		 return isSubsetSum(set, n-1, sum);
		
		// else check if a subset can be found by including or excluding the last element
	   return isSubsetSum(set, n-1, sum) || isSubsetSum(set, n-1, sum-set[n-1]);
	}
	*/

	// a dynamic programming method to solve the subset sum problem
	// it works in psedu-polynomial time, O(sum*n), which is the best known time complexity
	//	 family discovered as of yet 
	// Returns true iff set[] has as non-empty set that sums up to sum
	private boolean isSubsetSum(int set[], int n, int sum) {
		// The value of subset[i][j] will be true if there is a 
		// subset of set[0..j-1] with sum equal to i
		boolean[][] subset = new boolean[n+1][sum+1];
  
		// If sum is 0, a partial answer have been found
		for (int i = 0; i <= n; i++)
		  subset[i][0] = true;
  
		// If sum is not 0 and set is empty, a partial answer does not exist
		for (int i = 1; i <= sum; i++)
		  subset[0][i] = false;
  
		 // Fill the subset table in a botton up manner
		 for (int i = 1; i <= n; i++) {
		   for (int j = 1; j <= sum; j++) {
			 if(j < set[i-1])
				subset[i][j] = subset[i-1][j];
			 if (j >= set[i-1])
			   subset[i][j] = subset[i-1][j] || subset[i - 1][j-set[i-1]];
		   }
		 }
  
		 // the value of subset[n][sum] will therefore be if set[0...n] has a subset that adds to sum
		 return subset[n][sum];
	}

	// normal, old, and boring get objects
	public int getRow(){
		return ROWS;
	}

	// normaler, older, and boringer get objects
	public int getColumn(){
		return COLUMNS;
	}

	// normalest, oldest, and bornigest get object
	public int getMax(){
		return MAX;
	}

   // overriding the toString method
   public String toString(){
      // print in grid format like | 1 |
	  String str = "|\t";
      for(int i = 0; i < ROWS; i++){
		for(int j = 0; j < COLUMNS; j++){
			str += grid[i][j] + "\t";
        }
        str += "|\n";
        str += "|\t";
      }

	  return str; 
   }
}  
