import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
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
