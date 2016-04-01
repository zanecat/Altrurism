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

    public static int getNeighborIndex(int index, int shift){
        if (shift > 0){
            if (index + shift > 40)
                return 0;
            else return index + shift;
        }
        else {
            if (index + shift < 0)
                return 40;
            else return index + shift;
        }
    }

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

    public void output(Patch[][] grid){
        for (Patch[] patchRows : grid){
            for (Patch patch : patchRows){
                System.out.print(patch.getRole()+",");
            }
            System.out.println("");
        }
    }
}
