package org.example.tree;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

public class RedBlackTree<T extends Comparable<T>> {

    Node<T> root = null;

    public void add(T item) {
        Node<T> node = new Node<>(item);

        // the new node is the root
        if (root == null) {
            root = node;
            root.makeBlack();
            return;
        }

        // add a new node to the tree
        Node<T> current = root;
        while (true) {
            int comparisonResult = node.compareTo(current);
            if (comparisonResult == 0) {
                current.setValue(node.getValue());
                return;
            }
            if (comparisonResult < 0) {
                if (current.getLeft() == null) {
                    current.setLeft(node);
                    break;
                }
                current = current.getLeft();
            } else {
                if (current.getRight() == null) {
                    current.setRight(node);
                    break;
                }
                current = current.getRight();
            }
        }

        fixViolationsAfterInsert(node);
    }

    private void fixViolationsAfterInsert(Node<T> node) {
        while (node.getParent() != null && node.getParent().isRed()) {
            // if we don't have a grandparent, there's no need to check anything because we won't find the uncle
            if (node.getParent().getParent() == null) {
                break;
            }

            var uncle = node.getUncle();

            if (uncle != null && uncle.isRed()) {
                // recolor
                node.getParent().makeBlack();
                node.getParent().getParent().makeRed();
                uncle.makeBlack();
                node = node.getParent().getParent();
                continue;
            }

            // here, the uncle is null or black

            // triangle cases
            if (node.isLeftChild() && node.getParent().isRightChild()) {
                node.getParent().rotateRight();
                node = node.getRight();
                continue;
            }
            if (node.isRightChild() && node.getParent().isLeftChild()) {
                node.getParent().rotateLeft();
                node = node.getLeft();
                continue;
            }

            // line cases
            var grandparent = node.getParent().getParent();
            var parent = node.getParent();
            if (node.isRightChild() && parent.isRightChild()) {
                grandparent.rotateLeft();
                parent.makeBlack();
                grandparent.makeRed();
            } else if (node.isLeftChild() && parent.isLeftChild()) {
                grandparent.rotateRight();
                parent.makeBlack();
                grandparent.makeRed();
            }
            if (grandparent == root) {
                root = node.getParent();
            }
        }

        root.makeBlack();
    }

    public boolean contains(T item) {
        return find(item) != null;
    }

    private Node<T> find(T item) {
        Node<T> searchingNode = new Node<>(item);

        Node<T> currentNode = root;
        while (currentNode != null) {
            int comparisonResult = searchingNode.compareTo(currentNode);
            if (comparisonResult == 0) {
                return currentNode;
            }
            if (comparisonResult < 0) {
                currentNode = currentNode.getLeft();
            } else {
                currentNode = currentNode.getRight();
            }
        }
        return null;
    }

    @Getter
    @Setter
    static class Node<T extends Comparable<T>> implements Comparable<Node<T>> {

        private T value;
        private boolean red = true;
        private Node<T> left;
        private Node<T> right;
        private Node<T> parent;

        public Node(T item) {
            this.value = item;
        }

        public void makeRed() {
            red = true;
        }

        public void makeBlack() {
            red = false;
        }

        public boolean isBlack() {
            return !red;
        }

        public void setLeft(Node<T> node) {
            this.left = node;
            if (node != null) {
                node.parent = this;
            }
        }

        public void setRight(Node<T> node) {
            this.right = node;
            if (node != null) {
                node.parent = this;
            }
        }

        @Override
        public int compareTo(Node<T> o) {
            return value.compareTo(o.value);
        }

        public Node<T> getUncle() {
            var grandparent = parent.parent;
            if (grandparent == null) return null;
            if (grandparent.left == parent) {
                return grandparent.right;
            } else {
                return grandparent.left;
            }
        }

        public boolean isLeftChild() {
           return parent.left == this;
        }

        public boolean isRightChild() {
            return parent.right == this;
        }

        public void rotateLeft() {
            var rightChild = this.right;

            if (parent != null) {
                if (isRightChild()) {
                    parent.right = rightChild;
                } else {
                    parent.left = rightChild;
                }
            }
            rightChild.parent = parent;

            var leftSubtree = rightChild.left;

            parent = rightChild;
            rightChild.left = this;

            this.right = leftSubtree;
            if (leftSubtree != null) {
                leftSubtree.parent = this;
            }
        }

        public void rotateRight() {
            var leftChild = this.left;

            if (parent != null) {
                if (isRightChild()) {
                    parent.right = leftChild;
                } else {
                    parent.left = leftChild;
                }
            }
            leftChild.parent = parent;

            var rightSubtree = leftChild.right;

            parent = leftChild;
            leftChild.right = this;

            this.left = rightSubtree;
            if (rightSubtree != null) {
                rightSubtree.parent = this;
            }
        }

        public static <T extends Comparable<T>> Node<T> of(T value, boolean isRed) {
            var node = new Node<>(value);
            node.setRed(isRed);
            return node;
        }

        public static <T extends Comparable<T>> Node<T> of(T value, boolean isRed, Node<T> left, Node<T> right) {
            var node = new Node<>(value);
            node.setRed(isRed);
            node.setLeft(left);
            node.setRight(right);
            return node;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node<?> node = (Node<?>) o;
            return red == node.red && Objects.equals(value, node.value) && Objects.equals(left, node.left) && Objects.equals(right, node.right);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value, red, left, right);
        }
    }
}
