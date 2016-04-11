/**
 * Created by Zane on 2016/4/1.
 */
public class Test {
    public static void main(String[] args) {
        Model m = new Model(0.26f, 0.26f, 0.13f, 0.88f, 0.0f, 0.0f);
        m.setup();
        m.go();
        System.out.println("test done.");
    }
}
