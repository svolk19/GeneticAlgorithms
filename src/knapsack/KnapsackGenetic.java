/*
 * Created by sam on 3/6/2017.
 */

package knapsack;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;

class KnapsackGenetic {
    /*
    Knapsack problem data set
    solution chromosome: {1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 1, 1}
    solution value: 1458
    solution weight: 749
     */

    private static final int weightCapacity = 750;

    static final int chromLength = 15;

    private static final int[] weights = {70, 73, 77, 80, 82, 87, 90, 94, 98, 106, 110, 113, 115, 118, 120};

    private static final int[] profits = {135, 139, 149, 150, 156, 163, 173, 184, 192, 201, 210, 214, 221, 229, 240};

    static int getTotalWeight(ArrayList chrom){
        //the total weight of the chromosome representation of a knapsack solution
        int chromWeightTotal = 0;

        //add weights for chrom
        for (int i = 0; i < chromLength; i++){
            if (chrom.get(i).equals(1)){
                chromWeightTotal += weights[i];
            }
        }
        return chromWeightTotal;
    }

    static int getTotalValue(ArrayList chrom){

        //the total value of the chromosome representation of a knapsack solution
        int chromValueTotal = 0;

        //add values for chrom
        for (int i = 0; i < chromLength; i++){
            if (chrom.get(i).equals(1)){
                chromValueTotal += profits[i];
            }
        }
        return chromValueTotal;
    }

    static String chromToString(ArrayList chrom){

        String chromString = "";
        for (Object i: chrom){
            chromString += (int) i;
        }

        return chromString;
    }

    static boolean survive(ArrayList chrom){
        return getTotalWeight(chrom) <= weightCapacity;
    }

    static ArrayList betterParent(ArrayList parentA, ArrayList parentB){

        //get chromosome attributes for parents A and B
        int aValue = getTotalValue(parentA);
        int bValue = getTotalValue(parentB);
        int aWeight = getTotalWeight(parentA);
        int bWeight = getTotalWeight(parentB);

        if (aWeight > weightCapacity){
            aWeight = -1;
        }

        if (bWeight > weightCapacity){
            bWeight = -1;
        }

        if (aWeight != -1 && bWeight != -1){
            if (aValue > bValue){
                return parentA;
            }else{
                return parentB;
            }
        }

        if (aWeight == -1 && bWeight == -1){
            if (aValue > bValue){
                return parentA;
            }else{
                return parentB;
            }
        }

        if (aWeight != -1){
            return parentA;
        }else{
            return parentB;
        }

    }

    static ArrayList<ArrayList> fitness(ArrayList<ArrayList> population){

        //used to rank and store ranked values of population
        ArrayList<ArrayList> valueList = new ArrayList<>();

        for (int i = 0; i < population.size(); i++){
            ArrayList<Integer> valueListElement = new ArrayList<>();
            if (getTotalWeight(population.get(i)) > weightCapacity){
                valueListElement.add(-1);
                valueListElement.add(i);
                valueList.add(valueListElement);
            }else{
                valueListElement.add(getTotalValue(population.get(i)));
                valueListElement.add(i);
                valueList.add(valueListElement);
            }
        }

        //indicates whether an element has been switched
        boolean switchFlag;

        //sorts the values by switching elements
        while (true) {
            switchFlag = true;
            for (int i = 0; i < valueList.size() - 1; i++) {
                if ((int) valueList.get(i).get(0) < (int) valueList.get(i + 1).get(0)){
                    switchFlag = false;
                    ArrayList smallerValue = valueList.get(i);
                    ArrayList largerValue = valueList.get(i + 1);

                    valueList.set(i, largerValue);
                    valueList.set(i + 1, smallerValue);
                }
            }
            if (switchFlag){
                break;
            }
        }

        //final sorted chrom list
        ArrayList<ArrayList> ratedPopList = new ArrayList<>();

        //assignes sorted value indexes to chromosomes and places them in final sorted chrom
        for (ArrayList i: valueList){
            ratedPopList.add(population.get((int) i.get(1)));
        }

        return ratedPopList;

    }

    static ArrayList<ArrayList> getScaledFitnesses(ArrayList<ArrayList> population){

        ArrayList<ArrayList> valueList = new ArrayList<>();

        int totalPopValue = 0;
        int chromValue;

        for (int i = 0; i < population.size(); i++){
            if (getTotalWeight(population.get(i)) <= weightCapacity){
                ArrayList<Integer> valueListElement = new ArrayList<>();
                chromValue = getTotalValue(population.get(i));
                valueListElement.add(chromValue);
                valueListElement.add(i);
                totalPopValue += chromValue;
                valueList.add(valueListElement);
            }
        }

        ArrayList<ArrayList> scaledFitnesses = new ArrayList<>();

        for (ArrayList i: valueList){
            if ( ! i.get(0).equals(-1)){
                ArrayList scaledFitnessElement = new ArrayList();
                scaledFitnessElement.add((double) (int) i.get(0) / totalPopValue);
                scaledFitnessElement.add(i.get(1));
                scaledFitnesses.add(scaledFitnessElement);
            }
        }

        return scaledFitnesses;

    }

}

