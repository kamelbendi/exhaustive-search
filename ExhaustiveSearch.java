import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ExhaustiveSearch {
    private int[][] distances;
    private int numCities;
    private int[] bestTour;
    private int bestTourLength;

    public ExhaustiveSearch(int[][] distances) {
        this.distances = distances;
        this.numCities = distances.length;
        this.bestTour = new int[numCities];
        this.bestTourLength = Integer.MAX_VALUE;
    }

    public void solve() {
        int[] tour = new int[numCities];
        boolean[] visited = new boolean[numCities];
        tour[0] = 0; // Start from city 0
        visited[0] = true;
        findBestTour(tour, visited, 1, 0);

        System.out.println("Best tour: ");
        for (int i = 0; i < numCities; i++) {
            System.out.print(bestTour[i] + " ");
        }
        System.out.println();
        System.out.println("Best tour length: " + bestTourLength);
    }

    private void findBestTour(int[] tour, boolean[] visited, int depth, int currentLength) {
        if (depth == numCities) {
            int length = currentLength + distances[tour[depth - 1]][0]; // Add distance back to city 0
            if (length < bestTourLength) {
                bestTourLength = length;
                System.arraycopy(tour, 0, bestTour, 0, numCities);
            }
            return;
        }

        for (int i = 1; i < numCities; i++) {
            if (!visited[i]) {
                visited[i] = true;
                tour[depth] = i;
                int newLength = currentLength + distances[tour[depth - 1]][i];
                findBestTour(tour, visited, depth + 1, newLength);
                visited[i] = false;
            }
        }
    }

    public static void main(String[] args) {
        try {
            // Read matrix from file
            File file = new File("./g4.txt");
            Scanner scanner = new Scanner(file);

            int size = scanner.nextInt();
            int[][] matrix = new int[size][size];

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    matrix[i][j] = scanner.nextInt();
                }
            }

            // Solve the Traveling Salesman Problem
            ExhaustiveSearch solver = new ExhaustiveSearch(matrix);
            double times = 0;
            times = System.nanoTime();
            solver.solve();
            times = System.nanoTime() - times;
            System.out.println("Execution Time: " + times);
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
