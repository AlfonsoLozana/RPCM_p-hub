
package ga.pHub;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import ga.generic.GeneticAlgorithmRun;

public class PhuRunExe {
  
  public static void main(String args[]) throws Exception {
    String[] files = {"phub10_2.txt"};
    double[] mutationProbabilities = {0.1}; //, 0.02, 0.03, 0.04, 0.05, 0.05};
    double[] crossoverProbabilities = {0.7}; //,, 0.6, 0.7, 0.8, 0.9};
    int maxIterations = 100000;
    int populationSize = 512; 
    int interations = 30;

    PhuRun runner = new PhuRun();
    ArrayList<GeneticAlgorithmRun> runs =  new ArrayList<GeneticAlgorithmRun>();

    for (String file : files) {
        for (double mutationProb : mutationProbabilities) {
            for (double crossoverProb : crossoverProbabilities) {
                runs =  new ArrayList<GeneticAlgorithmRun>();
                for(int i=0 ; i < interations; i++){
                  runs.add(runner.run(new GeneticAlgorithmRun(crossoverProb, mutationProb, populationSize, file,maxIterations)));
                }
                save_data(runs,crossoverProb, mutationProb,file);
              }
            }
        }
    }

  private static void save_data(ArrayList<GeneticAlgorithmRun> runs, double crossoverProb ,double mutationProb,String file ){
    int maxIterations = runs.stream()
                .mapToInt(run -> run.getFitnessPerIteration().size())
                .max()
                .orElse(0);

        // Escribir los datos en un archivo .txt
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("GeneticAlgorithmRuns_"+file+".txt"))) {
            for (int i = 0; i < maxIterations; i++) {
                StringBuilder row = new StringBuilder();
                for (int j = 0; j < runs.size(); j++) {
                    GeneticAlgorithmRun run = runs.get(j);
                    if (i < run.getFitnessPerIteration().size()) {
                        row.append(-run.getFitnessPerIteration().get(i));
                    }
                    if (j < runs.size() - 1) {
                        row.append("\t");  // Usar tabulaciones para separar columnas
                    }
                }
                writer.write(row.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
  }
}
