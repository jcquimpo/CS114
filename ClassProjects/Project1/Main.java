package Project1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;

public class Main {
    public static void main(String[] args) {
        int number = 5;
        System.out.println("Factorial of " + number + " is: " + factorial(number));

        String filePath = "ClassProjects/Project1/maze.dat";
        readFile(filePath);
    }

    public static void readFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int factorial(int n) {
        // System.out.println("Calculating factorial(" + n + ")");
        if (n <= 1) {
            return 1;
        } else {
            return n * factorial(n - 1);
        }
    }
}
