package comp611.assignment2.subdivisions.land;

import java.util.Random;

public class LandValue {

    // init table, base and max values
    private final double[][] valueTable;
    private final double baseValue;
    private final double maxValue;

    // this class is used to generate a random land value for each subdivision
    public LandValue(int width, int height) {
        if (width <= 6 || height <= 6) {
            this.valueTable = genAndrewTable();
        } else if (width <= 20 || height <= 20) {
            this.valueTable = genTable();
        } else {
            this.valueTable = genLargeTable(height, width);
        }

        this.baseValue = 20;
        this.maxValue = 2000;
    }

    // basic rounding function
    private static double roundToNearestTen(double value) {
        return Math.round(value / 10.0d) * 10.0d;
    }

    // if subdivision is <= 6x6, use Andrew table
    private double[][] genAndrewTable() {
        double[][] values = new double[6][6];
        values[0] = new double[]{20, 40, 100, 130, 150, 200};
        values[1] = new double[]{40, 140, 250, 320, 400, 450};
        values[2] = new double[]{100, 250, 350, 420, 450, 500};
        values[3] = new double[]{130, 320, 420, 500, 600, 700};
        values[4] = new double[]{150, 400, 450, 600, 700, 800};
        values[5] = new double[]{200, 450, 500, 700, 800, 900};
        return values;
    }

    // if subdivision is > 6x6, use the static value table
    public double[][] genTable() {
        return new double[][]{{18.00, 19.80, 21.78, 23.96, 26.35, 28.99, 31.89, 35.08, 38.58, 42.44, 46.69, 51.36, 56.49, 62.14, 68.35, 75.19, 82.71, 90.98, 100.08, 110.09},
                {19.80, 132.00, 162.00, 216.00, 279.00, 418.00, 495.00, 468.00, 531.00, 715.00, 648.00, 711.00, 946.00, 837.00, 900.00, 963.00, 1254.00, 1331.00, 1152.00, 1485.00},
                {21.78, 162.00, 198.00, 243.00, 363.00, 440.00, 506.00, 583.00, 660.00, 737.00, 803.00, 880.00, 783.00, 1034.00, 1111.00, 972.00, 1035.00, 1342.00, 1161.00, 1496.00},
                {23.96, 264.00, 297.00, 352.00, 407.00, 473.00, 441.00, 605.00, 558.00, 748.00, 825.00, 738.00, 801.00, 1045.00, 1122.00, 1199.00, 1276.00, 1107.00, 1430.00, 1233.00},
                {26.35, 341.00, 363.00, 333.00, 378.00, 517.00, 477.00, 649.00, 715.00, 639.00, 693.00, 924.00, 1001.00, 1067.00, 1144.00, 999.00, 1062.00, 1364.00, 1179.00, 1518.00},
                {28.99, 418.00, 360.00, 387.00, 423.00, 572.00, 627.00, 558.00, 748.00, 666.00, 720.00, 783.00, 1023.00, 1100.00, 1166.00, 1243.00, 1309.00, 1386.00, 1463.00, 1540.00},
                {31.89, 495.00, 414.00, 441.00, 583.00, 513.00, 549.00, 737.00, 792.00, 858.00, 756.00, 990.00, 864.00, 1122.00, 981.00, 1265.00, 1342.00, 1152.00, 1215.00, 1562.00},
                {35.08, 468.00, 583.00, 495.00, 531.00, 558.00, 603.00, 639.00, 836.00, 902.00, 792.00, 1023.00, 891.00, 945.00, 1232.00, 1298.00, 1116.00, 1179.00, 1233.00, 1296.00},
                {38.58, 649.00, 540.00, 558.00, 715.00, 612.00, 792.00, 836.00, 891.00, 774.00, 828.00, 873.00, 1133.00, 981.00, 1035.00, 1331.00, 1397.00, 1474.00, 1260.00, 1606.00},
                {42.44, 715.00, 737.00, 612.00, 781.00, 666.00, 858.00, 738.00, 774.00, 1001.00, 864.00, 1122.00, 963.00, 1017.00, 1309.00, 1125.00, 1179.00, 1233.00, 1573.00, 1639.00},
                {46.69, 648.00, 803.00, 675.00, 847.00, 880.00, 756.00, 968.00, 828.00, 864.00, 909.00, 1166.00, 1221.00, 1287.00, 1107.00, 1152.00, 1206.00, 1260.00, 1314.00, 1368.00},
                {51.36, 711.00, 720.00, 738.00, 756.00, 783.00, 810.00, 837.00, 1067.00, 1122.00, 1166.00, 999.00, 1276.00, 1089.00, 1397.00, 1188.00, 1242.00, 1584.00, 1650.00, 1716.00},
                {56.49, 774.00, 957.00, 801.00, 819.00, 1023.00, 1056.00, 891.00, 927.00, 963.00, 1221.00, 1276.00, 1331.00, 1134.00, 1179.00, 1233.00, 1562.00, 1332.00, 1386.00, 1749.00},
                {62.14, 837.00, 846.00, 855.00, 873.00, 900.00, 1122.00, 1155.00, 981.00, 1243.00, 1287.00, 1089.00, 1134.00, 1179.00, 1496.00, 1269.00, 1606.00, 1672.00, 1738.00, 1467.00},
                {68.35, 900.00, 909.00, 1122.00, 1144.00, 1166.00, 981.00, 1232.00, 1035.00, 1309.00, 1107.00, 1397.00, 1179.00, 1496.00, 1269.00, 1606.00, 1359.00, 1404.00, 1458.00, 1503.00},
                {75.19, 1177.00, 1188.00, 1199.00, 999.00, 1017.00, 1265.00, 1298.00, 1089.00, 1375.00, 1152.00, 1452.00, 1507.00, 1269.00, 1314.00, 1359.00, 1716.00, 1449.00, 1826.00, 1892.00},
                {82.71, 1026.00, 1265.00, 1044.00, 1062.00, 1071.00, 1342.00, 1364.00, 1397.00, 1179.00, 1474.00, 1242.00, 1562.00, 1314.00, 1661.00, 1716.00, 1760.00, 1815.00, 1881.00, 1584.00},
                {90.98, 1089.00, 1342.00, 1107.00, 1116.00, 1134.00, 1152.00, 1441.00, 1474.00, 1233.00, 1260.00, 1296.00, 1628.00, 1672.00, 1716.00, 1771.00, 1815.00, 1530.00, 1575.00, 1629.00},
                {100.08, 1152.00, 1419.00, 1170.00, 1179.00, 1463.00, 1215.00, 1233.00, 1260.00, 1573.00, 1606.00, 1650.00, 1694.00, 1422.00, 1458.00, 1494.00, 1539.00, 1925.00, 1620.00, 1665.00},
                {110.09, 1485.00, 1496.00, 1233.00, 1518.00, 1540.00, 1278.00, 1584.00, 1314.00, 1639.00, 1672.00, 1716.00, 1431.00, 1467.00, 1503.00, 1548.00, 1936.00, 1991.00, 2035.00, 1710.00}};
    }

    // if subdivision is > than 20x20, generate random data table
    // for larger than 20x20, BEWARE: this will cause re-running any approaches to produce a different result, as random values are re-generated
    private double[][] genLargeTable(int height, int width) {
        double[][] newTable = new double[height][width];
        Random random = new Random();
        for (int i = 0; i < newTable.length; i++) {
            for (int j = 0; j < newTable[i].length; j++) {
                double r = maxValue - (maxValue - baseValue) * (1 - Math.sqrt(i * i + (double) j * j));
                double sq = Math.sqrt(newTable.length * newTable.length + (double) newTable[i].length * newTable[i].length);
                // generate random value either 0 or 1
                int rand = random.nextInt(2);
                // if rand is 0, then add 10% to the value, else subtract 10%
                newTable[i][j] = roundToNearestTen(((r / sq) + baseValue)) * (rand == 0 ? 1.1 : 0.9);
            }
        }

        // go through all the values of the table and as it goes from top left to bottom right, the further away from the top left, the more the value increases
        for (int i = 0; i < newTable.length; i++) {
            for (int j = 0; j < newTable[i].length; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                if (i == 0) {
                    newTable[i][j] = newTable[i][j - 1] * 1.1;
                } else if (j == 0) {
                    newTable[i][j] = newTable[i - 1][j] * 1.1;
                }
            }
        }

        return newTable;
    }

    // get the value of the table based on a given height and width
    public double getValue(int width, int height) {
        return valueTable[height - 1][width - 1];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (double[] doubles : valueTable) {
            for (double aDouble : doubles) {
                sb.append(String.format("%.2f,", aDouble)).append("\t");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}