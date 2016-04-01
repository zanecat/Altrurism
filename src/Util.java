import java.util.Scanner;

/**
 * Created by Zane on 16/4/1.
 */
public class Util {
    private static Scanner scanner = new Scanner(System.in);

    public static float getFloatInput(String paramName, float lowerBoundary, float upperBoundary){
        System.out.println("Please type in " + paramName + ", from " + lowerBoundary + " to " + upperBoundary);
        float input;
        while (true) {
            String s = scanner.nextLine();
            if (!s.matches("^(-?\\d+)(\\.\\d+)?$")) {
                System.out.println("Not a float, retype please");
                continue;
            }
            input = Float.parseFloat(s);
            if (input < lowerBoundary || upperBoundary < input)
                System.out.println("Out of boundary");
            else
                return input;
        }
    }
}
