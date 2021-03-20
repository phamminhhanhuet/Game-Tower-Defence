package tower_game.tower;

import java.awt.Image;
import java.io.Serializable;
import javax.swing.ImageIcon;
import tower_game.audio.SoundManager;

public class SniperTower extends Tower  implements Serializable{
    
    public SniperTower(int x, int y, int id, int cost , int speed, int limit, int damage,int maxAttackTime, int maxAttackWait) {
        super(x, y, id, cost, speed, limit, damage, maxAttackTime, maxAttackWait);
        image = new ImageIcon(getClass().getResource("/images/tower/sniperTower.png")).getImage();
    }
    
     public SniperTower (int x, int y)
    {
        super(x, y) ;
        this.image = new ImageIcon(getClass().getResource("/images/tower/sniperTower.png")).getImage();
        this.id = 1 ;
        this.cost = 60 ;
        this.speed =3 ;
        this.limit = 4;
        this.damage = 4 ;
        this.maxAttackTime = 7 ;
        this.maxAttackWait = 7 ;
        Image update_1 = new ImageIcon(getClass().getResource("/images/tower/sniperTower1.png")).getImage() ;
        Image update_2 = new ImageIcon(getClass().getResource("/images/tower/sniperTower2.png")).getImage() ;
        this.updateImage.add(update_1) ;
        this.updateImage.add(update_2) ;
        
        this.clipString = "Shotgun_Shot.wav" ;
    }
}
