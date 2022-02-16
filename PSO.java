import java.util.Arrays;
import java.util.Random;

public class PSO {
    //參數定義
    private double weight = 1;
    private double eta_cluster = 0.49445;// 集群的學習率
    private double eta_individual = 0.49445;// 個體的學習率
    private double max_iter = 300;// 迭代次數
    private double population_size = 50;// 族群規模
    private double move_speed = 0.5;// 粒子最大移動範圍
    private double move_range = Math.PI;// 粒子最大移動範圍

    private double[][] population;// 粒子位置
    private double[][] v;// 粒子移動速度
    private double[] fitness;//適應程度(對該位置)

    private double[] cluster_loc;// 族群最佳位置
    private double[][] individual_loc;// 個體最佳位置
    private double cluster_fitness;// 族群適應程度
    private double[] individual_fitness;// 個體適應程度

    /**
     * 要求解的方程式
     * @param x 輸入粒子位置
     * @return 粒子適應程度
     */
    public double func(double[] x) {
        if (x[0] == 0 && x[1] == 0) {
            return Math.exp((Math.cos(2 * Math.PI * x[0]) + Math.cos(2 * Math.PI * x[1])) / 2);
        } else {
            return Math.sin(Math.sqrt(Math.pow(x[0], 2) +
                    Math.pow(x[1], 2))) / Math.sqrt(Math.pow(x[0], 2) +
                    Math.pow(x[1], 2)) + Math.exp((Math.cos(2 * Math.PI * x[0]) +
                    Math.cos(2 * Math.PI * x[1])) / 2);
        }
    }

    /**
     * 初始化
     * @param size 設定族群大小
     */
    public void initialization(int size) {
        Random random = new Random();
        population = new double[size][2];
        v = new double[size][2];
        fitness = new double[size];

        for (int i = 0; i < size; i++) {
            population[i][0] = (random.nextDouble() - 0.5) * -move_range * 2;
            population[i][1] = (random.nextDouble() - 0.5) * move_range * 2;
            v[i][0] = (random.nextDouble() - 0.5) * -move_range * 2;
            v[i][1] = (random.nextDouble() - 0.5) * move_range * 2;
            fitness[i] = func(population[i]);
        }
    }

    /**
     * 得到最好的下一步
     */
    public void getBest(){
        // 推斷集群最佳位置
        double fitness_max = Double.MIN_VALUE;
        int fitness_max_loc = 0;

        for(int i=0;i<fitness.length;i++){
            if(fitness_max<fitness[i]){
                fitness_max = fitness[i];
                fitness_max_loc = i;
            }
        }

        cluster_loc = Arrays.copyOf(population[fitness_max_loc],2);// 長度恆定為2
        cluster_fitness = fitness_max;

        // 個體最佳位置和其適度值
        individual_loc = Arrays.copyOf(population, population.length);
        individual_fitness = Arrays.copyOf(fitness, fitness.length);
    }

    // set
    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setEta_cluster(double eta_cluster) {
        this.eta_cluster = eta_cluster;
    }

    public void setEta_individual(double eta_individual) {
        this.eta_individual = eta_individual;
    }

    public void setMax_iter(double max_iter) {
        this.max_iter = max_iter;
    }

    public void setPopulation_size(double population_size) {
        this.population_size = population_size;
    }

    public void setMove_speed(double move_speed) {
        this.move_speed = move_speed;
    }

    public void setMove_range(double move_range) {
        this.move_range = move_range;
    }

    public void setCluster_loc(double[] cluster_loc) {
        this.cluster_loc = cluster_loc;
    }

    public void setCluster_fitness(double cluster_fitness) {
        this.cluster_fitness = cluster_fitness;
    }

    public void setIndividual_loc(double[][] individual_loc) {
        this.individual_loc = individual_loc;
    }

    public void setIndividual_fitness(double[] individual_fitness) {
        this.individual_fitness = individual_fitness;
    }

    // get
    public double getWeight() {
        return weight;
    }

    public double getEta_cluster() {
        return eta_cluster;
    }

    public double getEta_individual() {
        return eta_individual;
    }

    public double getMax_iter() {
        return max_iter;
    }

    public double getPopulation_size() {
        return population_size;
    }

    public double getMove_speed() {
        return move_speed;
    }

    public double getMove_range() {
        return move_range;
    }

    public double[][] getPopulation() {
        return population;
    }

    public double[][] getV() {
        return v;
    }

    public double[] getFitness() {
        return fitness;
    }

    public double[] getCluster_loc() {
        return cluster_loc;
    }

    public double getCluster_fitness() {
        return cluster_fitness;
    }

    public double[][] getIndividual_loc() {
        return individual_loc;
    }

    public double[] getIndividual_fitness() {
        return individual_fitness;
    }
}
