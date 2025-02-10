package Project1;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class testFile {
    public static void main(String[] args) throws FileNotFoundException {
        File inFile = new File("ClassProjects/Project1/maze.dat"); // reading in file

        // variables for start and finish positions on the maze
        int startIndexX = 0;
        int startIndexY = 0;
        int exitIndexX = 0;
        int exitIndexY = 0;
        String S = "";
        Scanner sc = new Scanner(inFile);

        // scans the file incrementally for all the values needed
        while (sc.hasNextLine()) {
            S += sc.nextLine() + "\n"; // scans the maze in the file and then separates it by line
        }
        sc.close();

        // an array of strings is created, and is separated by lines
        String[] Ss = S.split("\n");
        int maxCols = 0;
        for (String line : Ss) {
            if (line.length() > maxCols) {
                maxCols = line.length();
            }
        }
        char[][] maze = new char[Ss.length][maxCols]; // [row][col]

        for (int i = 0; i < Ss.length; i++) { // runs through each row
            for (int x = 0; x < Ss[i].length(); x++) { // runs through each specific index in a given row (column)
                maze[i][x] = Ss[i].charAt(x);
                if (maze[i][x] == '+') {
                    startIndexX = i;
                    startIndexY = x;
                } else if (maze[i][x] == '-') {
                    exitIndexX = i;
                    exitIndexY = x;
                }
            }
            for (int x = Ss[i].length(); x < maxCols; x++) {
                maze[i][x] = 'X'; // Fill the remaining cells with 'X' to indicate walls
            }
        }

        // prints out the start and finish variables, along with unsolved maze, on console
        System.out.println("Start: (" + startIndexX + ", " + startIndexY + ")");
        System.out.println("Finish: (" + exitIndexX + ", " + exitIndexY + ")");
        printMaze(maze);

        // prints out the solved maze
        System.out.println(" ");
        System.out.println("Solved Maze:");
        if (solveMaze(maze, startIndexX, startIndexY, exitIndexX, exitIndexY)) {
            printMaze(maze);
        } else {
            System.out.println("Maze cannot be solved.");
        }
    }

    // prints the maze
    public static void printMaze(char[][] maze) {
        // similar to the string array, the for loop goes through each row and then each column
        for (int i = 0; i < maze.length; i++) {
            for (int x = 0; x < maze[i].length; x++) {
                System.out.print(maze[i][x]); // prints each char of the maze
            }
            System.out.println(); // separates the chars by line
        }
    }

    // solves the maze using the two-dimensional char array
    // all if statements check to see if they're out of bounds before moving
    public static boolean solveMaze(char[][] maze, int currentX, int currentY, int endX, int endY) {
        // checks to see if the current position is the finishing position
        // if finishing position has been reached, the solved maze is then printed
        if (currentX == endX && currentY == endY) {
            maze[currentX][currentY] = 'F';
            return true;
        }

        // Check if out of bounds or on a wall or already visited
        if (currentX < 0 || currentY < 0 || currentX >= maze.length || currentY >= maze[0].length || maze[currentX][currentY] == 'X' || maze[currentX][currentY] == 'V') {
            return false;
        }

        // Mark the current cell as visited
        maze[currentX][currentY] = 'V';

        // Recursively explore directions
        if (solveMaze(maze, currentX - 1, currentY, endX, endY)) return true; // Up
        if (solveMaze(maze, currentX + 1, currentY, endX, endY)) return true; // Down
        if (solveMaze(maze, currentX, currentY - 1, endX, endY)) return true; // Left
        if (solveMaze(maze, currentX, currentY + 1, endX, endY)) return true; // Right

        // Unmark the current cell (backtrack)
        maze[currentX][currentY] = ' ';

        return false;
    }
}