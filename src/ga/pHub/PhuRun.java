
package ga.pHub;
import java.io.EOFException;
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
    List<PhubInstance> instances = parser.parse("/RPCM_p-hub/resources/" + run.getProgramFile());
    if (instances == null) {
      throw new Exception("Hola");
    }


    for (PhubInstance instance : instances) {
      long startTime = System.currentTimeMillis();
      DefaultConfig config = new DefaultConfig(instance.getNumberOfNodes(),instance.getBits(),run.getPopulationSize(),run.getCrossoverProbability(),run.getMaxIterations(), run.getMutationProbability());
      System.out.println(config.getMutationProbability());
      Encoder encoder = new Encoder(instance);
      Problem problem = new ProblemPhub(instance,encoder);
      problem.set_target_fitness(0);
      ExeInstance<PhubInstance> executer = new ExeInstance<PhubInstance>();
      String[] a = {};
      Individual individual =  executer.Execute(problem, config,run, a);
      System.out.println("Solution: ");
      int[] decode = encoder.decode(individual);
      System.out.println("decode: ");
      for(int i = 0 ; i < decode.length ; i++){
        System.out.print(decode[i] + " ");
      }
      System.out.println(" ");
      System.out.println(individual.get_fitness());
      long endTime = System.currentTimeMillis();
      run.setExecutionTime(endTime - startTime);
    }
    return run;
  }

}
