public class Deque<T> {
    private T[] data;
    private int front;
    private int rear;
    private int size;
    private int capacity;

    @SuppressWarnings("unchecked")
    public Deque(int capacity) {
        this.capacity = capacity;
        data = (T[]) new Object[capacity];
        front = -1;
        rear = 0;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == capacity;
    }

    public void addFront(T item) {
        if (isFull()) throw new IllegalStateException("Deque is full");

        if (front == -1) {
            front = 0;
            rear = 0;
        } else {
            front = (front - 1 + capacity) % capacity;
        }

        data[front] = item;
        size++;
    }

    public void addRear(T item) {
        if (isFull()) throw new IllegalStateException("Deque is full");

        if (front == -1) {
            front = 0;
            rear = 0;
        } else {
            rear = (rear + 1) % capacity;
        }

        data[rear] = item;
        size++;
    }

    public T removeFront() {
        if (isEmpty()) throw new IllegalStateException("Deque is empty");

        T item = data[front];
        if (front == rear) {
            front = -1;
            rear = 0;
        } else {
            front = (front + 1) % capacity;
        }

        size--;
        return item;
    }

    public T removeRear() {
        if (isEmpty()) throw new IllegalStateException("Deque is empty");

        T item = data[rear];
        if (front == rear) {
            front = -1;
            rear = 0;
        } else {
            rear = (rear - 1 + capacity) % capacity;
        }

        size--;
        return item;
    }

    public T peekFront() {
        if (isEmpty()) throw new IllegalStateException("Deque is empty");
        return data[front];
    }

    public T peekRear() {
        if (isEmpty()) throw new IllegalStateException("Deque is empty");
        return data[rear];
    }

    public int size() {
        return size;
    }

    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>(5);

        deque.addRear(10);
        deque.addRear(20);
        deque.addFront(5);
        deque.addFront(1);

        System.out.println("Front: " + deque.peekFront()); // 1
        System.out.println("Rear: " + deque.peekRear());   // 20

        System.out.println("Removed Front: " + deque.removeFront()); // 1
        System.out.println("Removed Rear: " + deque.removeRear());   // 20

        System.out.println("Front now: " + deque.peekFront()); // 5
        System.out.println("Rear now: " + deque.peekRear());   // 10
    }

}
