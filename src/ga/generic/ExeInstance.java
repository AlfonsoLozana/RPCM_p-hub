package ga.generic;

import ga.generic.intrafece.Config;
import ga.ssGA.Algorithm;
import ga.ssGA.Individual;
import ga.ssGA.Problem;

public class ExeInstance<TInstance> {
    public Individual Execute(Problem problem, Config config, String args[]) throws Exception {
        problem.set_geneN(config.getGeneNumber());
        problem.set_geneL(config.getGeneLength());
        System.out.println("Lenght");
        System.out.println(config.getGeneLength());
        problem.set_target_fitness(config.getTargetFitness());

        Algorithm ga; // The ssGA being used
        ga = new Algorithm(problem, config.getPopulationSize(), config.getGeneNumber(), config.getGeneLength(),
                config.getCrossoverProbability(), config.getMutationProbability());

        for (int step = 0; step < config.getMaxIterations(); step++) {
            ga.go_one_step();
            System.out.print(step);
            System.out.print("  ");
            System.out.println(ga.get_bestf());

            if ((problem.tf_known()) &&
                    (ga.get_solution()).get_fitness() >= problem.get_target_fitness()) {
                System.out.print("Solution Found! After ");
                System.out.print(problem.get_fitness_counter());
                System.out.println(" evaluations");
                return ga.get_solution();
            }

        }

        return ga.get_solution();
    }
}
