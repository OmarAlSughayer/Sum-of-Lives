# Sum of Lives
Sum of Lives is a Cellular Automata Subset Sum problem (SSA), a combination of the subset sum problem and Conway's Game of Life.
Every SSA is defined with a triple tulip (TARGET_OFFSET, REWARD, PENALTY), e.g. (8, 1, -1), and played in a grid. 
Cells are populated with integers. Each step of the game, every individual cell is considered. If a non-empty subset sum of its neighboring values adds to (TARGET_OFFSET + cellValue) then the cell's value is increased by REWARD, otherwise it is decresed by PENALTY.
Cells are later colored given a color based on their integer value, where closer values have closer colors on an RGB scale. This allows some interesting patterns to emerge as (will soon be seen) below. 


## Future Plans 
// TODO: make the GUI faster
// TODO: allow GUI to save images into gif's
// TODO: find more interesting patterns and analize them
// TODO: make screen adjust automatically
// TODO: pass everything as main arguments