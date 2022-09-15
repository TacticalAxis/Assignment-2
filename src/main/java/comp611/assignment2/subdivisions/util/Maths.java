package comp611.assignment2.subdivisions.util;

public class Maths {

    private final double[][] valueTable;
    private final double baseValue;
    private final double maxValue;

    public Maths(int width, int height, double baseValue, double maxValue) {
        valueTable = new double[height][width];
        this.baseValue = baseValue;
        this.maxValue = maxValue;

        generateLandValue();

        // set the 0,0 value to the base value
        valueTable[0][0] = baseValue;
    }

    public int getHeights() {
        return valueTable.length;
    }

    public int getWidths() {
        return valueTable[0].length;
    }

    public double[][] getValueTable() {
        return valueTable;
    }

    private void generateLandValue() {
        for (int i = 0; i < valueTable.length; i++) {
            for (int j = 0; j < valueTable[i].length; j++) {
                double r = maxValue - (maxValue - baseValue) * (1 - Math.sqrt(i * i + (double) j * j));
                double sq = Math.sqrt(valueTable.length * valueTable.length + (double) valueTable[i].length * valueTable[i].length);
                valueTable[i][j] = roundToNearest(((r / sq) + baseValue), 10);
            }
        }
    }

    private static double roundToNearest(double value, double nearest) {
        return Math.round(value / nearest) * nearest;
    }

    public double getValue(int width, int height) {
        return valueTable[width][height];
    }

    public static void main(String[] args) {
        Maths maths = new Maths(10, 10, 0, 1000);

        for (double[] ints : maths.getValueTable()) {
            for (double anInt : ints) {
                System.out.print(anInt + " ");
            }
            System.out.println();
        }
    }
}