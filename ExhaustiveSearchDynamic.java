import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ExhaustiveSearchDynamic {
    private int[][] distances;
    private int numCities;
    private int[][] memo;

    public ExhaustiveSearchDynamic(int[][] distances) {
        this.distances = distances;
        this.numCities = distances.length;
        this.memo = new int[numCities][1 << numCities];
    }

    public void solve() {
        int optimalTourLength = tsp(0, 1);
        System.out.println("Optimal tour length: " + optimalTourLength);
        printOptimalPath();
    }

    private int tsp(int currentCity, int visited) {
        if (visited == (1 << numCities) - 1) { // All cities have been visited
            return distances[currentCity][0];
        }

        if (memo[currentCity][visited] != 0) {
            return memo[currentCity][visited];
        }

        int minDistance = Integer.MAX_VALUE;

        for (int nextCity = 0; nextCity < numCities; nextCity++) {
            if ((visited & (1 << nextCity)) == 0) { // Next city has not been visited
                int distance = distances[currentCity][nextCity] + tsp(nextCity, visited | (1 << nextCity));
                minDistance = Math.min(minDistance, distance);
            }
        }

        memo[currentCity][visited] = minDistance;

        return minDistance;
    }

    private void printOptimalPath() {
        int currentCity = 0;
        int visited = 1;
        System.out.print("Optimal tour path: 0 ");

        for (int i = 0; i < numCities - 1; i++) {
            int nextCity = -1;
            int minDistance = Integer.MAX_VALUE;

            for (int j = 0; j < numCities; j++) {
                if ((visited & (1 << j)) == 0) { // Next city has not been visited
                    int distance = distances[currentCity][j];
                    if (distance < minDistance) {
                        minDistance = distance;
                        nextCity = j;
                    }
                }
            }

            visited |= (1 << nextCity);
            currentCity = nextCity;
            System.out.print(nextCity + " ");
        }

        System.out.println("0"); // Return to the starting city
    }

    public static void main(String[] args) {
        try {
            // Read matrix from file
            File file = new File("./g13.txt");
            Scanner scanner = new Scanner(file);

            int size = scanner.nextInt();
            int[][] matrix = new int[size][size];

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    matrix[i][j] = scanner.nextInt();
                }
            }

            // Solve the Traveling Salesman Problem using dynamic programming
            ExhaustiveSearchDynamic solver = new ExhaustiveSearchDynamic(matrix);
            double times = 0;
            double start = 0;
            double end = 0;
            start = System.currentTimeMillis();
            solver.solve();
            end = System.currentTimeMillis();
            times = end - start;
            System.out.println("Execution Time: " + times);

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
