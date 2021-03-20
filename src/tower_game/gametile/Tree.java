package tower_game.gametile;

import javax.swing.ImageIcon;

public class Tree extends GameTile{

    public Tree(int x, int y) {
        super(x, y);
        image = new ImageIcon(getClass().getResource("/images/img_tree_3.png")).getImage();
    }
    
}
