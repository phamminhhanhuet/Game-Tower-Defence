package tower_game.enemy;

import java.awt.Color;
import tower_game.gametile.GameTile;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.ArrayList;
import javax.sound.sampled.Clip;
import tower_game.audio.SoundManager;
import tower_game.gametile.Road;
import tower_game.gametile.Spawner;
import tower_game.gametile.Target;
import tower_game.gui.GameStage;
import tower_game.tower.Bullet;

public class Enemy implements Serializable{

    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int UP = 2;
    public static final int DOWN = 3;

    private int x;
    private int y;
    private int heart;
    private int speed;
    private int defense;
    private int reward;
    protected Image image;
    private int orient;

    private int HEART ;
    public Enemy(int x, int y, int heart, int speed, int defense, int reward) {
        this.x = x;
        this.y = y;
        this.heart = heart;
        this.speed = speed;
        this.defense = defense;
        this.reward = reward;
        this.HEART = heart ;
    }
    
    
    public int getX ()
    {
        return this.x ;
    }
    public int getY ()
    {
        return this.y ;
    }
    public int getHealth ()
    {
        return this.heart ;
    }
    public int getReward ()
    {
        return this.reward ;
    }
    public int getOrient ()
    {
        return this.orient ;
    }
    public void setHealth (int heart)
    {
        this.heart = heart ;
    }
    public void setOrient (int orient)
    {
        this.orient = orient ;
    }
    public void draw(Graphics2D g2d) {
        g2d.drawImage(image, x, y, 25, 25, null);
        g2d.setColor(Color.BLUE);
        g2d.fillRect(x, y - 15, 25, 7);
        g2d.setColor(Color.red);
        
        double heart_scale = (double) this.heart / ((double) this.HEART) ;
   
        double change_heart = heart_scale * 25 ;
        g2d.fillRect(x, y - 15, (int)change_heart, 7);
    }

    public void changeOrient(ArrayList<GameTile> arrMaps) {
        int xR, yR;
        GameTile afterRoad = null;
        if(orient == LEFT){
            System.out.println("orient_LEFT(" + x +"," + y +")");
        } else if(orient == RIGHT){
            System.out.println("orient_RIGHT(" + x +"," + y +")");
        }else if(orient == DOWN){
            System.out.println("orient_DOWN(" + x +"," + y +")");
        }else if(orient == UP){
            System.out.println("orient_UP(" + x +"," + y +")");
        }
        if(x<-50 || y<-50) return;
        switch (orient) {
            case LEFT:
                afterRoad = findMap(x,y,arrMaps);
                if(x<= afterRoad.getX() + (50-image.getWidth(null)/2)){
                    return;
                }
                xR = x-25;
                afterRoad = findMap(xR,y,arrMaps);
                if(afterRoad instanceof Road || afterRoad instanceof Target){
                    orient = LEFT;
                } else {
                    afterRoad = findMap(x,y+50,arrMaps);
                    if(afterRoad instanceof Road || afterRoad instanceof Target){
                        orient = DOWN;
                    } else {
                        orient = UP;
                    }
                }
                break;
            case RIGHT:
                xR = x+50;
                afterRoad = findMap(xR,y,arrMaps);
                if(afterRoad instanceof Spawner){
                    return;
                } else if(x<=findMap(x,y,arrMaps).getX() + (50-image.getWidth(null)/2)){
                    return;
                } else if(afterRoad instanceof Road || afterRoad instanceof Target){
                    return;
                } else {
                    afterRoad = findMap(x,y+50,arrMaps);
                    if(afterRoad instanceof Road || afterRoad instanceof Target){
                        orient = DOWN;
                    } else {
                        orient = UP;
                    }
                }
                break;
            case UP:
                afterRoad = findMap(x,y,arrMaps);
                if(y<=afterRoad.getY() + (50-image.getHeight(null)/2)){
                    return;
                    
                }
                yR = y-25;
                afterRoad = findMap(x,yR,arrMaps);
                if(afterRoad instanceof Road || afterRoad instanceof Target){
                    orient = UP;
                } else {
                    afterRoad = findMap(x+50,y,arrMaps);
                    if(afterRoad instanceof Road || afterRoad instanceof Target){
                        orient = RIGHT;
                    } else {
                        orient = LEFT;
                    }
                }
                break;
            case DOWN:
                afterRoad = findMap(x,y,arrMaps);
                if(y<=afterRoad.getY() + (50-image.getHeight(null)/2)){
                    return;
                }
                yR = y+50;
                afterRoad = findMap(x,yR,arrMaps);
                if(afterRoad instanceof Road || afterRoad instanceof Target){
                    orient = DOWN;
                } else {
                    afterRoad = findMap(x+50,y,arrMaps);
                    if(afterRoad instanceof Road || afterRoad instanceof Target){
                        orient = RIGHT;
                    } else {
                        orient = LEFT;
                    }
                }
                break;
            default:
                break;
        }
        
    }
    
    public GameTile findMap(int x, int y, ArrayList<GameTile> arrMaps){
        GameTile result = null;
        for(GameTile map : arrMaps){
            if(map.getX()<=x && map.getY()<=y 
                    && map.getX()+50>=x && map.getY()+50>=y){
                result = map;
                break;
            }
        }
        return result;
    }
    
    public void move() {
        switch (orient) {
            case LEFT:
                x -= speed;
                break;
            case RIGHT:
                x += speed;
                break;
            case UP:
                y -= speed;
                break;
            case DOWN:
                y += speed;
                break;
        }
        
    }
    
    public Rectangle getRect(){
       // int w = image.getWidth(null);
       // int h = image.getHeight(null);
        int w = 50;
        int h =50;
        Rectangle rectangle = new Rectangle(x,y,w,h);
        return rectangle;
    }
    
   
    
    public boolean checkFinish(ArrayList<GameTile> arrMaps ){
        GameTile target = null;
        for(GameTile map : arrMaps){
            if(map instanceof Target == true){
                target = map;
                break;
            }
        }
        int xPos = this.x / 50 ;
        int yPos = this.y / 50 ;
        if(this.x>=target.getX()-50 && this.y >=target.getY()+50
                &&this.x<target.getX()+100 && this.y<target.getY()+100){
            return true;
        }
        return false;
    }
    
    public Enemy update ()
    {
        Enemy currentEnemy = this ;
        if (currentEnemy.heart <= 0)
        {
            return null ;
        }
        return currentEnemy ;
    }
    // public boolean checkDie(ArrayList<Bullet> arrBullet){
    //     for(Bullet bullet : arrBullet){
    //         Rectangle rectangle = getRect().intersection(bullet.getRect());
    //         if(rectangle.isEmpty() == false){
    //             arrBullet.remove(bullet);
    //             Clip clip = SoundManager.getSound("explosion_tank.wav");
    //             clip.start();
    //             return false;
    //         }
    //     }
    //     return true;
    // }
    
    
}
