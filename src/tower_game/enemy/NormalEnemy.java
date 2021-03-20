/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tower_game.enemy;

import javax.swing.ImageIcon;

/**
 *
 * @author Duy
 */
public class NormalEnemy extends Enemy{

    public NormalEnemy(int x, int y) {
        super(x, y, 1000, 1, 10, 10);
        image = new ImageIcon(getClass().getResource("/images/enemy/normalEnemy.png")).getImage();
    }
   
    
}
