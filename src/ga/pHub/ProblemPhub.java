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
        System.err.println(fitness);
        if(fitness == 0){
            
            fitness = 0 - calculateCost(endoder.decode(indiv));
            //System.out.println("Bien: " + fitness);
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

        // Asumiendo que decodeInfo es un objeto que se pasa como argumento
        // y que las funciones calculateCollectionCost, calculateTransferCost
        // y calculateDistributionCost están correctamente definidas

        double collectionCost = calculateCollectionCost(decodeInfo);
        double transferCost = calculateTransferCost(decodeInfo);
        double distributionCost = calculateDistributionCost(decodeInfo);

       System.out.println("Collection Cost: " + collectionCost);
       System.out.println("Transfer Cost: " + transferCost);
       System.out.println("Distribution Cost: " + distributionCost);

        double totalCost = collectionCost + transferCost + distributionCost;
        return totalCost;

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
        double[][] hubFlows = new double[n][n]; // Matriz para almacenar los flujos entre hubs

        // Calculando los flujos totales entre hubs
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int hubI = decodeInfo[i] - 1;
                int hubJ = decodeInfo[j] - 1;
                hubFlows[hubI][hubJ] += instance.getFlows()[i][j];
            }
        }

        // Calculando el costo de transferencia entre hubs
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j) { // Solo calculamos costos entre diferentes hubs
                    double distance = euclideanDistance(instance.getCoordinates()[i], instance.getCoordinates()[j]);
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
