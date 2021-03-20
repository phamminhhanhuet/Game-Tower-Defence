package tower_game.tower;

import java.awt.Image;
import java.io.Serializable;
import javax.swing.ImageIcon;
import tower_game.audio.SoundManager;

public class MachineGunTower extends Tower  implements Serializable{

    public MachineGunTower(int x, int y, int id, int cost, int speed, int limit, int damage,int maxAttackTime, int maxAttackWait) {
        super(x, y, id, cost, speed, limit, damage, maxAttackTime, maxAttackWait);
        image = new ImageIcon(getClass().getResource("/images/tower/machineGun.png")).getImage();
    }
    
     public MachineGunTower (int x, int y)
    {
        super(x, y) ;
        image = new ImageIcon(getClass().getResource("/images/tower/machineGun.png")).getImage();
        this.id = 2 ;
        this.cost = 100 ;
        this.speed =5 ;
        this.limit = 6;
        this.damage = 6 ;
        this.maxAttackTime = 10 ;
        this.maxAttackWait = 20 ;
        Image update_1 = new ImageIcon(getClass().getResource("/images/tower/machineGun1.png")).getImage() ;
        Image update_2 = new ImageIcon(getClass().getResource("/images/tower/machineGun2.png")).getImage() ;
        this.updateImage.add(update_1) ;
        this.updateImage.add(update_2) ;
        
        this.clipString = "Shotgun_Shot.wav" ;
    }
}
