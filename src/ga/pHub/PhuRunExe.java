
package ga.pHub;
import java.util.ArrayList;
import ga.generic.GeneticAlgorithmRun;

public class PhuRunExe {
  
  public static void main(String args[]) throws Exception {
    String[] files = {"phub1.txt"};
    double[] mutationProbabilities = {0.01}; //, 0.02, 0.03, 0.04, 0.05, 0.05};
    double[] crossoverProbabilities = {0.5}; //,, 0.6, 0.7, 0.8, 0.9};
    int maxIterations = 100000;
    int populationSize = 512; 
    int interations = 1;

    PhuRun runner = new PhuRun();
    ArrayList<GeneticAlgorithmRun> runs =  new ArrayList<GeneticAlgorithmRun>();

    for (String file : files) {
        for (double mutationProb : mutationProbabilities) {
            for (double crossoverProb : crossoverProbabilities) {
                for(int i=0 ; i < interations; i++)
                  runs.add(runner.run(new GeneticAlgorithmRun(crossoverProb, mutationProb, populationSize, file,maxIterations)));
            }
        }
    }
}
}
