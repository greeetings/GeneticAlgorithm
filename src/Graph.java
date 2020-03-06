import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Graph extends JPanel {

    List<Double> points = new ArrayList<>();
    int count;
    private static JFrame window;


    public Graph(List<Double> points , int count) {
        this.points = points;
        this.count = count;
    }

    private static final long serialVersionUID = 1L;
    private int labelPadding = 12;
    /**change the line color to the best you want;*/
    private Color lineColor = new Color(5,200,150);
    private Color fitnessColor = new Color(100,100,100);
    private Color pointColor = new Color(255,0,255 );
    private static final Stroke GRAPH_STROKE = new BasicStroke(3f);
    float[] dashl = {5,5};
    private   Stroke FITNESS_STROKE = new BasicStroke(0.5f,BasicStroke.CAP_ROUND,BasicStroke.JOIN_BEVEL,10,dashl,0);
    private static int pointWidth = 10;


    //javaxy3d
    //sequate values для excel


    /**
     * Math_Graph is a constructor method
     * @returns List scores;
     */


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);




        //Точки для графика целевой функции и фитнесс функции
        List<Point> graphPoints = new ArrayList<>();
        List<Point> fitnessPoints = new ArrayList<>();

        for (int i = 0; i < 1000; i+=1) {
            double tX = -5 + (i * 1.0 / 100);
            tX = -tX;
            double tY = 0.1*tX - 1.7*Math.abs(Math.sin(5.8*tX))*Math.cos(3.2*tX);
            double fY = 2 - tY;
            tY = -tY;
            fY = -fY;

            int x1 = (int) ( ( Math.abs(-5 - tX) ) * 80);
            int y1 = (int)  (tY * 100);

            int y2 = (int)  (fY * 100); //фитнесс


            graphPoints.add(new Point(x1, y1 + 400));
            fitnessPoints.add(new Point(x1, y2 + 400));
        }

        //Точки особей популяции

        List<Point> populationPoints = new ArrayList<>();
        for (int i = 0; i < 100; i+=2) {
            double tX = points.get(i);
            double tY = points.get(i+1);
            tY = -tY;
            int x1 = (int) ( ( Math.abs(-5 - tX) ) * 80);
            int y1 = (int)  (tY * 100);
            populationPoints.add(new Point(x1, y1 + 400));
        }





        g2.drawLine(400,0,400,800);
        g2.drawLine(0,400,800,400);





        //Разметка оси Y
        for (int i = 100 ; i <= 700 ; i += 100)
            g2.drawLine(390, i, 410, i);

        g2.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g2.drawString("3",380,100);
        g2.drawString("2",380,200);
        g2.drawString("1",380,300);
        g2.drawString("-1",380,500);
        g2.drawString("-2",380,600);
        g2.drawString("-3",380,700);

        //Разметка оси X
        for (int i = 0; i <=800 ; i+=80) {
            g2.drawLine(i, 390, i, 410);
            int temp = i / 80 - 5;
            g2.drawString(""+temp,i,420);
        }



        //рисуем график
        Stroke oldStroke = g2.getStroke();
        g2.setColor(lineColor);
        g2.setStroke(GRAPH_STROKE);
        for (int i = 0; i < graphPoints.size() - 1; i++) {
            int x1 = graphPoints.get(i).x;
            int y1 = graphPoints.get(i).y;
            int x2 = graphPoints.get(i + 1).x;
            int y2 = graphPoints.get(i + 1).y;
            g2.drawLine(x1, y1, x2, y2);
        }

        g2.setColor(fitnessColor);
        g2.setStroke(FITNESS_STROKE);

        for (int i = 0; i < fitnessPoints.size() - 1; i++) {
            int x1 = fitnessPoints.get(i).x;
            int y1 = fitnessPoints.get(i).y;
            int x2 = fitnessPoints.get(i + 1).x;
            int y2 = fitnessPoints.get(i + 1).y;
            g2.drawLine(x1, y1, x2, y2);
        }


        //Рисуем точки
        g2.setStroke(oldStroke);
        g2.setColor(pointColor);
        for (int i = 0; i < populationPoints.size(); i++) {
            int x = populationPoints.get(i).x - pointWidth / 2;
            int y = populationPoints.get(i).y - pointWidth / 2;
            int ovalW = pointWidth;
            int ovalH = pointWidth;
            g2.fillOval(x, y, ovalW, ovalH);
        }

        g2.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        g2.setColor(Color.BLACK);
        g2.drawString("Счетчик: "+count, 20,30);

    }



    /* creating the method createAndShowGui in the main method, where we create the frame too and pack it in the panel*/
    public static void createAndShowGui(List<Double> points, int count) {
        /* Main panel */
        Graph mainPanel = new Graph(points,count);
        mainPanel.setPreferredSize(new Dimension(800, 800));
        /* creating the frame */
        //JFrame frame = new JFrame("Sample Graph");
        JFrame frame = Singleton.getInstance();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }




}
