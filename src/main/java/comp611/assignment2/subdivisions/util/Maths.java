package comp611.assignment2.subdivisions.util;

import java.lang.Math;
public class Maths {

    public static double[][] getValueTable(int height, int width, double minValue, double maxValue) {
        double[][] valueTable = new double[height][width];

        //for loop that fills the outer edge of numbers
        //from 0,0 to 0,height and 0,0 to width,0
        for (int i = 0; i < height; i++) {
            valueTable[i][0] = i;
        }
        for (int i = 0; i < width; i++) {
            valueTable[0][i] = i;
        }


        for (int i = 1; i < height; i++) {
            for (int j = 1; j < width; j++) {
                //the values when adding cannot exceed the max value or be less than the min value
                //the values are also rounded to the nearest integer

                valueTable[i][j] = Math.sqrt(Math.pow(valueTable[i][j - 1], 2) + Math.pow(valueTable[i - 1][j], 2));
                if (valueTable[i][j] > maxValue) {
                    valueTable[i][j] = maxValue;
                }
                if (valueTable[i][j] < minValue) {
                    valueTable[i][j] = minValue;
                }
            }
        }


        return valueTable;
    }

    public static void main(String[] args) {
        double[][] valueTable = getValueTable(20, 20, 0, 150);
        for (double[] ints : valueTable) {
            for (double anInt : ints) {
                System.out.print(anInt + " ");
            }
            System.out.println();
        }
    }
}