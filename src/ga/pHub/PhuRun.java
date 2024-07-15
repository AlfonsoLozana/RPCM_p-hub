
package ga.pHub;
import java.io.EOFException;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ga.generic.DefaultConfig;
import ga.generic.ExeInstance;
import ga.generic.GeneticAlgorithmRun;
import ga.pHub.interfaces.PhubInstance;
import ga.ssGA.Individual;
import ga.ssGA.Problem;

public class PhuRun {
  
  public GeneticAlgorithmRun run(GeneticAlgorithmRun run) throws Exception{

    PhubParser parser = new PhubParser();
    // Parser file
    PhubInstance instance = parser.parse("/RPCM_p-hub/resources/" + run.getProgramFile());
    if (instance == null) {
      throw new Exception("Exception: fichero no encontrados");
    }

    //Medicion de tiempos
    long startTime = System.currentTimeMillis();

    

    //Inicialización de clases
    Encoder encoder = new Encoder(instance);
    Problem problem = new ProblemPhub(instance,encoder);
    ExeInstance<PhubInstance> executer = new ExeInstance<PhubInstance>();

    double targetFitness = 0;

    //Configuración de parámetros
    DefaultConfig config = new DefaultConfig(instance.getNumberOfNodes(),instance.getBits(),run.getPopulationSize(),run.getCrossoverProbability(),run.getMaxIterations(), run.getMutationProbability(), targetFitness);

   
    String[] args = {};
    Individual individual =  executer.Execute(problem, config,run, args);
    System.out.println("Solution Found in "+ problem.get_fitness_counter()+ " evaluations");
    int[] decode = encoder.decode(individual);
    for(int i = 0 ; i < decode.length ; i++){
      System.out.print(decode[i] + " ");
    }
    System.out.println(" ");
    System.out.println(individual.get_fitness());
    long endTime = System.currentTimeMillis();
    run.setExecutionTime(endTime - startTime);
    
    return run;
  }

}
