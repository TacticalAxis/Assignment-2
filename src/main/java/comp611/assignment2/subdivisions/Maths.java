package comp611.assignment2.subdivisions;

public class Maths {
    public static double[][] getValueTable(int height, int width, double minValue, double maxValue) {
        double[][] valueTable = new double[height][width];

        int random = (int) (Math.random() * 100);

        //for loop that fills the outer edge of numbers
        //from 0,0 to 0,height and 0,0 to width,0
        for (int i = 1; i < height; i++) {
            //values would be powers of 2
            valueTable[i][0] = Math.pow(2, i);
//            valueTable[i][0] = i;
        }
        for (int i = 1; i < width; i++) {
            //values would be powers of 2

            valueTable[0][i] = Math.pow(2, i);
            //round to 2 decimal places

//            valueTable[0][i] = i;
        }

        //for loop that fills the rest of the table
        //using the formula ( x + y )- 1
        for (int i = 1; i < height; i++) {
            for (int j = 1; j < width; j++) {
                //using the formula of adding the axis values together and adding 10% to it or subtracting 10%
                if(random % 2 == 0) {
                    //round to 2 decimal places
                    valueTable[i][j] = Math.round((valueTable[i - 1][j] + valueTable[i][j - 1]) * 1.1 * 100.0) / 100.0;
//                    valueTable[i][j] = (valueTable[i - 1][j] + valueTable[i][j - 1]) * 1.1;
                } else {
                    //round to 2dp
                    valueTable[i][j] = Math.round((valueTable[i - 1][j] + valueTable[i][j - 1]) * 0.9 * 100.0) / 100.0;
//                    valueTable[i][j] = (valueTable[i - 1][j] + valueTable[i][j - 1]) * 0.9;
                }
//                valueTable[i][j] = (valueTable[i][0] + valueTable[0][j]);
            }
        }


//        for (int i = 1; i < height; i++) {
//            for (int j = 1; j < width; j++) {
//                //the values when adding cannot exceed the max value or be less than the min value
//                //the values are also rounded to the nearest integer
//
//                valueTable[i][j] = Math.sqrt(Math.pow(valueTable[i][j - 1], 2) + Math.pow(valueTable[i - 1][j], 2));
//                if (valueTable[i][j] > maxValue) {
//                    valueTable[i][j] = maxValue;
//                }
//                if (valueTable[i][j] < minValue) {
//                    valueTable[i][j] = minValue;
//                }
//            }
//        }
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
