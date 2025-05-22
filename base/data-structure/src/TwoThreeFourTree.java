import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TwoThreeFourTree<T extends Comparable<T>> {

    class Node {
        List<T> keys = new ArrayList<>();
        List<Node> children = new ArrayList<>();
        boolean isLeaf() {
            return children.isEmpty();
        }
    }

    private Node root = new Node();

    // ===== Поиск =====
    public boolean contains(T key) {
        return contains(root, key);
    }

    private boolean contains(Node node, T key) {
        for (T k : node.keys) {
            if (k.equals(key)) return true;
        }
        if (node.isLeaf()) return false;

        int idx = findChildIndex(node, key);
        return contains(node.children.get(idx), key);
    }

    // ===== Вставка =====
    public void insert(T key) {
        if (root.keys.size() == 3) {
            Node newRoot = new Node();
            newRoot.children.add(root);
            splitChild(newRoot, 0);
            root = newRoot;
        }
        insertNonFull(root, key);
    }

    private void insertNonFull(Node node, T key) {
        int i = Collections.binarySearch(node.keys, key);
        if (i >= 0) return; // ключ уже существует

        if (node.isLeaf()) {
            node.keys.add(-i - 1, key);
        } else {
            int childIndex = findChildIndex(node, key);
            Node child = node.children.get(childIndex);
            if (child.keys.size() == 3) {
                splitChild(node, childIndex);
                if (key.compareTo(node.keys.get(childIndex)) > 0) {
                    childIndex++;
                }
            }
            insertNonFull(node.children.get(childIndex), key);
        }
    }

    private void splitChild(Node parent, int index) {
        Node fullChild = parent.children.get(index);
        Node left = new Node();
        Node right = new Node();

        // разделение ключей
        left.keys.add(fullChild.keys.get(0));
        right.keys.add(fullChild.keys.get(2));
        T middleKey = fullChild.keys.get(1);
        parent.keys.add(index, middleKey);

        // делим потомков (если не лист)
        if (!fullChild.isLeaf()) {
            left.children.add(fullChild.children.get(0));
            left.children.add(fullChild.children.get(1));
            right.children.add(fullChild.children.get(2));
            right.children.add(fullChild.children.get(3));
        }

        parent.children.set(index, left);
        parent.children.add(index + 1, right);
    }

    private int findChildIndex(Node node, T key) {
        int idx = 0;
        while (idx < node.keys.size() && key.compareTo(node.keys.get(idx)) > 0) {
            idx++;
        }
        return idx;
    }

    // ===== Удаление =====
    public void delete(T key) {
        delete(root, key);

        // если корень опустел и у него есть дети
        if (root.keys.isEmpty() && !root.isLeaf()) {
            root = root.children.get(0);
        }
    }

    private void delete(Node node, T key) {
        int idx = node.keys.indexOf(key);

        if (idx != -1) {
            if (node.isLeaf()) {
                node.keys.remove(idx); // просто удаляем
            } else {
                Node predChild = node.children.get(idx);
                Node succChild = node.children.get(idx + 1);

                if (predChild.keys.size() >= 2) {
                    T pred = getPredecessor(predChild);
                    node.keys.set(idx, pred);
                    delete(predChild, pred);
                } else if (succChild.keys.size() >= 2) {
                    T succ = getSuccessor(succChild);
                    node.keys.set(idx, succ);
                    delete(succChild, succ);
                } else {
                    // объединяем и удаляем рекурсивно
                    mergeChildren(node, idx);
                    delete(predChild, key);
                }
            }
        } else {
            if (node.isLeaf()) return;

            int childIdx = findChildIndex(node, key);
            Node child = node.children.get(childIdx);

            if (child.keys.size() == 1) {
                fixChildSize(node, childIdx);
                child = node.children.get(childIdx);
            }

            delete(child, key);
        }
    }

    private void fixChildSize(Node parent, int idx) {
        Node child = parent.children.get(idx);

        if (idx > 0 && parent.children.get(idx - 1).keys.size() >= 2) {
            // заимствуем у левого
            Node left = parent.children.get(idx - 1);
            child.keys.add(0, parent.keys.get(idx - 1));
            parent.keys.set(idx - 1, left.keys.remove(left.keys.size() - 1));

            if (!left.isLeaf()) {
                child.children.add(0, left.children.remove(left.children.size() - 1));
            }

        } else if (idx < parent.children.size() - 1 && parent.children.get(idx + 1).keys.size() >= 2) {
            // заимствуем у правого
            Node right = parent.children.get(idx + 1);
            child.keys.add(parent.keys.get(idx));
            parent.keys.set(idx, right.keys.remove(0));

            if (!right.isLeaf()) {
                child.children.add(right.children.remove(0));
            }

        } else {
            // объединяем
            if (idx < parent.children.size() - 1) {
                mergeChildren(parent, idx);
            } else {
                mergeChildren(parent, idx - 1);
            }
        }
    }

    private void mergeChildren(Node parent, int idx) {
        Node left = parent.children.get(idx);
        Node right = parent.children.get(idx + 1);
        T middleKey = parent.keys.remove(idx);

        left.keys.add(middleKey);
        left.keys.addAll(right.keys);
        if (!left.isLeaf()) {
            left.children.addAll(right.children);
        }

        parent.children.remove(idx + 1);
    }

    private T getPredecessor(Node node) {
        while (!node.isLeaf())
            node = node.children.get(node.children.size() - 1);
        return node.keys.get(node.keys.size() - 1);
    }

    private T getSuccessor(Node node) {
        while (!node.isLeaf())
            node = node.children.get(0);
        return node.keys.get(0);
    }

    // ===== Отладка: печать =====
    public void printTree() {
        printTree(root, 0);
        System.out.println();
    }

    private void printTree(Node node, int level) {
        System.out.print("  ".repeat(level) + node.keys + "\n");
        for (Node child : node.children) {
            printTree(child, level + 1);
        }
    }

    public static void main(String[] args) {
        TwoThreeFourTree<Integer> tree = new TwoThreeFourTree<>();

        for (int n : new int[]{50, 40, 60, 30, 70, 20, 80, 10, 90}) {
            tree.insert(n);
        }

        tree.printTree();

        System.out.println("Contains 30? " + tree.contains(30)); // true
        System.out.println("Contains 99? " + tree.contains(99)); // false

        tree.delete(30);
        tree.delete(20);
        tree.delete(10);
        tree.printTree();
    }

}
