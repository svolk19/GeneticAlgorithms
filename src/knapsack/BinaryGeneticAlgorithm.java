/*
 * Created by sam on 3/16/2017.
 */

package knapsack;

import java.util.ArrayList;

public class BinaryGeneticAlgorithm {

    //define genetic algorithm parameters
    private static final boolean printValues = true;
    private static final int popSize = 50;
    private static final int chromLength = KnapsackGenetic.chromLength;
    private static final int generations = 1000;
    private static final int elitism = 3;
    private static final double crossRate = 1.0;
    private static final double mutationRate = 0.1;

    //define main
    public static void main(String[] args){
        runGeneticAlgorithm();
    }

    //GA body method
    private static void runGeneticAlgorithm(){

        //define initial population
        ArrayList<ArrayList> population = Util.generateRandomPop(popSize, chromLength);
        int genCount = 0;

        //GA body
        while (genCount < generations){

            ArrayList<ArrayList> newPop = new ArrayList<>();

            //following elitism, copy the most fit individuals into the new population
            ArrayList<ArrayList> sortedPop = KnapsackGenetic.fitness(population);
            for (int i = 0; i < elitism; i ++){
                newPop.add(sortedPop.get(i));
            }

            //cross until new population is full
            while (newPop.size() != popSize){

                //select two parents via roulette wheel
                ArrayList parentA = population.get(Util.rankSelection(KnapsackGenetic.getScaledFitnesses(population)));
                ArrayList parentB = population.get(Util.rankSelection(KnapsackGenetic.getScaledFitnesses(population)));

                //which parent is better
                ArrayList betterParent = KnapsackGenetic.betterParent(parentA, parentB);
                ArrayList worseParent = new ArrayList();

                if (betterParent == parentA){
                    worseParent = parentB;
                }else{
                    worseParent = parentA;
                }

                //if parents are the same, try again
                if (parentA.equals(parentB)){
                    continue;
                }

                //maybe cross two parents, evaluated stochastically via roulette wheel selection
                if (Util.getRandom() < crossRate){

                    //crossover to produce new offspring
                    ArrayList offspring = Util.crossover(betterParent, worseParent);

                    //maybe mutate offspring, depending on stochastic assessment
                    offspring = Util.mutate(offspring, mutationRate);
                    if (KnapsackGenetic.survive(offspring)) {
                        newPop.add(offspring);
                    }
                }else{
                    newPop.add(betterParent);
                }
            }

            if (printValues){
                System.out.println(KnapsackGenetic.getTotalValue(KnapsackGenetic.fitness(population).get(0)));
            }

            //reset the current generation as the one just created
            population = newPop;

            //index the number of passed generations
            genCount++;

        }

        //display the final solution
        ArrayList solutionChrom = KnapsackGenetic.fitness(population).get(0);
        System.out.println("***Solution generated***");
        System.out.printf("Greatest possible value: %d%n", KnapsackGenetic.getTotalValue(solutionChrom));
        System.out.printf("Solution weight: %d%n", KnapsackGenetic.getTotalWeight(solutionChrom));
        System.out.printf("Best chromosome: %s%n", KnapsackGenetic.chromToString(solutionChrom));

    }
}


