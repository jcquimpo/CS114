package Project1;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        // file path for your desktop computer
        File inFile = new File("CS114/ClassProjects/Project1/maze.dat");
        
        // file path for your laptop computer
        // File inFile = new File("ClassProjects/Project1/maze.dat");


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

        String[] Ss = S.split("\n");
        int maxCols = 0;
        for (String line : Ss) {
            if (line.length() > maxCols) {
                maxCols = line.length();
            }
        }
        // [row][col]
        char[][] maze = new char[Ss.length][maxCols]; 

        for (int i = 0; i < Ss.length; i++) { // runs through each row
            for (int x = 0; x < Ss[i].length(); x++) { 
                maze[i][x] = Ss[i].charAt(x);
                if (maze[i][x] == '+') {
                    maze[i][x] = 'S';
                    startIndexX = i;
                    startIndexY = x;
                } else if (maze[i][x] == '-') {
                    maze[i][x] = 'F';
                    exitIndexX = i;
                    exitIndexY = x;
                }
            }
        }

        // prints out the start and finish variables
        System.out.println("Start: (" + startIndexX + ", " + startIndexY + ")");
        System.out.println("Finish: (" + exitIndexX + ", " + exitIndexY + ")");
        // printMaze(maze);

        // prints out the solved maze
        System.out.println(" ");
        System.out.println("Solved Maze:");
        if (solveMaze(maze, startIndexX, startIndexY, exitIndexX, exitIndexY)) {
            printMaze(maze);
        } else {
            System.out.println("Unsolvable Maze...");
        }
    }

    // prints the maze
    public static void printMaze(char[][] maze) {
        // for loop goes through each row and then each column
        for (int i = 0; i < maze.length; i++) {
            for (int x = 0; x < maze[i].length; x++) {
                System.out.print(maze[i][x]);
            }
            System.out.println(); 
        }
    }

    // base case
    public static boolean solveMaze(char[][] maze, int currentX, int currentY, int endX, int endY) {
        // checks to see if the current position is the finishing position
        // if finishing position has been reached; the solved maze is then printed
        if (currentX == endX && currentY == endY) {
            maze[currentX][currentY] = '-';
            return true;
        }

        // check if out of bounds or on a wall or already visited
        if (currentX < 0 || currentY < 0 || currentX >= maze.length || currentY >= maze[0].length || maze[currentX][currentY] == 'X' || maze[currentX][currentY] == '+') {
            return false;
        }

        // mark visited cell
        maze[currentX][currentY] = '+';

        // Recursively explore directions
        if (solveMaze(maze, currentX - 1, currentY, endX, endY)) return true; // Up
        if (solveMaze(maze, currentX + 1, currentY, endX, endY)) return true; // Down
        if (solveMaze(maze, currentX, currentY - 1, endX, endY)) return true; // Left
        if (solveMaze(maze, currentX, currentY + 1, endX, endY)) return true; // Right

        // '.' the current cell but is dead end
        maze[currentX][currentY] = '.';

        return false;
    }
}