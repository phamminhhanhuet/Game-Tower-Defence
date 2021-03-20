package tower_game.enemy;

import javax.swing.ImageIcon;

public class SmallerEnemy extends Enemy{

    public SmallerEnemy(int x, int y){
        super(x, y, 800, 5, 10, 10);
        image = new ImageIcon(getClass().getResource("/images/enemy/smallerEnemy.png")).getImage();
    }
    
    
    
}
