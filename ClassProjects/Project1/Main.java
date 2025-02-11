package Project1;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class Main {

    private static char[][] maze;
    private static int startrow, startcol, finishrow, finishcol;
    private static ArrayList<String> mazeBuffer;

    public static void initializeMaze(String fileName) {
        startrow = startcol = finishrow = finishcol = -1;

        mazeBuffer = new ArrayList<String>();
        int numcols = 0;
        try {
            Scanner file = new Scanner(new File(fileName));
            while (file.hasNext()) {
                String nextLine = file.nextLine();
                mazeBuffer.add(nextLine);
                if (nextLine.length() > numcols)
                    numcols = nextLine.length();
            }
        } catch (Exception e) {
            System.out.println(fileName + " has an issue");
        }
        int numrows = mazeBuffer.size();
        maze = new char[numrows][numcols];
        for (int r = 0; r < numrows; r++) {
            String row = mazeBuffer.get(r);
            for (int c = 0; c < numcols; c++) {
                if (c < row.length())
                    maze[r][c] = row.charAt(c);
                else
                    maze[r][c] = 'X';

                if (maze[r][c] == '+') {
                    startrow = r;
                    startcol = c;
                }
                if (maze[r][c] == '-') {
                    finishrow = r;
                    finishcol = c;
                }
            }
        }
        System.out.println("Maze loaded");
    }

    public static void printMaze() {
        for (char[] row : maze) {
            for (char c : row)
                System.out.print(c);
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        initializeMaze("ClassProjects/Project1/maze.dat");
        printMaze();
        if (solveMaze(startrow, startcol))
            printMaze();
        else
            System.out.println("Unsolvable.");
    }

    public static boolean solveMaze(int r, int c) {
        // Check if out of bounds
        if (r < 0 || c < 0 || r >= maze.length || c >= maze[0].length)
            return false;

        // Check if we found the goal
        if (maze[r][c] == '-')
            return true;

        // Check if on a wall or already visited
        if (maze[r][c] == 'X' || maze[r][c] == '.' || maze[r][c] == '+')
            return false;

        // Mark the current cell as part of the path
        maze[r][c] = '+';

        // Recursively explore directions
        if (solveMaze(r - 1, c)) return true; // Up
        if (solveMaze(r + 1, c)) return true; // Down
        if (solveMaze(r, c - 1)) return true; // Left
        if (solveMaze(r, c + 1)) return true; // Right

        // Unmark the current cell (backtrack)
        maze[r][c] = '.';
        return false;
    }
}
