import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class MenuScene extends Scene {
    public MenuScene(StackPane root, MainWindow stage, double width, double height) {
        super(root, width, height);
        root.setPrefSize(width, height);
        VBox menu = new VBox();
        menu.setSpacing(20);

        Text bunnyWorld = new Text("Bunny World!");
        bunnyWorld.setFill(Color.WHITE);
        bunnyWorld.setStroke(Color.HOTPINK);
        bunnyWorld.setFont(Font.font(80));

        Background background = new Background(width, height);
        Text start = new Text("Start");
        start.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.initGame();
            }
        });


        Text mapBuilder = new Text("Map Builder");
        mapBuilder.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.initMapBuilder();
            }
        });

        Text about = new Text("About");

        setTextsStyle(start, mapBuilder, about);

        menu.setAlignment(Pos.CENTER);
        menu.getChildren().addAll(bunnyWorld, start, mapBuilder, about);
        root.getChildren().addAll(background.getBackground(), menu);
        StackPane.setAlignment(menu, Pos.CENTER);
    }

    private void setTextsStyle(Text... texts){
        for(Text text: texts){
            text.setFill(Color.LIGHTPINK);
            text.setStroke(Color.HOTPINK);
            text.setFont(Font.font(40));
            text.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    text.setFont(Font.font(50));
                }
            });

            text.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    text.setFont(Font.font(40));
                }
            });
        }
    }
}
