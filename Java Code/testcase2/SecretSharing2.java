import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SecretSharing2 {

  // Method to decode the y value from the given base
  private static BigInteger decodeYValue(String base, String value) {
    int baseInt = Integer.parseInt(base);
    return new BigInteger(value, baseInt);
  }

  // Manually parse the JSON-like structure and extract points (x, y)
  public static List<BigInteger[]> parsePoints(Map<String, Map<String, String>> jsonObject) {
    List<BigInteger[]> points = new ArrayList<>();
    Map<String, String> keys = jsonObject.get("keys");
    int n = Integer.parseInt(keys.get("n"));
    int k = Integer.parseInt(keys.get("k"));

    for (String key : jsonObject.keySet()) {
      if (!key.equals("keys")) {
        BigInteger x = new BigInteger(key);
        Map<String, String> root = jsonObject.get(key);
        String base = root.get("base");
        String value = root.get("value");

        BigInteger y = decodeYValue(base, value);
        points.add(new BigInteger[] { x, y });
      }
    }
    return points;
  }

  // Method to implement Lagrange Interpolation
  public static BigInteger lagrangeInterpolation(List<BigInteger[]> points) {
    BigInteger constantTerm = BigInteger.ZERO;
    int k = points.size(); // We will use k points

    // Implementing Lagrange interpolation
    for (int i = 0; i < k; i++) {
      BigInteger[] pointI = points.get(i);
      BigInteger yi = pointI[1];
      BigInteger term = yi;

      for (int j = 0; j < k; j++) {
        if (i != j) {
          BigInteger[] pointJ = points.get(j);
          term = term.multiply(BigInteger.ZERO.subtract(pointJ[0]))
              .divide(pointI[0].subtract(pointJ[0]));
        }
      }
      constantTerm = constantTerm.add(term);
    }
    return constantTerm;
  }

  public static void main(String[] args) {
    // Sample input using a Map to represent the JSON structure
    Map<String, Map<String, String>> jsonObject = new HashMap<>();

    // Setting up the "keys"
    Map<String, String> keys = new HashMap<>();
    keys.put("n", "9");
    keys.put("k", "6");
    jsonObject.put("keys", keys);

    // Setting up the points
    Map<String, String> point1 = new HashMap<>();
    point1.put("base", "10");
    point1.put("value", "28735619723837");
    jsonObject.put("1", point1);

    Map<String, String> point2 = new HashMap<>();
    point2.put("base", "16");
    point2.put("value", "1A228867F0CA");
    jsonObject.put("2", point2);

    Map<String, String> point3 = new HashMap<>();
    point3.put("base", "12");
    point3.put("value", "32811A4AA0B7B");
    jsonObject.put("3", point3);

    Map<String, String> point4 = new HashMap<>();
    point4.put("base", "11");
    point4.put("value", "917978721331A");
    jsonObject.put("4", point4);

    Map<String, String> point5 = new HashMap<>();
    point5.put("base", "16");
    point5.put("value", "1A22886782E1");
    jsonObject.put("5", point5);

    Map<String, String> point6 = new HashMap<>();
    point6.put("base", "10");
    point6.put("value", "28735619654702");
    jsonObject.put("6", point6);

    Map<String, String> point7 = new HashMap<>();
    point7.put("base", "14");
    point7.put("value", "71AB5070CC4B");
    jsonObject.put("7", point7);

    Map<String, String> point8 = new HashMap<>();
    point8.put("base", "9");
    point8.put("value", "122662581541670");
    jsonObject.put("8", point8);

    Map<String, String> point9 = new HashMap<>();
    point9.put("base", "8");
    point9.put("value", "642121030037605");
    jsonObject.put("9", point9);

    // Parse the points
    List<BigInteger[]> points = parsePoints(jsonObject);

    // Apply Lagrange interpolation to find the constant term
    BigInteger constantTerm = lagrangeInterpolation(points);

    // Print the constant term (the secret c)
    System.out.println("The constant term (c) is: " + constantTerm);
  }
}