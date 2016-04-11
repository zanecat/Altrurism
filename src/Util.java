import java.util.Scanner;

/**
 * Created by Zane on 16/4/1.
 * This class contains three static functions, which are used to
 * deal with specific requirements in the Model to loosen coupling
 */
public class Util {
    private static Scanner scanner = new Scanner(System.in);

    /**
     * This function is used to get user input, and check the range
     * and the type right after the user types in float params.
     * If the input is invalid or out of range, the function will ask
     * the user to type in again.
     * @param paramName the name of the input params
     * @param lowerBoundary the lower boundary of the param range
     * @param upperBoundary the upper boundary of the param range
     * @return the valid value for the param
     */
    public static float getFloatInput(String paramName
            , float lowerBoundary, float upperBoundary){
        System.out.println("Please type in " + paramName + ", from "
                + lowerBoundary + " to " + upperBoundary);
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

    /**
     * This function is for getting the neighbor's index of a specific
     * patch in the grid (2-d array), it returns index + shift and checks
     * if the return value is out of boundary (40), if so, find neighbor
     * from the other side.
     * @param index the column or row index of the patch
     * @param shift the shift value of the
     * @return the neighbor's index
     */
    public static int getNeighborIndex(int index, int shift){
        if (shift > 0){
            if (index + shift > 40)
                return index + shift - 40;
            else return index + shift;
        }
        else {
            if (index + shift < 0)
                return index + shift + 40;
            else return index + shift;
        }
    }

    /**
     * This function is for counting the numbers of altruists and selfish
     * patches, and outputs the result to the command line. When any side
     * goes extinct, the function returns false to stop the model from
     * running
     * @param grid the model
     * @return if both side exists, true for yes and false for no
     */
    public static boolean count(Patch[][] grid){
        int altNum = 0;
        int selfNum = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("Grid : \n");
        for (Patch[] aGrid : grid) {
            for (Patch patch : aGrid) {
                sb.append(patch.getRole());
                sb.append(" ");
                switch (patch.getColor()) {
                    case Pink:
                        altNum++;
                        break;
                    case Green:
                        selfNum++;
                        break;
                    case Black:
                        break;
                }
            }
            sb.append("\n");
        }
        sb.append("Altruists: ");
        sb.append(altNum);
        sb.append(", ");
        sb.append("Selfish: ");
        sb.append(selfNum);
        sb.append("\n");

        System.out.println(sb.toString());
        return (altNum != 0 && selfNum != 0);
    }
}
