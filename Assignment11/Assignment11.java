// Assignment: 11
// Name: Michael Buerger
// StudentID: 1214351462
// Lecture: Tu Th 9:00 AM - 10:15 AM
// Description: Driver class for maze solver, calls methods for initializing solver,
//              reading user input to maze, searching maze, and finally outputting results of search.

public class Assignment11 {

	public static void main(String[] args) {
		MazeSolver mazeSolver = new MazeSolver();
		mazeSolver.readMaze();

		System.out.println("Original maze: ");
		mazeSolver.printMaze();

		mazeSolver.depthFirstSearch();

		System.out.println("Maze after Easter Bunny traverse: ");
		mazeSolver.printMaze();

		if (mazeSolver.getEggFound() == 0)
			System.out.println("The Bunny was unable to recover the eggs, Easter is cancelled!");
		else
			System.out.println("The Easter Bunny found " + mazeSolver.getEggFound() + " egg(s)!");
	}
}