package org.example.tree;

import org.junit.jupiter.api.Test;

import static org.example.tree.RedBlackTree.*;
import static org.junit.jupiter.api.Assertions.*;

class RedBlackTreeTest {

    @Test
    void rootTest() {
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        tree.add(15);

        assertTrue(tree.root.isBlack());
        assertEquals(15, tree.root.getValue());
    }

    @Test
    void twoNodesTest() {
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        tree.add(15);
        tree.add(5);

        assertTrue(tree.root.isBlack());
        assertEquals(15, tree.root.getValue());
        assertTrue(tree.root.getLeft().isRed());
        assertEquals(5, tree.root.getLeft().getValue());
    }

    @Test
    void twoNodesTest2() {
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        tree.add(15);
        tree.add(25);

        assertTrue(tree.root.isBlack());
        assertEquals(15, tree.root.getValue());
        assertTrue(tree.root.getRight().isRed());
        assertEquals(25, tree.root.getRight().getValue());
    }

    @Test
    void lineTest() {
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        tree.add(15);
        tree.add(5);
        tree.add(1);

        assertTrue(tree.root.isBlack());
        assertEquals(5, tree.root.getValue());
        assertTrue(tree.root.getLeft().isRed());
        assertEquals(1, tree.root.getLeft().getValue());
        assertTrue(tree.root.getRight().isRed());
        assertEquals(15, tree.root.getRight().getValue());
    }

    @Test
    void complexTest() {
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        tree.add(8);
        tree.add(15);
        tree.add(5);
        tree.add(12);
        tree.add(19);
        tree.add(9);
        tree.add(13);
        tree.add(10);
        tree.add(23);
        tree.add(24);

        assertEquals(Node.of(12, false,
                Node.of(8, true,
                        Node.of(5, false),
                        Node.of(9, false, null, Node.of(10, true))
                ),
                Node.of(15, true,
                        Node.of(13, false),
                        Node.of(23, false, Node.of(19, true), Node.of(24, true))
                )
        ), tree.root);
        assertTrue(tree.root.isBlack());
    }

    @Test
    void complexTest2() {
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        tree.add(50);
        tree.add(3);
        tree.add(5);
        tree.add(78);
        tree.add(35);
        tree.add(10);
        tree.add(55);
        tree.add(17);
        tree.add(23);
        tree.add(76);

        assertEquals(Node.of(17, false,
                Node.of(5, true,
                        Node.of(3, false),
                        Node.of(10, false)
                ),
                Node.of(50, true,
                        Node.of(35, false, Node.of(23, true), null),
                        Node.of(76, false, Node.of(55, true), Node.of(78, true))
                )
        ), tree.root);
        assertTrue(tree.root.isBlack());
    }

    @Test
    void containsTest() {
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        tree.add(8);
        tree.add(15);
        tree.add(5);
        tree.add(12);
        tree.add(19);
        tree.add(9);
        tree.add(13);
        tree.add(10);
        tree.add(23);
        tree.add(24);

        assertTrue(tree.contains(8));
        assertTrue(tree.contains(15));
        assertTrue(tree.contains(5));
        assertTrue(tree.contains(12));
        assertTrue(tree.contains(19));
        assertTrue(tree.contains(9));
        assertTrue(tree.contains(13));
        assertTrue(tree.contains(10));
        assertTrue(tree.contains(23));
        assertTrue(tree.contains(24));

        assertFalse(tree.contains(3));
        assertFalse(tree.contains(16));
        assertFalse(tree.contains(6));
        assertFalse(tree.contains(14));
        assertFalse(tree.contains(20));
        assertFalse(tree.contains(7));
        assertFalse(tree.contains(16));
        assertFalse(tree.contains(11));
        assertFalse(tree.contains(25));
        assertFalse(tree.contains(26));
    }
}