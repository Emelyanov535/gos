import java.util.ArrayList;
import java.util.NoSuchElementException;

public class HeapPriorityQueue<T> {
    private static class Node<T> {
        T value;
        int priority;

        Node(T value, int priority) {
            this.value = value;
            this.priority = priority;
        }
    }

    private final ArrayList<Node<T>> heap = new ArrayList<>();

    public void add(T value, int priority) {
        Node<T> node = new Node<>(value, priority);
        heap.add(node);
        siftUp(heap.size() - 1);
    }

    public T poll() {
        if (heap.isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        T result = heap.get(0).value;
        Node<T> last = heap.remove(heap.size() - 1);
        if (!heap.isEmpty()) {
            heap.set(0, last);
            siftDown(0);
        }
        return result;
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    private void siftUp(int idx) {
        while (idx > 0) {
            int parent = (idx - 1) / 2;
            if (heap.get(parent).priority >= heap.get(idx).priority) break;
            swap(parent, idx);
            idx = parent;
        }
    }

    private void siftDown(int idx) {
        int left, right, largest;
        while ((left = 2 * idx + 1) < heap.size()) {
            right = left + 1;
            largest = idx;

            if (heap.get(left).priority > heap.get(largest).priority) {
                largest = left;
            }

            if (right < heap.size() && heap.get(right).priority > heap.get(largest).priority) {
                largest = right;
            }

            if (largest == idx) break;

            swap(largest, idx);
            idx = largest;
        }
    }

    private void swap(int i, int j) {
        Node<T> tmp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, tmp);
    }

    public static void main(String[] args) {
        HeapPriorityQueue<String> pq = new HeapPriorityQueue<>();
        pq.add("Low", 1);
        pq.add("Medium", 5);
        pq.add("High", 10);

        while (!pq.isEmpty()) {
            System.out.println(pq.poll());
        }
    }

}
