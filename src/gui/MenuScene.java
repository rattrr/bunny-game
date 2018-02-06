package gui;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import map.Background;


class MenuScene extends Scene {
    MenuScene(StackPane root, MainWindow stage, double width, double height) {
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
        start.setOnMouseClicked(event -> stage.initGame());


        Text mapBuilder = new Text("Map Builder");
        mapBuilder.setOnMouseClicked(event -> stage.initMapBuilder());

        Text exit = new Text("Exit");
        exit.setOnMouseClicked(event -> Platform.exit());
        setTextsStyle(start, mapBuilder, exit);
        menu.setAlignment(Pos.CENTER);
        menu.getChildren().addAll(bunnyWorld, start, mapBuilder, exit);
        root.getChildren().addAll(background.getBackground(), menu);
        StackPane.setAlignment(menu, Pos.CENTER);
    }

    private void setTextsStyle(Text... texts){
        for(Text text: texts){
            text.setFill(Color.LIGHTPINK);
            text.setStroke(Color.HOTPINK);
            text.setFont(Font.font(40));
            text.setOnMouseEntered(event -> text.setFont(Font.font(50)));

            text.setOnMouseExited(event -> text.setFont(Font.font(40)));
        }
    }
}
