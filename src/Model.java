/**
 * Created by Zane on 16/4/1.
 */
public class Model {
    private float alt_probability;
    private float self_probability;
    private float cost_of_alt;
    private float benefit_from_alt;
    private float disease;
    private float harsh_fitness;
    private static final Patch[][] grid = new Patch[41][41];

    public Model(){
        this.alt_probability = Util.getFloatInput("altruistic probability", 0.0f, 0.5f);
        this.self_probability = Util.getFloatInput("selfish probability", 0.0f, 0.5f);
        this.self_probability = Util.getFloatInput("selfish probability", 0.0f, 0.9f);
        this.self_probability = Util.getFloatInput("selfish probability", 0.0f, 0.9f);
        this.self_probability = Util.getFloatInput("selfish probability", 0.0f, 1.0f);
        this.self_probability = Util.getFloatInput("selfish probability", 0.0f, 1.0f);
    }

    public void go(){

    }

}
