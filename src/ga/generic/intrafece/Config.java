package ga.generic.intrafece;

public interface Config {
    // PARAMETERS
    int getGeneNumber(); // Gene number
    int getGeneLength(); // Gene length
    int getPopulationSize(); // Population size
    double getCrossoverProbability(); // Crossover probability
    double getMutationProbability(); // Mutation probability
    double getTargetFitness(); // Target fitness being sought
    long getMaxIterations(); // Maximum number of iterations
}
