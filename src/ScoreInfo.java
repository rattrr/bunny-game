import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ScoreInfo extends Text {
    private int counter = 0;
    public ScoreInfo(double x, double y){
        super(x, y, "0");
        setFont(Font.font(48));
        setFill(Color.WHITE);
        setStroke(Color.BLACK);
    }

    public void increment(){
        counter++;
        setText(String.valueOf(counter));
    }
}
