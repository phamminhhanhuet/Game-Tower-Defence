package tower_game.gametile;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

public class GameTile {
    private int x;
    private int y;
    Image image;

    public GameTile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }
    
    public void draw(Graphics2D g2d) {
        g2d.drawImage(image, x, y, 50, 50, null);
    }
    
    public Rectangle getRect(){
        int w = image.getWidth(null);
        int h = image.getHeight(null);
        Rectangle rectangle = new Rectangle(x,y,w,h);
        return rectangle;
    }
}
