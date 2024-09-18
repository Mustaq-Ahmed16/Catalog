import java.util.*;

public class ShamirSecretSharing {

  // Method to decode the y value from the given base
  private static int decodeYValue(String base, String value) {
    int baseInt = Integer.parseInt(base);
    return Integer.parseInt(value, baseInt);
  }

  // Manually parse the JSON-like structure and extract points (x, y)
  public static List<int[]> parsePoints(Map<String, Map<String, String>> jsonObject) {
    List<int[]> points = new ArrayList<>();
    Map<String, String> keys = jsonObject.get("keys");
    int n = Integer.parseInt(keys.get("n"));
    int k = Integer.parseInt(keys.get("k"));

    for (String key : jsonObject.keySet()) {
      if (!key.equals("keys")) {
        int x = Integer.parseInt(key);
        Map<String, String> root = jsonObject.get(key);
        String base = root.get("base");
        String value = root.get("value");

        int y = decodeYValue(base, value);
        points.add(new int[] { x, y });
      }
    }
    return points;
  }

  // Method to implement Lagrange Interpolation
  public static double lagrangeInterpolation(List<int[]> points) {
    double constantTerm = 0.0;
    int k = points.size(); // We will use k points

    // Implementing Lagrange interpolation
    for (int i = 0; i < k; i++) {
      int[] pointI = points.get(i);
      double yi = pointI[1];
      double term = yi;

      for (int j = 0; j < k; j++) {
        if (i != j) {
          int[] pointJ = points.get(j);
          term *= ((0 - pointJ[0]) * 1.0) / (pointI[0] - pointJ[0]);
        }
      }
      constantTerm += term;
    }
    return constantTerm;
  }

  public static void main(String[] args) {
    // Sample input using a Map to represent the JSON structure
    Map<String, Map<String, String>> jsonObject = new HashMap<>();

    // Setting up the "keys"
    Map<String, String> keys = new HashMap<>();
    keys.put("n", "4");
    keys.put("k", "3");
    jsonObject.put("keys", keys);

    // Setting up the points
    Map<String, String> point1 = new HashMap<>();
    point1.put("base", "10");
    point1.put("value", "4");
    jsonObject.put("1", point1);

    Map<String, String> point2 = new HashMap<>();
    point2.put("base", "2");
    point2.put("value", "111");
    jsonObject.put("2", point2);

    Map<String, String> point3 = new HashMap<>();
    point3.put("base", "10");
    point3.put("value", "12");
    jsonObject.put("3", point3);

    Map<String, String> point6 = new HashMap<>();
    point6.put("base", "4");
    point6.put("value", "213");
    jsonObject.put("6", point6);

    // Parse the points
    List<int[]> points = parsePoints(jsonObject);

    // Apply Lagrange interpolation to find the constant term
    double constantTerm = lagrangeInterpolation(points);

    // Print the constant term (the secret c), rounded to the nearest integer
    System.out.println("The constant term (c) is: " + Math.round(constantTerm));
  }
}