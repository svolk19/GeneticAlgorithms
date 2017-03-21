/**
 * Created by sam on 3/16/2017.
 */

package knapsack;

import java.util.ArrayList;

public class BinaryGeneticAlgorithm {

    //define genetic algorithm parameters
    private static final int popSize = 50;
    private static final int chromLength = KnapsackGenetic.chromLength;
    private static final int generations = 1000;
    private static final int elitism = 3;
    private static final double crossRate = 0.5;
    private static final double mutationRate = 0.1;

    //define main
    public static void main(String[] args){
        runGeneticAlgorithm();
    }

    //GA body method
    private static void runGeneticAlgorithm(){

        //define initial population
        ArrayList<ArrayList> population = Util.generateRandomPop(popSize, chromLength);

//        //for debugging
//        for (ArrayList i: population){
//            for (Object j: i){
//                String chromString = "";
//                chromString += (int) j;
//                System.out.println(chromString);
//
//            }
//        }

        ArrayList<ArrayList> newPop = new ArrayList<>();
        int genCount = 0;

        //GA body
        while (genCount < generations){

            //following elitism, copy the most fit individuals into the new population
            ArrayList<ArrayList> sortedPop = KnapsackGenetic.fitness(population);
            for (int i = 0; i < elitism; i ++){
                newPop.add(i, sortedPop.get(i));
            }

            //for adding to the end of the new population later
            int nextIndex = elitism;

            //cross until new population is full
            while (newPop.size() != popSize){
                System.out.println("here");


                //select two parents via roulette wheel
                ArrayList parentA = population.get(Util.rouletteSelection(KnapsackGenetic.getScaledFitnesses(population)));
                ArrayList parentB = population.get(Util.rouletteSelection(KnapsackGenetic.getScaledFitnesses(population)));

                //if parents are the same, try again
                if (parentA.equals(parentB)){
                    continue;
                }

                //maybe cross two parents, evaluated stochastically via roulette wheel selection
                if (Util.getRandom() < crossRate){
                    ArrayList offspring = Util.crossover(parentA, parentB);

                    //maybe mutate offspring, depending on stochastic assessment
                    offspring = Util.mutate(offspring, mutationRate);
                    if (KnapsackGenetic.survive(offspring)) {
                        newPop.set(nextIndex, offspring);
                        nextIndex++;
                    }
                }
            }

            population = newPop;

            //display the value of the current best chromosome
            for (int j = 0; j < 50; j++){
                String solution = new String();
                for (Object i: sortedPop.get(j)){
                    solution += (int) i;
                }

                System.out.println(solution);
                solution = "";


            }



            //index the number of passed generations
            genCount++;
        }
    }

}
