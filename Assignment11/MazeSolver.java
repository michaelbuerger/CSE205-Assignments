// Assignment: 11
// Name: Michael Buerger
// StudentID: 1214351462
// Lecture: Tu Th 9:00 AM - 10:15 AM
// Description: Maze solver, handles user input to maze, depth-first search of maze, and print method.

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class MazeSolver {
	// stack to store paths to pursue, for traversal and backtracking maze
	private Stack<Node> stack;
	private char[][] maze; // represents maze matrix
	private int eggFound; // num eggs found in search
	private int mWidth, mHeight; // width and height of maze

	// initialize maze
	public MazeSolver() {
		stack = new Stack<Node>();
		eggFound = 0;
	}

	public void depthFirstSearch() {
		stack.push(new Node(0, 0)); // push bunny's initial position

		while(!stack.empty()) {
			/* CURRENT NODE PROCESSING */
			Node currentNode = stack.pop(); // pop currently visited node

			if(getNodeValue(currentNode) == 'E') // current node is egg
				eggFound++;
			
			markNodeAsVisited(currentNode);
			/*                         */

			/* FUTURE NODE FINDING */
			// west
			Node futureNode = nodeOffset(currentNode, -1, 0); // create node representing potential path, set to west node
			char futureNodeValue = getNodeValue(futureNode); // get value of potential path
			if(futureNodeValue == '.' || futureNodeValue == 'E') // check if path is viable (either egg or non-visited path)
				stack.push(futureNode);

			// east
			futureNode = nodeOffset(currentNode, 1, 0); // set future node to east node
			futureNodeValue = getNodeValue(futureNode); // get value of potential path
			if(futureNodeValue == '.' || futureNodeValue == 'E') // check if path is viable (either egg or non-visited path)
				stack.push(futureNode);

			// north
			futureNode = nodeOffset(currentNode, 0, -1); // set future node to north node
			futureNodeValue = getNodeValue(futureNode); // get value of potential path
			if(futureNodeValue == '.' || futureNodeValue == 'E') // check if path is viable (either egg or non-visited path)
				stack.push(futureNode);

			// south
			futureNode = nodeOffset(currentNode, 0, 1); // set future node to south node
			futureNodeValue = getNodeValue(futureNode); // get value of potential path
			if(futureNodeValue == '.' || futureNodeValue == 'E') // check if path is viable (either egg or non-visited path)
				stack.push(futureNode);
			/*                     */
		}
	}

	// utility method to get value of node
	private char getNodeValue(Node node) {
		if(node != null) {
			int x = node.getX();
			int y = node.getY();

			if(x >= 0 && y >= 0 && x < mWidth && y < mHeight) // check if node is in bounds
				return maze[y][x]; // return value at node coords
		}

		return '!'; // node out of bounds or does not exist
	}

	// utility method to mark node as visited
	private void markNodeAsVisited(Node node) {
		if(node != null) {
			int x = node.getX();
			int y = node.getY();

			if(x >= 0 && y >= 0 && x < mWidth && y < mHeight) // check if node is in bounds
				maze[y][x] = 'x'; // set value at node to 'x'
		}
	}

	// utility method to create node given base node and coordinate offset, does not care about bounds
	private Node nodeOffset(Node baseNode, int xOffset, int yOffset) {
		if(baseNode == null)
			return new Node(0, 0); // default case, baseNode null

		return new Node(baseNode.getX() + xOffset, baseNode.getY() + yOffset); // return node with offset
	}

	// returns num eggs found
	public int getEggFound() {
		return eggFound;
	}

	// utility method to print maze matrix to console
	public void printMaze() {
		for(int y=0; y < mHeight; y++) { // traverse rows
			for(int x=0; x < mWidth; x++) { // traverse columns in each row
				System.out.print(maze[y][x]);
			}
			System.out.print('\n');
		}
		System.out.print('\n');
	}

	// ************************************************************************************
	// ************** Utility method to read maze from user input *************************
	// ************************************************************************************
	public void readMaze() {

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); // instanciate reader
			System.out.println("Height of the maze: ");
			String line = reader.readLine(); // get user input for height
			mHeight = Integer.parseInt(line); // set height of the maze

			System.out.println("Width of the maze: ");
			line = reader.readLine(); // get user input for width
			mWidth = Integer.parseInt(line); // set width of maze
			maze = new char[mHeight][mWidth]; // instanciate maze matrix with given dimensions

			for (int i = 0; i < mHeight; i++) { // traverse rows
				line = reader.readLine(); // read in line of data for given row
				for (int j = 0; j < mWidth; j++) {
					maze[i][j] = line.charAt(j); // set each element in line of data for given row to individual cells
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
