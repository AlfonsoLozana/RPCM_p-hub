package ga.pHub;

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
        double fitness = 0 - Double.MAX_VALUE;
        indiv.set_fitness(fitness);

        indiv.get_chromosome().print();
        int max_hub = instance.getNumberOfHubs();
        System.out.println(indiv.get_length());
        for(int i=0; i<indiv.get_length(); i++)
            if(indiv.get_allele(i)==1)
                max_hub-=1;
        //int[] decodeInfo = endoder.decode(indiv);

        //if(!isAllValuesValid(decodeInfo)) return fitness;
        //if(!isValuesSameToHubs(decodeInfo, instance.getNumberOfHubs())) return fitness;

        //fitness = 0 - calculateCost(decodeInfo);
        //
        if(max_hub > 0)
            max_hub = -max_hub;
        indiv.set_fitness(max_hub);
        return max_hub;
    }

    private boolean isAllValuesValid(int[] decodeInfo){
        for(int i = 0 ; i < decodeInfo.length ; i++){
            if(decodeInfo[i] < 0 || decodeInfo[i] >= instance.getNumberOfNodes()){
                return false;
            }
        }
        return true;
    }

    //Calculate cost
    private double calculateCost(int[] decodeInfo){
        return calculateColectionCost(decodeInfo) + calculateTransferCost(decodeInfo) + calculateDistributionCost(decodeInfo);
    }


    //Colection cost
    private double calculateColectionCost(int[] decodeInfo){
        double cost = 0;
        for(int i = 0 ; i < decodeInfo.length ; i++){
            cost += calculateDistance(i, decodeInfo[i]) * instance.getCollectionCost() * calculateFlowCost(i, decodeInfo[i]);
        }
        return cost;
    }

    private double calculateTransferCost(int[] decodeInfo){
        double cost = 0;
        for(int i = 0 ; i < decodeInfo.length ; i++){
            cost += calculateDistance(i, decodeInfo[i]) * instance.getTransferCost() * calculateFlowCost(i, decodeInfo[i]);
        }
        return cost;
    }

    private double calculateDistributionCost(int[] decodeInfo){
        double cost = 0;
        for(int i = 0 ; i < decodeInfo.length ; i++){
            cost += calculateDistance(i, decodeInfo[i]) * instance.getDistributionCost() * calculateFlowCost(i, decodeInfo[i]);
        }
        return cost;
    }

    //Calculatge flow cost between two nodes
    private double calculateFlowCost(int node1, int node2){
        return instance.getFlows()[node1][node2] * calculateDistance(node1, node2) * instance.getDistributionCost();
    }

    //Calculate distribution cost



    //Calculate distance between two nodes
    private double calculateDistance(int node1, int node2){
        double x1 = instance.getCoordinates()[node1][0];
        double y1 = instance.getCoordinates()[node1][1];
        double x2 = instance.getCoordinates()[node2][0];
        double y2 = instance.getCoordinates()[node2][1];
        return Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2));
    }


    //Check if number o diferent values is equal to number of hubs
    private boolean isValuesSameToHubs(int[] decodeInfo, int numberOfHubs){
        //calculate number of diferent values
        int[] diferentValues = new int[instance.getNumberOfNodes()];
        return diferentValues.length == numberOfHubs;
    }
    
}
