package ga.pHub;

import ga.pHub.interfaces.PhubInstance;

public class PhubInstanceImpl implements PhubInstance {

    private int numberOfNodes;
    private int numberOfHubs;
    private double[][] flows;
    private double[][] coordinates;
    private double collectionCost;
    private double transferCost;
    private double distributionCost;
    private int bits;

    public PhubInstanceImpl(int numberOfNodes, int numberOfHubs, double[][] flows,double[][] coordinates, double collectionCost,
            double transferCost, double distributionCost) {
        this.numberOfNodes = numberOfNodes;
        this.numberOfHubs = numberOfHubs;
        this.flows = flows;
        this.collectionCost = collectionCost;
        this.transferCost = transferCost;
        this.distributionCost = distributionCost;
        this.coordinates = coordinates;
        this.bits = calculateBits(numberOfHubs);
    }


    @Override
    public int getNumberOfNodes() {
        return numberOfNodes;
    }

    @Override
    public int getNumberOfHubs() {
        return numberOfHubs;
    }

    @Override
    public double[][] getFlows() {
        return flows;
    }

    @Override
    public double getCollectionCost() {
        return collectionCost;
    }

    @Override
    public double getTransferCost() {
        return transferCost;
    }

    @Override
    public double getDistributionCost() {
        return distributionCost;
    }

    @Override
    public double[][] getCoordinates() {
        return coordinates;
    }

    @Override
    public int getBits() {
        return bits;
    }


    private int calculateBits(int decimalNumber) {
        if (decimalNumber == 0) {
            return 1;
        }
        return (int) (Math.log(Math.abs(decimalNumber)) / Math.log(2)) + 1;
    }
    
}
