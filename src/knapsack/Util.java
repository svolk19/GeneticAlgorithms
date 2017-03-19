/**
 * Created by sam on 3/16/2017.
 */

package knapsack;

import java.util.ArrayList;
import java.util.Random;

class Util {

    //return a random number greater than 0, less than one
    static double getRandom(){

        //generate random double
        Random rand = new Random();
        return rand.nextDouble();
    }

    //Stochastically generate an initial population
    static int[][] generateRandomPop(int popSize, int chromLength){

        Random rand = new Random();
        int[][] population = new int[popSize][chromLength];

        for (int i = 0; i < popSize; i++){
            for (int j = 0; j < chromLength; j++){
                population[i][j] = rand.nextInt(2);
            }
        }

        return population;
    }

    //applies a single point crossover on two parents, produces a single offspring
    static int[] crossover(int[] parentA, int[] parentB){

        Random rand = new Random();

        int[] offspring = new int[parentA.length];
        int crossLocation =  rand.nextInt(parentA.length);
        for (int i = 0; i < crossLocation; i++){
            offspring[i] = parentA[i];
        }
        for (int i = crossLocation; i < parentA.length; i++){
            offspring[i] = parentB[i];
        }

        return offspring;

    }

    //applies mutation to new offspring
    static int[] mutate(int[] offspring, double mutationRate){

        for (int i = 0; i < offspring.length; i++){
            if (getRandom() < mutationRate){
                if (offspring[i] == 0){
                    offspring[i] = 1;
                }else{
                    offspring[i] = 0;
                }
            }
        }

        return offspring;
    }

    //generates an item index randomly, higher value items having a higher probability of selection
    static int rouletteSelection(ArrayList<ArrayList> scaledValueList){

        //define roulette wheel
        double[][] wheel = new double[scaledValueList.size()][2];

        double startingPlace = 0;
        double endingPlace;
        for (int i = 0; i < scaledValueList.size(); i++){
            endingPlace = startingPlace + (double) scaledValueList.get(i).get(0);
            wheel[i][0] = startingPlace;
            wheel[i][1] = endingPlace;
            startingPlace = endingPlace;
        }

        double randChoice = getRandom();
        int valueIndex = 0;

        for (int i = 0; i < wheel.length; i++){
            if (randChoice >= wheel[i][0] && randChoice < wheel[i][1]){
                valueIndex = i;
            }
        }

        return (int) scaledValueList.get(valueIndex).get(1);
    }
}
