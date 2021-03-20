package tower_game.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class GameStage extends JFrame 
{
    public static final int H_FRAME = 589;
    public static final int W_FRAME = 765;
    public  GameField gameField = null;
    public Thread thread ;
    public CheckPlaying checkGame = new CheckPlaying() ;
    public StartPanel startPanel = new StartPanel () ;;
    public boolean inOldGame = false ;
    public boolean inNewGame = false ;
    public GameStage() throws IOException, FileNotFoundException, ClassNotFoundException {
        this.setSize(W_FRAME, H_FRAME);
        this.setTitle("TowerDefense!");
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        this.add (this.startPanel) ;
        this.setFocusable(true);
        this.addMouseListener(this.new MouseHandler ());
    }

    public class CheckPlaying 
    {
        public boolean keepPlaying = false ;
        public CheckPlaying ()
        {
            keepPlaying = false ;
        }
        public void resetGame (GameStage gameStage) throws IOException, FileNotFoundException, ClassNotFoundException
        {
            GameStage newGameStage = new GameStage () ;
            gameStage.dispose();
        }
    }
    
    public class StartPanel extends JPanel implements Runnable
    {
       public boolean Running = true ;
       Thread thread ;
       public StartPanel ()
       {
           setFocusable(true);
           setBackground(Color.LIGHT_GRAY);
           thread = new Thread (this) ;
           thread.start(); 
       }
        protected void paintComponent(Graphics g) 
        {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
                super.paintComponent(g2d);
                Image start_img = new ImageIcon(getClass().getResource("/images/start_background.png")).getImage();
                g2d.drawImage(start_img, 0, 0, W_FRAME, H_FRAME, null);
        }
        
        public void run ()
        {
            while (Running == true)
            {
                repaint () ;
            }
        }
    }
    public void updateMouse (MouseEvent e) throws IOException, FileNotFoundException, ClassNotFoundException
    {
        if (this.gameField == null )
        {
            if (e.getX() >= 290 && e.getX() <= 460 && e.getY() >= 440 && e.getY() <= 510)
                {
                    inOldGame = true ;
                    inNewGame = true ;
                    this.startPanel.Running = false ;
                    this.getContentPane().remove(this.startPanel);
                    this.gameField = new GameField (this) ;
                    this.add(this.gameField) ;
                    this.getContentPane().invalidate();
                    this.getContentPane().validate();
                }
                else if (e.getX() >= 290 && e.getX() <= 460 && e.getY() >= 440 - 510 + 440 - 20&& e.getY() <= 440 -20)
                {
                    inOldGame = false ;
                    inNewGame = true ;
                    this.startPanel.Running = false ;
                    this.getContentPane().remove(this.startPanel);
                    this.gameField = new GameField (this) ;
                    this.add(this.gameField) ;
                    this.getContentPane().invalidate();
                    this.getContentPane().validate();
                }
        }
        
    }
    public class MouseHandler implements MouseListener, MouseMotionListener
        {

            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    updateMouse(e);
                } catch (IOException ex) {
                    Logger.getLogger(GameStage.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(GameStage.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                try {
                    updateMouse(e);
                } catch (IOException ex) {
                    Logger.getLogger(GameStage.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(GameStage.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseExited(MouseEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            
        }
}
