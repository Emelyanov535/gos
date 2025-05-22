public class RedBlackTree<T extends Comparable<T>> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private class Node {
        T value;
        Node left, right, parent;
        boolean color = RED;

        Node(T value) {
            this.value = value;
        }
    }

    private Node root;

    // ===== Вставка =====
    public void insert(T value) {
        Node inserted = bstInsert(root, value);
        fixInsert(inserted);
    }

    private Node bstInsert(Node root, T value) {
        if (this.root == null) {
            this.root = new Node(value);
            this.root.color = BLACK;
            return this.root;
        }

        Node current = root;
        Node parent = null;

        while (current != null) {
            parent = current;
            int cmp = value.compareTo(current.value);
            if (cmp < 0) {
                current = current.left;
            } else if (cmp > 0) {
                current = current.right;
            } else {
                return current; // дубликат
            }
        }

        Node newNode = new Node(value);
        newNode.parent = parent;

        if (value.compareTo(parent.value) < 0)
            parent.left = newNode;
        else
            parent.right = newNode;

        return newNode;
    }

    private void fixInsert(Node node) {
        while (node != root && node.parent.color == RED) {
            Node parent = node.parent;
            Node grandparent = parent.parent;

            if (parent == grandparent.left) {
                Node uncle = grandparent.right;

                if (getColor(uncle) == RED) {
                    parent.color = BLACK;
                    uncle.color = BLACK;
                    grandparent.color = RED;
                    node = grandparent;
                } else {
                    if (node == parent.right) {
                        node = parent;
                        rotateLeft(node);
                    }
                    parent.color = BLACK;
                    grandparent.color = RED;
                    rotateRight(grandparent);
                }
            } else {
                Node uncle = grandparent.left;

                if (getColor(uncle) == RED) {
                    parent.color = BLACK;
                    uncle.color = BLACK;
                    grandparent.color = RED;
                    node = grandparent;
                } else {
                    if (node == parent.left) {
                        node = parent;
                        rotateRight(node);
                    }
                    parent.color = BLACK;
                    grandparent.color = RED;
                    rotateLeft(grandparent);
                }
            }
        }

        root.color = BLACK;
    }

    // ===== Удаление =====
    public void delete(T value) {
        Node node = search(root, value);
        if (node != null)
            deleteNode(node);
    }

    private void deleteNode(Node z) {
        Node y = z;
        Node x;
        boolean yOriginalColor = y.color;

        if (z.left == null) {
            x = z.right;
            transplant(z, z.right);
        } else if (z.right == null) {
            x = z.left;
            transplant(z, z.left);
        } else {
            y = minimum(z.right);
            yOriginalColor = y.color;
            x = y.right;
            if (y.parent == z) {
                if (x != null) x.parent = y;
            } else {
                transplant(y, y.right);
                y.right = z.right;
                if (y.right != null) y.right.parent = y;
            }
            transplant(z, y);
            y.left = z.left;
            if (y.left != null) y.left.parent = y;
            y.color = z.color;
        }

        if (yOriginalColor == BLACK)
            fixDelete(x, z.parent);
    }

    private void fixDelete(Node x, Node parent) {
        while (x != root && getColor(x) == BLACK) {
            if (x == parent.left) {
                Node w = parent.right;
                if (getColor(w) == RED) {
                    w.color = BLACK;
                    parent.color = RED;
                    rotateLeft(parent);
                    w = parent.right;
                }

                if (getColor(w.left) == BLACK && getColor(w.right) == BLACK) {
                    w.color = RED;
                    x = parent;
                    parent = x.parent;
                } else {
                    if (getColor(w.right) == BLACK) {
                        if (w.left != null) w.left.color = BLACK;
                        w.color = RED;
                        rotateRight(w);
                        w = parent.right;
                    }
                    w.color = parent.color;
                    parent.color = BLACK;
                    if (w.right != null) w.right.color = BLACK;
                    rotateLeft(parent);
                    x = root;
                }
            } else {
                Node w = parent.left;
                if (getColor(w) == RED) {
                    w.color = BLACK;
                    parent.color = RED;
                    rotateRight(parent);
                    w = parent.left;
                }

                if (getColor(w.left) == BLACK && getColor(w.right) == BLACK) {
                    w.color = RED;
                    x = parent;
                    parent = x.parent;
                } else {
                    if (getColor(w.left) == BLACK) {
                        if (w.right != null) w.right.color = BLACK;
                        w.color = RED;
                        rotateLeft(w);
                        w = parent.left;
                    }
                    w.color = parent.color;
                    parent.color = BLACK;
                    if (w.left != null) w.left.color = BLACK;
                    rotateRight(parent);
                    x = root;
                }
            }
        }
        if (x != null)
            x.color = BLACK;
    }

    // ===== Поиск =====
    public boolean contains(T value) {
        return search(root, value) != null;
    }

    private Node search(Node node, T value) {
        while (node != null) {
            int cmp = value.compareTo(node.value);
            if (cmp < 0)
                node = node.left;
            else if (cmp > 0)
                node = node.right;
            else
                return node;
        }
        return null;
    }

    // ===== Обход =====
    public void inOrder() {
        inOrder(root);
        System.out.println();
    }

    private void inOrder(Node node) {
        if (node != null) {
            inOrder(node.left);
            System.out.print(node.value + (node.color == RED ? "R " : "B "));
            inOrder(node.right);
        }
    }

    // ===== Хелперы =====
    private void rotateLeft(Node x) {
        Node y = x.right;
        x.right = y.left;

        if (y.left != null)
            y.left.parent = x;

        y.parent = x.parent;

        if (x.parent == null)
            root = y;
        else if (x == x.parent.left)
            x.parent.left = y;
        else
            x.parent.right = y;

        y.left = x;
        x.parent = y;
    }

    private void rotateRight(Node x) {
        Node y = x.left;
        x.left = y.right;

        if (y.right != null)
            y.right.parent = x;

        y.parent = x.parent;

        if (x.parent == null)
            root = y;
        else if (x == x.parent.right)
            x.parent.right = y;
        else
            x.parent.left = y;

        y.right = x;
        x.parent = y;
    }

    private void transplant(Node u, Node v) {
        if (u.parent == null)
            root = v;
        else if (u == u.parent.left)
            u.parent.left = v;
        else
            u.parent.right = v;

        if (v != null)
            v.parent = u.parent;
    }

    private Node minimum(Node node) {
        while (node.left != null)
            node = node.left;
        return node;
    }

    private boolean getColor(Node node) {
        return node != null && node.color == RED;
    }

    public static void main(String[] args) {
        RedBlackTree<Integer> tree = new RedBlackTree<>();

        tree.insert(20);
        tree.insert(10);
        tree.insert(30);
        tree.insert(25);
        tree.insert(5);

        tree.inOrder(); // пример: 5R 10B 20B 25R 30B

        tree.delete(10);
        tree.inOrder();

        System.out.println("Contains 25? " + tree.contains(25)); // true
        System.out.println("Contains 10? " + tree.contains(10)); // false
    }

}
