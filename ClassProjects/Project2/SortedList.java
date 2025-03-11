package Project2;

public class List {
    
}
package Project2;

import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 *
 *  List.java
 *
 */

public abstract class List<E> implements Iterable<E> {

    protected class Node<T> {

        protected Node(T data) {
            this.data = data;
        }

        protected T data;
        protected Node<T> next;
    }

    public abstract void insert(E data);
    public abstract void remove(E data);
    public abstract E retrieve(int index);
    public abstract boolean search(E data);

    protected Node<E> head;
}

/*
 *
 *  SortedList.java
 *
 */

public class SortedList<E extends Comparable<? super E>> extends List<E> {

    @Override
    public void insert(E data) {
        head = insertRecursively(head, data);
    }

    private Node<E> insertRecursively(Node<E> node, E data) {
        if (node == null || data.compareTo(node.data) < 0) {
            Node<E> newNode = new Node<>(data);
            newNode.next = node;
            return newNode;
        }
        node.next = insertRecursively(node.next, data);
        return node;
    }

    @Override
    public void remove(E data) {
        head = removeRecursively(head, data);
    }

    private Node<E> removeRecursively(Node<E> node, E data) {
        if (node == null) {
            return null;
        }
        if (data.equals(node.data)) {
            return node.next;
        }
        node.next = removeRecursively(node.next, data);
        return node;
    }

    @Override
    public E retrieve(int index) {
        return retrieveRecursively(head, index);
    }

    private E retrieveRecursively(Node<E> node, int index) {
        if (node == null) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) {
            return node.data;
        }
        return retrieveRecursively(node.next, index - 1);
    }

    @Override
    public boolean search(E data) {
        return searchRecursively(head, data);
    }

    private boolean searchRecursively(Node<E> node, E data) {
        if (node == null) {
            return false;
        }
        if (data.equals(node.data)) {
            return true;
        }
        return searchRecursively(node.next, data);
    }

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
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            E data = current.data;
            current = current.next;
            return data;
        }
    }
}