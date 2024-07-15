package ga.pHub;

import java.util.ArrayList;

import ga.pHub.interfaces.PhubInstance;
import ga.ssGA.Individual;
import ga.ssGA.Problem;

public class ProblemPhub extends Problem {

    private PhubInstance instance;
    private Encoder endoder;

    public ProblemPhub(PhubInstance instance, Encoder endoder) {
        super();
        this.instance = instance;
        this.endoder = endoder;
    }


    @Override
    public double Evaluate(Individual indiv) {
        double fitness = 0;
        indiv.set_fitness(fitness);
        fitness = isAllValuesValid(indiv);
        if(fitness == 0){
            
            fitness = 0 - calculateCost(endoder.decode(indiv));
        }
        indiv.set_fitness(fitness);
        return fitness;
    }

    private int isAllValuesValid(Individual indiv){
        int[] predecodeInfo = endoder.predecode(indiv);
        int max_hub = instance.getNumberOfHubs();
        for(int i=0; i< predecodeInfo.length; i++){
            if(predecodeInfo[i] == 0)
                max_hub -=1;
            if(predecodeInfo[i] > instance.getNumberOfHubs())
                max_hub -= instance.getNumberOfHubs() * 2;
        }

        if(max_hub > 0)
            max_hub = -max_hub;
        
        return max_hub * 100000;
    }

    
    //Calculate cost
    private double calculateCost(int[] decodeInfo){
        return calculateCollectionCost(decodeInfo) + calculateTransferCost(decodeInfo) + calculateDistributionCost(decodeInfo);
    }


    //Colection cost
    private double calculateCollectionCost(int[] decodeInfo) {
        double cost = 0.0;
        int n = instance.getNumberOfNodes();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if(decodeInfo[i] -1 == i) continue;
                double distance = euclideanDistance(instance.getCoordinates()[i], instance.getCoordinates()[decodeInfo[i] -1]);
                cost += instance.getFlows()[i][j] * instance.getCollectionCost() * distance;
            }
        }

        return cost;
    }

    private double calculateTransferCost(int[] decodeInfo) {
        double cost = 0.0;
        int n = instance.getNumberOfNodes();
        double[][] hubFlows = new double[n][n]; 

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int hubI = decodeInfo[i] - 1;
                int hubJ = decodeInfo[j] - 1;
                hubFlows[hubI][hubJ] += instance.getFlows()[i][j];
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j && decodeInfo[i] != decodeInfo[j]) { 
                    double distance = euclideanDistance(instance.getCoordinates()[decodeInfo[i]-1], instance.getCoordinates()[decodeInfo[j]-1]);
                    cost += hubFlows[i][j] * instance.getTransferCost() * distance;
                }
            }
        }

        return cost;
    }

    private double calculateDistributionCost(int[] decodeInfo) {
        double cost = 0.0;
        int n = instance.getCoordinates().length;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if(decodeInfo[i] -1 == i) continue;
                double distance = euclideanDistance(instance.getCoordinates()[decodeInfo[j]-1], instance.getCoordinates()[j]);
                cost += instance.getFlows()[i][j] * instance.getDistributionCost() * distance;
            }
        }

        return cost;
    }

    //Calculate distance between two nodes
    private double euclideanDistance(double[] point1, double[] point2) {
        return Math.sqrt(Math.pow(point2[0] - point1[0], 2) + Math.pow(point2[1] - point1[1], 2)) / 10000;
    }
    
}
