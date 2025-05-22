public class SinglyLinkedList<T> {
    private Node<T> head;
    private int size;

    // Класс узла односвязного списка
    private static class Node<T> {
        T data;
        Node<T> next;

        public Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    // Конструктор
    public SinglyLinkedList() {
        head = null;
        size = 0;
    }

    // Добавление элемента в конец списка
    public void add(T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
        } else {
            Node<T> current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    // Добавление элемента по индексу
    public void add(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        Node<T> newNode = new Node<>(data);
        if (index == 0) {
            newNode.next = head;
            head = newNode;
        } else {
            Node<T> previous = getNode(index - 1);
            newNode.next = previous.next;
            previous.next = newNode;
        }
        size++;
    }

    // Получение элемента по индексу
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return getNode(index).data;
    }

    // Удаление элемента по индексу
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        T removedData;
        if (index == 0) {
            removedData = head.data;
            head = head.next;
        } else {
            Node<T> previous = getNode(index - 1);
            removedData = previous.next.data;
            previous.next = previous.next.next;
        }
        size--;
        return removedData;
    }

    // Удаление первого вхождения элемента
    public boolean remove(T data) {
        if (head == null) {
            return false;
        }

        if (head.data.equals(data)) {
            head = head.next;
            size--;
            return true;
        }

        Node<T> current = head;
        while (current.next != null) {
            if (current.next.data.equals(data)) {
                current.next = current.next.next;
                size--;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    // Получение размера списка
    public int size() {
        return size;
    }

    // Проверка на пустоту
    public boolean isEmpty() {
        return size == 0;
    }

    // Очистка списка
    public void clear() {
        head = null;
        size = 0;
    }

    // Получение узла по индексу
    private Node<T> getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    // Вывод списка в строку
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<T> current = head;
        while (current != null) {
            sb.append(current.data);
            if (current.next != null) {
                sb.append(", ");
            }
            current = current.next;
        }
        sb.append("]");
        return sb.toString();
    }

    // Пример использования
    public static void main(String[] args) {
        SinglyLinkedList<String> list = new SinglyLinkedList<>();

        list.add("Apple");
        list.add("Banana");
        list.add("Cherry");
        System.out.println("Список: " + list); // [Apple, Banana, Cherry]

        list.add(1, "Blueberry");
        System.out.println("После вставки: " + list); // [Apple, Blueberry, Banana, Cherry]

        System.out.println("Элемент с индексом 2: " + list.get(2)); // Banana

        list.remove(2);
        System.out.println("После удаления по индексу: " + list); // [Apple, Blueberry, Cherry]

        list.remove("Blueberry");
        System.out.println("После удаления по значению: " + list); // [Apple, Cherry]

        System.out.println("Размер списка: " + list.size()); // 2
    }
}