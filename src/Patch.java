/**
 * Created by Zane on 16/4/1.
 */
public class Patch {
    public enum COLOR {Green, Pink, Black}
    private float altruism_benefit ;
    private float fitness;
    private float self_weight;
    private float alt_weight;
    private float self_fitness;
    private float alt_fitness;
    private float harsh_weight;
    private float harsh_fitness;
    private boolean benefit_out;
    private COLOR color;

    public Patch(COLOR color){
        this.color = color;
        this.benefit_out  = (color == COLOR.Pink);
        this.alt_fitness = 0;
        this.alt_weight = 0;
        this.altruism_benefit = 0;
        this.fitness = 0;
        this.harsh_fitness = 0;
        this.harsh_weight = 0;
        this.self_fitness = 0;
        this.self_weight = 0;
    }

    public float getAltruism_benefit() {
        return altruism_benefit;
    }

    public void setAltruism_benefit(float altruism_benefit) {
        this.altruism_benefit = altruism_benefit;
    }

    public float getFitness() {
        return fitness;
    }

    public void setFitness(float fitness) {
        this.fitness = fitness;
    }

    public float getSelf_weight() {
        return self_weight;
    }

    public void setSelf_weight(float self_weight) {
        this.self_weight = self_weight;
    }

    public float getAlt_weight() {
        return alt_weight;
    }

    public void setAlt_weight(float alt_weight) {
        this.alt_weight = alt_weight;
    }

    public float getSelf_fitness() {
        return self_fitness;
    }

    public void setSelf_fitness(float self_fitness) {
        this.self_fitness = self_fitness;
    }

    public float getAlt_fitness() {
        return alt_fitness;
    }

    public void setAlt_fitness(float alt_fitness) {
        this.alt_fitness = alt_fitness;
    }

    public float getHarsh_weight() {
        return harsh_weight;
    }

    public void setHarsh_weight(float harsh_weight) {
        this.harsh_weight = harsh_weight;
    }

    public float getHarsh_fitness() {
        return harsh_fitness;
    }

    public void setHarsh_fitness(float harsh_fitness) {
        this.harsh_fitness = harsh_fitness;
    }

    public COLOR getColor() {
        return color;
    }

    public void setColor(COLOR color) {
        this.color = color;
        this.benefit_out  = (color == COLOR.Pink);
    }

    public int getBenefit_out() {
        return benefit_out ? 1 : 0;
    }

    public char getRole(){
        switch (this.color){
            case Green:
                return 'S';
            case Pink:
                return 'A';
            default:
                return '_';
        }
    }
}
