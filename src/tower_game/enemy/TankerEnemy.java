package tower_game.enemy;

import javax.swing.ImageIcon;

public class TankerEnemy extends Enemy{

    public TankerEnemy(int x, int y) {
        super(x, y, 1600, 1, 10, 10);
        image = new ImageIcon(getClass().getResource("/images/enemy/tankerEnemy.png")).getImage();
    }
    
    
}
