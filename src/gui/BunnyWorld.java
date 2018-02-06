package gui;

import javafx.application.Application;
import javafx.stage.Stage;



public class BunnyWorld extends Application {

    private MainWindow stage = new MainWindow();


    public static void main(String [] argv){
        launch(argv);
    }

    public void start(Stage primaryStage) throws Exception {
        stage.setTitle("Bunny World!");
        stage.setResizable(false);
        stage.initMenu();
    }



}
