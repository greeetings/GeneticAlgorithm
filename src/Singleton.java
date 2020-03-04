import javax.swing.*;

public class Singleton {
    private static JFrame window;
    private Singleton(){}

    public static JFrame getInstance(){ // #3
        if(window == null){		//если объект еще не создан
            window = new JFrame("Genetic algorithm");	//создать новый объект
        }
        return window;		// вернуть ранее созданный объект
    }
}
