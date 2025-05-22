public class DoublyLinkedList<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    // Класс узла двусвязного списка
    private static class Node<T> {
        T data;
        Node<T> prev;
        Node<T> next;

        public Node(T data) {
            this.data = data;
            this.prev = null;
            this.next = null;
        }
    }

    // Конструктор
    public DoublyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    // Добавление элемента в конец списка
    public void add(T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    // Добавление элемента по индексу
    public void add(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        if (index == size) {
            add(data);
            return;
        }

        Node<T> newNode = new Node<>(data);
        if (index == 0) {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        } else {
            Node<T> current = getNode(index);
            newNode.prev = current.prev;
            newNode.next = current;
            current.prev.next = newNode;
            current.prev = newNode;
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

        Node<T> toRemove;
        if (index == 0) {
            toRemove = head;
            head = head.next;
            if (head != null) {
                head.prev = null;
            } else {
                tail = null;
            }
        } else if (index == size - 1) {
            toRemove = tail;
            tail = tail.prev;
            tail.next = null;
        } else {
            toRemove = getNode(index);
            toRemove.prev.next = toRemove.next;
            toRemove.next.prev = toRemove.prev;
        }
        size--;
        return toRemove.data;
    }

    // Удаление первого вхождения элемента
    public boolean remove(T data) {
        Node<T> current = head;
        while (current != null) {
            if (current.data.equals(data)) {
                if (current == head) {
                    remove(0);
                } else if (current == tail) {
                    remove(size - 1);
                } else {
                    current.prev.next = current.next;
                    current.next.prev = current.prev;
                    size--;
                }
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
        tail = null;
        size = 0;
    }

    // Получение узла по индексу
    private Node<T> getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        Node<T> current;
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
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
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();

        list.add(10);
        list.add(20);
        list.add(30);
        System.out.println("Список: " + list); // [10, 20, 30]

        list.add(0, 15);
        System.out.println("После вставки: " + list); // [15, 10, 20, 30]

        System.out.println("Элемент с индексом 2: " + list.get(2)); // 20

        list.remove(2);
        System.out.println("После удаления: " + list); // [15, 10, 30]

        list.remove(Integer.valueOf(15));
        System.out.println("После удаления значения 15: " + list); // [10, 30]
    }

}