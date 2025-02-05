package Project1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class testFile {

    private char[][] theMaze;
    private int colStart, rowStart;
    private int rows, cols;
    private String outputFilename;

    public testFile(String filename) throws IOException {
        try {
            this.outputFilename = filename;
            Scanner scan = new Scanner(new File(filename));
            StringBuilder sb = new StringBuilder();
            while (scan.hasNext()) {
                sb.append(scan.nextLine());
                this.rows++;
            }
            this.cols = sb.length() / this.rows;
            this.theMaze = new char[this.rows][this.cols];
            int m = 0;
            System.out.println();
            for (int i = 0; i < this.rows; i++) {
                for (int j = 0; j < this.cols; j++) {
                    this.theMaze[i][j] = sb.charAt(m++);
                }
            }
            scan.close();
            findStart();
            if (solve(this.rowStart, this.colStart)) {
                System.out.println("Maze solved!");
            } else {
                System.out.println("Maze cannot be solved.");
            }
            printMaze();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("ERROR : " + e.getMessage());
        }
    }

    private void findStart() {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                if (this.theMaze[i][j] == '+') {
                    this.rowStart = i;
                    this.colStart = j;
                    return;
                }
            }
        }
    }

    private boolean solve(int row, int col) {
        // Check if out of bounds or on a wall or already visited
        if (row < 0 || col < 0 || row >= rows || col >= cols || theMaze[row][col] == 'X' || theMaze[row][col] == '.') {
            return false;
        }

        // Check if we found the goal
        if (theMaze[row][col] == '-') { 
            return true;
        }

        // Mark the current cell as part of the path
        if (theMaze[row][col] != '+') {
            theMaze[row][col] = '+';
        }

        // Recursively explore directions
        if (solve(row - 1, col)) return true; // Up
        if (solve(row + 1, col)) return true; // Down
        if (solve(row, col - 1)) return true; // Left
        if (solve(row, col + 1)) return true; // Right

        // Unmark the current cell (backtrack)
        if (theMaze[row][col] != '+') {
            theMaze[row][col] = '.';
        }
        return false;
    }

    private void printMaze() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(theMaze[i][j]);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        try {
            new testFile("ClassProjects/Project1/compressedMaze.dat");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
