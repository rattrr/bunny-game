package gui;

import map.GameMap;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

class MainWindow extends Stage {
    private double sceneWidth = 800;
    private double sceneHeight = 400;

    void initMenu(){
        StackPane menuRoot = new StackPane();
        MenuScene menuScene = new MenuScene(menuRoot, this, sceneWidth, sceneHeight);
        setScene(menuScene);
        show();
    }

    void initGame(){
        StackPane gameRoot = new StackPane();
        GameMap gameMap = new GameMap(2000, 400);
        GameScene gameScene = new GameScene(gameRoot, this, sceneWidth, sceneHeight, gameMap);
        setScene(gameScene);
        show();
    }

    void initMapBuilder(){
        ScrollPane mapBuilderRoot = new ScrollPane();
        MapBuilderScene mapBuilderScene = new MapBuilderScene(mapBuilderRoot, this, sceneWidth, sceneHeight+20);
        setScene(mapBuilderScene);
        show();
    }
}
