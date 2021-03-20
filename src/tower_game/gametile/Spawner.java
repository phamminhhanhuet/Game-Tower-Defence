package tower_game.gametile;

import java.awt.Graphics2D;
import javax.swing.ImageIcon;

public class Spawner extends GameTile{

    public Spawner(int x, int y) {
        super(x, y);
        image = new ImageIcon(getClass().getResource("/images/start_rubble.png")).getImage();
    }
    
    public void draw(Graphics2D g2d) {
        g2d.drawImage(image, this.getX(), this.getY(), 120, 80, null);
    }
    
}
