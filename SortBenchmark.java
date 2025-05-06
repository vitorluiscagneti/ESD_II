import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class SortBenchmark {

    static final int NUM_REPETITIONS = 30;

    public static void main(String[] args) throws IOException {
        int[] sizes = {1000, 10000};
        String[] distributions = {"Random", "Sorted", "Reversed"};
        String[] algorithms = {"Bubble", "Insertion", "Selection", "Quick", "Merge"};

        FileWriter writer = new FileWriter("sort_results.csv");
        writer.write("Algorithm,Size,Distribution,Run,TimeMillis\n");

        for (String algorithm : algorithms) {
            for (int size : sizes) {
                for (String dist : distributions) {
                    for (int i = 0; i < NUM_REPETITIONS; i++) {
                        int[] data = generateArray(size, dist);
                        long start = System.nanoTime();
                        runAlgorithm(algorithm, data);
                        long end = System.nanoTime();
                        long duration = (end - start) / 1_000_000; // ms
                        writer.write(algorithm + "," + size + "," + dist + "," + (i + 1) + "," + duration + "\n");
                        System.gc();
                    }
                }
            }
        }

        writer.close();
        System.out.println("Experimentos concluídos. Resultados salvos em sort_results.csv");
    }

    static void runAlgorithm(String name, int[] data) {
        switch (name) {
            case "Bubble":
                bubbleSort(data);
                break;
            case "Insertion":
                insertionSort(data);
                break;
            case "Selection":
                selectionSort(data);
                break;
            case "Quick":
                quickSort(data, 0, data.length - 1);
                break;
            case "Merge":
                mergeSort(data, 0, data.length - 1);
                break;
        }
    }

    public static int[] generateArray(int size, String distribution) {
        int[] array = new int[size];
        Random rand = new Random();
        switch (distribution) {
            case "Random":
                for (int i = 0; i < size; i++) array[i] = rand.nextInt(size);
                break;
            case "Sorted":
                for (int i = 0; i < size; i++) array[i] = i;
                break;
            case "Reversed":
                for (int i = 0; i < size; i++) array[i] = size - i;
                break;
        }
        return array;
    }

    // Bubble Sort
    public static void bubbleSort(int[] array) {
        int n = array.length;
        boolean swapped;
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - 1 - i; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
    }

    // Insertion Sort
    public static void insertionSort(int[] array) {
        for (int i = 1; i < array.length; i++) {
            int key = array[i], j = i - 1;
            while (j >= 0 && array[j] > key) array[j + 1] = array[j--];
            array[j + 1] = key;
        }
    }

    // Selection Sort
    public static void selectionSort(int[] array) {
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++)
                if (array[j] < array[minIdx]) minIdx = j;
            int temp = array[minIdx];
            array[minIdx] = array[i];
            array[i] = temp;
        }
    }

    // Quick Sort
    public static void quickSort(int[] array, int low, int high) {
        if (low < high) {
            int p = partition(array, low, high);
            quickSort(array, low, p - 1);
            quickSort(array, p + 1, high);
        }
    }

    private static int partition(int[] array, int low, int high) {
        int pivot = array[high], i = low - 1;
        for (int j = low; j < high; j++) {
            if (array[j] < pivot) {
                i++;
                int temp = array[i]; array[i] = array[j]; array[j] = temp;
            }
        }
        int temp = array[i + 1]; array[i + 1] = array[high]; array[high] = temp;
        return i + 1;
    }

    // Merge Sort
    public static void mergeSort(int[] array, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(array, left, mid);
            mergeSort(array, mid + 1, right);
            merge(array, left, mid, right);
        }
    }

    private static void merge(int[] array, int left, int mid, int right) {
        int n1 = mid - left + 1, n2 = right - mid;
        int[] L = Arrays.copyOfRange(array, left, mid + 1);
        int[] R = Arrays.copyOfRange(array, mid + 1, right + 1);

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2)
            array[k++] = (L[i] <= R[j]) ? L[i++] : R[j++];
        while (i < n1) array[k++] = L[i++];
        while (j < n2) array[k++] = R[j++];
    }
}
