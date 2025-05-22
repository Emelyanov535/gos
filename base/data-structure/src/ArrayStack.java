public class ArrayStack<T> {
    private static final int DEFAULT_CAPACITY = 10;
    private Object[] elements;
    private int size;

    public ArrayStack() {
        elements = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    public ArrayStack(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("Initial capacity must be positive");
        }
        elements = new Object[initialCapacity];
        size = 0;
    }

    // Добавление элемента в стек (push)
    public void push(T item) {
        if (size == elements.length) {
            resize();
        }
        elements[size++] = item;
    }

    // Извлечение элемента из стека (pop)
    @SuppressWarnings("unchecked")
    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        T item = (T) elements[--size];
        elements[size] = null; // Для избежания утечки памяти
        return item;
    }

    // Просмотр верхнего элемента без извлечения (peek)
    @SuppressWarnings("unchecked")
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return (T) elements[size - 1];
    }

    // Проверка на пустоту
    public boolean isEmpty() {
        return size == 0;
    }

    // Размер стека
    public int size() {
        return size;
    }

    // Увеличение емкости при необходимости
    private void resize() {
        int newCapacity = elements.length * 2;
        Object[] newArray = new Object[newCapacity];
        System.arraycopy(elements, 0, newArray, 0, size);
        elements = newArray;
    }

    // Пример использования
    public static void main(String[] args) {
        ArrayStack<Integer> stack = new ArrayStack<>();
        
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