package ga.pHub;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

class Coordinate implements Comparable<Coordinate> {
    int i;
    int new_i;
    double x, y;

    @Override
    public int compareTo(Coordinate other) {
        if (this.y < other.y) return -1;
        if (this.y > other.y) return 1;
        if (this.x < other.x) return -1;
        if (this.x > other.x) return 1;
        System.err.println("ERROR: Two nodes at the same location");
        System.exit(1);
        return 0;
    }
}

public class Generate {
    private static final int N_ROWS = 5;

    public static void main(String[] args) {
        args = new String[] { "10", "3", "resources/APdata200.txt" };
        if (args.length != 3) {
            System.err.println("USAGE: java HubLocation n p inputFilePath");
            System.exit(1);
        }

        int nn = Integer.parseInt(args[0]); // number of nodes
        int np = Integer.parseInt(args[1]); // number of hubs
        String inputFilePath = args[2];

        if (nn <= np || nn <= 0 || np <= 0) {
            System.err.println("ERROR: Incorrect problem parameters");
            System.exit(1);
        }

        if (nn % N_ROWS != 0) {
            System.err.println("ERROR: Number of nodes must be a multiple of " + N_ROWS);
            System.exit(1);
        }

        String[] lines;
        try {
            lines = loadFile(inputFilePath);
        } catch (IOException e) {
            System.err.println("ERROR: Unable to read input file");
            System.exit(1);
            return;
        }

        int NN = Integer.parseInt(lines[0]); // read in number of nodes & skip hub number
        if (NN != 200) {
            System.err.println("WARNING: Input is not the standard data file of AP problems");
        }

        Coordinate[] pts = new Coordinate[NN];
        int currentIndex = 1;

        for (int p = 0; p < NN; p++) {
            pts[p] = new Coordinate();
            String[] coordinates = lines[currentIndex++].split(" ");
            pts[p].x = Double.parseDouble(coordinates[0]);
            pts[p].y = Double.parseDouble(coordinates[1]);
            pts[p].i = p;
        }

        double[][] w = new double[NN][NN];
        double[] OD = new double[NN];

        for (int p = 0; p < NN; p++) {
            String[] flowValues = lines[currentIndex++].split(" ");
            for (int q = 0; q < NN; q++) {
                w[p][q] = Double.parseDouble(flowValues[q]);
                OD[p] += w[p][q];
                OD[q] += w[p][q];
            }
        }

        int p = Integer.parseInt(lines[currentIndex++]);
        double collect = Double.parseDouble(lines[currentIndex++]);
        double transfer = Double.parseDouble(lines[currentIndex++]);
        double distribute = Double.parseDouble(lines[currentIndex]);

        Coordinate[] sortedPts = Arrays.copyOf(pts, pts.length);
        Arrays.sort(sortedPts);

        int n_cols = nn / N_ROWS;
        int row_step = NN / N_ROWS;
        int row_start = 0;

        for (int row = 0; row < N_ROWS; row++) {
            int row_end = row_start + row_step;
            if (row < NN % N_ROWS) row_end++;

            Arrays.sort(sortedPts, row_start, row_end);

            int col_step = (row_end - row_start) / n_cols;
            int col_start = row_start;

            for (int col = 0; col < n_cols; col++) {
                int col_end = col_start + col_step;
                if (col < (row_end - row_start) % n_cols) col_end++;

                for (int r = col_start; r < col_end; r++) {
                    sortedPts[r].new_i = row * n_cols + col;
                }

                col_start = col_end;
            }

            row_start = row_end;
        }

        double[] new_x = new double[nn];
        double[] new_y = new double[nn];
        double[][] new_w = new double[nn][nn];
        double[] new_OD = new double[nn];

        for (int i = 0; i < nn; i++) {
            new_x[i] = new_y[i] = new_OD[i] = 0;

            for (int j = 0; j < nn; j++) {
                new_w[i][j] = 0;
            }
        }

        for (int i = 0; i < NN; i++) {
            new_x[sortedPts[i].new_i] += OD[sortedPts[i].i] * sortedPts[i].x;
            new_y[sortedPts[i].new_i] += OD[sortedPts[i].i] * sortedPts[i].y;
            new_OD[sortedPts[i].new_i] += OD[sortedPts[i].i];

            for (int j = 0; j < NN; j++) {
                new_w[sortedPts[i].new_i][sortedPts[j].new_i] += w[sortedPts[i].i][sortedPts[j].i];
            }
        }

        for (int i = 0; i < nn; i++) {
            new_x[i] /= new_OD[i];
            new_y[i] /= new_OD[i];
        }

        System.out.println(nn);

        for (int i = 0; i < nn; i++) {
            System.out.println(new_x[i] + " " + new_y[i]);
        }

        for (int i = 0; i < nn; i++) {
            for (int j = 0; j < nn; j++) {
                System.out.print(new_w[i][j] + ((j == nn - 1) ? '\n' : ' '));
            }
        }

        System.out.println(np);
        System.out.println(collect);
        System.out.println(transfer);
        System.out.println(distribute);
    }

    private static String[] loadFile(String path) throws IOException {
        Path filePath = Paths.get(path);
        return Files.readAllLines(filePath).toArray(String[]::new);
    }
}
