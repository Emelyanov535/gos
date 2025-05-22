public class LinkedQueue<T> {
    private static class Node<T> {
        T data;
        Node<T> next;
        
        Node(T data) {
            this.data = data;
        }
    }
    
    private Node<T> front;
    private Node<T> rear;
    private int size;
    
    public LinkedQueue() {
        front = null;
        rear = null;
        size = 0;
    }
    
    // Добавление элемента (enqueue)
    public void enqueue(T item) {
        Node<T> newNode = new Node<>(item);
        if (isEmpty()) {
            front = rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
        size++;
    }
    
    // Удаление элемента (dequeue)
    public T dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        T item = front.data;
        front = front.next;
        if (front == null) {
            rear = null;
        }
        size--;
        return item;
    }
    
    // Просмотр первого элемента (peek)
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        return front.data;
    }
    
    // Проверка на пустоту
    public boolean isEmpty() {
        return front == null;
    }
    
    // Размер очереди
    public int size() {
        return size;
    }
    
    // Пример использования
    public static void main(String[] args) {
        LinkedQueue<Integer> queue = new LinkedQueue<>();
        
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        
        System.out.println("Первый элемент: " + queue.peek()); // 1
        System.out.println("Размер очереди: " + queue.size());  // 3
        
        System.out.println("Извлечение: " + queue.dequeue()); // 1
        System.out.println("Извлечение: " + queue.dequeue()); // 2
        
        queue.enqueue(4);
        System.out.println("Первый элемент: " + queue.peek()); // 3
        System.out.println("Размер очереди: " + queue.size());  // 2
    }
}