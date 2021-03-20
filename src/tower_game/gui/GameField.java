package tower_game.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import tower_game.enemy.BossEnemy;
import tower_game.enemy.Enemy;
import tower_game.enemy.NormalEnemy;
import tower_game.enemy.SmallerEnemy;
import tower_game.enemy.TankerEnemy;
import tower_game.main.Main;
import tower_game.tower.MachineGunTower;

public class GameField extends JPanel implements Runnable
{   
    public GameEntity gameEntity = new GameEntity(this);
    public GameStage gameStage   ;
   // public StartPanel startPanel  = new StartPanel () ;
    public boolean isRunning = true ;
    public boolean isWaiting = true ;
    public boolean Starting = false ;
    public boolean isExiting = false ;
    public boolean isplayOldSave = false ;
   
    
    public int bitSet = 0;
    Thread thread ;

    GameField(GameStage gameStage) throws IOException, FileNotFoundException, ClassNotFoundException 
    {
        this.gameStage = gameStage ;
        this.gameStage.setFocusable(true);
        this.gameStage.addKeyListener(this.gameEntity.new KeyHandler ());
        this.gameStage.addMouseListener(this.gameEntity.new MouseHandler ());
        this.gameStage.addMouseMotionListener(this.gameEntity.new MouseHandler ());
        this.Starting = this.gameStage.inNewGame ;
        setFocusable(true);
        setBackground(Color.LIGHT_GRAY);
        
       // this.gameStage.add(this.startPanel) ;
        System.out.print(this.gameStage.inOldGame + "\n" + this.gameEntity.fileSave.exists());
        
        if (this.gameEntity.fileSave.exists() == false)
        {
            gameEntity.initGame();
        }
        else if (this.gameEntity.fileSave.exists() == true)
        {
            if (this.gameStage.inOldGame == true)
            {
                System.out.print(this.gameEntity.mapName);
                //gameEntity.initGame();
                try {
                    System.out.print("Load game___________");

                    gameEntity.readFileSave();
                    } catch (IOException ex) {
                    Logger.getLogger(GameField.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(GameField.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else 
            {
                gameEntity.initGame();
            }
        }
       
        thread = new Thread (this) ;
        thread.start();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        super.paintComponent(g2d);
       
        if (Starting == true )
        {
                Image start_img = new ImageIcon(getClass().getResource("/images/01_Background_Games.png")).getImage();
                g2d.drawImage(start_img, 0, 0, gameStage.getWidth(), gameStage.getHeight(), null);
                gameEntity.draw(g2d);
        }
        if (isExiting == true)
        {
            Image exit_img = new ImageIcon(getClass().getResource("/images/exit_background.png")).getImage();
            int xPos = this.gameStage.getWidth() / 2 - exit_img.getWidth(null) / 2 ;
            int yPos = this.gameStage.getHeight()/ 2 - exit_img.getHeight(null) /2;
            g2d.drawImage(exit_img, xPos , yPos, exit_img.getWidth(null), exit_img.getHeight(null), null) ;
        }
       // g2d.dispose();
    }
    @Override
    public void run() {
        
        int count = 0 ;
        while (isRunning){
            if (isWaiting == false ) {
               
                if (this.gameEntity.isSkilling == true && count <= this.gameEntity.skillEffectTime)
                {
                    count ++ ;
                }
                else
                {
                    count = 0 ;
                    this.gameEntity.skillEffectTime = 0 ;
                    this.gameEntity.skillPause = false ;
                    this.gameEntity.isSkilling = false ;
                    this.gameEntity.skillId = -1 ;
                }
                if (this.gameEntity.skillPause == false)
                {
                     gameEntity.AI();
                     this.gameEntity.enemyUpdate(); 
                }
               
                this.gameEntity.bulletUpdate();
                this.gameEntity.towerUpdate();
            }
            repaint();
            System.out.print(this.gameEntity.coin + "\n");
            
            
            if (this.gameEntity.getHeart() <= 0) {
                System.out.print("Game Over!");
                if (gameEntity.fileSave.exists() == true) 
                {
                    gameEntity.fileSave.delete() ;
                }
                isExiting = true ;
            }
           
         try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
         
        } 
    
      if (this.gameStage.checkGame.keepPlaying == true){
            try {
                try {
                    this.gameStage.checkGame.resetGame(this.gameStage);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(GameField.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(GameField.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (IOException ex) {
                Logger.getLogger(GameField.class.getName()).log(Level.SEVERE, null, ex);
            }
      }
    }
    public void EXIT ()
    {
        this.gameStage.dispose();
    }
}
