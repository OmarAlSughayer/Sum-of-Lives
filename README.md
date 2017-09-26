# Sum of Lives
Sum of Lives is a Cellular Automata Subset Sum problem (SSA), a combination of the subset sum problem and Conway's Game of Life.

Every SSA is defined with a triple tulip (TARGET_OFFSET, REWARD, PENALTY), e.g. (8, 1, -1), and played in a grid. 

Cells are populated with integers. Each step of the game, every individual cell is considered. If a non-empty subset sum of its neighboring values adds to (TARGET_OFFSET + cellValue) then the cell's value is increased by REWARD, otherwise it is decresed by PENALTY.

Cells are later colored given a color based on their integer value, where closer values have closer colors on an RGB rainbow scale. This allows some interesting patterns to emerge as (will soon be seen) below. 

## setup

No setup is required. Just click and run, no strings attached. 

## Functionality 

Piggybacking on Conway’s own description of his Game, the Sum of Lives demonstrates that order could emerge from chaos; and that simple rules could produce complexity. 

Moreover, the colorful representation functions as a new way of displaying interactions between numbers, in a manner that could be utilized as a ‘theory mine’. More precisely, some pre-existing theories of Number Theory explains some of the patterns that can be observed in the game. This suggest that some other patterns might serve as hints towards some other yet-to-be-discovered theories. 


## Interesting Patterns 

Gifs displayed here are produced through this same project, but their discovery rights go to Tom Quinn, jnazario, and several other Redditors. 

![a zero grid with a 2x2 matrix of 4's in the middle gives a flower effect
](https://github.com/OmarAlSughayer/Sum-of-Lives/tree/master/bin/Debug/world1.gif)

![a randomized grid with (3, 1, 999) as parameters, gives a whorly effect
](https://github.com/OmarAlSughayer/Sum-of-Lives/tree/master/bin/Debug/world2.gif)

![a randomized grid with (2, 5, 8) as parameters, gives a siezure effect
](https://github.com/OmarAlSughayer/Sum-of-Lives/tree/master/bin/Debug/world1.gif)

## Notes 

  * Currently for the animation within the JPanel to be saved correctly, the JPanel needs to be closed properly using the X button. Hopefully I'll patch that soon. 

## Future Plans 

TODO: ~~make the GUI faster~~

TODO: ~~allow GUI to save images into gif's~~

TODO: find more interesting patterns and analize them

TODO: make screen adjust automatically

TODO: pass everything as main arguments

TODO: ~~rewrite method comments with @params~~

TODO: add option to write numbers inside the cells

TODO: ~~fix the black color bug~~

TODO: fix the "cannot write empty sequence" error

TODO: force gif to be saved regardless of closing method