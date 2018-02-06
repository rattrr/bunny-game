package gui;

import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Notification extends Label {
    public Notification(String message, double fontSize, double width, double height){
        setBackground(new Background(new BackgroundFill(Color.LIGHTCORAL, new CornerRadii(10), new Insets(-5))));
        setText(message);
        setLayoutX(10);
        setLayoutY(10);
        setMaxWidth(width);
        setMaxHeight(height);
        setWrapText(true);
        setMaxHeight(200);
        setFont(new Font(fontSize));
        setTextFill(Color.BLACK);

    }

    public void displayAt(Pane pane, double seconds){
        pane.getChildren().add(this);
        new AnimationTimer(){
            long startTime = 0;
            @Override
            public void handle(long now) {
                if(startTime == 0){
                    startTime = now;
                }
                if(now - startTime > ((long) (1000000000) * seconds)){
                    pane.getChildren().remove(Notification.this);
                    this.stop();
                }
            }


        }.start();
    }

    public void displayAt(Group group, double seconds){
        group.getChildren().add(this);
        new AnimationTimer(){
            long startTime = 0;
            @Override
            public void handle(long now) {
                if(startTime == 0){
                    startTime = now;
                }
                if(now - startTime > ((long) (1000000000) * seconds)){
                    group.getChildren().remove(Notification.this);
                    this.stop();
                }
            }


        }.start();
    }
}
