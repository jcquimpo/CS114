package Project1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class testFile {

    private static char[][] maze;
    private static int rows, cols;

    public static void main(String[] args) {
        try {
            // Read maze from file
            Scanner scanner = new Scanner(new File("ClassProjects/Project1/compressedMaze.dat"));
            rows = scanner.nextInt();
            cols = scanner.nextInt();
            scanner.nextLine();
            maze = new char[rows][cols];
            for (int i = 0; i < rows; i++) {
                String line = scanner.nextLine();
                for (int j = 0; j < cols; j++) {
                    maze[i][j] = line.charAt(j);
                }
            }
            scanner.close();

            // Find starting point
            int startRow = -1, startCol = -1;
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (maze[i][j] == '+') {
                        startRow = i;
                        startCol = j;
                        break;
                    }
                }
                if (startRow != -1) 
                break;
            }

            // Solve maze
            if (solveMaze(startRow, startCol)) {
                System.out.println("Maze solved!");
                printMaze();
            } else {
                System.out.println("Maze cannot be solved.");
            }

        } catch (FileNotFoundException e) {
            System.out.println("Error: maze.dat not found.");
        }
    }

    private static boolean solveMaze(int row, int col) {
        // Base case: reached the end
        if (maze[row][col] == '-') {
            return true;
        }

        // Mark current cell
        maze[row][col] = '+';

        // Check surrounding cells
        if (isValid(row - 1, col) && solveMaze(row - 1, col)) return true; // Up
        if (isValid(row + 1, col) && solveMaze(row + 1, col)) return true; // Down
        if (isValid(row, col - 1) && solveMaze(row, col - 1)) return true; // Left
        if (isValid(row, col + 1) && solveMaze(row, col + 1)) return true; // Right

        // Backtrack: mark cell as dead end
        maze[row][col] = '.';
        return false;
    }

    private static boolean isValid(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols && maze[row][col] != '#' && maze[row][col] != '.';
    }

    private static void printMaze() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(maze[i][j]);
            }
            System.out.println();
        }
    }
}