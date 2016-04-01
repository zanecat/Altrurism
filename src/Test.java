/**
 * Created by Zane on 2016/4/1.
 */
public class Test {
    public static void main(String[] args) {
        Model m = new Model(0.3f, 0.3f, 0.1f, 0.5f, 0, 0);
        m.setup();
        m.go();
        System.out.println("test done.");
    }
}
