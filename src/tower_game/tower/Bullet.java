package tower_game.tower;

import tower_game.gui.GameStage;
import java.io.Serializable;
import java.awt.*;
import static java.lang.Math.sqrt;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import tower_game.audio.SoundManager;
import tower_game.enemy.Enemy;

public class Bullet implements Serializable
{
    private int speedFire;
    private int limit;
    private int damage;
    public int x;
    public int y;
    
    private int originalX ;
    private int originalY ;
    
    public double orient ;
    public Enemy target ;
    private Image image = new ImageIcon(getClass().getResource(
            "/images/bullet.png")).getImage();
    
    protected Clip clip ;
    protected SoundManager soundManger ;
    
    public Bullet(int x, int y, int speedFire, int limit, int damage) {
       // this.x = x - image.getWidth(null) / 2;
       // this.y = y - image.getHeight(null) / 2;
        this.x = x ;
        this.y = y ;
        this.speedFire = speedFire;
        this.limit = limit;
        this.damage = damage;
    }
    public Bullet (int x, int y, int speedFire,  int limit, int damage,Enemy target, String clipString)
    {
        this.x = x ;
        this.y = y ;
        this.originalX = x ;
        this.originalY = y ;
        this.speedFire = speedFire ;
        this.damage = damage ;
        this.limit = limit ;
        this.target = target ;
        
        this.soundManger = new SoundManager ();
        this.clip = this.soundManger.getSound(clipString) ;
        this.clip.start(); 
    }
    public void draw(Graphics2D g2d) {
        g2d.drawImage(image, this.x, this.y, 10, 10, null);
    }
    
    public int getX ()
    {
        return this.x ;
    }
    public int getY ()
    {
        return this.y ;
    }
    public int getOriginalX ()
    {
        return this.originalX ;
    }
    public int getOriginalY ()
    {
        return this.originalY ;
    }
    public void setX (int x)
    {
        this.x = x ;
    }
    public void setY (int y)
    {
        this.y = y ;
    }
    public void setOriginalX (int x)
    {
        this.originalX = x ;
    }
    public void setOriginalY (int y)
    {
        this.originalY = y ;
    }
    public void move(){
        // Dịch chuyển đạn
        
        int xDistance = this.target.getX() - this.x ;
        int yDistance = this.target.getY() - this.y ;
        double orient = Math.atan2((double)yDistance, (double)xDistance) ;
        double xPos = (double) this.x + (double)this.speedFire * Math.cos(orient);
        double yPos = (double) this.y + (double)this.speedFire * Math.sin(orient);
        this.x = (int) xPos ;
        this.y = (int) yPos ;
    
        /*int xDistance = this.x - this.target.getX() ;
        int yDistance = this.y - this.target.getY() ;
	   int zDistance = (int) sqrt (xDistance * xDistance + yDistance * yDistance) ;
        if (xDistance >= 0 && yDistance >=0)
        {
            this.x -= (10 * xDistance) / zDistance;
            this.y -= (10 * yDistance) / zDistance;
        }
        else if (xDistance >= 0 && yDistance <= 0)
        {
            this.x -= (10 * xDistance) / zDistance ;
            this.y += (10 * yDistance) / zDistance ;
        }
        else if (xDistance <= 0 && yDistance <= 0)
        {
            this.x += (10 * xDistance) / zDistance;
            this.y += (10 * yDistance) / zDistance;
        }
        else if (xDistance <= 0 && yDistance >= 0)
        {
            this.x += (10 * xDistance) / zDistance;
            this.y -= (10 * yDistance) / zDistance;
        } */
        
        //if (x <= 0 || y <= 0 || x >= GameStage.W_FRAME || y >= GameStage.H_FRAME 
          //      || x >= x+limit || x <= x-limit
         //       || y >= y+limit || y <= y-limit) {
         //   return false;
        //}
      //  return true;
    }
    
    public void updateTarget ()
    {
        if (this.x > this.originalX + this.limit * 25 || this.x < this.originalX - this.limit * 25  || this.y > this.originalY + this.limit * 25  || this.y < this.originalY - this.limit * 25 )
        {
            this.target = null ;
            System.out.print("A Bullet goes out of Tower's limit!!");
        }
        else 
        {
            int xDistance = Math.abs (this.target.getX() - this.x) ;
            int yDistance = Math.abs( this.target.getY() - this.y) ;
            int closestDistance = 3 ;
        if (xDistance * xDistance + yDistance *yDistance < closestDistance * closestDistance)
        {
           this.target.setHealth(this.target.getHealth() - this.damage);
           this.target = null ;
           System.out.print("Enemy got attacked!!");
        }
        }
        
        
        
        
        /*Rectangle rect_enemy = this.target.getRect() ;
        boolean check_die = this.getRect().intersects(rect_enemy) ;
        if (check_die == true) 
        {
            this.target = null ; System.out.print("++++++++++++++++++++++++++!!");
        } */
            //if (this.target.getHealth() <= 0) this.target = null ;
        //int changeXPos = Math.abs (this.x - this.originalX) ;
        //int changeYPos = Math.abs (this.y - this.originalY) ;
       /* int bulletX = (int)this.x / 50;
        int bulletY = (int)this.y / 50;
        int oriX = (int)this.originalX / 50 ;
        int oriY = (int)this.originalY / 50 ;
        int changeXPos = bulletX - oriX ;
        int changeYPos = bulletY - oriY ;
        if (changeXPos * changeXPos + changeYPos * changeYPos >= this.limit * this.limit)
        {
            this.target = null ;
            System.out.print("____________________!!");
        } */
    }
    
    public Rectangle getRect(){
        Rectangle rectangle = new Rectangle(x,y,10,10);
        return rectangle;
    }
    
}
