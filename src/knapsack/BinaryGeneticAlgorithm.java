/**
 * Created by sam on 3/16/2017.
 */

package knapsack;

public class BinaryGeneticAlgorithm {

    //define genetic algorithm parameters
    private static final int popSize = 50;
    private static final int chromLength = KnapsackGenetic.chromLength;
    private static final int generations = 1000;
    private static final int elitism = 3;
    private static final double crossRate = 0.5;
    private static final double mutationRate = 0.1;


    //define population
    private static int[][] population = Util.generateRandomPop(popSize, chromLength);

    //define main
    public static void main(String[] args){
        runGeneticAlgorithm();
    }

    //GA body method
    private static void runGeneticAlgorithm(){

        int[][] newPop = new int[popSize][chromLength];
        int genCount = 0;

        //GA body
        while (genCount < generations){
            
            //following elitism, copy the most fit individuals into the new population
            int[][] sortedPop = KnapsackGenetic.fitness(population);
            for (int i = 0; i < elitism; i ++){
                newPop[i] = sortedPop[i];
            }

            //for adding to the end of the new population later
            int nextIndex = elitism;

            //cross until new population is full
            while (newPop.length != popSize){

                //select two parents via roulette wheel
                int[] parentA = population[Util.rouletteSelection(KnapsackGenetic.getScaledFitnesses(population))];
                int[] parentB = population[Util.rouletteSelection(KnapsackGenetic.getScaledFitnesses(population))];

                //if parents are the same, try again
                if (parentA == parentB){
                    continue;
                }

                //maybe cross two parents, evaluated stochastically via roulette wheel selection
                if (Util.getRandom() < crossRate){
                    int[] offspring = Util.crossover(parentA, parentB);

                    //maybe mutate offspring, depending on stochastic assessment
                    offspring = Util.mutate(offspring, mutationRate);
                    if (KnapsackGenetic.survive(offspring)) {
                        newPop[nextIndex] = offspring;
                        nextIndex++;
                    }
                }
            }

            //display the value of the current best chromosome
            System.out.println(sortedPop[0]);

            //index the number of passed generations
            genCount++;
        }
    }

}
