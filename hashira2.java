import java.math.BigInteger;

public class BaseConverter {

    public static void main(String[] args) {
        // --- Test Case 2 ---
        String valueInBase15 = "aed7015a346d635";
        int base = 15;

        // The BigInteger constructor can directly convert a string
        // representation of a number from any base (radix) to decimal.
        BigInteger decimalValue = new BigInteger(valueInBase15, base);

        // Print the results
        System.out.println("Input Value (Base " + base + "): " + valueInBase15);
        System.out.println("Converted Decimal (Base 10) Value: " + decimalValue);
    }
}
