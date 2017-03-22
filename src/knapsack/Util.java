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
    static ArrayList<ArrayList> generateRandomPop(int popSize, int chromLength){

        Random rand = new Random();
        ArrayList<ArrayList> population = new ArrayList<>();


        for (int i = 0; i < popSize; i++){
            ArrayList chrom = new ArrayList();
            for (int j = 0; j < chromLength; j++){
                chrom.add(rand.nextInt(2));
            }

            population.add(chrom);
        }

        return population;
    }

    //applies a single point crossover on two parents, produces a single offspring
    static ArrayList crossover(ArrayList parentA, ArrayList parentB){

        Random rand = new Random();

        ArrayList offspring = new ArrayList();
        int crossLocation =  rand.nextInt(parentA.size());
        for (int i = 0; i < crossLocation; i++){
            offspring.set(i, parentA.get(i));
        }
        for (int i = crossLocation; i < parentA.size(); i++){
            offspring.set(i, parentB.get(i));
        }

        return offspring;

    }

    //applies mutation to new offspring
    static ArrayList mutate(ArrayList offspring, double mutationRate){

        for (int i = 0; i < offspring.size(); i++){
            if (getRandom() < mutationRate){
                if (offspring.get(i).equals(0)){
                    offspring.set(i, 1);
                }else{
                    offspring.set(i, 0);
                }
            }
        }

        return offspring;
    }

    //generates an item index randomly, higher value items having a higher probability of selection
    static int rouletteSelection(ArrayList<ArrayList> scaledValueList){

        //Bug: values of roulette wheel are exceeding one


        //for debugging
//        for (int i = 0; i < scaledValueList.size(); i++){
//            System.out.println(scaledValueList.get(i).get(0));
//        }

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

        //for debugging
//        for (int i = 0; i < wheel.length; i++){
//            System.out.println(wheel[i][0]);
//            System.out.println(wheel[i][1]);
//        }

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
