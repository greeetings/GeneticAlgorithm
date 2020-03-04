
public class Individual {
    static double step = 0.00000953674;
    private double number;

    public Individual(double number) {
        this.number = number;
    }

    public double getNumber() {
        return number;
    }

    public double getFenotype() {
        double result = -5 + step * number;
        return result;
    }

    public String getGenotype() {
        String result = "";
        int temp = (int) number;
        String binaryString = Integer.toBinaryString(temp);
        int size = binaryString.length();
        if (size < 20) {
            for (int i = 0; i < 20-size; i++)
                result+="0";
            result+= binaryString;
        } else
            result = binaryString;
        return result;
    }

    public double getTargetFunc() {
       double targetFunc =  0.1*getFenotype() - 1.7*Math.abs(Math.sin(5.8*getFenotype()))*Math.cos(3.2*getFenotype());
        return targetFunc;
    }

    public double getFitnessFunc() {

        return 5 - getTargetFunc();
    }
}
