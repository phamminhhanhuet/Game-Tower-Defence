package tower_game.gui;

import java.awt.Color;
import java.awt.Graphics;
import tower_game.enemy.Enemy;
import tower_game.gametile.GameTile;
import tower_game.tower.Bullet;
import tower_game.tower.Tower;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import tower_game.audio.SoundManager;
import tower_game.enemy.BossEnemy;
import tower_game.enemy.NormalEnemy;
import tower_game.enemy.SmallerEnemy;
import tower_game.enemy.TankerEnemy;
import tower_game.gametile.EmptyBox;
import tower_game.gametile.Mountain;
import tower_game.gametile.Road;
import tower_game.gametile.Spawner;
import tower_game.gametile.Target;
import tower_game.gametile.Tree;
import tower_game.main.Main;
import tower_game.tower.MachineGunTower;
import tower_game.tower.NormalTower;
import tower_game.tower.SniperTower;

public class GameEntity {
    public ArrayList<Tower> arrTower;
    public ArrayList<Bullet> arrBullet;
    public ArrayList<Enemy> arrEnemy;
    public ArrayList<GameTile> arrMaps;
    public ArrayList<Image> arrSkill ;
    public ArrayList<Stone> arrStone ;
    
    
    private int heart;
    public int coin;
    private int bit;
    private int orderRow;
    private int spawnedRow ;
    
    public GameField gameField ;
    public boolean isBuying = false ; // for tower_buying menu
    public boolean isSealing = false ; // for seal your own tower
    
    public boolean isSkilling = false ;  // for skill menu
    public boolean skillPause = false ;
    
    public boolean placeTower = true ; // for state of buying a new tower
    
    public boolean isTowerMenu = false ;
    public boolean isSkillMenu = false ;
    public boolean isDownWait = false ;
    public boolean isDownDisplay = true ; // for downMenu
    public boolean isUpdateDisplay = false ; //for Tower 's update menu
    
    
    
    public boolean notEmptyBox = true ; 
    
    public int sealXPos = 0 ;
    public int sealYPos = 0 ;
    public int updateXPos = 0 ;
    public int updateYPos = 0 ;
    public int mouseXPos = 0 ;
    public int mouseYPos = 0 ;
    
    public int mapRow = 0 ;
    public int mapColumn = 0 ;
    public Tower [] towerList = new Tower [3] ;
    public int id = -1 ;  // for Tower
    public int skillId = - 1 ; // for special skills
    public int skillEffectTime = 0 ;
    
    public int [] skillCost = new int [3];
    
    public String mapName ;
    public File fileSave = new File("src/res/fileSave.txt");
    
    
    public GameEntity (GameField gameField)
    {
        this.gameField = gameField ;
    }
    public int getHeart ()
    {
        return this.heart ;
    }
    public void setHeart (int heart)
    {
        this.heart = heart;
    }
    //Bắt đầu màn chơi
    public void initGame(){
        heart = 20;
        bit = 0;
        coin = 2000;
        orderRow = 0;
        spawnedRow = 0 ;
        arrBullet = new ArrayList<>(10);
        arrEnemy = new ArrayList<>();
        arrTower = new ArrayList<>();
        arrSkill = new ArrayList<>(10) ;
        arrStone = new ArrayList<>(10) ;
        for (int i = 0 ; i < 3 ; i ++)
        {
            skillCost [i] = 50 * i ; 
        }
        readMap("mapData.txt");
        mapName = "mapData.txt";
        defineMenuTower();
        initBoss();
    }
    
    public void initBoss(){
        GameTile spawer = getSpawner();
        int x = spawer.getX();
        int y = spawer.getY();
        int orientStart = start();
        System.out.println(orientStart);
        File file = new File("src/maps/boss.txt");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            int row = 0;
            String line = reader.readLine();
            while (line!=null){
                if(row == orderRow){
                    String[] arr = line.split(" ");
                    int numberEnemy = Integer.parseInt(arr[0]) - spawnedRow;
                    if(numberEnemy>0){
                        NormalEnemy e ;
                        for(int i=0; i<numberEnemy; i++){
                            switch(orientStart){
                                case Enemy.RIGHT:
                                    x = x-100;
                                    e = new NormalEnemy(x, y) ;
                                    e.setOrient(orientStart);
                                    arrEnemy.add(e); 
                                    break;
                                case Enemy.LEFT:                                        
                                    x = x+100;
                                    e = new NormalEnemy(x, y) ;
                                    e.setOrient(orientStart);
                                    arrEnemy.add(e); 
                                    break;
                                case Enemy.UP:
                                    y = y +100;
                                    e = new NormalEnemy(x, y) ;
                                    e.setOrient(orientStart);
                                    arrEnemy.add(e); 
                                    break;
                                case Enemy.DOWN:  
                                    y = y -100;
                                    e = new NormalEnemy(x, y) ;
                                    e.setOrient(orientStart);
                                    arrEnemy.add(e); 
                                    break;
                            }
                        }
                    }
                    
                    numberEnemy = Integer.parseInt(arr[1]) - spawnedRow;
                    if(numberEnemy>0){
                        SmallerEnemy e;
                        for(int i=0; i<numberEnemy; i++){
                            switch(orientStart){
                                
                                case Enemy.RIGHT:
                                    x = x-100;
                                    e = new SmallerEnemy(x, y) ;
                                    e.setOrient(orientStart);
                                    arrEnemy.add(e); 
                                    break;
                                case Enemy.LEFT:                                        
                                    x = x+100;
                                     e = new SmallerEnemy(x, y) ;
                                    e.setOrient(orientStart);
                                    arrEnemy.add(e); 
                                    break;
                                case Enemy.UP:
                                    y = y +100;
                                     e = new SmallerEnemy(x, y) ;
                                    e.setOrient(orientStart);
                                    arrEnemy.add(e); 
                                    break;
                                case Enemy.DOWN:  
                                    y = y -100;
                                    e = new SmallerEnemy(x, y) ;
                                    e.setOrient(orientStart);
                                    arrEnemy.add(e);  
                                    break;
                            }
                        }
                    }
                    
                    numberEnemy = Integer.parseInt(arr[2]) - spawnedRow;
                    if(numberEnemy>0){
                        TankerEnemy e ;
                        for(int i=0; i<numberEnemy; i++){
                            switch(orientStart){
                                case Enemy.RIGHT:
                                    x = x-100;
                                    e = new TankerEnemy(x, y) ;
                                    e.setOrient(orientStart);
                                    arrEnemy.add(e); 
                                    break;
                                case Enemy.LEFT:                                        
                                    x = x+100;
                                    e = new TankerEnemy(x, y) ;
                                    e.setOrient(orientStart);
                                    arrEnemy.add(e); 
                                    break;
                                case Enemy.UP:
                                    y = y +100;
                                    e = new TankerEnemy(x, y) ;
                                    e.setOrient(orientStart);
                                    arrEnemy.add(e); 
                                    break;
                                case Enemy.DOWN: 
                                    y = y -100;
                                    e = new TankerEnemy(x, y) ;
                                    e.setOrient(orientStart);
                                    arrEnemy.add(e); 
                                    break;
                            }
                        }
                    }
                    numberEnemy = Integer.parseInt(arr[3]) - spawnedRow;
                    if(numberEnemy>0){
                        BossEnemy e ;
                        for(int i=0; i<numberEnemy; i++){
                            switch(orientStart){
                                case Enemy.RIGHT:
                                    x = x-100;
                                    e = new BossEnemy(x, y) ;
                                    e.setOrient(orientStart);
                                    arrEnemy.add(e); 
                                    break;
                                case Enemy.LEFT:                                        
                                    x = x+100;
                                    e = new BossEnemy(x, y) ;
                                    e.setOrient(orientStart);
                                    arrEnemy.add(e); 
                                    break;
                                case Enemy.UP:
                                    y = y +100;
                                    e = new BossEnemy(x, y) ;
                                    e.setOrient(orientStart);
                                    arrEnemy.add(e); 
                                    break;
                                case Enemy.DOWN:  
                                    y = y -100;
                                    e = new BossEnemy(x, y) ;
                                    e.setOrient(orientStart);
                                    arrEnemy.add(e);  
                                    break;
                            }
                        }
                    }
                    break;
                }
                row++;
                line = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public void readMap(String map){
        arrMaps = new ArrayList<>();
        File file = new File("src/maps/"+map);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            int row = 0;
            String line = reader.readLine();
            while (line!=null){
                for (int i = 0;i<line.length();i++){
                    int x = i * 50;
                    int y = row * 50;
                    int bit = Integer.parseInt(line.charAt(i)+"");
                    mapColumn ++ ;
                    switch(bit){
                        case 0 :
                            arrMaps.add(new Mountain(x,y));
                            break;
                        case 1 :
                            arrMaps.add(new Road(x,y));
                            break;
                        case 2 :
                            arrMaps.add(new Spawner(x,y));
                            break;
                        case 3 :
                            arrMaps.add(new Target(x,y));
                            break;
                        case 4 :
                            arrMaps.add(new EmptyBox(x,y));
                            break;
                        case 5 :
                            arrMaps.add(new Tree(x,y));
                            break;
                    }
                    
                }
                row++;
                line = reader.readLine();
                mapRow ++ ;
            
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
            mapColumn = mapColumn / mapRow ;
            System.out.print(mapColumn + "  " + mapRow);
    }
    
    public void readFileSave () throws FileNotFoundException, IOException, ClassNotFoundException
    {
        FileInputStream fileInput = new FileInputStream (fileSave) ;
        DataInputStream dataInput = new DataInputStream (fileInput) ;
        
        mapName = new String (dataInput.readUTF()) ;
        System.out.print(mapName);
        
        
        // TowerMap 
        char [] tower_char = new char [5];
        for (int i = 0 ; i < 5 ; i ++ )
        {
            tower_char[i] = dataInput.readChar() ;
            System.out.print(tower_char[i]);
        }
        int arrTowerSize = dataInput.readInt();

        arrTower = new ArrayList<>() ;
        System.out.print(arrTowerSize);
        for (int i = 0 ; i < arrTowerSize ; i ++)
        {
            String stringTower = dataInput.readUTF() ;
            int x = dataInput.readInt() ;
            int y = dataInput.readInt() ;
            System.out.print("\n" + stringTower + "at " + x + "  " + y );
            String stringNormal = "NormalTower";
            String stringSniper = "SniperTower";
            String stringMachine = "MachineGunTower" ;
            if (stringTower.equals(stringNormal))
            {
                NormalTower tower = new NormalTower (x, y);
                arrTower.add(tower);
                System.out.print("A new Normal Tower is created at " + x + "  " + y +"\n" );
            }
            else if(stringTower.equals(stringSniper))
            {
                SniperTower tower = new SniperTower (x, y);
                arrTower.add(tower);
                System.out.print("A new Sniper Tower is created at " + x + "  " + y +"\n" );
            }
            else if (stringTower.equals(stringMachine))
            {
                MachineGunTower tower = new MachineGunTower (x, y);
                arrTower.add(tower);
                System.out.print("A new Machine Gun Tower is created at " + x + "  " + y +"\n" );
            }
        }
        
        
        
        // EnemyMap
        char [] enemy_char = new char [5];
        for (int i = 0 ; i < 5 ; i ++ )
        {
            enemy_char[i] = dataInput.readChar() ;
            System.out.print(enemy_char[i]);
        }
        int arrEnemySize = dataInput.readInt();

        
        arrEnemy = new ArrayList<>() ;
        System.out.print(arrEnemySize);
        for (int i = 0 ; i < arrEnemySize ; i ++)
        {
            String stringEnemy = dataInput.readUTF() ;
            int x = dataInput.readInt() ;
            int y = dataInput.readInt() ;
            int heart = dataInput.readInt() ;
            int orient = dataInput.readInt() ;
            System.out.print("\n" + stringEnemy + "got heart " + heart + "at " + x + "  " + y );
            String stringNormal = "NormalEnemy";
            String stringSmaller = "SmallerEnemy";
            String stringTanker = "TankerEnemy" ;
            String stringBoss = "BossEnemy" ;
            if (stringEnemy.equals(stringNormal))
            {
                NormalEnemy enemy = new NormalEnemy (x, y);
                enemy.setHealth(heart);
                enemy.setOrient(orient);
                arrEnemy.add(enemy);
                System.out.print("A new Normal Enemy is created got heart " + heart + "at " + x + "  " + y +"orient" + orient + "\n" );
            }
            else if(stringEnemy.equals(stringSmaller))
            {
                SmallerEnemy enemy = new SmallerEnemy (x, y);
                enemy.setHealth(heart);
                enemy.setOrient(orient);
                arrEnemy.add(enemy);
                System.out.print("A new Smaller Enemy is created got heart " + heart + "at " + x + "  " + y +"orient" + orient + "\n" );
            }
            else if (stringEnemy.equals(stringTanker))
            {
                TankerEnemy enemy = new TankerEnemy (x, y);
                enemy.setHealth(heart);
                enemy.setOrient(orient);
                arrEnemy.add(enemy);
                System.out.print("A new Tanker Enemy is created got heart " + heart + "at " + x + "  " + y +"orient" + orient + "\n" );
            }
            else if (stringEnemy.equals(stringBoss))
            {
                BossEnemy enemy = new BossEnemy (x, y);
                enemy.setHealth(heart);
                enemy.setOrient(orient);
                arrEnemy.add(enemy);
                System.out.print("A new Boss Enemy is created got heart " + heart + "at " + x + "  " + y +"orient" + orient + "\n" );
            }
        }
        
        for (int i = 0 ; i <arrEnemy.size() ; i ++)
        {
            System.out.print("Enemy " + (i +1 ) + "  live   " + arrEnemy.get(i).getHealth() + "  at " + arrEnemy.get(i).getX() + "  " + arrEnemy.get(i).getY() +"orient" + arrEnemy.get(i).getOrient() + "\n" );
        }
        //  Player
        this.heart = dataInput.readInt() ;
        this.coin = dataInput.readInt () ;
        this.orderRow = dataInput.readInt() ;
        this.spawnedRow = arrEnemySize ;
        this.bit = 0 ;
        arrBullet = new ArrayList<>(10) ;
        arrSkill = new ArrayList<>(10) ;
        arrStone = new ArrayList<>(10) ;
        for (int i = 0 ; i < 3 ; i ++)
        {
            skillCost [i] = 50 * i ; 
        }
        readMap(mapName) ;
        defineMenuTower() ;
        initBoss() ;
       
        fileInput.close();
        dataInput.close();
        
        
        
        
    }
    
    public void writeFileSave () throws IOException
    {
        if (this.fileSave.exists() == true)
        {
            this.fileSave.delete() ;
            this.fileSave.createNewFile(); 
        }
        else this.fileSave.createNewFile(); 
        
            FileOutputStream fileOutput = new FileOutputStream (fileSave) ;
            DataOutputStream dataOutput = new DataOutputStream (fileOutput) ;
           
            dataOutput.writeUTF(mapName);
            
            //Tower
            if (arrTower.isEmpty() == false)
            {
                dataOutput.writeChars("Tower");
                dataOutput.writeInt(arrTower.size());
                for (int i = 0 ; i < arrTower.size() ; i++)
                {
                    String stringTower ;
                    int x = arrTower.get(i).getX() ;
                    int y = arrTower.get(i).getY() ;
                    if (arrTower.get(i) instanceof NormalTower)
                    {
                        stringTower = "NormalTower" ;
                        dataOutput.writeUTF(stringTower);
                    }
                    if (arrTower.get(i) instanceof SniperTower)
                    {
                        stringTower = "SniperTower" ;
                        dataOutput.writeUTF(stringTower);
                    }
                    if (arrTower.get(i) instanceof MachineGunTower)
                    {
                        stringTower = "MachineGunTower" ;
                        dataOutput.writeUTF(stringTower);
                    }
                    dataOutput.writeInt(x);
                    dataOutput.writeInt(y);
                }
                
            }
            
            
            // Enemy
            if (arrEnemy.isEmpty() == false)
            {
                dataOutput.writeChars("Enemy");
                dataOutput.writeInt(arrEnemy.size());
                for (int i = 0 ; i < arrEnemy.size() ; i++)
                {
                    String stringEnemy ;
                    int x = arrEnemy.get(i).getX() ;
                    int y = arrEnemy.get(i).getY() ;
                    int heart = arrEnemy.get(i).getHealth() ;
                    int orient = arrEnemy.get(i).getOrient() ;
                    if (arrEnemy.get(i) instanceof NormalEnemy)
                    {
                        stringEnemy = "NormalEnemy" ;
                        dataOutput.writeUTF(stringEnemy);
                    }
                    if (arrEnemy.get(i) instanceof SmallerEnemy)
                    {
                        stringEnemy = "SmallerEnemy" ;
                        dataOutput.writeUTF(stringEnemy);
                    }
                    if (arrEnemy.get(i) instanceof TankerEnemy)
                    {
                        stringEnemy = "TankerEnemy" ;
                        dataOutput.writeUTF(stringEnemy);
                    }
                    if (arrEnemy.get(i) instanceof BossEnemy)
                    {
                        stringEnemy = "BossEnemy" ;
                        dataOutput.writeUTF(stringEnemy);
                    }
                    dataOutput.writeInt(x);
                    dataOutput.writeInt(y);
                    dataOutput.writeInt(heart);
                    dataOutput.writeInt(orient) ;
                }
            }
            
            // Player
            dataOutput.writeInt(this.heart);
            dataOutput.writeInt(this.coin) ;
            dataOutput.writeInt(this.orderRow);
            
            fileOutput.close();
            dataOutput.close();
        
    }
    
    public void draw(Graphics2D g2d){
        try{
            for (GameTile map:arrMaps) {
               
                map.draw(g2d);
            }
          if (isDownDisplay == true)
          {
              Image downMenu = new ImageIcon(getClass().getResource("/images/menu/hideDownMenu.png")).getImage();
              g2d.drawImage (downMenu, 0, this.gameField.gameStage.getHeight() - downMenu.getHeight(null) - 30, downMenu.getWidth(null), downMenu.getHeight(null), null) ;
          }
          else if (isDownDisplay == false)
          {
              if (isDownWait == true)
              {
                  Image waitDownMenu = new ImageIcon(getClass().getResource("/images/menu/waitDownMenu.png")).getImage();
                  g2d.drawImage(waitDownMenu, 0, this.gameField.gameStage.getHeight() - waitDownMenu.getHeight(null) - 30, waitDownMenu.getWidth(gameField) , 100, null) ;
              }
              else
              {
                  if (isBuying == false && isTowerMenu == true )
                    {
                    
                        Image buyingTowerMenu = new ImageIcon(getClass().getResource("/images/menu/buyingTowerMenu.png")).getImage();
                        g2d.drawImage(buyingTowerMenu, 0, this.gameField.gameStage.getHeight() - buyingTowerMenu.getHeight(null) - 30, buyingTowerMenu.getWidth(gameField) , 100, null) ;
                        for (int i = 0 ; i < 3 ; i ++)
                        {
                            towerList[i].draw(g2d);
                            if (this.coin < towerList[i].getCost())
                            {
                                g2d.setColor (new Color (255,0,0,100)) ;
                                g2d.fillOval(towerList[i].getX() - 10,towerList[i].getY() - 10, 70, 70);
                            }
                        } 
                    }
                if (isSkillMenu == true )
                {
                    Image choosingSkillMenu = new ImageIcon(getClass().getResource("/images/menu/choosingSkillMenu.png")).getImage();
                    g2d.drawImage(choosingSkillMenu, 0, this.gameField.gameStage.getHeight() - choosingSkillMenu.getHeight(null) - 30, choosingSkillMenu.getWidth(gameField) , 100, null) ;
                    for (int i = 0 ; i < 3 ; i ++)
                        {
                            if (this.coin < skillCost[i])
                            {
                                g2d.setColor (new Color (255,0,0,100)) ;
                                g2d.fillRect(towerList[i].getX() -5,towerList[i].getY() - 20, 70, 70);
                            }
                        } 
                }
                  
             }
              
          }
            if (isBuying == true )
            {
                //isDownDisplay = false ;
                isTowerMenu = false ;
                Image tower_img ;
                switch (id)
                {
                    case 0: tower_img = new ImageIcon(getClass().getResource("/images/tower/normalTower.png")).getImage(); break ;
                    case 1: tower_img = new ImageIcon(getClass().getResource("/images/tower/sniperTower.png")).getImage(); break ;
                    case 2: tower_img = new ImageIcon(getClass().getResource("/images/tower/machineGun.png")).getImage(); break ;
                    default : tower_img = new ImageIcon(getClass().getResource("/images/tower/normalTower.png")).getImage(); break ;
                }
                
                g2d.drawImage(tower_img, mouseXPos - 50 /2, mouseYPos - 50 /2 , 50, 50, null) ;
            }
            else if (isBuying = false)
            {
                //g2d.dispose();
            }
            if (isUpdateDisplay == true)
            {
                Image updateTowerMenu = new ImageIcon(getClass().getResource("/images/menu/updateTowerMenu.png")).getImage();
                g2d.drawImage(updateTowerMenu, updateXPos, updateYPos ,updateTowerMenu.getWidth(null), updateTowerMenu.getHeight(null) , null) ;
                int pos = FindTowerPosition (sealXPos, sealYPos) ;
                if (pos >=0)
                {
                    Image update_1 = arrTower.get(pos).updateImage.get(0) ;
                    Image update_2 = arrTower.get(pos).updateImage.get(1) ;
                    g2d.drawImage(update_1, updateXPos + 100, updateYPos + 100, 50, 50, null);
                    g2d.drawImage(update_2, updateXPos + 150, updateYPos + 100, 50, 50, null);
                    for (int i = 0 ; i < 5 ; i ++)
                    {
                        if (this.coin < arrTower.get(pos).updateCost[i])
                        {
                            g2d.setColor (new Color (255,0,0,100)) ;
                            switch (i)
                            {
                                case 0 : g2d.fillRect(updateXPos , updateYPos + 50, 100, 50); break ;
                                case 1 : g2d.fillRect(updateXPos , updateYPos + 100, 100, 50); break ;
                                case 2 : g2d.fillRect(updateXPos , updateYPos + 150, 100, 50); break ;
                                case 3 : g2d.fillRect(updateXPos + 100 , updateYPos + 100, 50, 50); break ;
                                case 4 : g2d.fillRect(updateXPos + 150, updateYPos + 100, 50, 50); break ;
                            }
                        }
                    }
                }
                
            }
            // coin + heart for player
            Image heart_player = new ImageIcon(getClass().getResource("/images/heart_player.png")).getImage();
            g2d.drawImage(heart_player, 10, 10, 150, 50, null) ;
            g2d.drawString("Health: " + heart, 10 + 50, 10 + 30);
            Image coin_player = new ImageIcon(getClass().getResource("/images/coin_player.png")).getImage();
            g2d.drawImage(coin_player, 10, 10 + 5 + 50, 150, 50, null) ;
            g2d.drawString("Coin: " + coin, 10 + 50 , 10 + 30 + 5 + 50);
            
            // tower
            for (Tower tower:arrTower){
                tower.draw(g2d);
            }
            if (isSkilling == false || skillId == 0 || skillId == 1)
            {
                for (Enemy enemy:arrEnemy){
                    enemy.draw(g2d);
                }    
            }
            else 
            {
                if (skillId == 2)
                {
                    int limit = arrSkill.size() ;
                    for (int i = 0 ; i < arrEnemy.size() ; i ++)
                    {
                        arrEnemy.get(i).draw(g2d);
                        if (i < limit)
                        {
                            Image image = arrSkill.get(i);
                            g2d.drawImage(image, arrEnemy.get(i).getX(),arrEnemy.get(i).getY() + 5,20, 20,null);
                        }
                    }
                }
            }
          
            if (skillId == 0)
            {
                for (Stone stone : arrStone)
                {
                    if (stone.y <= stone.limit )
                    {
                        stone.y ++ ;
                    }
                   
                    g2d.drawImage (stone.image, stone.x, stone.y, 50, 50, null);
                }
            }
            for (Bullet bullet:arrBullet){
                if (bullet != null) bullet.draw(g2d);
            }
        }catch (ConcurrentModificationException ex){
            ex.printStackTrace();
        }
    }

    
    public void setBit(int bit) {
        this.bit = bit;
    }
    
    public void AI(){    
        int i;
        for (i=arrEnemy.size()-1;i>=0; i--) {
            arrEnemy.get(i).changeOrient(arrMaps);
            arrEnemy.get(i).move();
            if(arrEnemy.get(i).checkFinish(arrMaps)){
                
                int hp = 0 ;
                if (arrEnemy.get(i) instanceof NormalEnemy == true)
                        {
                            hp = 1 ;
                        }
                        else if (arrEnemy.get(i) instanceof SmallerEnemy == true)
                        {
                            hp = 1 ;
                        }
                        else if (arrEnemy.get(i) instanceof TankerEnemy == true)
                        {
                            hp = 2 ;
                        }
                        else if (arrEnemy.get(i) instanceof BossEnemy == true)
                        {
                            hp = 3;
                        }
                       
                        this.heart -= hp ;
                        arrEnemy.remove(i);
            }
            if(arrEnemy.size()==0){
                orderRow++;
                initBoss();
            }
            if (isSkilling == true)
            {
                if (skillId == 0 || skillId == 2)
                {
                    if (i < 10)
                    {
                        int hp = arrEnemy.get(i).getHealth() ;
                        arrEnemy.get(i).setHealth(hp - 1);
                    }
                    
                }
                
            }
        }
        if (arrStone.isEmpty() == false)
          {
            for (int ind = 0 ; ind < arrStone.size() ; ind ++ )
               {
                    if (arrStone.get(ind).y >= arrStone.get(ind).limit)
                                arrStone.remove(ind) ;
                  }
                }
        
    }
    
    public int start(){
        GameTile spawner = getSpawner();
        int orientStart;
        if(spawner.getX()==0) {
            orientStart = Enemy.RIGHT;
        } else if (spawner.getX()<=GameStage.W_FRAME 
                && spawner.getX()>=GameStage.W_FRAME-50) {
            orientStart = Enemy.LEFT;
        } else if (spawner.getY()==0){
            orientStart = Enemy.DOWN;
        } else {
            orientStart = Enemy.UP;
        }
        return orientStart;
    }
    
    public GameTile getSpawner(){
        GameTile spawner = null;
        for(GameTile map: arrMaps){
            if(map instanceof Spawner){
                spawner = map;
                break;
            }
        }
        return spawner;
    }

    public void enemyUpdate ()
    {
        for (int i = 0 ; i < arrEnemy.size() ; i ++)
        {
            if (arrEnemy.get(i) != null)
            {
                Enemy enemy = arrEnemy.get(i).update() ;
                if (enemy == null) {   
                    this.coin += arrEnemy.get(i).getReward() ;
                    arrEnemy.remove(i);
                }
                else {
                    arrEnemy.set(i, enemy) ;
                }
            }
        }
    }
    
    public void towerUpdate ()
    {
        for (Tower tower: arrTower)
        {
            if (tower != null) TowerAttack (tower) ;
        }
    }
    
    public void bulletUpdate ()
    {
        for (int i = 0 ; i < arrBullet.size() ; i ++)
        {
            if (arrBullet.get(i)!= null)
            {
                    arrBullet.get(i).move(); 
                    arrBullet.get(i).updateTarget();
                    if (arrBullet.get(i).target == null)
                    {
                        arrBullet.set(i, null) ;
                        System.out.print("A Bullet Go!");
                        
                    }
            }
        }
    }
    public void TowerAttack (Tower tower)
    {
        if (tower.target == null)
        {
            if (tower.attackWait > tower.maxAttackWait)
            {
                Enemy currentEnemy = tower.defineEnemy(arrEnemy, tower.getX(), tower.getY());
                if (currentEnemy != null)
                {
                    tower.TowerAttack(tower.getX(), tower.getY(), currentEnemy, this.arrBullet );
                    tower.target = currentEnemy ;
                    tower.attackTime = 0 ;
                    tower.attackWait = 0 ;
                    
                    System.out.println ("Attack enemy! Health:" + currentEnemy.getHealth() + "at x: " + currentEnemy.getX() + "y: " + currentEnemy.getY() );
                }
                else if (currentEnemy == null)
                {
                    arrBullet.clear();
                }
            }
            else 
            {
                tower.attackWait += 1 ;
            }
        }
        else 
        {
            if (tower.attackTime < tower.maxAttackTime)
            {
                tower.attackTime += 1 ;
            }
            else 
            {
                tower.target = null ;
            }
        }
    }
    
     
    
    //MouseListener
    public void defineMenuTower ()
    {
        towerList[0] = new NormalTower (310 + 20,this.gameField.gameStage.getHeight() - 100  );
        towerList[1] = new SniperTower (430 + 20,this.gameField.gameStage.getHeight() - 100 );
        towerList[2] = new MachineGunTower (550 + 20,this.gameField.gameStage.getHeight() - 100 );
    }
    public void updateMouse (MouseEvent e)
    {
        // dispaly hideDownMenu, click, menu appears
        if (isDownDisplay == true)   
        {
            if (e.getX() >= 710 && e.getX() <= this.gameField.gameStage.getWidth())
            {
                if (e.getY() >= this.gameField.gameStage.getHeight() - 50 - 30 && e.getY() <= this.gameField.gameStage.getHeight())
                {
                   // isTowerMenu = true ;
                   isDownWait = true ;
                    isDownDisplay = false ;
                }
            }
        }
        else 
        {
            if (this.gameField.Starting == true && isDownDisplay == false && isBuying == false )
        {
                if (isDownWait == true)
                 {
                     
                    if (e.getY() >=  this.gameField.gameStage.getHeight() - 100 + 20 && e.getY() <= this.gameField.gameStage.getWidth() )
                    {
                         if (e.getX() >= 10 && e.getX() <= 110)
                        {
                            isTowerMenu = true ;
                            isDownWait = false ;
                        }
                         if (e.getX() >= 130 && e.getX() <= 230)
                         {
                             isSkillMenu = true ;
                             isDownWait = false ;
                         }
                    }
                    if (e.getY() > this.gameField.gameStage.getHeight() - 50 - 30 && e.getY() < this.gameField.gameStage.getHeight())
                    {
                        if (e.getX() >= 290 && e.getX() <= 400)
                        {
                            if (this.gameField.Starting == true && this.gameField.isWaiting == true)
                            {
                                this.gameField.isWaiting = false ;   
                                isDownWait = false ;
                                isDownDisplay = true ;
                            }
                            else if (this.gameField.isWaiting == false)
                            {
                                this.gameField.isWaiting = true ;
                                isDownWait = false ;
                                isDownDisplay = true ;
                            }
                        }
                        if (e.getX() >= 410 && e.getX() <= 490)
                        {
                            if (fileSave.exists() == true) 
                            {
                                fileSave.delete() ;
                            }
                            try {
                                    writeFileSave();
                            } catch (IOException ex) {
                                    Logger.getLogger(GameField.class.getName()).log(Level.SEVERE, null, ex);
                            }
                                System.exit(0);
                        }
                        if (e.getX() >= 510 && e.getX() <= 590)
                        {
                             if (this.gameField.isRunning == true && this.gameField.isExiting == false)
                                    this.gameField.isExiting = true ;
                        }
                        if (e.getX() >= 610 && e.getX() <= 720)
                        {
                            if (this.gameField.gameStage.checkGame.keepPlaying == false)
                            {
                                this.gameField.gameStage.checkGame.keepPlaying = true ;
                                this.gameField.isRunning = false ;
                            }
                        }
                    }
                 }
                else if (isDownWait == false)
                {
                    if (e.getY() >=  this.gameField.gameStage.getHeight() - 100 + 20 && e.getY() <= this.gameField.gameStage.getWidth() )
                    {
                         if (e.getX() >= 10 && e.getX() <= 110)
                        {
                            if (isTowerMenu == false && isSkillMenu== true)
                            {
                                isSkillMenu = false ;
                                isTowerMenu = true ;
                            }
                        }
                         if (e.getX() >= 130 && e.getX() <= 230)
                         {
                             if (isSkillMenu == false && isTowerMenu == true)
                             {
                                isSkillMenu = true ;
                                isTowerMenu = false ;
                             }
                            
                         }
                    }
                    
                    if (e.getX() >= 310 &&e.getX() <= 650 )
                    {
                        if (e.getY() > this.gameField.gameStage.getHeight() - 50 - 30 && e.getY() < this.gameField.gameStage.getHeight() )
                        {
                            for (int i = 1 ; i <= 3 ; i++)
                            {
                                if (e.getX() >= 310 + (i-1) * 100  && e.getX() <= 310  + i * 100  )
                                {
                                    if (isTowerMenu == true) {
                                        if (this.coin > this.towerList[i -1].getCost())
                                        {
                                            isBuying = true ;
                                            id = i - 1 ;
                                        }
                                    }
                                    else if (isSkillMenu == true) {
                                        
                                        if (isSkilling == false || skillId <= 0)
                                        {
                                            if (this.coin > this.skillCost[i-1])
                                            {
                                                isSkilling = true ;
                                                skillId = i -1 ;
                                            }
                                        }    
                                    }
                                    break ;
                                }
                            }
                        }
                    }  
                }
                if (e.getX() >= 710 && e.getX() <= this.gameField.gameStage.getWidth())
                {
                    if (e.getY() >= this.gameField.gameStage.getHeight() - 50 - 30 && e.getY() <= this.gameField.gameStage.getHeight())
                    {
                        // isTowerMenu = true ;
                        isDownDisplay = true ;
                        isDownWait = false ;
                        isTowerMenu = false ;
                        isSkillMenu = false ;
                    }
                }
               
        }
        }
        
        
        // when you exiting
        if (this.gameField.isExiting == true)
        {
            if (e.getY() >= gameField.gameStage.getHeight() / 2 - 200 / 2 + 100&&e.getY() <= gameField.gameStage.getHeight() / 2 -200 / 2 + 160)
            {
                if (e.getX() >= gameField.gameStage.getWidth() / 2 - 400 / 2 + 40 &&e.getX() <= gameField.gameStage.getWidth() / 2 - 400 / 2 + 180)
                {
                    if (fileSave.exists() == true) 
                    {
                        fileSave.delete() ;
                    }
                    try {
                        writeFileSave();
                    } catch (IOException ex) {
                        Logger.getLogger(GameField.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.exit(0);
               } 
            }
            if (e.getX() >= gameField.gameStage.getWidth() / 2 - 400 / 2 + 220 &&e.getX() <= gameField.gameStage.getWidth() / 2 - 400 / 2 + 360)
            {
                if (fileSave.exists() == true) 
                    {
                        fileSave.delete() ;
                    }
                this.gameField.isExiting = false ;
                isDownDisplay = true ;
            }
            
        }
        
        // when you want to update your own towers
        if (isUpdateDisplay == true)
        {
            int id = - 1 ;
            if (e.getX() >= updateXPos && e.getX() <= updateXPos + 100)
            {
                for (int i = 1 ; i <= 3 ; i ++)
                {
                    if (e.getY() >= updateYPos  + 50 + (i -1) * 50 && e.getY() <= updateYPos + (i +1 ) * 50 + 50 )
                    {
                        id = i - 1 ;
                        break ;
                    }
                }
            }    
            if (e.getY() > updateYPos + 100 + 25 && e.getY() < updateYPos + 150 + 25)
            {
                if (e.getX() > updateXPos + 100 && e.getX() < updateXPos + 150)
                    id = 3 ;
                if (e.getX() > updateXPos + 150 && e.getX() < updateXPos +200  )
                    id = 4 ;
              
            }
            if (e.getX() >= updateXPos && e.getX() <= updateXPos + 200)
            {
                if (e.getY() >= updateYPos + 250 && e.getY() <= updateYPos + 300)
                    
                {
                    isSealing = true ;
                    SealTower (sealXPos, sealYPos);
                }
            }
           if (id >= 0)
           {
               int pos = FindTowerPosition (sealXPos, sealYPos) ;
               if (pos >= 0)
               {
                   if (this.coin > arrTower.get(pos).updateCost[id])
                   {
                       arrTower.get(pos).updateLevel(id);
                       this.coin -= arrTower.get(pos).updateCost[id] ;
                   }
               }
           }
        }
         isUpdateDisplay = false ;
    }
   public boolean PlaceTower (int x, int y)
    {
            int xPos = x / 50 ;
            int yPos = y / 50 ;
            // nếu map tại (xPos,yPos) == EmptyBox và tiền >= giá mua tháp thì thêm tháp vào arrTower
            
            if (arrMaps.get(yPos * mapColumn + xPos) instanceof EmptyBox  == true) {
               EmptyBox emptyBox = (EmptyBox) arrMaps.get(yPos * mapColumn + xPos);
               if (emptyBox.isEmpty == true)
               {
                switch (id)
                {
                    case 0 : if (this.coin >= this.towerList[0].getCost ()){
                        this.arrTower.add(new NormalTower (xPos * 50, yPos * 50)) ;
                        this.coin -= this.towerList[0].getCost () ;
                    }
                        else System.out.println ("You can't afford for this Tower!" ) ;break ;
                    case 1 : if (this.coin >= this.towerList[1].getCost ()){
                        this.arrTower.add(new SniperTower (xPos * 50, yPos * 50)) ;
                        this.coin -= this.towerList[1].getCost () ;
                    } 
                        else System.out.println ("You can't afford for this Tower!" ) ;break ;
                    case 2 : if (this.coin >= this.towerList[2].getCost ()){
                        this.arrTower.add(new MachineGunTower (xPos * 50, yPos * 50)) ;
                        this.coin -= this.towerList[2].getCost () ;
                    } 
                        else System.out.println ("You can't afford for this Tower!" ) ;break ;
                }
               notEmptyBox = false ;
               emptyBox.isEmpty = false ;
               arrMaps.set(yPos * mapColumn + xPos, emptyBox) ;
               return true ;
               }
                notEmptyBox = true ; return false ;
            }
            notEmptyBox = false ;
            return false ;
    }
   
   public int FindTowerPosition (int x, int y)
   {
       for (int i = 0 ; i < arrTower.size() ; i ++)
       {
           if (arrTower.get(i).getX() <= x && arrTower.get(i).getX() + 50 >= x  && arrTower.get(i).getY() <= y && arrTower.get(i).getY() + 50 >= y )
            {
                return i ;
            }
       }
       return -1 ;
   }
    public void SealTower (int x, int y)
    {
        int pos = FindTowerPosition (x, y) ;
            if (pos >= 0)
            {
                if (isSealing == true)
                {
                    this.coin += arrTower.get(pos).getCost() ;
                    int xPos = arrTower.get(pos).getX() / 50 ;
                    int yPos = arrTower.get(pos).getY() / 50 ;
                    EmptyBox emptyBox = (EmptyBox) arrMaps.get(yPos * mapColumn + xPos) ;
                    
                    emptyBox.isEmpty = true ;
                    arrMaps.set(yPos * mapColumn + xPos, emptyBox) ;
                    arrTower.remove(pos) ;
                    isSealing = false ;
                    return ;
                }
            }
        isSealing = false ;
    }
    public void MousePosition (MouseEvent e)
    {
        mouseXPos = e.getX() ;
        mouseYPos = e.getY() ;
    }
    public void UpdateTowerMenuPosition (MouseEvent e)
    {
        int pos = FindTowerPosition (e.getX(), e.getY()) ;
        if (pos >= 0)
        {
            if (arrTower.get(pos).getY() >= this.gameField.gameStage.getHeight() - 200 )
            {
                updateXPos = arrTower.get(pos).getX() + 50 ;
                updateYPos = arrTower.get(pos).getX()   ;
            }
            else 
            {
                updateXPos = arrTower.get(pos).getX() + 50 ;
                updateYPos = arrTower.get(pos).getY() + 50 ;
            }
        }
    }
    
   public class MouseHandler implements MouseListener, MouseMotionListener
    {
        @Override
        public void mouseClicked(MouseEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1)
                {
                    if (isBuying == true)
                    {
                        placeTower = PlaceTower (e.getX(), e.getY()) ;  
                        if (placeTower == true && notEmptyBox == false) 
                        {
                          isDownDisplay = true ;
                           isBuying = false ;
                        }
                        else if (placeTower == false && notEmptyBox == true)
                        {
                            isBuying = false ;
                        }
                    }
                }
                updateMouse (e);
                if (e.getButton() == MouseEvent.BUTTON3)
                {
                    if (isBuying == true)
                    {
                        isBuying = false ;
                    }
                    
                    if (isUpdateDisplay == false && isBuying == false)
                    {
                        
                        int xPos = e.getX() / 50;
                        int yPos = e.getY() / 50 ;
                        if (arrMaps.get(yPos * mapColumn + xPos) instanceof EmptyBox)
                        {
                            EmptyBox cell = (EmptyBox) arrMaps.get(yPos * mapColumn + xPos) ;
                            if (cell.isEmpty == false)
                            {
                                sealXPos = e.getX() ;
                                sealYPos = e.getY() ;
                                UpdateTowerMenuPosition (e) ;
                                isUpdateDisplay = true ;

                            }
                        }
                        
                    }
                }
                if (isSkilling == true && skillId >= 0 && skillEffectTime == 0)
                    {
                        initSkill (skillId);
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
            MousePosition(e) ;
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            MousePosition(e) ;
        }
        
    }
    
    
    
    // KeyListener
    public void keyESC ()
        {
            if (this.gameField.isRunning == true && this.gameField.isExiting == false)
            this.gameField.isExiting = true ;
            else if (this.gameField.isExiting == true)
            {
                this.gameField.isExiting = false ;
            }
        }

    public void keyENTER ()
        {
           if (this.gameField.isExiting == true)
           {
               this.gameField.isRunning = false ;
               if (fileSave.exists() == true) 
                {
                    fileSave.delete() ;
                }
                try {
                    writeFileSave();
                } catch (IOException ex) {
                    Logger.getLogger(GameField.class.getName()).log(Level.SEVERE, null, ex);
                }
           }
           else if (this.gameField.isWaiting == true && isBuying == false && isDownDisplay == false)
            {this.isDownDisplay = true;}
           else if (this.gameField.isWaiting == false && isBuying == false && isDownDisplay == false)
           {
               this.isDownDisplay = true;
           }
           else this.gameField.isWaiting = false ;
        }
        
    public  void keySPACE() 
        {
           if (this.gameField.Starting == false && this.gameField.isWaiting == true)
            this.gameField.Starting = true;
        }
    public void keyEnd () throws IOException 
    {
        if (this.fileSave.exists()== true)
       {
           this.fileSave.delete() ;
           this.writeFileSave();
           
       }
       else 
       {
           this.writeFileSave(); 
       }
       System.exit(0);
    }
    public class KeyHandler implements KeyListener
    {
        @Override
        public void keyPressed(KeyEvent e) 
        {
            int keyCode = e.getKeyCode();
            //System.out.println(e.getKeyCode() + "\n");
            if (keyCode == KeyEvent.VK_ESCAPE)
            {
               keyESC();
            }
            if (keyCode == KeyEvent.VK_ENTER)
            {
               keyENTER();
            }
            if (keyCode == KeyEvent.VK_SPACE)
            {
               keySPACE();
            }
            if (keyCode == KeyEvent.VK_END)
            {
                try {
                    keyEnd () ;
                } catch (IOException ex) {
                    Logger.getLogger(GameEntity.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
       
        @Override
        public void keyReleased(KeyEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        @Override
        public void keyTyped(KeyEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
    
    
    public void initSkill (int id)
    {
        switch (id)
        {
            case 0: 
                arrStone = new ArrayList<>(10) ;
                Image image_1 = new ImageIcon(getClass().getResource("/images/skill/skill0.png")).getImage();
                for (int i = 0 ; i <10 ; i ++)
                {
                    Random rd = new Random () ;
                    int x = i * 70 + rd.nextInt(50)  ;
                    int y =  rd.nextInt(100) - 110 ;
                    int limit = rd.nextInt(5) * 70 + rd.nextInt(100) + 100 ;
                    Stone stone = new Stone (x , y,limit, image_1) ;
                    arrStone.add(stone) ;
                }
                skillEffectTime = 500 ;
                this.coin -= this.skillCost[2] ;
                break ;
            case 1: skillPause  = true ; skillEffectTime = 300 ;
                 this.coin -= this.skillCost[2] ;
                break ;
            case 2: 
                arrSkill = new ArrayList<>(10) ;
                Image image_2 = new ImageIcon(getClass().getResource("/images/skill/skill2.png")).getImage();
                if (arrEnemy.isEmpty() == false)
                {
                    for (int i = 0 ; i< 10 ; i ++)
                    {
                        arrSkill.add(image_2) ;
                    }
                }
                skillEffectTime = 500 ;
                this.coin -= this.skillCost[2] ;
                break ;
        }
    }
    
    public class Stone 
    {
        public int x ;
        public int y ;
        public int limit ;
        Image image ;
        SoundManager soundManager ;
        Clip clip ;
       // public SoundManager soundManager = new SoundManager ();
        //public Clip clip = soundManager.getSound("Laser_Shot.wav") ;
        public Stone (int x, int y, int limit, Image image)
        {
            this.x = x ;
            this.y = y ;
            this.limit = limit ;
            this.image = image ;
           // soundManager = new SoundManager ();
           // clip = soundManager.getSound("Mortal_Shot.wav") ;
            //this.clip.start(); 
        }
    }
    
    
}
