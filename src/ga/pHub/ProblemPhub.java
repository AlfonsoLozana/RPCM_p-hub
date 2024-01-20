package ga.pHub;

import ga.ssGA.Individual;
import ga.ssGA.Problem;

public class ProblemPhub extends Problem {

    @Override
    public double Evaluate(Individual Indiv) {
        double fitness = 0.0;
        //Calculate fitness for phub problem
       
        
        Indiv.set_fitness(fitness);
        return fitness;
    }
    
}
