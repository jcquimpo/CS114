import java.util.Arrays;

public class heapSort {

    public static <T extends Comparable<? super T>> void heapSort(T[] array) {
        // Build heap
        for (int i = array.length / 2 - 1; i >= 0; i--) {
            heapify(array, array.length, i);
        }
        
        // Extract elements from heap
        for (int i = array.length - 1; i > 0; i--) {
            // Swap root with last element
            T temp = array[0];
            array[0] = array[i];
            array[i] = temp;
            
            heapify(array, i, 0);
        }
    }

    private static <T extends Comparable<? super T>> void heapify(T[] array, int heapSize, int root) {
        int largest = root;
        int L_chld = 2 * root + 1;
        int R_chld = 2 * root + 2;
        
        // Check if left child is larger than root
        if (L_chld < heapSize && array[L_chld].compareTo(array[largest]) > 0) {
            largest = L_chld;
        }
        
        // Check if right child is larger than current largest
        if (R_chld < heapSize && array[R_chld].compareTo(array[largest]) > 0) {
            largest = R_chld;
        }
        
        // If largest is not root
        if (largest != root) {
            T temp = array[root];
            array[root] = array[largest];
            array[largest] = temp;
            
            // Recursively heapify chosen sub-tree
            heapify(array, heapSize, largest);
        }
    }

    // Main method for testing
    public static void main(String[] args) {
        // Test with an array of integers
        Integer[] intArray = {4, 10, 3, 5, 1};
        System.out.println("Original array: " + Arrays.toString(intArray));
        heapSort(intArray);
        System.out.println("Sorted array: " + Arrays.toString(intArray));

        // Test with an array of strings
        String[] stringArray = {"banana", "apple", "cherry", "date"};
        System.out.println("\nOriginal array: " + Arrays.toString(stringArray));
        heapSort(stringArray);
        System.out.println("Sorted array: " + Arrays.toString(stringArray));
    }

}