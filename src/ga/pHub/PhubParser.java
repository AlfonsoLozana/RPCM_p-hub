package ga.pHub;
import java.nio.file.Files;
import java.nio.file.Paths;


import ga.pHub.interfaces.PhubInstance;

public class PhubParser {
    
    public PhubInstance parse(String path) {
        String[] lines = loadFile(path);

        if (lines == null)
            return null;

        int currentIndex = 0;


        try {
            // Lee el número de nodos
            int numberOfNodes = Integer.parseInt(lines[currentIndex++].trim());
            // Lee las coordenadas de los nodos
            double[][] nodeCoordinates = new double[numberOfNodes][2];
            for (int i = 0; i < numberOfNodes; i++) {
                String[] coordinates = lines[currentIndex++].split(" ");
                nodeCoordinates[i][0] = Double.parseDouble(coordinates[0]);
                nodeCoordinates[i][1] = Double.parseDouble(coordinates[1]);
            }

            // Lee los flujos entre nodos
            double[][] flows = new double[numberOfNodes][numberOfNodes];
            for (int i = 0; i < numberOfNodes; i++) {
                String[] flowValues = lines[currentIndex++].split(" ");
                for (int j = 0; j < numberOfNodes; j++) {
                    flows[i][j] = Double.parseDouble(flowValues[j]);
                }
            }

            // Lee el número de hubs y los costos
            int numberOfHubs = Integer.parseInt(lines[currentIndex++].trim());
            double collectionCost = Double.parseDouble(lines[currentIndex++].trim());
            double transferCost = Double.parseDouble(lines[currentIndex++].trim());
            double distributionCost = Double.parseDouble(lines[currentIndex++].trim());

            // Crea una instancia de PhubInstance y agrégala a la lista
            PhubInstance phubInstance = new PhubInstanceImpl(numberOfNodes, numberOfHubs,flows, nodeCoordinates, 
                    collectionCost, transferCost, distributionCost);
            return phubInstance;

        } catch (Exception e) {
            System.out.println("Error parsing file: " + e.getMessage());
        }

        return null;
    }

    private String[] loadFile(String path) {
        String[] lines = null;
        try {
            String currentPath = Paths.get("").toAbsolutePath().toString();
            lines = Files.readAllLines(Paths.get(currentPath + path)).toArray(new String[0]);
        } catch (Exception e) {
            System.out.println("Error loading file: " + e.getMessage());
        }

        return lines;
    }
}
