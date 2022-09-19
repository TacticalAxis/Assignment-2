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

        if(width <= 6 && height <= 6) {
            double[][] t = generateTestValues();
            for(int y = 0; y < height; y++) {
                for(int x = 0; x < width; x++) {
                    valueTable[y][x] = t[y][x];
                }
            }
        } else {
            generateLandValue();

            // set the 0,0 value to the base value
            valueTable[0][0] = baseValue;

            // set the max value to the max value
            valueTable[height - 1][width - 1] = maxValue;
        }
    }

    private double[][] generateTestValues() {
        double[][] values = new double[6][6];
        values[0] = new double[]{20, 40, 100, 130, 150, 200};
        values[1] = new double[]{40, 140, 250, 320, 400, 450};
        values[2] = new double[]{100, 250, 350, 420, 450, 500};
        values[3] = new double[]{130, 320, 420, 500, 600, 700};
        values[4] = new double[]{150, 400, 450, 600, 700, 800};
        values[5] = new double[]{200, 450, 500, 700, 800, 900};
        return values;
    }

    private void generateLandValue() {
        for (int i = 0; i < valueTable.length; i++) {
            for (int j = 0; j < valueTable[i].length; j++) {
                double r = maxValue - (maxValue - baseValue) * (1 - Math.sqrt(i * i + (double) j * j));
                double sq = Math.sqrt(valueTable.length * valueTable.length + (double) valueTable[i].length * valueTable[i].length);
                valueTable[i][j] = roundToNearestTen(((r / sq) + baseValue));
            }
        }
    }

    private static double roundToNearestTen(double value) {
        return Math.round(value / 10.0d) * 10.0d;
    }

    public double getValue(int width, int height) {
        return valueTable[height - 1][width - 1];
    }


    public void landValueTable(){
        //2d array of land values
        int[][] landValue = {{2, 4, 6, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072, 262144, 524288},
                            {2},
                            {4},
                            {6},
                            {8},
                            {32},
                            {64},
                            {128},
                            {256},                
                            {512},                
                            {1024},               
                            {2048},               
                            {4096},               
                            {8192},               
                            {16384},              
                            {32768},              
                            {65536},              
                            {131072},
                            {262144},
                            {524288}};
    }


}