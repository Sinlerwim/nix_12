package com.repository;

import com.model.Auto;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Optional;

public class BinaryTree {

    public int numberOfNodes = 0;

    private TreeNode head;

    private BigDecimal sumLeft;

    private BigDecimal sumRight;

    private final Comparator<Auto> comparator = Comparator.comparing(Auto::getPrice).reversed()
            .thenComparing(auto -> auto.getClass().getSimpleName())
            .thenComparingInt(Auto::getCount);

    private static class TreeNode {
        Auto item;
        TreeNode left;
        TreeNode right;
        TreeNode parent;

        TreeNode(TreeNode left, Auto element, TreeNode right, TreeNode parent) {
            this.item = element;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }

        @Override
        public String toString() {
            String string = "TreeNode" + this.hashCode() + "{item='" + item + '\'' + ", left=";
            if (left != null) {
                string += left.hashCode() + '\'';
            } else string += "0";

            string += ", right= ";

            if (right != null) {
                string += right.hashCode() + '\'';
            } else string += "0";

            string += ", parent= ";

            if (parent != null) {
                string += parent.hashCode() + '\'';
            } else string += "0";

            return string;
        }
    }

    private void add(TreeNode node, Auto element) {
        int resultOfComparing = comparator.compare(element, node.item);
        if (resultOfComparing >= 0) {
            if (node.right == null) {
                node.right = new TreeNode(null, element, null, node);
                numberOfNodes++;
            } else add(node.right, element);
        } else {
            if (node.left == null) {
                node.left = new TreeNode(null, element, null, node);
                numberOfNodes++;
            } else add(node.left, element);
        }
    }

    public void add(Auto element) {
        if (head == null) {
            head = new TreeNode(null, element, null, null);
            numberOfNodes++;
        } else {
            add(head, element);
        }
    }

    private void printToConsole(TreeNode node) {
        if (node.left != null) {
            printToConsole(node.left);
        }
        if (node.right != null) {
            printToConsole(node.right);
        }
        System.out.println(node);
    }

    public void printToConsole() {
        printToConsole(head);
    }

    public BigDecimal sumLeft() {
        if (head.left == null)
            return BigDecimal.ZERO;
        sumLeft = BigDecimal.ZERO;
        sumLeft(head.left);
        return sumLeft;
    }

    private void sumLeft(TreeNode node) {
        if (node.left != null)
            sumLeft(node.left);
        if (node.right != null)
            sumLeft(node.right);
        sumLeft = sumLeft.add(node.item.getPrice());
    }

    public BigDecimal sumRight() {
        if (head.right == null)
            return BigDecimal.ZERO;
        sumRight = BigDecimal.ZERO;
        sumRight(head.right);
        return sumRight;
    }

    private void sumRight(TreeNode node) {
        if (node.left != null)
            sumRight(node.left);
        if (node.right != null)
            sumRight(node.right);
        sumRight = sumRight.add(node.item.getPrice());
    }
}
