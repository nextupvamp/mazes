## Solution
* Implemented algorithms for constructing mazes: the Prima algorithm, the algorithm
Wilson's algorithm for constructing a braided maze.
* Implemented pathfinding algorithms: iterative DFS, A-star.

Various types of surfaces (modifiers) are implemented. Their
use is advisable only in mazes that have
several possible paths between any two points. Since the algorithms
Prima and Wilson generate "ideal" mazes, then it was decided
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
unlike non-woven, i.e. "ideal",
several paths can be laid between any two points.
4. Based on the user's choice, you will be prompted to add modifiers
to the maze. Modifiers increase or decrease the cost of passing
through the cell. There are two types of modifiers: jungle and diamonds. Jungle
increases the cost of passing through the cage by 2, diamonds - reduce by 3.
5. If the user agrees to add modifiers, then it will be offered
enter the probability of their appearance on each cell. The general probability
of the appearance of a modifier is introduced. The probability of whether it will be a diamond or a jungle
is the same.
6. Select the algorithm for constructing the maze.
7. Select the algorithm for finding the path from the entry point to the exit.

After entering the input data, an image of the generated
maze and an image of the maze with the found path will be displayed. 
The parameters of the found path will also be displayed: the length of the path is the cost of passing along the path, taking into account
modifiers, the number of steps is the number of traversed maze cells.
If the path is not found, a message about it will be displayed.
## Maze generation and solution algorithms
### Generation algorithms
#### Prim's algorithm
The algorithm works as follows:
1. Select an arbitrary vertex from G (graph) and add it to some (initially empty) set V.
2. Select an edge randomly, provided that it crosses the so-called "boundary" of the maze (a set of edges that move from inside the maze to a cell located outside the maze).
3. Add this edge to the minimum spanning tree, and the other vertex of the edge to V.
4. Repeat steps 2 and 3 until V includes all vertices from G.
   The result is a minimal spanning tree G – the maze.
#### Wilson's algorithm
The algorithm works as follows:
1. Select any random vertex and add it to a homogeneous spanning tree.
2. Select any vertex that is not yet in the homogeneous spanning tree, and perform a random move through the maze until we meet a vertex that is in a homogeneous spanning tree.
3. Add vertices and edges visited during random movement to a homogeneous spanning tree.
4. Repeat steps 2 and 3 until all vertices are added to a homogeneous spanning tree.
#### Braid maze building algorithm
The algorithm finds dead ends in an already built maze and removes them. As a result, the maze becomes braided.
### Solution algorithms
#### Iterative DFS algorithm
The algorithm works as follows:
1. Add the initial node to the stack.
2. Loop until the stack is empty:
1. Get the node at the top of the stack (current), mark it as visited and delete it.
   2. For each uninitiated child element of the current node, perform the following actions:
      1. Check if this is the target node. If yes, we return this child node.
      2. Otherwise, we put it on the stack.
3. If the stack is empty, then the target node has not been found.

#### A-star algorithm
The algorithm works as follows:
1. Create two lists: for open nodes and for closed nodes.
2. Initialize both lists. In a closed list, the start node points to an open list.
3. While there are elements in the open list:
1. We are looking for the node min with the smallest F
2. We remove min from the open list
3. We determine the neighbors of min
4. We check each neighbor:
      * If the target cell is a neighbor, stop searching
      * If not, calculate G for it, H. G = min.G + the distance between the neighbor and min. F = G + H.
      * If a node with the same position as its neighbor is in the open list and its F is less than that of its neighbor, skip this neighbor
      * If the node with the same position as the neighbor is in the closed list, and its F is less than that of the neighbor, skip this neighbor. Otherwise, we add the node to the open list.
   5. Add min to the closed list
