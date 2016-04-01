/**
 * Created by Zane on 16/3/28.
 */
public class Main {

    public static void main(String[] args) {
        float i;
        Model m = new Model();
        i = Util.getFloatInput("value", 0.1f, 1.0f);
        System.out.println(i);
    }
}
