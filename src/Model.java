import java.util.Random;

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

    public void setup(){
        initPatches(grid);
        go();
    }

    private void initPatches(Patch[][] grid){
        Random random = new Random();
        float r;
        int i = 0;
        for (; i < 41; i++) {
            int j = 0;
            for (; j < 41; j++) {
                r = random.nextFloat();
                if (r < alt_probability)
                    grid[i][j] = new Patch(Patch.COLOR.Pink);
                else if (r < self_probability + alt_probability)
                    grid[i][j] = new Patch(Patch.COLOR.Green);
                else
                    grid[i][j] = new Patch(Patch.COLOR.Black);
            }
        }
    }

    public void go(){
        boolean flag = true;
        float value;
        while (flag){
            for(Patch[] patches : grid){
                for (Patch patch : patches){
//                    value = benefit_from_alt * (patch.getBenefit_out() + )
//                    patch.setAltruism_benefit();
                }
            }
        }
    }

    public void output(){
        for (Patch[] patchRows : grid){
            for (Patch patch : patchRows){
                System.out.print(patch.getRole()+",");
            }
            System.out.println("");
        }
    }

}
