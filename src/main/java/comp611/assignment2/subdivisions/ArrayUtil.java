package comp611.assignment2.subdivisions;

import java.util.Arrays;
import java.util.HashMap;

public class ArrayUtil {

    public static void main(String[] args) {
        int[][] test1 = {{495053715, 495053715, 495053715, 1922154895, 1922154895, 1922154895},{793589513, 495053715, 495053715, 1922154895, 1922154895, 1922154895},{495053715, 495053715, 495053715, 1922154895, 1922154895, 1922154895}};
        int[][] test2 = {{885284298, 885284298, 885284298, 1389133897, 1389133897, 1389133897},{960604060, 885284298, 885284298, 1389133897, 1389133897, 1389133897},{885284298, 885284298, 885284298, 1389133897, 1389133897, 1389133897}};
        int[][] test3 = {{1338668845, 1338668845, 1338668845, 666641942, 666641942, 666641942},{1338668845, 1338668845, 1338668845, 666641942, 666641942, 666641942},{1338668845, 1338668845, 1338668845, 666641942, 666641942, 666641942}};

        System.out.println(areTheyTheSame(test1, test2));
        System.out.println(areTheyTheSame(test1, test3));
    }

    public static boolean areTheyTheSame(int[][] test1, int[][] test2) {
        if(test1.length != test2.length || test1[0].length != test2[0].length) {
            return false;
        }

        // convert each array to a one dimensional array
        int[] test1Array = convertTo1DArray(test1);
        int[] test2Array = convertTo1DArray(test2);

//        System.out.println(Arrays.toString(test1Array));
//        System.out.println(Arrays.toString(test2Array));

        HashMap<Integer, Integer> map = new HashMap<>();
        for(int i = 0; i < test1Array.length; i++) {
            if(!map.containsKey(test1Array[i])) {
                if(map.containsValue(test2Array[i])) {
                    return false;
                }
                map.put(test1Array[i], test2Array[i]);
            } else {
                if(map.get(test1Array[i]) != test2Array[i]) {
                    return false;
                }
            }
        }

        return true;
    }

    public static int[] convertTo1DArray(int[][] array) {
        return Arrays.stream(array).flatMapToInt(Arrays::stream).toArray();
    }
}
