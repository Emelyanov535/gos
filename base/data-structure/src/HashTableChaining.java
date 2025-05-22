import java.util.*;

public class HashTableChaining<K, V> {

    private MyLinkedList<K, V>[] table;
    private int capacity;

    public HashTableChaining(int capacity) {
        this.capacity = capacity;
        table = new MyLinkedList[capacity];
        for (int i = 0; i < capacity; i++) {
            table[i] = new MyLinkedList<>();
        }
    }

    private int hash(K key) {
        return Math.abs(key.hashCode() % capacity);
    }

    public void put(K key, V value) {
        int index = hash(key);
        table[index].put(key, value);
    }

    public V get(K key) {
        int index = hash(key);
        return table[index].get(key);
    }

    public boolean remove(K key) {
        int index = hash(key);
        return table[index].remove(key);
    }

    public void printTable() {
        for (int i = 0; i < capacity; i++) {
            System.out.print("Bucket " + i + ": ");
            table[i].print();
        }
    }

    class MyLinkedList<K, V> {
        static class Node<K, V> {
            K key;
            V value;
            Node<K, V> next;

            Node(K key, V value) {
                this.key = key;
                this.value = value;
            }
        }

        private Node<K, V> head;

        // Добавить или обновить
        public void put(K key, V value) {
            Node<K, V> node = head;
            while (node != null) {
                if (node.key.equals(key)) {
                    node.value = value;
                    return;
                }
                node = node.next;
            }
            Node<K, V> newNode = new Node<>(key, value);
            newNode.next = head;
            head = newNode;
        }

        // Получить значение
        public V get(K key) {
            Node<K, V> node = head;
            while (node != null) {
                if (node.key.equals(key)) {
                    return node.value;
                }
                node = node.next;
            }
            return null;
        }

        // Удалить по ключу
        public boolean remove(K key) {
            Node<K, V> curr = head, prev = null;
            while (curr != null) {
                if (curr.key.equals(key)) {
                    if (prev == null) {
                        head = curr.next;
                    } else {
                        prev.next = curr.next;
                    }
                    return true;
                }
                prev = curr;
                curr = curr.next;
            }
            return false;
        }

        // Вывод всех элементов
        public void print() {
            Node<K, V> node = head;
            while (node != null) {
                System.out.print(node.key + "=" + node.value + " -> ");
                node = node.next;
            }
            System.out.println("null");
        }
    }

    public static void main(String[] args) {
        HashTableChaining<String, Integer> map = new HashTableChaining<>(5);

        map.put("apple", 1);
        map.put("banana", 2);
        map.put("cherry", 3);
        map.put("banana", 10); // обновление

        map.printTable();

        System.out.println("Get 'banana': " + map.get("banana"));
        map.remove("banana");
        System.out.println("Get 'banana' after remove: " + map.get("banana"));

        map.printTable();
    }

}
