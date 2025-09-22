package org.example;

import java.util.Arrays;

public class ClosestPair {

    static class Point {
        int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static double closestPair(Point[] points) {
        Point[] sortedByX = Arrays.copyOf(points, points.length);
        Arrays.sort(sortedByX, (a, b) -> a.x - b.x);

        Point[] sortedByY = Arrays.copyOf(points, points.length);
        Arrays.sort(sortedByY, (a, b) -> a.y - b.y);

        return closestPair(sortedByX, sortedByY, 0, points.length - 1);
    }

    private static double closestPair(Point[] sortedByX, Point[] sortedByY, int left, int right) {
        if (right - left <= 3) {
            return bruteForce(sortedByX, left, right);
        }

        int mid = (left + right) / 2;
        Point midPoint = sortedByX[mid];

        Point[] leftByY = new Point[mid - left + 1];
        Point[] rightByY = new Point[right - mid];
        int li = 0, ri = 0;

        for (int i = left; i <= right; i++) {
            if (sortedByY[i].x <= midPoint.x) {
                leftByY[li++] = sortedByY[i];
            } else {
                rightByY[ri++] = sortedByY[i];
            }
        }

        double d1 = closestPair(sortedByX, leftByY, left, mid);
        double d2 = closestPair(sortedByX, rightByY, mid + 1, right);

        double d = Math.min(d1, d2);

        return Math.min(d, stripCheck(sortedByY, left, right, midPoint.x, d));
    }

    private static double stripCheck(Point[] sortedByY, int left, int right, int midX, double d) {
        double minDist = d;
        Point[] strip = new Point[right - left + 1];
        int j = 0;

        for (int i = left; i <= right; i++) {
            if (Math.abs(sortedByY[i].x - midX) < d) {
                strip[j++] = sortedByY[i];
            }
        }

        for (int i = 0; i < j; i++) {
            for (int k = i + 1; k < j && (sortedByY[k].y - sortedByY[i].y) < minDist; k++) {
                double dist = distance(strip[i], strip[k]);
                minDist = Math.min(minDist, dist);
            }
        }

        return minDist;
    }

    private static double distance(Point p1, Point p2) {
        return Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
    }

    private static double bruteForce(Point[] points, int left, int right) {
        double minDist = Double.MAX_VALUE;
        for (int i = left; i <= right; i++) {
            for (int j = i + 1; j <= right; j++) {
                minDist = Math.min(minDist, distance(points[i], points[j]));
            }
        }
        return minDist;
    }

    public static void main(String[] args) {
        Point[] points = {
                new Point(2, 3),
                new Point(12, 30),
                new Point(40, 50),
                new Point(5, 1),
                new Point(12, 10),
                new Point(3, 4)
        };

        double result = closestPair(points);
        System.out.println("The closest pair distance is: " + result);
    }
}

