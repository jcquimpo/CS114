package Project2;

/*
 *
 *  SortedList.java
 *
 */

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class SortedList2<E extends Comparable<? super E>> extends List<E> {

    // Recursive insert method
    @Override
    public void insert(E data) {
        head = insertNode(head, data);
    }

    private Node<E> insertNode(Node<E> node, E data) {
        if (node == null || data.compareTo(node.data) < 0) {
            Node<E> newNode = new Node<>(data);
            newNode.next = node;
            return newNode;
        }
        node.next = insertNode(node.next, data);
        return node;
    }

    // Recursive remove method
    @Override
    public void remove(E data) {
        head = removeNode(head, data);
    }

    private Node<E> removeNode(Node<E> node, E data) {
        if (node == null)
            return null;
        if (node.data.equals(data))
            return node.next;
        node.next = removeNode(node.next, data);
        return node;
    }

    // Recursive retrieve method
    @Override
    public E retrieve(int index) {
        return retrieveNode(head, index);
    }

    private E retrieveNode(Node<E> node, int index) {
        if (node == null)
            throw new IndexOutOfBoundsException();
        if (index == 0)
            return node.data;
        return retrieveNode(node.next, index - 1);
    }

    // Recursive search method
    @Override
    public boolean search(E data) {
        return searchNode(head, data);
    }

    private boolean searchNode(Node<E> node, E data) {
        if (node == null)
            return false;
        if (node.data.equals(data))
            return true;
        return searchNode(node.next, data);
    }

    // Iterator inner class
    @Override
    public Iterator<E> iterator() {
        return new SortedListIterator();
    }

    private class SortedListIterator implements Iterator<E> {
        private Node<E> current = head;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public E next() {
            if (!hasNext())
                throw new NoSuchElementException();
            E data = current.data;
            current = current.next;
            return data;
        }
    }

    // Main method for testing
    public static void main(String[] args) {
        SortedList2<Integer> list = new SortedList2<>();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter integers to insert into the sorted list (press 'Enter' on an empty line to finish):");
        while (true) {
            String input = scanner.nextLine().trim(); // Read the entire line and trim whitespace
            if (input.isEmpty()) { // Stop if the input is empty
                break;
            }
            try {
                int value = Integer.parseInt(input); // Parse the input as an integer
                list.insert(value);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter an integer or press 'Enter' to finish.");
            }
        }

        System.out.println("\nList contents:");
        for (Integer value : list) {
            System.out.println(value);
        }

        System.out.println("\nEnter an index to retrieve:");
        int index = scanner.nextInt();
        try {
            System.out.println("Value at index " + index + ": " + list.retrieve(index));
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Invalid index.");
        }

        System.out.println("\nEnter a value to search for:");
        int searchValue = scanner.nextInt();
        if (list.search(searchValue)) {
            System.out.println("Value " + searchValue + " found in the list.");
        } else {
            System.out.println("Value " + searchValue + " not found in the list.");
        }

        System.out.println("\nEnter a value to remove:");
        int removeValue = scanner.nextInt();
        list.remove(removeValue);
        System.out.println("List after removing " + removeValue + ":");
        for (Integer value : list) {
            System.out.println(value);
        }

        scanner.close();
    }
}
