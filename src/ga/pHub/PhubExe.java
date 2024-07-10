
package ga.pHub;
import java.util.List;

import ga.generic.DefaultConfig;
import ga.generic.ExeInstance;
import ga.pHub.interfaces.PhubInstance;
import ga.ssGA.Individual;
import ga.ssGA.Problem;

public class PhubExe {
  public static void main(String args[]) throws Exception {

    PhubParser parser = new PhubParser();
    // Parser file
    List<PhubInstance> instances = parser.parse(args.length > 0 ? args[0] : "/RPCM_p-hub/resources/phub10_2.txt");
    if (instances == null) {
      System.out.println("Error parsing file");
      return;
    }


    for (PhubInstance instance : instances) {


      DefaultConfig config = new DefaultConfig(instance.getNumberOfNodes(),instance.getBits(),512,0.8,500000);
      Encoder encoder = new Encoder(instance);
      Problem problem = new ProblemPhub(instance,encoder);
      problem.set_target_fitness(0);
      ExeInstance<PhubInstance> executer = new ExeInstance<PhubInstance>();
      Individual individual =  executer.Execute(problem, config, args);
      System.out.println("Solution: ");
      int[] decode = encoder.decode(individual);
      System.out.println("decode: ");
      for(int i = 0 ; i < decode.length ; i++){
        System.out.print(decode[i] + " ");
      }
      System.out.println(" ");
      System.out.println(individual.get_fitness());
    }
  }

}
