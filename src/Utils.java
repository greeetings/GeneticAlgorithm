import java.io.*;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class Utils {
    static double step = 0.00000953674;   // 10/2^20
    static final int n = 50;


    //Integer.parseInt( binNumber, 2);


    public static ArrayList<Individual> createFirstPopulation(int n) {  //n - количество особей
        ArrayList<Individual> population = new ArrayList<>();
        for (int i = 1; i <= n; i ++) {
            double temp = Math.pow(2,20);   // 2^20
            double tStep = temp / n;       // делим пространство на 50 равных частей
            population.add(new Individual(tStep * i));
        }
        return population;
    }



    public static ArrayList<Individual> crossing(ArrayList<Individual> population) {
        int n = population.size();
        ArrayList<Individual> result = population;

        for (int i = 0 ; i < n; i+=2) {
            if (Math.random() < 0.8 ) {
                String first = population.get(i).getGenotype();
                String second = population.get(i+1).getGenotype();
                int gap = (int) (Math.random() * 20) ;
                String child1 = "";
                String child2 = "";
                child1 += first.substring(0,gap);
                child1 += second.substring(gap,20);
                child2 += second.substring(0,gap);
                child2 += first.substring(gap,20);

                if (Math.random() < 0.15) {
                    char[] ch1 = child1.toCharArray();
                    char[] ch2 = child2.toCharArray();
                    child1 = "";
                    child2 = "";
                    int mutationGen = (int) (Math.random()*20);
                    ch1[mutationGen] = ch1[mutationGen] == 1 ? '0' : '1';
                    ch2[mutationGen] = ch2[mutationGen] == 1 ? '0' : '1';
                    for (int j = 0 ; j < 20; j++) {
                        child1 += ch1[j];
                        child2 += ch2[j];
                    }

                }

                Double chld1 =(double) Integer.parseInt( child1, 2);
                Double chld2 =(double) Integer.parseInt( child2, 2);
                result.add(new Individual(chld1) );
                result.add(new Individual(chld2) );
            }
        }
        return result;
    }


    public static ArrayList<Individual> reduction(ArrayList<Individual> population) {
        ArrayList<Individual> before = population;
        ArrayList<Individual> result = new ArrayList<>();
        ArrayList<Individual> losers = new ArrayList<>();
        before = population;
        Individual first;
        Individual second;

        for (int i = 0; i < before.size(); i+=2) {
            first = before.get(i);
            second = before.get(i+1);

            if (first.getFitnessFunc() > second.getFitnessFunc()) {
                result.add(first);
                losers.add(second);
            } else {
                result.add(second);
                losers.add(first);
            }
        }

        if (result.size() < n ) {
            int n = 50 - result.size();
            for (int j = 0; j < n * 2  ; j +=2) {
                first = losers.get(j);
                second = losers.get(j+1);
                if (first.getFitnessFunc() > second.getFitnessFunc()) {
                    result.add(first);
                } else {
                    result.add(second);
                }
            }
        }

        return result;
    }

    public static double calculateSummaryFitness(ArrayList<Individual> population) {
        double result = 0.0;
        for (int i = 0 ; i < n ; i++) {
            result += population.get(i).getFitnessFunc();
        }
        return result;
    }

    public static void printData(ArrayList<Individual> population) {
        String filePath = "D:/temp/test.csv";


        try (FileWriter wr = new FileWriter(filePath, true)) {

            StringBuilder sb = new StringBuilder();
            sb.append("Number of individual");
            sb.append(';');
            sb.append("Fenotype");
            sb.append(';');
            sb.append("Genotype");
            sb.append(';');
            sb.append("Target func");
            sb.append(';');
            sb.append("Fitness func");
            sb.append('\n');

            for (int i = 0 ; i < n; i++) {
                Individual ind = population.get(i);
                sb.append(i+1);
                sb.append(';');
                sb.append("" + String.format("%.2f",ind.getFenotype()) );
                sb.append(';');
                sb.append("" + ind.getGenotype());
                sb.append(';');
                sb.append("" + String.format("%.2f", ind.getTargetFunc()) );
                sb.append(';');
                sb.append("" + String.format("%.2f", ind.getFitnessFunc()) );
                sb.append('\n');
            }


            BufferedWriter bufferWriter = new BufferedWriter(wr);
            bufferWriter.write(sb.toString());
            bufferWriter.close();

            System.out.println("done!");

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }



    }

    public static void clearFile() {

        String filePath = "D:/temp/test.csv";

        try (FileWriter fstream1 = new FileWriter(filePath)) {
            BufferedWriter out1 = new BufferedWriter(fstream1); //  создаём буферезированный поток
            out1.write(""); // очищаем, перезаписав поверх пустую строку
            out1.close(); // закрываем
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void openExcel() {
        Runtime rt = Runtime.getRuntime();
        try {
            Process p = rt.exec("C:/Program Files/Microsoft Office/root/Office16/EXCEL.exe D:/temp/test.csv");
        } catch (IOException e) {
            System.out.println("troubles");
        }
    }




}
