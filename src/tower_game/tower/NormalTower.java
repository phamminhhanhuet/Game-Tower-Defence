package tower_game.tower;

import java.awt.Image;
import java.io.Serializable;
import javax.swing.ImageIcon;
import tower_game.audio.SoundManager;

public class NormalTower extends Tower  implements Serializable {
    
   
    public NormalTower(int x, int y, int id, int cost, int speed, int limit, int damage,int maxAttackTime, int maxAttackWait) {
        super(x, y, id, cost, speed, limit, damage, maxAttackTime, maxAttackWait);
        image = new ImageIcon(getClass().getResource("/images/tower/normalTower.png")).getImage();
    }
    
     public NormalTower (int x, int y)
    {
        super(x, y) ;
        image = new ImageIcon(getClass().getResource("/images/tower/normalTower.png")).getImage();
        this.id = 0 ;
        this.cost = 20 ;
        this.speed =4 ; // cant not define speed as 1 coz it can make bullet's xPos not changed
        this.limit = 4;
        this.damage = 1 ;
        this.maxAttackTime = 4 ;
        this.maxAttackWait = 10 ;
        Image update_1 = new ImageIcon(getClass().getResource("/images/tower/normalTower1.png")).getImage() ;
        Image update_2 = new ImageIcon(getClass().getResource("/images/tower/normalTower2.png")).getImage() ;
        this.updateImage.add(update_1) ;
        this.updateImage.add(update_2) ;
        
        
        this.clipString = "Laser_Shot.wav" ;
    }
}
