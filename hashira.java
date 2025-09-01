import org.json.JSONObject;
import org.json.JSONException;

import java.util.*;

public class PolynomialSolver {

    // Solve linear system using Gaussian elimination
    public static double[] solveLinearSystem(double[][] matrix) {
        int n = matrix.length;

        // Forward elimination
        for (int i = 0; i < n; i++) {
            int maxRow = i;
            for (int k = i + 1; k < n; k++) {
                if (Math.abs(matrix[k][i]) > Math.abs(matrix[maxRow][i])) {
                    maxRow = k;
                }
            }
            double[] temp = matrix[i];
            matrix[i] = matrix[maxRow];
            matrix[maxRow] = temp;

            for (int k = i + 1; k < n; k++) {
                double factor = matrix[k][i] / matrix[i][i];
                for (int j = i; j <= n; j++) {
                    matrix[k][j] -= factor * matrix[i][j];
                }
            }
        }

        // Back substitution
        double[] solution = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            solution[i] = matrix[i][n];
            for (int j = i + 1; j < n; j++) {
                solution[i] -= matrix[i][j] * solution[j];
            }
            solution[i] /= matrix[i][i];
        }
        return solution;
    }

    public static void main(String[] args) throws JSONException {
        // Sample JSON input
        String jsonStr = """
        {
          "keys": { "n": 4, "k": 3 },
          "1": { "base": "10", "value": "4" },
          "2": { "base": "2", "value": "111" },
          "3": { "base": "10", "value": "12" },
          "6": { "base": "4", "value": "213" }
        }
        """;

        JSONObject data = new JSONObject(jsonStr);

        int k = data.getJSONObject("keys").getInt("k");
        int m = k - 1;

        List<Double> xCoords = new ArrayList<>();
        List<Double> yCoords = new ArrayList<>();

        for (String key : data.keySet()) {
            if (key.equals("keys")) continue;
            if (xCoords.size() >= k) break;

            double x = Double.parseDouble(key);
            JSONObject point = data.getJSONObject(key);
            int base = Integer.parseInt(point.getString("base"));
            String valueStr = point.getString("value");
            long decValue = Long.parseLong(valueStr, base);

            xCoords.add(x);
            yCoords.add((double) decValue);
        }

        // Build augmented matrix
        double[][] augmentedMatrix = new double[k][k + 1];
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < k; j++) {
                augmentedMatrix[i][j] = Math.pow(xCoords.get(i), j);
            }
            augmentedMatrix[i][k] = yCoords.get(i);
        }

        double[] coefficients = solveLinearSystem(augmentedMatrix);

        System.out.println("Polynomial Degree (m): " + m);
        System.out.println("\nFound Coefficients:");
        for (int i = 0; i < coefficients.length; i++) {
            System.out.printf("c%d = %.2f%n", i, coefficients[i]);
        }
    }
}
