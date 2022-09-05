package comp611.assignment2.subdivisions;

public class Main {
    public static void main(String[] args) {
        System.out.println(bruteForce(6,3));
    }

    public static float bruteForce(int m, int n) {
        return (m - 1) + ((float) n - 1);
    }
}