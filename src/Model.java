import java.util.Random;

/**
 * Created by Zane on 16/4/1.
 *
 * SWEN90004 Modelling complex
 * Assignment 1
 * The altruism model
 */
class Model {
    private float alt_probability;
    private float self_probability;
    private float cost_of_alt;
    private float benefit_from_alt;
    private float disease;
    private float harshness;
    private int tick = 0;
    private Random random = new Random();
    private static final Patch[][] grid = new Patch[41][41];

    /**
     * Construct a model, the user will be asked to type in six params:
     * altruistic probability, selfish probability, cost of altruism,
     * benefit from altruism, disease and harshness
     */
    public Model(){
        this.alt_probability = Util.getFloatInput("altruistic probability",
                0.0f, 0.5f);
        this.self_probability = Util.getFloatInput("selfish probability",
                0.0f, 0.5f);
        this.cost_of_alt = Util.getFloatInput("cost of altruism",
                0.0f, 0.9f);
        this.benefit_from_alt = Util.getFloatInput("benefit from altruism",
                0.0f, 0.9f);
        this.disease = Util.getFloatInput("disease", 0.0f, 1.0f);
        this.harshness = Util.getFloatInput("harshness", 0.0f, 1.0f);
    }

    Model(float f1, float f2, float f3, float f4, float f5, float f6){
        this.alt_probability = f1;
        this.self_probability = f2;
        this.cost_of_alt = f3;
        this.benefit_from_alt = f4;
        this.disease = f5;
        this.harshness = f6;
    }

    /**
     * Initiate a grid, the role of each patch will be
     * assigned randomly according to the possibility decided by the user
     */
    public void setup(){
        float r;
        for (int i = 0; i < 41; i++) {
            for (int j = 0; j < 41; j++) {
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

    /**
     * To run the model, it will stop when any side goes extinct
     */
    public void go(){
        if (grid[0][0] == null)
            System.out.println("Grid is not initiated, run setup please");
        else {
            float value;
            while (Util.count(grid)) {
                System.out.println("--------round " + (tick + 1) + "---------");
                for (int i = 0; i < grid.length; i++) {
                    for (int j = 0; j < grid.length; j++) {
                        value = benefit_from_alt * (
                                grid[i][j].getBenefit_out() +
                                        grid[Util.getNeighborIndex(i, 1)][j].
                                        getBenefit_out() +
                                        grid[Util.getNeighborIndex(i, -1)][j].
                                        getBenefit_out() +
                                        grid[i][Util.getNeighborIndex(j, 1)].
                                        getBenefit_out() +
                                        grid[i][Util.getNeighborIndex(j, -1)].
                                        getBenefit_out()) / 5.0f;
                        grid[i][j].setAltruism_benefit(value);
                    }
                }
                performFitnessCheck();
                lottery();
                tick++;
            }
        }
    }

    /**
     * Set fitness for each patch
     */
    private void performFitnessCheck(){
        for (Patch[] aGrid : grid) {
            for (Patch patch : aGrid) {
                switch (patch.getColor()) {
                    case Green:
                        patch.setFitness(1 + patch.getAltruism_benefit());
                        break;
                    case Pink:
                        patch.setFitness(1 - patch.getAltruism_benefit()
                                - cost_of_alt);
                        break;
                    case Black:
                        patch.setFitness(harshness);
                        break;
                }
            }
        }
    }

    /**
     * invoke other method to finish a lottery
     */
    private void lottery(){
        recordNeighborFitness();
        findLotteryWeights();
        nextGeneration();
    }

    /**
     * record the altruist fitness, self fitness, and harsh fitness for each
     * patch
     */
    private void recordNeighborFitness(){
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                grid[i][j].setAlt_fitness(0.0f);
                grid[i][j].setSelf_fitness(0.0f);
                grid[i][j].setHarsh_fitness(0.0f);
                switch (grid[i][j].getColor()){
                    case Pink:
                        grid[i][j].setAlt_fitness(grid[i][j].getFitness());
                        break;
                    case Green:
                        grid[i][j].setSelf_fitness(grid[i][j].getFitness());
                        break;
                    case Black:
                        grid[i][j].setHarsh_fitness(grid[i][j].getFitness());
                        break;
                }
                updateFitnessFromNeighbor(grid[i][j],
                        grid[Util.getNeighborIndex(i, 1)][j]);
                updateFitnessFromNeighbor(grid[i][j],
                        grid[Util.getNeighborIndex(i, -1)][j]);
                updateFitnessFromNeighbor(grid[i][j],
                        grid[i][Util.getNeighborIndex(j, 1)]);
                updateFitnessFromNeighbor(grid[i][j],
                        grid[i][Util.getNeighborIndex(j, -1)]);
            }
        }
    }

    /**
     * Update altruist fitness, selfish fitness or harshness fitness of a
     * patch according to its neighbor
     * @param patch the specific patch
     * @param neighbor the neighbor of the patch
     */
    private void updateFitnessFromNeighbor(Patch patch, Patch neighbor){
        switch (neighbor.getColor()){
            case Pink:
                patch.setAlt_fitness(patch.getAlt_fitness()
                        + neighbor.getFitness());
            case Green:
                patch.setSelf_fitness(patch.getSelf_fitness()
                        + neighbor.getFitness());
            case Black:
                patch.setHarsh_fitness(patch.getHarsh_fitness()
                        + neighbor.getFitness());
        }
    }

    /**
     * Update lottery weight
     */
    private void findLotteryWeights(){
        float fitnessSum;
        for (Patch[] aGrid : grid) {
            for (Patch patch : aGrid) {
                fitnessSum = patch.getAlt_fitness() + patch.getSelf_fitness()
                        + patch.getHarsh_fitness() + disease;
                if (fitnessSum > 0) {
                    patch.setAlt_weight(patch.getAlt_fitness() / fitnessSum);
                    patch.setSelf_weight(patch.getSelf_fitness() /
                            fitnessSum);
                    patch.setHarsh_weight(patch.getHarsh_fitness() /
                            fitnessSum);
                } else {
                    patch.setAlt_weight(0);
                    patch.setSelf_weight(0);
                    patch.setHarsh_weight(0);
                }
            }
        }
    }

    /**
     * Move to next generation according to the weights
     */
    private void nextGeneration(){
        float breedChance;
        for (Patch[] aGrid : grid) {
            for (Patch patch : aGrid) {
                breedChance = random.nextFloat();
                if (breedChance < patch.getAlt_weight()) {
                    patch.setColor(Patch.COLOR.Pink);
                }
                else {
                    if (breedChance < patch.getAlt_weight() +
                            patch.getSelf_weight()) {
                        patch.setColor(Patch.COLOR.Green);
                    }
                    else{
                        clearPatch(patch);
                    }
                }
            }
        }
    }

    /**
     * Clear a patch, set all params to 0
     * @param patch the specific patch
     */
    private void clearPatch(Patch patch){
        patch.setColor(Patch.COLOR.Black);
        patch.setFitness(0);
        patch.setHarsh_fitness(0);
        patch.setAltruism_benefit(0);
        patch.setSelf_fitness(0);
        patch.setAlt_fitness(0);
        patch.setAlt_weight(0);
        patch.setHarsh_weight(0);
        patch.setSelf_weight(0);
    }
}
