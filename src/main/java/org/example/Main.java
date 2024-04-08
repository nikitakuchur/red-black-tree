package org.example;

import org.example.tree.RedBlackTree;

public class Main {
    public static void main(String[] args) {
        RedBlackTree<Integer> tree = new RedBlackTree<>();

        tree.add(5);
        tree.add(6);
        tree.add(2);
        tree.add(4);

        System.out.println("Hello world!");
    }
}