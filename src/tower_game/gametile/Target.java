package tower_game.gametile;

import java.awt.Graphics2D;
import javax.swing.ImageIcon;

public class Target extends GameTile {

    public Target(int x, int y) {
        super(x, y);
        image = new ImageIcon(getClass().getResource("/images/finish_tower_3.png")).getImage();
    }
    public void draw(Graphics2D g2d) {
        g2d.drawImage(image, this.getX() -60, this.getY() - 50 , 100, 150, null);
    }
}
