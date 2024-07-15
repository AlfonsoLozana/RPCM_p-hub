package ga.generic;

import ga.generic.intrafece.Config;

public class DefaultConfig implements Config {

    private int geneNumber = 512;
    private int geneLength = 1;
    private int populationSize = 512;
    private double crossoverProbability = 0.8;
    private long maxIterations = 5000;
    private double mutationProbability = 0;
    private double targetFitness = 0;

    public DefaultConfig() {
    }

    public DefaultConfig(int geneNumber, int geneLength, int populationSize, double crossoverProbability, long maxIterations, double mutationProbability, double targetFitness) {
        //check null parameters

        this.geneNumber = geneNumber;
        this.geneLength = geneLength;
        this.populationSize = populationSize;
        this.crossoverProbability = crossoverProbability;
        this.maxIterations = maxIterations;
        this.mutationProbability = mutationProbability;
        this.targetFitness = targetFitness;
    }

    @Override
    public int getGeneNumber() {
        return geneNumber;
    }

    @Override
    public int getGeneLength() {
        return geneLength;
    }

    @Override
    public int getPopulationSize() {
        return populationSize;
    }

    @Override
    public double getCrossoverProbability() {
        return crossoverProbability;
    }

    @Override
    public double getMutationProbability() {
        return mutationProbability == 0 ? 1.0 / (double) (getGeneNumber() * getGeneLength()) : mutationProbability;
    }

    @Override
    public double getTargetFitness() {
        return targetFitness;
    }

    @Override
    public long getMaxIterations() {
        return maxIterations;
    }
}
