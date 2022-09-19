package comp611.assignment2.subdivisions.land;

@SuppressWarnings("ManualArrayCopy")
public class LandValue {

    private final double[][] valueTable;
    private final double baseValue;
    private final double maxValue;

    public LandValue(int width, int height, double baseValue, double maxValue) {
        this.valueTable = new double[height][width];
        this.baseValue = baseValue;
        this.maxValue = maxValue;

//        if(width <= 6 && height <= 6) {
            double[][] t = generateSuperTest();
            for(int y = 0; y < height; y++) {
                for(int x = 0; x < width; x++) {
                    valueTable[y][x] = t[y][x];
                }
            }
//        } else {
//            generateLandValue();
//
//            // set the 0,0 value to the base value
//            valueTable[0][0] = baseValue;
//
//            // set the max value to the max value
//            valueTable[height - 1][width - 1] = maxValue;
//        }
    }

//    private double[][] generateTestValues() {
//        double[][] values = new double[6][6];
//        values[0] = new double[]{20, 40, 100, 130, 150, 200};
//        values[1] = new double[]{40, 140, 250, 320, 400, 450};
//        values[2] = new double[]{100, 250, 350, 420, 450, 500};
//        values[3] = new double[]{130, 320, 420, 500, 600, 700};
//        values[4] = new double[]{150, 400, 450, 600, 700, 800};
//        values[5] = new double[]{200, 450, 500, 700, 800, 900};
//        return values;
//    }

    private double[][] generateSuperTest() {
        return new double[][]{{9, 14, 7, 26, 44, 13, 46, 20, 74, 93, 44, 58, 123, 122, 109, 44, 121, 153, 152, 68},
                {14, 35, 20, 62, 73, 113, 80, 135, 52, 74, 233, 192, 163, 165, 99, 271, 155, 60, 252, 161},
                {7, 20, 93, 17, 39, 149, 207, 239, 258, 109, 223, 104, 299, 316, 216, 280, 435, 578, 134, 576},
                {26, 62, 17, 56, 78, 98, 246, 64, 291, 69, 121, 263, 131, 392, 529, 264, 194, 386, 620, 256},
                {44, 73, 39, 78, 71, 288, 229, 139, 221, 202, 549, 300, 209, 477, 628, 200, 721, 314, 901, 330},
                {13, 113, 149, 98, 288, 262, 349, 512, 534, 372, 660, 618, 392, 312, 561, 333, 477, 1009, 741, 1149},
                {46, 80, 207, 246, 229, 349, 437, 527, 492, 538, 458, 508, 592, 298, 1141, 1113, 1156, 675, 949, 588},
                {20, 135, 239, 64, 139, 512, 527, 117, 121, 873, 938, 316, 652, 746, 301, 224, 1054, 1079, 1339, 225},
                {74, 52, 258, 291, 221, 534, 492, 121, 487, 295, 265, 949, 1137, 596, 1110, 392, 1203, 775, 588, 533},
                {93, 74, 109, 69, 202, 372, 538, 873, 295, 924, 413, 266, 503, 380, 204, 269, 296, 762, 1694, 1350},
                {44, 233, 223, 121, 549, 660, 458, 938, 265, 413, 942, 1093, 793, 1613, 1302, 828, 1052, 1001, 272, 2105},
                {58, 192, 104, 263, 300, 618, 508, 316, 949, 266, 1093, 588, 949, 1382, 1678, 921, 1709, 1104, 2411, 1245},
                {123, 163, 299, 131, 209, 392, 592, 652, 1137, 503, 793, 949, 254, 1800, 528, 960, 2109, 528, 2399, 2554},
                {122, 165, 316, 392, 477, 312, 298, 746, 596, 380, 1613, 1382, 1800, 610, 2285, 1401, 1562, 318, 967, 2699},
                {109, 99, 216, 529, 628, 561, 1141, 301, 1110, 204, 1302, 1678, 528, 2285, 1587, 2325, 1816, 2585, 726, 688},
                {44, 271, 280, 264, 200, 333, 1113, 224, 392, 269, 828, 921, 960, 1401, 2325, 1953, 636, 3090, 830, 2352},
                {121, 155, 435, 194, 721, 477, 1156, 1054, 1203, 296, 1052, 1709, 2109, 1562, 1816, 636, 742, 2416, 1942, 2701},
                {153, 60, 578, 386, 314, 1009, 675, 1079, 775, 762, 1001, 1104, 528, 318, 2585, 3090, 2416, 1638, 2593, 484},
                {152, 252, 134, 620, 901, 741, 949, 1339, 588, 1694, 272, 2411, 2399, 967, 726, 830, 1942, 2593, 1952, 477},
                {68, 161, 576, 256, 330, 1149, 588, 225, 533, 1350, 2105, 1245, 2554, 2699, 688, 2352, 2701, 484, 477, 3252}};
    }

//    private void generateLandValue() {
//        for (int i = 0; i < valueTable.length; i++) {
//            for (int j = 0; j < valueTable[i].length; j++) {
//                double r = maxValue - (maxValue - baseValue) * (1 - Math.sqrt(i * i + (double) j * j));
//                double sq = Math.sqrt(valueTable.length * valueTable.length + (double) valueTable[i].length * valueTable[i].length);
//                valueTable[i][j] = roundToNearestTen(((r / sq) + baseValue));
//            }
//        }
//    }

//    private static double roundToNearestTen(double value) {
//        return Math.round(value / 10.0d) * 10.0d;
//    }

    public double getValue(int width, int height) {
        return valueTable[height - 1][width - 1];
    }


//    public void landValueTable(){
//        //2d array of land values
//        int[][] landValue = {{2, 4, 6, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072, 262144, 524288},
//                            {2},
//                            {4},
//                            {6},
//                            {8},
//                            {32},
//                            {64},
//                            {128},
//                            {256},
//                            {512},
//                            {1024},
//                            {2048},
//                            {4096},
//                            {8192},
//                            {16384},
//                            {32768},
//                            {65536},
//                            {131072},
//                            {262144},
//                            {524288}};
//    }


}