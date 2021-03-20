package tower_game.tower;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Random;
import java.io.Serializable;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import tower_game.audio.SoundManager;
import tower_game.enemy.Enemy;
import tower_game.gui.GameEntity;

public class Tower implements Serializable
{
    
    protected int id ;
    protected int cost ;
    protected int speed; // tốc độ bắn
    protected int limit; // tầm bắn
    protected int damage; // sát thương;
    protected int x;
    protected int y;
    
    public int attackTime ; //(timer) how long do we want the laser/ attack or stay on
    public int attackWait ; // (timer) Pause between each attack
    
    public int maxAttackTime ;
    public int maxAttackWait ;
    
    public Enemy target ;
    
 
    protected Image image;
    protected String clipString ;

    public ArrayList<Image> updateImage = new ArrayList <>(2) ;
    public int [] updateCost = new int [5];
 
    public Tower (int x, int y)
    {
        this.x = x ;
        this.y = y ;
        for (int i = 0 ; i < 5 ;i ++)
        {
            updateCost[i] = 20 ;
        }
    }
    
    public Tower(int x, int y, int id, int cost, int speed, int limit, int damage, int maxAttackTime, int maxAttackWait) {
        this.x = x;
        this.y = y;
        this.id = id ;
        this.cost = cost ;
        this.speed = speed;
        this.limit = limit;
        this.damage = damage;
        this.maxAttackTime = maxAttackTime ;
        this.maxAttackWait = maxAttackWait;
        
        this.attackTime = 0 ;
        this.attackWait = 0 ;
    }

    public int getX ()
    {
        return this.x ;
    }
    public int getY ()
    {
        return this.y ;
    }
    public int getLimit ()
    {
        return this.limit ;
    }
    public int getDamage ()
    {
        return this.damage ;
    }
    public int getSpeed ()
    {
        return this.speed ;
    }
    public int getCost ()
    {
        return this.cost ;
    }
    public void setImage (Image image)
    {
        this.image = image ;
    }
    public void draw(Graphics2D g2d) {
        g2d.setColor(new Color (64, 64, 64, 64));
        g2d.fillOval(x - this.limit * 25 + 25, y - this.limit * 25 + 25 , this.limit * 50, this.limit * 50);
        g2d.drawImage(image, x, y, 50, 50, null);
        
    }
    
    // Bắn
    public void fire(ArrayList<Bullet> arr, int x, int y)
    {
        for (Bullet bullet: arr)
        {
            if (bullet != null)
            {
                
            }
        }
    }
    
    public Enemy defineEnemy (ArrayList<Enemy> arrEnemy, int x, int y)
    {
        ArrayList<Enemy> enemyInLimit = new ArrayList<> () ;
        int towerX = (int) x / 50;
        int towerY = (int) y / 50;
        
        int towerLimit = this.limit ;
        int enemyLimit = 0 ;
        
        int enemyX ;
        int enemyY ;
        
        for (Enemy enemy :arrEnemy)
        {
            if (enemy != null)
            {
                /*enemyX = (int) (enemy.getX() / 50) ;
                enemyY = (int) (enemy.getY() / 50) ;
                
                int xDistance = enemyX - towerX ;
                int yDistance = enemyY - towerY ;
                
                int limitDistance = towerLimit + enemyLimit ;
                if (xDistance * xDistance + yDistance * yDistance < limitDistance * limitDistance)
                {
                    enemyInLimit.add(enemy) ;
                } */
                int xDistance = Math.abs(this.x - enemy.getX()) ;
                int yDistance = Math.abs(this.y - enemy.getY()) ;
                
                if (xDistance * xDistance + yDistance * yDistance <= (this.limit * 25) * (this.limit * 25))
                {
                    enemyInLimit.add(enemy) ;
                }
            }
        }
   
            int totalEnemy = 0 ;
            for (Enemy enemy : enemyInLimit)
            {
                if (enemy != null) totalEnemy ++ ;
            }
            if (enemyInLimit.isEmpty() == false )return enemyInLimit.get(0) ;
        return null ;
    }
    
    
    public void TowerAttack (int x, int y, Enemy enemy, ArrayList<Bullet> arrBullet)
    {
       // enemy.setHealth(enemy.getHealth() - this.damage) ;
        System.out.print(arrBullet.size());
        for (int i = 0 ; i < 10 ; i ++)
        {
            Bullet bullet = new Bullet (x + 25, y + 25 , this.speed, this.limit, this.damage,  enemy, this.clipString) ;
            arrBullet.add(i, bullet) ;
            System.out.print("A new Bullet was spawned!!");
            //this.clip.start(); 
        }
      
    }

    public void updateLevel (int id)
    {
        this.cost += 10 ;
        switch (id)
        {
            case 0: this.speed ++ ; this.maxAttackTime ++ ;
                updateCost[0] += 5 ; break ;
            case 1: this.limit ++ ; this.maxAttackTime -- ; 
                updateCost[1] += 5 ;break ;
            case 2: this.damage += 2 ; 
                updateCost[2] += 5 ; break ;
            case 3: this.image = this.updateImage.get(0) ; 
                 break ;
            case 4: this.image = this.updateImage.get(1) ; 
                 break ;
            default: break ;
        }
    }
}
