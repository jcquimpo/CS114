public class BinarySearchTree<E extends Comparable<? super E>> extends BinaryTree<E> {

    @Override
    public void insert(E data) {
        root = insertRec(root, data);
    }

    private Node<E> insertRec(Node<E> root, E data) {
        // When tree is empty, make a new node
        if (root == null) {
            return new Node<>(data);
        }

        int compareResult = data.compareTo(root.data);
        
        if (compareResult < 0) {
            // Insert in left subtree
            root.left = insertRec(root.left, data);
        } else if (compareResult > 0) {
            // Insert in right subtree
            root.right = insertRec(root.right, data);
        }

        return root;
    }

    @Override
    public boolean search(E data) {
        return searchRec(root, data);
    }

    private boolean searchRec(Node<E> root, E data) {
        // Base case: root is null or data is present at root
        if (root == null) {
            return false;
        }
        
        int compareResult = data.compareTo(root.data);
        
        if (compareResult == 0) {
            return true;
        } else if (compareResult < 0) {
            // Search in left subtree
            return searchRec(root.left, data);
        } else {
            // Search in right subtree
            return searchRec(root.right, data);
        }
    }

    @Override
    public void remove(E data) {
        root = removeRec(root, data);
    }

    private Node<E> removeRec(Node<E> root, E data) {
        // Base case
        if (root == null) {
            return null;
        }

        int compareResult = data.compareTo(root.data);
        
        if (compareResult < 0) {
            // Data is in the left subtree
            root.left = removeRec(root.left, data);
        } else if (compareResult > 0) {
            // Data is in the right subtree
            root.right = removeRec(root.right, data);
        } else {
            // Node with only one child or no child
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }

            // Node with two children: Get the inorder successor
            root.data = findMin(root.right).data;

            // Delete the inorder successor
            root.right = removeRec(root.right, root.data);
        }
        
        return root;
    }

    private Node<E> findMin(Node<E> node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    @Override
    public java.util.Iterator<E> iterator() {
        return new InOrderIterator();
    }

    private class InOrderIterator implements java.util.Iterator<E> {
        private java.util.Stack<Node<E>> stack;
        private Node<E> current;

        public InOrderIterator() {
            stack = new java.util.Stack<>();
            current = root;
            // Initialize inorder traversal
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            
            // Get next node in the inorder traversal
            Node<E> node = stack.pop();
            E data = node.data;
            
            // If right child exists, push all its left children
            if (node.right != null) {
                current = node.right;
                while (current != null) {
                    stack.push(current);
                    current = current.left;
                }
            }
            
            return data;
        }
    }
}
