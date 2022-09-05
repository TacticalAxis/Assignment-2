package comp611.assignment2.subdivisions;

public class ExactApproach {
    int [][] area;
    int x,y;
    int subDivisions = 0;

    public ExactApproach(int x, int y) {
        this.x = x;
        this.y = y;
        area = new int[x][y];
    }

    public void calculateSubDivisions() {
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (area[i][j] == 0) {
                    subDivisions++;
                    fillArea(i, j);
                }
            }
        }
        System.out.println("Exact Approach: " + subDivisions);
    }

    private void fillArea(int i, int j) {
        if (i < 0 || i >= x || j < 0 || j >= y || area[i][j] == 1) {
            return;
        }
        area[i][j] = 1;
        fillArea(i + 1, j);
        fillArea(i - 1, j);
        fillArea(i, j + 1);
        fillArea(i, j - 1);
    }


}
