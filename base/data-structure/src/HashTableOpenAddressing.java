import java.util.Arrays;

public class HashTableOpenAddressing {
    private static final int EMPTY = Integer.MIN_VALUE;
    private static final int DELETED = Integer.MAX_VALUE;

    private int[] table;
    private int size;
    private int capacity;

    public enum Method { LINEAR, QUADRATIC, DOUBLE_HASHING }

    private final Method method;

    public HashTableOpenAddressing(int capacity, Method method) {
        this.capacity = capacity;
        this.table = new int[capacity];
        Arrays.fill(table, EMPTY);
        this.method = method;
    }

    private int hash(int key) {
        return Math.abs(key) % capacity;
    }

    private int hash2(int key) {
        return 1 + (Math.abs(key) % (capacity - 1));
    }

    private int probe(int key, int i) {
        switch (method) {
            case LINEAR:
                return (hash(key) + i) % capacity;
            case QUADRATIC:
                int c1 = 1, c2 = 3;
                return (hash(key) + c1 * i + c2 * i * i) % capacity;
            case DOUBLE_HASHING:
                return (hash(key) + i * hash2(key)) % capacity;
            default:
                throw new IllegalArgumentException("Unknown probing method");
        }
    }

    public boolean insert(int key) {
        for (int i = 0; i < capacity; i++) {
            int idx = probe(key, i);
            if (table[idx] == EMPTY || table[idx] == DELETED) {
                table[idx] = key;
                size++;
                return true;
            }
        }
        return false; // таблица полна
    }

    public boolean search(int key) {
        for (int i = 0; i < capacity; i++) {
            int idx = probe(key, i);
            if (table[idx] == EMPTY) return false;
            if (table[idx] == key) return true;
        }
        return false;
    }

    public boolean delete(int key) {
        for (int i = 0; i < capacity; i++) {
            int idx = probe(key, i);
            if (table[idx] == EMPTY) return false;
            if (table[idx] == key) {
                table[idx] = DELETED;
                size--;
                return true;
            }
        }
        return false;
    }

    public void printTable() {
        System.out.println(Arrays.toString(table));
    }

    public static void main(String[] args) {
        HashTableOpenAddressing table = new HashTableOpenAddressing(11, HashTableOpenAddressing.Method.DOUBLE_HASHING);

        table.insert(10);
        table.insert(21);
        table.insert(32); // все вызывают коллизии
        table.printTable();

        System.out.println("Search 21: " + table.search(21));
        table.delete(21);
        System.out.println("Search 21: " + table.search(21));
        table.printTable();
    }

}
