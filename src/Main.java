import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        final int n = 50;
        ArrayList<Individual> population = new ArrayList<>();
        double summaryFitness1 = 0;
        double summaryFitness2 ;
        double dF;
        int counter = 0;

        population = Utils.createFirstPopulation(n);
        Utils.clearFile();

            do {
                population = Utils.crossing(population);
                population = Utils.reduction(population);

                //Подсчет относительной погрешности между 2-мя поколениями
                summaryFitness2 = Utils.calculateSummaryFitness(population);
                dF = (summaryFitness2 - summaryFitness1 ) / summaryFitness2;
                summaryFitness1 = summaryFitness2;


                counter++;

                if (counter == 1 || counter == 3 ) {
                    Utils.printData(population);
                }

                List<Double> points = new ArrayList<>();
                for (int i = 0 ; i < n; i ++) {
                    points.add(population.get(i).getFenotype());
                    points.add(population.get(i).getTargetFunc());
                }

                Graph.createAndShowGui(points,counter);
                Thread.sleep(1000);
            } while ( (Math.abs(dF) > 0.001) && counter < 25 );


        System.out.println(counter);


        Utils.printData(population);
        Utils.openExcel();

    }
}
