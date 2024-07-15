package ga.generic;

import java.util.ArrayList;
import java.util.List;

public class GeneticAlgorithmRun {
    private double crossoverProbability;
    private double mutationProbability;
    private int populationSize;
    private List<Double> fitnessPerIteration;
    private long executionTime;
    private String programFile;
    private int maxIterations;

    public GeneticAlgorithmRun( double crossoverProbability, double mutationProbability, int populationSize, String programFile, int maxIterations) {
        this.crossoverProbability = crossoverProbability;
        this.mutationProbability = mutationProbability;
        this.populationSize = populationSize;
        this.fitnessPerIteration = new ArrayList<>();
        this.programFile = programFile;
        this.maxIterations = maxIterations;
    }


    public String getProgramFile(){
        return programFile;
    }

    public int getMaxIterations(){
        return maxIterations;
    }

    public double getCrossoverProbability() {
        return crossoverProbability;
    }

    public void setCrossoverProbability(double crossoverProbability) {
        this.crossoverProbability = crossoverProbability;
    }

    public double getMutationProbability() {
        return mutationProbability;
    }

    public void setMutationProbability(double mutationProbability) {
        this.mutationProbability = mutationProbability;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }

    public List<Double> getFitnessPerIteration() {
        return fitnessPerIteration;
    }

    public void addFitness(double fitness) {
        this.fitnessPerIteration.add(fitness);
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }

    @Override
    public String toString() {
        return "GeneticAlgorithmRun{" +
                ", crossoverProbability=" + crossoverProbability +
                ", mutationProbability=" + mutationProbability +
                ", populationSize=" + populationSize +
                ", fitnessPerIteration=" + fitnessPerIteration +
                ", executionTime=" + executionTime +
                '}';
    }
}

