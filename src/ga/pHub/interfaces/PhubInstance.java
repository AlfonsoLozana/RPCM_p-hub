package ga.pHub.interfaces;

import ga.generic.intrafece.Instance;

public interface PhubInstance extends Instance{
    public int getNumberOfNodes();
    public int getNumberOfHubs();
    public int getBits();
    public double[][] getFlows();
    public double[][] getCoordinates();
    public double getCollectionCost();
    public double getTransferCost();
    public double getDistributionCost();
}