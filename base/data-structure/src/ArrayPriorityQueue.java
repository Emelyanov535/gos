import java.util.NoSuchElementException;

public class ArrayPriorityQueue<T> {
    private static class Node<T> {
        T value;
        int priority;

        Node(T value, int priority) {
            this.value = value;
            this.priority = priority;
        }
    }

    private Node<T>[] data;
    private int size;

    @SuppressWarnings("unchecked")
    public ArrayPriorityQueue(int capacity) {
        data = new Node[capacity];
        size = 0;
    }

    public void add(T value, int priority) {
        if (size == data.length) {
            throw new IllegalStateException("Queue is full");
        }
        data[size++] = new Node<>(value, priority);
    }

    public T poll() {
        if (size == 0) {
            throw new NoSuchElementException("Queue is empty");
        }

        int highest = 0;
        for (int i = 1; i < size; i++) {
            if (data[i].priority > data[highest].priority) {
                highest = i;
            }
        }

        T result = data[highest].value;
        data[highest] = data[size - 1]; // Replace with last
        data[size - 1] = null;
        size--;
        return result;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public static void main(String[] args) {
        ArrayPriorityQueue<String> pq = new ArrayPriorityQueue<>(10);
        pq.add("Low", 1);
        pq.add("Medium", 5);
        pq.add("High", 10);

        while (!pq.isEmpty()) {
            System.out.println(pq.poll());
        }
    }

}
