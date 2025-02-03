package Project1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class testFile {
    public static void main(String[] args) {
        String filePath = "ClassProjects/Project1/maze.dat";
        char[][] maze = readMaze(filePath);
        if (maze != null) {
            int[] start = findStart(maze);
            if (start != null) {
                int startX = start[0];
                int startY = start[1];
                if (navigateMaze(maze, startX, startY)) {
                    System.out.println("Path found!");
                } else {
                    System.out.println("No path found.");
                }
                printMaze(maze);
            } else {
                System.out.println("No starting point found.");
            }
        }
    }

    public static char[][] readMaze(String filePath) {
        int rows = 0;
        int cols = 0;

        // First pass to determine the size of the maze
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                cols = line.length();
                rows++;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        char[][] maze = new char[rows][cols];

        // Second pass to read the maze data
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int row = 0;
            while ((line = br.readLine()) != null) {
                maze[row] = line.toCharArray();
                row++;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return maze;
    }

    public static int[] findStart(char[][] maze) {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if (maze[i][j] == '+') {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    public static boolean navigateMaze(char[][] maze, int x, int y) {
        // Check if out of bounds or on a wall or already visited
        if (x < 0 || y < 0 || x >= maze.length || y >= maze[0].length || maze[x][y] == 'X' || maze[x][y] == '.') {
            return false;
        }

        // Check if we found the goal
        if (maze[x][y] == '-') { // Assuming '-' is the endpoint
            return true;
        }

        // Mark the current cell as part of the path
        if (maze[x][y] != '+') {
            maze[x][y] = '.';
        }

        // Recursively explore neighbors
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for (int[] dir : directions) {
            if (navigateMaze(maze, x + dir[0], y + dir[1])) {
                return true;
            }
        }

        // Unmark the current cell (backtrack)
        if (maze[x][y] != '+') {
            maze[x][y] = ' ';
        }
        return false;
    }

    public static void printMaze(char[][] maze) {
        for (char[] row : maze) {
            for (char cell : row) {
                System.out.print(cell);
            }
            System.out.println();
        }
    }
}