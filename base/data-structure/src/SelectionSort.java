public class SelectionSort {
    public static void selectionSort(int[] arr) {
        int n = arr.length;

        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            // ищем индекс минимального элемента в оставшейся части массива
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }

            // меняем текущий элемент с найденным минимальным
            int temp = arr[minIndex];
            arr[minIndex] = arr[i];
            arr[i] = temp;
        }
    }

    public static void main(String[] args) {
        int[] array = {64, 25, 12, 22, 11};
        System.out.println("До сортировки:");
        for (int num : array) System.out.print(num + " ");

        selectionSort(array);

        System.out.println("\nПосле сортировки:");
        for (int num : array) System.out.print(num + " ");
    }
}
