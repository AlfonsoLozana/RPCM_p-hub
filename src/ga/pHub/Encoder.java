package ga.pHub;

import ga.pHub.interfaces.PhubInstance;
import ga.ssGA.Individual;

public class Encoder {
    PhubInstance instance;

    public Encoder(PhubInstance instance) {
        this.instance = instance;
    }

    public int[] predecode(Individual indiv){
        int[] decode = new int[instance.getNumberOfNodes()];
        int index = 0;
        for(int i = 0 ; i < indiv.get_length() ; i+=instance.getBits()){
            byte[] binary = new byte[instance.getBits()];
            for(int j = 0 ; j < instance.getBits() ; j++){
                binary[instance.getBits()- 1- j] = indiv.get_allele(i+j);
            }
            decode[index] = convertToDecimal(binary);
            index++;
        }
        return decode;
    }

    public int[] decode(Individual indiv){
        int[] decode = new int[instance.getNumberOfNodes()];
        int[] precode = predecode(indiv);
        int[] hub = new int[instance.getNumberOfHubs()];
        int numHub = 0;

        for(int i = 0; i < precode.length ; i++){
            if(precode[i] == 0){
                hub[numHub] = i + 1;
                numHub++;
            }
        }

        for(int i = 0; i < precode.length ; i++){
            if(precode[i] == 0){
                decode[i] = i + 1;
            }else{
                decode[i] = hub[precode[i]-1];
            }
        }
        return decode;
    }

    private int convertToDecimal(byte[] binary){
        int decimal = 0;
        for (int i = 0; i < binary.length; i++) {
            decimal += binary[i] * Math.pow(2, i);
        }
        return decimal;
    }
}
