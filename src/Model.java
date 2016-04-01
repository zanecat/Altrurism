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
    private float harshness;
    private int tick = 0;
    Random random = new Random();
    private static final Patch[][] grid = new Patch[41][41];

    public Model(){
        this.alt_probability = Util.getFloatInput("altruistic probability", 0.0f, 0.5f);
        this.self_probability = Util.getFloatInput("selfish probability", 0.0f, 0.5f);
        this.cost_of_alt = Util.getFloatInput("cost of altruism", 0.0f, 0.9f);
        this.benefit_from_alt = Util.getFloatInput("benefit from altruism", 0.0f, 0.9f);
        this.disease = Util.getFloatInput("disease", 0.0f, 1.0f);
        this.harshness = Util.getFloatInput("harshness", 0.0f, 1.0f);
    }

    public Model(float f1, float f2, float f3, float f4, float f5, float f6){
        this.alt_probability = f1;
        this.self_probability = f2;
        this.cost_of_alt = f3;
        this.benefit_from_alt = f4;
        this.disease = f5;
        this.harshness = f6;
    }

    public void setup(){
        initPatches(grid);
    }

    private void initPatches(Patch[][] grid){
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

    public void go(){
        float value;
        while (Util.count(grid)){
            System.out.println("--------round "+tick + 1 + "---------");
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid.length; j++) {
                    value = 0.2f *benefit_from_alt * (
                            grid[i][j].getBenefit_out()
                            + grid[Util.getNeighborIndex(i, 1)][j].getBenefit_out()
                            + grid[Util.getNeighborIndex(i, -1)][j].getBenefit_out()
                            + grid[i][Util.getNeighborIndex(j, 1)].getBenefit_out()
                            + grid[i][Util.getNeighborIndex(j, -1)].getBenefit_out()
                            );
                    grid[i][j].setAltruism_benefit(value);
                }
            }
            performFitnessCheck();
            lottery();
            tick ++;
        }
    }

    private void performFitnessCheck(){
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                switch (grid[i][j].getColor()){
                    case Green:
                        grid[i][j].setFitness(1 + grid[i][j].getAltruism_benefit());
                        break;
                    case Pink:
                        grid[i][j].setFitness(1 - grid[i][j].getAltruism_benefit() - cost_of_alt);
                        break;
                    case Black:
                        grid[i][j].setFitness(harshness);
                        break;
                }
            }
        }
    }

    private void lottery(){
        recordNeighborFitness();
        findLotteryWeights();
        nextGeneration();
    }

    private void recordNeighborFitness(){
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
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
                updateFitnessFromNeighbor(grid[i][j], grid[Util.getNeighborIndex(i, 1)][j]);
                updateFitnessFromNeighbor(grid[i][j], grid[Util.getNeighborIndex(i, -1)][j]);
                updateFitnessFromNeighbor(grid[i][j], grid[i][Util.getNeighborIndex(j, 1)]);
                updateFitnessFromNeighbor(grid[i][j], grid[i][Util.getNeighborIndex(j, -1)]);
            }
        }
    }

    private void updateFitnessFromNeighbor(Patch patch, Patch neighbor){
        switch (neighbor.getColor()){
            case Pink:
                patch.setAlt_fitness(patch.getAlt_fitness() + neighbor.getFitness());
            case Green:
                patch.setSelf_fitness(patch.getSelf_fitness() + neighbor.getFitness());
            case Black:
                patch.setHarsh_fitness(patch.getHarsh_fitness() + neighbor.getFitness());
        }
    }

    private void findLotteryWeights(){
        float fitnessSum;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                fitnessSum = grid[i][j].getAlt_fitness() + grid[i][j].getSelf_fitness()
                        + grid[i][j].getHarsh_fitness() + disease;
                if (fitnessSum > 0){
                    grid[i][j].setAlt_weight(grid[i][j].getAlt_fitness()/fitnessSum);
                    grid[i][j].setSelf_weight(grid[i][j].getSelf_fitness()/fitnessSum);
                    grid[i][j].setHarsh_weight(grid[i][j].getHarsh_fitness()/fitnessSum);
                }
                else {
                    grid[i][j].setAlt_weight(0);
                    grid[i][j].setSelf_weight(0);
                    grid[i][j].setHarsh_weight(0);
                }
            }
        }
    }

    private void nextGeneration(){
        float breedChance;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                breedChance = random.nextFloat();
                if (breedChance < grid[i][j].getAlt_weight())
                    grid[i][j].setColor(Patch.COLOR.Pink);
                else if (breedChance < grid[i][j].getAlt_weight() + grid[i][j].getSelf_weight())
                    grid[i][j].setColor(Patch.COLOR.Green);
                else
                    clearPatch(grid[i][j]);
            }
        }
    }

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
