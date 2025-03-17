package Project2;

/*
 *
 *  SortedList.java
 *
 */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SortedList<E extends Comparable<? super E>> extends List<E> {

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
        SortedList<Integer> list = new SortedList<>();
        list.insert(5);
        list.insert(3);
        list.insert(8);
        list.insert(1);

        System.out.println("List contents:");
        for (Integer value : list) {
            System.out.println(value);
        }

        System.out.println("Retrieve index 2: " + list.retrieve(2));
        System.out.println("Search for 8: " + list.search(8));
        System.out.println("Search for 10: " + list.search(10));

        list.remove(3);
        System.out.println("List after removing 3:");
        for (Integer value : list) {
            System.out.println(value);
        }
    }
}
