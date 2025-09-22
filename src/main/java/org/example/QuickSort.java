package org.example;

import java.util.Arrays;
import java.util.Random;

public class QuickSort {
    private static final Random RANDOM = new Random();

    public static void quickSort(int[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }

    private static void quickSort(int[] arr, int low, int high) {
        while (low < high) {
            int pivotIndex = low + RANDOM.nextInt(high - low + 1);
            int pivot = arr[pivotIndex];

            swap(arr, pivotIndex, high);

            int p = partition(arr, low, high, pivot);

            if (p - low < high - p) {
                quickSort(arr, low, p - 1);
                low = p + 1;
            } else {
                quickSort(arr, p + 1, high);
                high = p - 1;
            }
        }
    }

    private static int partition(int[] arr, int low, int high, int pivot) {
        int i = low;
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                swap(arr, i, j);
                i++;
            }
        }
        swap(arr, i, high);
        return i;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        int[] arr = {9, 3, 7, 1, 5, 2, 8, 6, 4};
        quickSort(arr);

        System.out.println(Arrays.toString(arr));
    }
}

