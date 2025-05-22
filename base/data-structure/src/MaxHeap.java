import java.util.Arrays;
import java.util.NoSuchElementException;

public class MaxHeap {
    private int[] heap;
    private int size;
    private int capacity;

    public MaxHeap(int capacity) {
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

        // "Всплываем" вверх по дереву, чтобы поддержать max-heap property
        while (current > 0 && heap[current] > heap[parent(current)]) {
            swap(current, parent(current));
            current = parent(current);
        }
    }

    public int extractMax() {
        if (size == 0)
            throw new NoSuchElementException("Heap is empty");

        int max = heap[0];
        heap[0] = heap[size - 1];
        size--;
        heapifyDown(0);
        return max;
    }

    private void heapifyDown(int i) {
        int left = leftChild(i);
        int right = rightChild(i);
        int largest = i;

        if (left < size && heap[left] > heap[largest])
            largest = left;
        if (right < size && heap[right] > heap[largest])
            largest = right;

        if (largest != i) {
            swap(i, largest);
            heapifyDown(largest);
        }
    }

    // Удаление по индексу
    public void remove(int i) {
        if (i < 0 || i >= size)
            throw new IndexOutOfBoundsException("Invalid index");

        // Увеличиваем значение в позиции i до максимального, чтобы всплыть наверх
        increaseKey(i, Integer.MAX_VALUE);
        extractMax();  // теперь максимальный элемент (который мы хотим удалить) - в корне, удаляем его
    }

    // Увеличение значения в позиции i (используется для удаления)
    public void increaseKey(int i, int newVal) {
        if (i < 0 || i >= size)
            throw new IndexOutOfBoundsException("Invalid index");

        if (newVal < heap[i])
            throw new IllegalArgumentException("New value is smaller than current value");

        heap[i] = newVal;
        // "Всплываем" вверх
        while (i > 0 && heap[i] > heap[parent(i)]) {
            swap(i, parent(i));
            i = parent(i);
        }
    }

    private void grow() {
        capacity = capacity * 2;
        heap = Arrays.copyOf(heap, capacity);
    }

    @Override
    public String toString() {
        return Arrays.toString(Arrays.copyOf(heap, size));
    }

    // --- Тест ---
    public static void main(String[] args) {
        MaxHeap heap = new MaxHeap(5);

        heap.insert(10);
        heap.insert(4);
        heap.insert(15);
        heap.insert(20);
        heap.insert(0);

        System.out.println("Heap: " + heap);

        System.out.println("Max: " + heap.extractMax()); // 20
        System.out.println("Heap after extractMax: " + heap);

        heap.remove(1); // удалить элемент по индексу (после извлечения максимума)

        System.out.println("Heap after remove(1): " + heap);

        heap.insert(3);
        System.out.println("Heap after insert 3: " + heap);

        heap.increaseKey(1, 25);
        System.out.println("Heap after increaseKey(1, 25): " + heap);
    }
}
