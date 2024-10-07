## Solution
* Implemented algorithms for constructing mazes: the Prim's algorithm,
the Wilson's algorithm, the algorithm for constructing a braided maze.
* Implemented pathfinding algorithms: iterative DFS, A-star.

Various types of surfaces (modifiers) are implemented. Their
use is advisable only in mazes that have
several possible paths between any two points. Since the Prim's and Wilson's algorithms
generate "ideal" mazes, then it was decided
to propose placing modifiers only on braided mazes,
since they are multipath. It is also
advisable to use the A-star search algorithm with modifiers, since it takes into account
the cost of the transition. However, its use is not limited to mazes
without modifiers.
## Usage
To build an image of the maze and find a
path in it, you need to:
1. Enter the parameters of the maze: height and
width. The minimum dimensions of the maze are 1 x 1, the maximum is 50 x 50.
2. Enter the entry point and exit point. The user will be offered
two ways to enter these points: by quadrants and by coordinates.
Quadrant input implies the location of the input/output in the corners
of the selected quadrant. Coordinate input is only possible on the boundary
cells of the maze. Entering the entrance and exit in the same cell is not prohibited.
3. Choose whether the maze will be braided. The braided maze is 
a maze without dead ends. Its peculiarity is that,
unlike non-braid, i.e. "ideal",
several paths can be laid between any two points.
4. Based on the user's choice, you will be prompted to add modifiers
to the maze. Modifiers increase or decrease the cost of passing
through the cell. There are two types of modifiers: jungle and diamonds. Jungle
increases the cost of passing through the cage by 2, diamonds - reduce by 3.
5. If the user agrees to add modifiers, then it will be offered
enter the probability of their appearance on each cell. User enters the general probability
of the appearance of a modifier. The probability of whether it will be a diamond or a jungle
is the same (general_probability / 2).
6. Select the algorithm for constructing the maze.
7. Select the algorithm for finding the path from the entry point to the exit.

After entering the input data, an image of the generated
maze and an image of the maze with the found path will be displayed. 
The parameters of the found path will also be displayed: the length of the path is the cost of passing along the path, taking into account
modifiers, the number of steps is the number of traversed maze cells.
If the path is not found, a message about it will be displayed.

## Example of generated maze
Unicursal maze generated with Prim's algorithm. Path was found with iterative DFS.
━━━━━━━━━━━━┳━┳━┳━━━━━━━━━━━━━━━━━━━━━━━┳━┳━┳━━━┳━┳━━━━━━━━━┓
 * * * * * *┃ ┃ ┃  * * * * * * * * * * *┃ ┃ ┃   ┃ ┃         ┃
┏━━━━━━━┳━━ ┃ ┃ ┗━━ ┏━━━━ ┃ ┏━━━━ ┏━━━┓ ┃ ┃ ┗━━ ┃ ┣━━━━━━ ┏━┫
┃       ┃  * * * * *┃     ┃ ┃     ┃   ┃*┃ ┃     ┃ ┃       ┃ ┃
┣━━ ┃ ┃ ┗━━━┳━━ ┏━━ ┣━━ ┏━┫ ┣━━ ┏━╋━┓ ┃ ┃ ┗━┳━━ ┃ ┣━━━┳━┓ ┃ ┃
┃   ┃ ┃     ┃   ┃   ┃   ┃ ┃ ┃   ┃ ┃ ┃  * * *┃   ┃ ┃   ┃ ┃ ┃ ┃
┣━━━╋━┻━━ ┃ ┗━━ ┃ ┃ ┣━━━┛ ┃ ┣━┳━┛ ┃ ┃ ┏━━━┓ ┣━━ ┃ ┣━━ ┃ ┃ ┃ ┃
┃   ┃     ┃     ┃ ┃ ┃       ┃ ┃       ┃   ┃*┃   ┃ ┃   ┃     ┃
┣━━ ┗━━━━━┫ ┏━━━╋━┛ ┗━┓ ┃ ┃ ┃ ┃ ┃ ┃ ┏━┻━━ ┃ ┗━━ ┃ ┗━━ ┗━━━━ ┃
┃         ┃ ┃   ┃     ┃ ┃ ┃ ┃   ┃ ┃ ┃      * * * * * * * * *┃
┃ ┏━━━┳━━ ┗━╋━━ ┣━━━┳━╋━┻━┻━┛ ━━╋━┛ ┃ ┏━┳━━━━━━━┳━━━┳━━ ┏━━ ┃
┃ ┃   ┃     ┃   ┃   ┃ ┃         ┃   ┃ ┃ ┃       ┃   ┃   ┃  *┃
┣━┻━━ ┃ ┃ ┃ ┣━━ ┃ ┏━┫ ┗━━━━ ┏━━ ┃ ┏━┻━┛ ┃ ┏━━ ━━╋━┓ ┗━━ ┃ ┃ ┃
┃       ┃ ┃ ┃   ┃ ┃ ┃       ┃   ┃ ┃       ┃     ┃ ┃     ┃ ┃*┃
┃ ┏━━━━━┛ ┗━╋━━ ┃ ┃ ┣━━━┓ ━━┫ ┏━╋━┻━━ ┃ ┏━┛ ┏━━ ┃ ┃ ┃ ┏━┻━┛ ┃
┃ ┃         ┃   ┃   ┃   ┃   ┃ ┃ ┃     ┃ ┃   ┃       ┃ ┃    *┃
┣━╋━━ ┃ ┏━━ ┗━━ ┃ ┏━╋━━ ┗━━━┻━┛ ┗━┳━┓ ┃ ┣━━━┛ ┃ ┃ ┃ ┣━╋━━━┓ ┃
┃ ┃   ┃ ┃         ┃ ┃             ┃ ┃ ┃ ┃     ┃ ┃ ┃ ┃ ┃   ┃*┃
┃ ┗━━━╋━┫ ┏━━━━ ┃ ┃ ┣━━━━━━━┳━┳━┓ ┃ ┃ ┗━┫ ┃ ┏━┛ ┃ ┃ ┃ ┣━━ ┃ ┃
┃     ┃ ┃ ┃     ┃   ┃       ┃ ┃ ┃ ┃     ┃ ┃ ┃   ┃ ┃ ┃ ┃  * *┃
┣━┳━┓ ┃ ┃ ┃ ┃ ┃ ┣━━ ┗━━━━ ━━┫ ┃ ┃ ┗━━ ┏━╋━┛ ┣━━━╋━╋━┫ ┃ ┃ ┃ ┃
┃ ┃ ┃     ┃ ┃ ┃ ┃           ┃         ┃ ┃   ┃   ┃ ┃ ┃   ┃*┃ ┃
┃ ┃ ┗━━━┳━╋━┻━╋━┫ ┃ ┃ ┏━━━━ ┗━━ ┏━━━━━┛ ┗━━━╋━┓ ┃ ┃ ┗━━━┛ ┗━┫
┃ ┃     ┃ ┃   ┃ ┃ ┃ ┃ ┃         ┃           ┃ ┃          *  ┃
┃ ┗━━ ━━┛ ┗━━ ┃ ┃ ┃ ┗━┫ ┏━━ ┏━━ ┣━━━━━━━┓ ━━┛ ┗━━ ┏━━ ┏━━ ┃ ┃
┃                 ┃   ┃ ┃   ┃   ┃       ┃         ┃   ┃  *┃ ┃
┃ ┏━━ ━━┓ ┃ ┃ ┏━┓ ┗━┳━┛ ┣━┳━╋━━ ┣━━━━━━ ┃ ┏━━ ┃ ┃ ┣━━━┻━━ ┗━┫
┃ ┃     ┃ ┃ ┃ ┃ ┃   ┃   ┃ ┃ ┃   ┃       ┃ ┃   ┃ ┃ ┃      * *┃
┣━┛ ━━━━┫ ┣━┻━┛ ┃ ┃ ┣━━━┛ ┃ ┗━━━┻━━━━━━ ┗━┛ ┃ ┣━┛ ┃ ┏━━ ┃ ┃ ┃
┃       ┃ ┃       ┃ ┃                       ┃ ┃   ┃ ┃   ┃ ┃* 
┗━━━━━━━┻━┻━━━━━━━┻━┻━━━━━━━━━━━━━━━━━━━━━━━┻━┻━━━┻━┻━━━┻━┻━━
