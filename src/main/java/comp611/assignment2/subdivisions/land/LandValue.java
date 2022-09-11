package comp611.assignment2.subdivisions.land;

import java.util.Arrays;

public class LandValue {

    private final double[][] values;
    private final double baseValue;
    private final double maxValue;

    public LandValue(int width, int height, double baseValue, double maxValue) {
        int bigger = Math.max(width, height);
        this.values = new double[bigger][bigger];
        this.baseValue = baseValue;
        this.maxValue = maxValue;

        generateLandValue();
    }

    // algorithm to generate land value with 1x1 being the base value and the max value being the max value, all other values are between the two, and get smaller as the distance from the base value increases
    private void generateLandValue() {
        // generate table values
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[i].length; j++) {
                double distance = Math.sqrt(Math.pow(i, 2) + Math.pow(j, 2));
                double value = round(Math.abs(baseValue - (maxValue - baseValue) * (1 - distance / Math.sqrt(2 * Math.pow(values.length, 2)))));
                values[i][j] = value;
            }
        }

        // invert table values
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[i].length; j++) {
                values[i][j] = maxValue - values[i][j];
            }
        }

        // set max value to max value
        values[0][0] = baseValue;
        values[values.length - 1][values.length - 1] = maxValue;
    }

    private double round(double value) {
        return Math.round(value) * (double) 1;
    }

    public double getValue(int width, int height) {
        System.out.println("Getting value for " + width + " " + height + ": " + values[height - 1][width - 1]);
        return values[height - 1][width - 1];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (double[] doubles : values) {
            sb.append(Arrays.toString(doubles));
            sb.append("\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        LandValue landValue = new LandValue(10, 10, 20, 1000);
        System.out.println(landValue);
    }
}