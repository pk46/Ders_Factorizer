import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The program writes out - in an expected form - factorial of all integers
 * read in from an input file

 * The input file is specified as the fist parameter so the call looks e.g.:
 * java Faktorizer input.txt

 * The file input.txt can contain e.g.:  "10,5.24 7"
 * And output is like:
 * 10! = 3628800
 * 5! = 120
 * 24! = 620448401733239439360000
 * 7! = 5040
 */
class Factorizer {

    private final HashMap<BigInteger, BigInteger> cache = new HashMap<>();

    private BigInteger calculateFactorial(BigInteger bigIntegerInput) {
        if (cache.containsKey(bigIntegerInput)) {
            return cache.get(bigIntegerInput);
        }
        if (bigIntegerInput.equals(BigInteger.valueOf(0))) {
            cache.put(BigInteger.valueOf(0), BigInteger.valueOf(1));
            return BigInteger.valueOf(1);
        } else {
            final BigInteger result = bigIntegerInput.multiply((calculateFactorial(bigIntegerInput
                    .subtract(BigInteger.valueOf(1)))));
            cache.put(bigIntegerInput, result);
            return result;
        }
    }


    /**
     * Reads numbers from file and write out the factorials.
     *
     * @param fileName file to be processed
     * @throws IOException if file is non-existent
     */
    private String readFile(String fileName) {
        StringBuilder string = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
             string.append(reader.readLine());
        } catch (IOException ex) {
            Logger.getLogger(Factorizer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return string.toString();
    }

    private ArrayList<Integer> getNumbersFromString(String input) {
        ArrayList<Integer> numbers = new ArrayList<>();
        StringBuilder currentNumber = new StringBuilder();

        for (char character : input.toCharArray()) {
            if (Character.isDigit(character)) {
                currentNumber.append(character);
            } else {
                numbers.add(Integer.parseInt(currentNumber.toString()));
                currentNumber.setLength(0);
            }
        }
        numbers.add(Integer.parseInt(currentNumber.toString())); // handling the last number

        return numbers;
    }

    private void processInput(ArrayList<Integer> numbers) {
        for (int number : numbers) {
            BigInteger result = calculateFactorial(BigInteger.valueOf(number));
            System.out.printf("%d! = %d\n", number, result);
        }
    }

    /**
     * Main method, which is called from command line. As the only parameter it
     * expects a file in which numbers are separated by a char that is not a number.
     * @param args file to be processed
     */
    public static void main(String[] args) {
        String fileName = args[0];
        Factorizer factorizer = new Factorizer();

        String data = factorizer.readFile(fileName);
        ArrayList<Integer> numbers = factorizer.getNumbersFromString(data);
        factorizer.processInput(numbers);
    }
}