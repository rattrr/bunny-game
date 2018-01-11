import java.io.Serializable;

public class MapObjectBlueprint implements Serializable{
    private MapObject type;
    private double x;
    private double y;

    public MapObjectBlueprint(MapObject type, double x, double y){

        this.type = type;
        this.x = x;
        this.y = y;
    }

    public MapObject getType() {
        return type;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
