package tower_game.gametile;

import javax.swing.ImageIcon;

public class Mountain extends GameTile{

    public Mountain(int x, int y) {
        super(x, y);
        image = new ImageIcon(getClass().getResource("/images/img_mountain.png")).getImage();
    }
    
}
