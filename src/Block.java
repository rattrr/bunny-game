import javafx.scene.image.Image;

public class Block implements Renderable {
    private int posX ;
    private int posY ;
    private Image img = new Image(this.getClass().getClassLoader().getResourceAsStream("img/grass.png"));

    public Block(int posX, int posY){
        this.posX = posX;
        this.posY = posY;
        System.out.println(img.getHeight());
    }


    @Override
    public int getPosX() {
        return this.posX;
    }

    @Override
    public int getPosY() {
        return this.posY;
    }

    @Override
    public Image getCurrentState() {
        return this.img;
    }
}
