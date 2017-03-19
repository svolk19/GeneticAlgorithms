/**
 * Created by sam on 3/6/2017.
 */

package knapsack;

import java.util.ArrayList;

public class KnapsackGenetic {

    private static final int weightCapacity = 750;

    static final int chromLength = 15;

    public static final int[] solution = {1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 1, 1};

    private static final int[] weights = {70, 73, 77, 80, 82, 87, 90, 94, 98, 106, 110, 113, 115, 118, 120};

    private static final int[] profits = {135, 139, 149, 150, 156, 163, 173, 184, 192, 201, 210, 214, 221, 229, 240};

    private static int getTotalWeight(int[] chrom){
        //the total weight of the chromosome representation of a knapsack solution
        int chromWeightTotal = 0;

        //add weights for chrom
        for (int i = 0; i < chromLength; i++){
            if (chrom[i] == 1){
                chromWeightTotal += weights[i];
            }
        }
        return chromWeightTotal;
    }

    static int getTotalValue(int[] chrom){

        //the total value of the chromosome representation of a knapsack solution
        int chromValueTotal = 0;

        //add values for chrom
        for (int i = 0; i < chromLength; i++){
            if (chrom[i] == 1){
                chromValueTotal += profits[i];
            }
        }
        return chromValueTotal;
    }

    static int[][] fitness(int[][] population){

        int[][] valueList = new int[population.length][2];

        System.out.println("here: 1");

        for (int i = 0; i < population.length; i++){
            if (getTotalWeight(population[i]) > weightCapacity){
                valueList[i][0] = -1;
                valueList[i][1] = i;
            }else{
                valueList[i][0] = getTotalValue(population[i]);
                valueList[i][1] = i;
            }
        }

        //indicates whether an element has been switched
        boolean switchFlag = true;

        System.out.println("here: 2");
        //sorts the values by switching elements
        while (true) {
            for (int i = 0; i < valueList.length - 1; i++) {
                if (valueList[i][0] < valueList[i + 1][0]) {
                    switchFlag = false;
                    int[] smallerValue = valueList[i];
                    int[] largerValue = valueList[i + 1];

                    valueList[i] = largerValue;
                    valueList[i + 1] = smallerValue;
                }
            }
            if (switchFlag){
                break;
            }
        }

        //final sorted chrom list
        int[][] ratedPopList = new int[population.length][chromLength];

        System.out.println("here: 3");
        //assignes sorted value indexes to chromosomes and places them in final sorted chrom
        for (int i = 0; i < valueList.length; i++){
            ratedPopList[i] = population[valueList[i][1]];
        }

        return ratedPopList;

    }

    static ArrayList<ArrayList> getScaledFitnesses(int[][] population){
        int[][] valueList = new int[population.length][2];
        int totalPopValue = 0;
        int chromValue = 0;


        for (int i = 0; i < population.length; i++){
            if (getTotalWeight(population[i]) <= weightCapacity){
                chromValue = getTotalValue(population[i]);
                valueList[i][0] = chromValue;
                valueList[i][1] = i;
                totalPopValue += chromValue;
            }
        }

        ArrayList<ArrayList> scaledFitnesses = new ArrayList();
        ArrayList smallArray = new ArrayList();

        for (int i = 0; i < valueList.length; i++){
            if (valueList[i][0] != -1){
                smallArray.add(valueList[i][0] / totalPopValue);
                smallArray.add(valueList[i][1]);
            }
        }

        return scaledFitnesses;

    }


}

