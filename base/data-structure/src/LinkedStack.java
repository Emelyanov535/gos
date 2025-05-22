public class LinkedStack<T> {
    private static class Node<T> {
        T data;
        Node<T> next;
        
        Node(T data) {
            this.data = data;
        }
    }
    
    private Node<T> top;
    private int size;
    
    public LinkedStack() {
        top = null;
        size = 0;
    }
    
    public void push(T item) {
        Node<T> newNode = new Node<>(item);
        newNode.next = top;
        top = newNode;
        size++;
    }
    
    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        T item = top.data;
        top = top.next;
        size--;
        return item;
    }
    
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return top.data;
    }
    
    public boolean isEmpty() {
        return top == null;
    }
    
    public int size() {
        return size;
    }

    // Пример использования
    public static void main(String[] args) {
        LinkedStack<Integer> stack = new LinkedStack<>();

        stack.push(10);
        stack.push(20);
        stack.push(30);

        System.out.println("Верхний элемент: " + stack.peek()); // 30
        System.out.println("Размер стека: " + stack.size());    // 3

        System.out.println("Извлечение: " + stack.pop());      // 30
        System.out.println("Извлечение: " + stack.pop());      // 20

        stack.push(40);
        System.out.println("Верхний элемент: " + stack.peek()); // 40
        System.out.println("Размер стека: " + stack.size());    // 2
    }

}