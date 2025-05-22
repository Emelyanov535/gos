import java.util.Arrays;
import java.util.NoSuchElementException;

public class MinHeap {
    private int[] heap;
    private int size;
    private int capacity;

    public MinHeap(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.heap = new int[capacity];
    }

    private int parent(int i) { return (i - 1) / 2; }
    private int leftChild(int i) { return 2 * i + 1; }
    private int rightChild(int i) { return 2 * i + 2; }

    private void swap(int i, int j) {
        int tmp = heap[i];
        heap[i] = heap[j];
        heap[j] = tmp;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int peek() {
        if (size == 0)
            throw new NoSuchElementException("Heap is empty");
        return heap[0];
    }

    public void insert(int val) {
        if (size == capacity) {
            grow();
        }
        heap[size] = val;
        int current = size;
        size++;

        // "Всплываем" вверх по дереву, чтобы поддержать min-heap property
        while (current > 0 && heap[current] < heap[parent(current)]) {
            swap(current, parent(current));
            current = parent(current);
        }
    }

    public int extractMin() {
        if (size == 0)
            throw new NoSuchElementException("Heap is empty");

        int min = heap[0];
        heap[0] = heap[size - 1];
        size--;
        heapifyDown(0);
        return min;
    }

    private void heapifyDown(int i) {
        int left = leftChild(i);
        int right = rightChild(i);
        int smallest = i;

        if (left < size && heap[left] < heap[smallest])
            smallest = left;
        if (right < size && heap[right] < heap[smallest])
            smallest = right;

        if (smallest != i) {
            swap(i, smallest);
            heapifyDown(smallest);
        }
    }

    // Удаление по индексу
    public void remove(int i) {
        if (i < 0 || i >= size)
            throw new IndexOutOfBoundsException("Invalid index");

        // Уменьшаем значение в позиции i до минимального, чтобы всплыть наверх
        decreaseKey(i, Integer.MIN_VALUE);
        extractMin();  // теперь минимальный элемент (который мы хотим удалить) - в корне, удаляем его
    }

    // Уменьшение значения в позиции i (используется для удаления)
    public void decreaseKey(int i, int newVal) {
        if (i < 0 || i >= size)
            throw new IndexOutOfBoundsException("Invalid index");

        if (newVal > heap[i])
            throw new IllegalArgumentException("New value is greater than current value");

        heap[i] = newVal;
        // "Всплываем" вверх
        while (i > 0 && heap[i] < heap[parent(i)]) {
            swap(i, parent(i));
            i = parent(i);
        }
    }

    private void grow() {
        capacity = capacity * 2;
        heap = Arrays.copyOf(heap, capacity);
    }

    // Для отладки - вывести кучу в массивном виде
    @Override
    public String toString() {
        return Arrays.toString(Arrays.copyOf(heap, size));
    }

    // --- Тест ---
    public static void main(String[] args) {
        MinHeap heap = new MinHeap(5);

        heap.insert(10);
        heap.insert(4);
        heap.insert(15);
        heap.insert(20);
        heap.insert(0);

        System.out.println("Heap: " + heap);

        System.out.println("Min: " + heap.extractMin()); // 0
        System.out.println("Heap after extractMin: " + heap);

        heap.remove(1); // удалить элемент по индексу (после извлечения минимума)

        System.out.println("Heap after remove(1): " + heap);

        heap.insert(3);
        System.out.println("Heap after insert 3: " + heap);

        heap.decreaseKey(1, 1);
        System.out.println("Heap after decreaseKey(1, 1): " + heap);
    }
}
