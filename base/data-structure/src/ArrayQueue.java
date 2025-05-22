public class ArrayQueue<T> {
    private static final int DEFAULT_CAPACITY = 10;
    private Object[] elements;
    private int front;
    private int rear;
    private int size;

    public ArrayQueue() {
        elements = new Object[DEFAULT_CAPACITY];
        front = 0;
        rear = -1;
        size = 0;
    }

    public ArrayQueue(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("Initial capacity must be positive");
        }
        elements = new Object[initialCapacity];
        front = 0;
        rear = -1;
        size = 0;
    }

    // Добавление элемента в очередь (enqueue)
    public void enqueue(T item) {
        if (isFull()) {
            resize();
        }
        rear = (rear + 1) % elements.length;
        elements[rear] = item;
        size++;
    }

    // Удаление элемента из очереди (dequeue)
    @SuppressWarnings("unchecked")
    public T dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        T item = (T) elements[front];
        elements[front] = null; // Для избежания утечки памяти
        front = (front + 1) % elements.length;
        size--;
        return item;
    }

    // Просмотр первого элемента без удаления (peek)
    @SuppressWarnings("unchecked")
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        return (T) elements[front];
    }

    // Проверка на пустоту
    public boolean isEmpty() {
        return size == 0;
    }

    // Проверка на заполненность
    public boolean isFull() {
        return size == elements.length;
    }

    // Размер очереди
    public int size() {
        return size;
    }

    // Увеличение емкости
    private void resize() {
        int newCapacity = elements.length * 2;
        Object[] newArray = new Object[newCapacity];
        
        for (int i = 0; i < size; i++) {
            newArray[i] = elements[(front + i) % elements.length];
        }
        
        elements = newArray;
        front = 0;
        rear = size - 1;
    }

    // Пример использования
    public static void main(String[] args) {
        ArrayQueue<String> queue = new ArrayQueue<>();
        
        queue.enqueue("Первый");
        queue.enqueue("Второй");
        queue.enqueue("Третий");
        
        System.out.println("Первый элемент: " + queue.peek()); // "Первый"
        System.out.println("Размер очереди: " + queue.size()); // 3
        
        System.out.println("Извлечение: " + queue.dequeue()); // "Первый"
        System.out.println("Извлечение: " + queue.dequeue()); // "Второй"
        
        queue.enqueue("Четвертый");
        System.out.println("Первый элемент: " + queue.peek()); // "Третий"
        System.out.println("Размер очереди: " + queue.size()); // 2
    }
}