public class BinarySearchTree<T extends Comparable<T>> {
    private Node<T> root;

    // Вставка
    public void insert(T data) {
        root = insertRec(root, data);
    }

    private Node<T> insertRec(Node<T> current, T data) {
        if (current == null) return new Node<>(data);
        if (data.compareTo(current.data) < 0)
            current.left = insertRec(current.left, data);
        else
            current.right = insertRec(current.right, data);
        return current;
    }

    // Поиск
    public boolean find(T key) {
        return findRec(root, key) != null;
    }

    private Node<T> findRec(Node<T> current, T key) {
        if (current == null) return null;
        int cmp = key.compareTo(current.data);
        if (cmp == 0) return current;
        return cmp < 0 ? findRec(current.left, key) : findRec(current.right, key);
    }

    // Удаление
    public void delete(T key) {
        root = deleteRec(root, key);
    }

    private Node<T> deleteRec(Node<T> current, T key) {
        if (current == null) return null;

        int cmp = key.compareTo(current.data);
        if (cmp < 0) {
            current.left = deleteRec(current.left, key);
        } else if (cmp > 0) {
            current.right = deleteRec(current.right, key);
        } else {
            // Один или ноль потомков
            if (current.left == null) return current.right;
            if (current.right == null) return current.left;

            // Два потомка: найти наименьший в правом поддереве
            Node<T> minRight = findMin(current.right);
            current.data = minRight.data;
            current.right = deleteRec(current.right, minRight.data);
        }
        return current;
    }

    private Node<T> findMin(Node<T> node) {
        while (node.left != null)
            node = node.left;
        return node;
    }

    // Обходы
    public void traverseInOrder() {
        traverseInOrder(root);
        System.out.println();
    }

    private void traverseInOrder(Node<T> node) {
        if (node != null) {
            traverseInOrder(node.left);
            System.out.print(node + " ");
            traverseInOrder(node.right);
        }
    }

    public void traversePreOrder() {
        traversePreOrder(root);
        System.out.println();
    }

    private void traversePreOrder(Node<T> node) {
        if (node != null) {
            System.out.print(node + " ");
            traversePreOrder(node.left);
            traversePreOrder(node.right);
        }
    }

    public void traversePostOrder() {
        traversePostOrder(root);
        System.out.println();
    }

    private void traversePostOrder(Node<T> node) {
        if (node != null) {
            traversePostOrder(node.left);
            traversePostOrder(node.right);
            System.out.print(node + " ");
        }
    }

    class Node<T extends Comparable<T>> {
        T data;
        Node<T> left;
        Node<T> right;

        public Node(T data) {
            this.data = data;
        }

        public String toString() {
            return data.toString();
        }
    }

    public static void main(String[] args) {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();

        tree.insert(50);
        tree.insert(30);
        tree.insert(70);
        tree.insert(20);
        tree.insert(40);
        tree.insert(60);
        tree.insert(80);

        System.out.print("In-order: ");
        tree.traverseInOrder();  // 20 30 40 50 60 70 80

        System.out.print("Pre-order: ");
        tree.traversePreOrder(); // 50 30 20 40 70 60 80

        System.out.print("Post-order: ");
        tree.traversePostOrder(); // 20 40 30 60 80 70 50

        System.out.println("Find 40: " + tree.find(40)); // true
        System.out.println("Find 90: " + tree.find(90)); // false

        tree.delete(30);  // удаление узла с двумя потомками
        System.out.print("After deleting 30: ");
        tree.traverseInOrder();  // 20 40 50 60 70 80
    }

}
