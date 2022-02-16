import processing.core.PApplet;

import java.util.Arrays;
import java.util.Random;

public class Main extends PApplet {

    PSO pso;
    Random random;
    final int size = 500;

    @Override
    public void settings() {
        size(1200, 800);
        pso = new PSO();
        pso.initialization(size);
        pso.getBest();
        random = new Random();
    }

    @Override
    public void start() {
        noStroke();
        frameRate(20);
    }

    @Override
    public void draw() {
        background(250);
        noStroke();

        double t = 0.05;
        float draw_zoom = 300;// 放大比例
        double[][] pop = pso.getPopulation();
        double[][] v = pso.getV();
        double[] fitness = pso.getFitness();

        // 更新速度值
        for (int i = 0; i < size; i++) {
            v[i][0] += pso.getEta_individual() * random.nextDouble() *
                    (pso.getIndividual_loc()[i][0] - pso.getPopulation()[i][0]) +
                    pso.getEta_cluster() * random.nextDouble() *
                            (pso.getCluster_loc()[0] - pso.getPopulation()[i][0]);

            v[i][1] += pso.getEta_individual() * random.nextDouble() *
                    (pso.getIndividual_loc()[i][1] - pso.getPopulation()[i][1]) +
                    pso.getEta_cluster() * random.nextDouble() *
                            (pso.getCluster_loc()[1] - pso.getPopulation()[i][1]);

            v[i][0] = Math.min(v[i][0], pso.getMove_speed());
            v[i][1] = Math.min(v[i][1], pso.getMove_speed());
            v[i][0] = Math.max(v[i][0], -1 * pso.getMove_speed());
            v[i][1] = Math.max(v[i][1], -1 * pso.getMove_speed());
        }

        // 更新粒子位置
        for (int i = 0; i < size; i++) {
            pop[i][0] = t * (0.5 * v[i][0]) + (1 - t) * pop[i][0];
            pop[i][1] = t * (0.5 * v[i][1]) + (1 - t) * pop[i][1];

            v[i][0] = Math.min(v[i][0], pso.getMove_range());
            v[i][1] = Math.min(v[i][1], pso.getMove_range());
            v[i][0] = Math.max(v[i][0], -1 * pso.getMove_range());
            v[i][1] = Math.max(v[i][1], -1 * pso.getMove_range());
        }

        // 適應度更新
        for (int i = 0; i < size; i++) {
            fitness[i] = pso.func(pop[i]);
        }

        for (int i = 0; i < size; i++) {
            if (fitness[i] > pso.getIndividual_fitness()[i]) {
                pso.getIndividual_fitness()[i] = fitness[i];
                pso.getIndividual_loc()[i] = Arrays.copyOf(pop[i], 2);
            }
        }

        double fitness_max = Double.MIN_VALUE;
        int fitness_max_loc = 0;

        for (int i = 0; i < fitness.length; i++) {
            if (fitness_max < fitness[i]) {
                fitness_max = fitness[i];
                fitness_max_loc = i;
            }
        }

        if (fitness_max > pso.getCluster_fitness()) {
            pso.setCluster_fitness(fitness_max);
            pso.setCluster_loc(new double[]{pop[fitness_max_loc][0], pop[fitness_max_loc][1]});
        }

        fill(0, 100, 120);
        for (int i = 0; i < size; i++) {
            System.out.println(pop[i][0] + " " + pop[i][1]);
            ellipse((float) pop[i][0] * draw_zoom + 600, (float) pop[i][1] * draw_zoom + 400, 5, 5);
        }
    }

    public static void main(String[] passedArgs) {
        String[] appletArgs = new String[]{"Main"};
        PApplet.main(appletArgs);
    }
}
