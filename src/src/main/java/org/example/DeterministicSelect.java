package org.example;

import java.util.Arrays;

public class DeterministicSelect {
    public static int select(int[] arr, int k) {
        return select(arr, 0, arr.length - 1, k);
    }

    private static int select(int[] arr, int left, int right, int k) {
        if (left == right) {
            return arr[left];
        }

        int pivotIndex = medianOfMedians(arr, left, right);
        pivotIndex = partition(arr, left, right, pivotIndex);

        int numElements = pivotIndex - left + 1;

        if (k == numElements) {
            return arr[pivotIndex];
        } else if (k < numElements) {
            return select(arr, left, pivotIndex - 1, k);
        } else {
            return select(arr, pivotIndex + 1, right, k - numElements);
        }
    }

    private static int partition(int[] arr, int left, int right, int pivotIndex) {
        int pivotValue = arr[pivotIndex];
        swap(arr, pivotIndex, right);
        int storeIndex = left;

        for (int i = left; i < right; i++) {
            if (arr[i] < pivotValue) {
                swap(arr, storeIndex, i);
                storeIndex++;
            }
        }

        swap(arr, storeIndex, right);
        return storeIndex;
    }

    private static int medianOfMedians(int[] arr, int left, int right) {
        if (right - left + 1 <= 5) {
            return partition5(arr, left, right);
        }

        for (int i = left; i <= right; i += 5) {
            int subRight = Math.min(i + 4, right);
            int median5 = partition5(arr, i, subRight);
            swap(arr, median5, left + (i - left) / 5);
        }

        int mid = (right - left) / 10 + left + 1;
        return select(arr, left, left + (right - left) / 5, mid);
    }

    private static int partition5(int[] arr, int left, int right) {
        Arrays.sort(arr, left, right + 1);
        return (left + right) / 2;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        int[] arr = {12, 3, 5, 7, 19, 26, 17, 25};
        int k = 4;

        System.out.println("The " + k + "-th smallest element is: " + select(arr, k));
    }
}
