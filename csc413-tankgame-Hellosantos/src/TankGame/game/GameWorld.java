/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TankGame.game;


import TankGame.GameConstants;
import TankGame.Launcher;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;



/**
 *
 * @author anthony-pc
 */
public class GameWorld extends JPanel implements Runnable {

    static final int worldHeight = 800;
    static final int worldWidth = 1280;
    private BufferedImage world;
    private BufferedImage lifeImg;
    private BufferedImage bulletImg;
    private Graphics2D buffer;
    private static Tank t1;
    private static Tank t2;
    private static Bullet b;
    private Launcher lf;
    private long tick = 0;
    private ObjectHandler  handler;

    private int t1CameraX, t1CameraY, t1XBound,t1YBound;
    private int t2CameraX, t2CameraY, t2XBound, t2YBound;

    private ArrayList<Bullet> bullets;


    public GameWorld(Launcher lf){
        this.lf = lf;
    }

    @Override
    public void run(){
        try {
          //this.resetGame();
            while (true) {

                /*
                 * simulate an end game event
                 * we will do this with by ending the game when drawn 2000 frames have been drawn
                 */
              //  if(this.tick > 2000){
              //      this.lf.setFrame("end");
                //    return;
               // }


/*
                    for (int i = 0; i < this.gameObjects.size(); i++) {
                        if (((this.gameObjects.get(i) instanceof BreakableWall) && ((BreakableWall) this.gameObjects.get(i)).getHealth() == 0)) {
                            this.gameObjects.remove(i);
                        }

                        this.gameObjects = this.CH.collisionHandler(this.gameObjects);

                    }


**/             loadArrayList();

                this.tick++;
                t1.update(); // update tank1
                t2.update(); // update tank2
                this.checkShoot();;
                this.shootUpdate();
                handler.update();
                this.repaint();   // redraw game
                Thread.sleep(1000 / 144); //sleep for a few milliseconds
            }
        } catch (InterruptedException ignored) {
            System.out.println(ignored);
        }
    }

    private void loadArrayList(){
        bullets = new ArrayList<>();
    }

    /**
     * Reset game to its initial state.
     *
    public void resetGame(){
        this.tick = 0;
        this.t1.setX(tank1SpawnX);
        this.t1.setY(tank1SpawnY);
        this.t2.setX(tank2SpawnX);
        this.t2.setY(tank2SpawnY);
    }

*/
    /**
     * Load all resources for Tank Wars Game. Set all Game Objects to their
     * initial state as well.
     */
    public void gameInitialize() {
        handler = new ObjectHandler();
        this.world = new BufferedImage(GameWorld.worldWidth,
                GameWorld.worldHeight,
                BufferedImage.TYPE_INT_RGB);

        BufferedImage t2img = null, t1img = null, floorImg = null, unbreakableWallBorderImg = null, unbreakableWallImg = null, breakableWallImg = null, powerUpImg = null;
        try {
            /*
             * note class loaders read files from the out folder (build folder in Netbeans) and not the
             * current working directory.
             */


            // Set BreakableWall Image
            breakableWallImg = ImageIO.read(getClass().getResource("/StoneWall.png"));
            BreakableWall.setBreakableWallImage(breakableWallImg);

            // Set UnbreakableWall Image
            unbreakableWallImg = ImageIO.read(getClass().getResource("/BrickWall.png"));
            Wall.setUnbreakableWall(unbreakableWallImg);

            unbreakableWallBorderImg = ImageIO.read(getClass().getResource("/BrickWall.png"));
            Wall.setUnreabkableWallBorder(unbreakableWallBorderImg);

            // Sets floor image
            floorImg = ImageIO.read(getClass().getResource("/Background.bmp"));
            Wall.setFloorImage(floorImg);

            // Sets Tank image
            t1img = ImageIO.read(getClass().getResource("/Tank1.png"));
            t2img = ImageIO.read(getClass().getResource("/Tank2.png"));

            // Set PowerUp image
            powerUpImg = ImageIO.read(getClass().getResource("/HeartPowerUp.png"));
            PowerUp.setPowerUpImage(powerUpImg);

            // Set Life image
            lifeImg = ImageIO.read(getClass().getResource("/LifeIcon.png"));

            //Set Bullet image
            bulletImg = ImageIO.read(getClass().getResource("/Shell.png"));
            Bullet.setBulletImage(bulletImg);

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        // Tank1 Specs
        int tank1SpawnX = 100;
        int tank1SpawnY = 100;
        int tank1Angle = 0;
        int tank1Speed = 3;
        t1 = new Tank(tank1SpawnX, tank1SpawnY,t1img.getWidth(),t1img.getHeight(), ID.Player1, handler, t1img, tank1Angle,tank1Speed);

        // Tank 2 Specs
        int tank2SpawnX = 1100;
        int tank2SpawnY = 100;
        int tank2Angle = 180;
        int tank2Speed = 3;
        t2 = new Tank(tank2SpawnX, tank2SpawnY,t2img.getWidth(),t2img.getHeight(), ID.Player2, handler, t2img, tank2Angle,tank2Speed);

        TankControls();


        //Adding floorimage
        for(int i = 0; i < GameWorld.worldWidth; i = i + 320){
            for(int j = 0; j < GameWorld.worldHeight; j = j +240){
                handler.addObject(new Wall(i,j, floorImg, 32,32, ID.Wall,true));
            }
        }

        // WIDTH = 40 HEIGHT = 50
        int[] newArrayMap = {
                1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
                1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,
                1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,
                1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,
                1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,
                1,0,0,0,0,0,0,0,4,2,2,2,2,2,4,4,4,4,4,4,4,4,4,4,4,2,2,2,2,2,2,4,0,0,0,0,0,0,0,1,
                1,0,0,0,0,0,0,0,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4,0,0,0,0,0,0,0,1,
                1,0,0,0,0,0,0,0,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4,0,0,0,0,0,0,0,1,
                1,0,0,0,0,0,0,0,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4,0,0,0,0,0,0,0,1,
                1,0,0,0,0,0,0,0,0,0,0,0,0,4,4,4,4,4,4,4,4,4,4,4,4,4,0,0,0,0,0,0,0,0,0,0,0,0,0,1,
                1,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,2,0,0,0,0,0,2,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,1,
                1,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,2,0,0,0,0,0,2,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,1,
                1,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,2,0,0,3,0,0,2,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,1,
                1,0,0,0,0,0,0,4,0,0,0,0,0,2,0,0,2,0,0,0,0,0,2,0,0,2,0,0,0,0,0,0,4,0,0,0,0,0,0,1,
                1,0,0,0,0,0,0,4,0,0,0,0,0,4,4,4,4,4,4,4,4,4,4,4,4,4,0,0,0,0,0,0,4,0,0,0,0,0,0,1,
                1,0,0,0,0,0,0,4,0,0,0,0,0,0,0,0,0,0,0,4,0,0,0,0,0,0,0,0,0,0,0,0,4,0,0,0,0,0,0,1,
                1,2,2,2,2,2,2,4,0,0,0,0,0,0,0,0,0,0,0,4,0,0,0,0,0,0,0,0,0,0,0,0,4,2,2,2,2,2,2,1,
                1,0,0,0,0,0,0,4,0,0,0,0,0,0,0,0,0,0,0,4,0,0,0,0,0,0,0,0,0,0,0,0,4,0,0,0,0,0,0,1,
                1,0,0,0,0,0,0,4,0,0,0,0,0,0,0,0,0,0,0,4,0,0,0,0,0,0,0,0,0,0,0,0,4,0,0,0,0,0,0,1,
                1,0,0,0,0,0,0,4,0,0,0,0,0,0,0,0,0,0,0,4,0,0,0,0,0,0,0,0,0,0,0,0,4,0,0,0,0,0,0,1,
                1,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,4,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,1,
                1,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,4,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,1,
                1,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,4,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,1,
                1,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,4,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,1,
                1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
        };



        int column = 0;
        int index = 0;

        //Loops through ArrayMap
        for(int i = 0; i < 25; i++){
            for(int j = 0; j < 40; j++){
                if(column == 25){
                    column = 0;
                }
                int tempValue = newArrayMap[index];
                if(tempValue != 0){
                    if(tempValue == 2){
                        handler.addObject(new BreakableWall(j*32,i*32, 32,32, ID.BreakableWall,breakableWallImg));
                    } else if (tempValue == 3){
                        handler.addObject(new PowerUp(j*32,i*32, 32,32, ID.PowerUp,powerUpImg));
                    }else if(tempValue == 4){
                        handler.addObject(new Wall(j*32, i*32, unbreakableWallImg,32,32, ID.WallB, false));

                    } else{
                        handler.addObject(new Wall(j*32, i*32, unbreakableWallBorderImg,32,32, ID.Wall, false));

                    }

                }
                column ++;
                index ++;
            }
        }
    }


    private void TankControls(){

        // Tank Controls
        TankControl tc1 = new TankControl(t1, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);
        TankControl tc2 = new TankControl(t2, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);
        this.lf.getJf().addKeyListener(tc1);
        this.lf.getJf().addKeyListener(tc2);
    }



    @Override
    public void paintComponent(Graphics g) {
      //  getGameImage();

        Graphics2D g2d = (Graphics2D) g;
        buffer = world.createGraphics();
     //  super.paintComponent(g2d);

        for(int i = 0; i < handler.object.size(); i++){
          handler.object.get(i).drawImage(buffer);
            t1.drawImage(buffer);
            t2.drawImage(buffer);
        }

        handler.drawImage(g);

        g2d.drawImage(world,0,0,null); // Draws GameWorld

     //Splitscreen with Camera
      TankCameraCheck();
     BufferedImage leftSplitScreen = world.getSubimage( t1CameraX, t1CameraY, GameConstants.GAME_SCREEN_WIDTH / 2,GameConstants.GAME_SCREEN_HEIGHT);
     BufferedImage rightSplitScreen = world.getSubimage( t2CameraX, t2CameraY, GameConstants.GAME_SCREEN_WIDTH/2,GameConstants.GAME_SCREEN_HEIGHT);

     g2d.drawImage(leftSplitScreen, 0 ,0,null);
     g2d.drawImage(rightSplitScreen,GameConstants.GAME_SCREEN_WIDTH/2, 0,null);

     // Draws MiniMap
     g2d.drawImage(world, GameConstants.GAME_SCREEN_WIDTH / 2 - GameWorld.worldWidth / 8, GameConstants.GAME_SCREEN_HEIGHT - GameWorld.worldHeight / 4, worldWidth / 4, worldHeight / 4, null);

     drawHUD(g2d);

    }

    // Tank position Check for Camera
    private void TankCameraCheck(){

        // Tank 1
         t1CameraX = t1.getTankCenterX() - GameConstants.GAME_SCREEN_WIDTH / 4;
         t1CameraY = t1.getTankCenterY() - GameConstants.GAME_SCREEN_HEIGHT / 2;
         t1XBound = GameWorld.worldWidth - GameConstants.GAME_SCREEN_WIDTH / 2;
         t1YBound = GameWorld.worldHeight - GameConstants.GAME_SCREEN_HEIGHT;
        if(t1CameraX > t1XBound){
            t1CameraX = t1XBound;
        }else if(t1CameraX < 0){
            t1CameraX = 0;
        }
        if(t1CameraY > t1YBound){
            t1CameraY = t1YBound;
        }else if(t1CameraY < 0){
            t1CameraY = 0;
        }

        // Tank 2
        t2CameraX = t2.getTankCenterX() - GameConstants.GAME_SCREEN_WIDTH / 4;
        t2CameraY = t2.getTankCenterY() - GameConstants.GAME_SCREEN_HEIGHT / 2;
        t2XBound = GameWorld.worldWidth - GameConstants.GAME_SCREEN_WIDTH / 2;
        t2YBound = GameWorld.worldHeight - GameConstants.GAME_SCREEN_HEIGHT;
        if(t2CameraX > t2XBound){
            t2CameraX = t2XBound;
        }else if(t2CameraX < 0){
            t2CameraX = 0;
        }
        if(t2CameraY > t2YBound){
            t2CameraY = t2YBound;
        }else if(t2CameraY < 0){
            t2CameraY = 0;
        }


    }



    private void checkShoot(){
        if(t1.getIsShooting()){
            Bullet newBullet = new Bullet(t1.getTankCenterX(),t1.getTankCenterY(), bulletImg.getWidth(),bulletImg.getHeight(), ID.Bullet, handler, bulletImg, 5,t1.getAngle(),this);
           handler.addObject(newBullet);
            t1.setShooting(false);
        }

        if(t2.getIsShooting()){
            Bullet newBullet = new Bullet(t2.getTankCenterX(),t2.getTankCenterY(), bulletImg.getWidth(),bulletImg.getHeight(), ID.Bullet, handler, bulletImg, 5,t2.getAngle(),this);
           handler.addObject(newBullet);
            t2.setShooting(false);
        }
    }

    // Bullet Tracking
    private void shootUpdate(){
        for(int i = 0; i < bullets.size(); i++){
            Bullet bullet = bullets.get(i);

            if(bullet.isVisible()){
                bullet.update();
            } else{
                bullets.remove(i);
                i--;
            }
        }
    }

    //HUD for tanks
    private void drawHUD(Graphics g){

        int t1Health = t1.getHealth();
        int t2Health = t2.getHealth();

        int t1Lives = t1.getLife();
        int t2Lives = t2.getLife();

        int t1HealthX = 120;
        int t1HealthY = 740;

        int t2HealthX = 640;
        int t2HealthY = 740;

        int healthWidth = 200;
        int healthHeight = 30;

        int coordinateOffset = 4;
        int sizeOffset = 8;

        //Health Outline
        g.setColor(Color.BLACK);
        g.fillRect(t1HealthX,t1HealthY,healthWidth,healthHeight); // Tank1
        g.fillRect(t2HealthX,t2HealthY,healthWidth,healthHeight); // Tank2

        //Secondary Health Color
        g.setColor(Color.DARK_GRAY);
        g.fillRect(t1HealthX + coordinateOffset,t1HealthY + coordinateOffset,healthWidth - sizeOffset,healthHeight- sizeOffset); // Tank1
        g.fillRect(t2HealthX + coordinateOffset,t2HealthY + coordinateOffset,healthWidth - sizeOffset,healthHeight - sizeOffset); // Tank2

        //Primary Health Color
        g.setColor(Color.GREEN);
        g.fillRect(t1HealthX + coordinateOffset,t1HealthY + coordinateOffset,t1Health - sizeOffset,healthHeight- sizeOffset); // Tank1
        g.fillRect(t2HealthX + coordinateOffset,t2HealthY + coordinateOffset,t2Health - sizeOffset,healthHeight - sizeOffset); // Tank2

        //Player 1 and Player 2
        g.setColor(Color.BLUE);
        g.setFont(new Font("default", Font.BOLD, 16));
        g.drawString("PLAYER 1", 180, t1HealthY + 20);

        g.setColor(Color.RED);
        g.setFont(new Font("default", Font.BOLD, 16));
        g.drawString("PLAYER 2", 700, t2HealthY + 20);

        //Tank1 Lives
        int t1LifeX = 120;
        int t1LifeY = 720;
        int t1Offset = 25;
        for(int i = 0; i < t1Lives; i++){
            g.drawImage(lifeImg, t1LifeX + (i * t1Offset),t1LifeY,null);
        }

        //Tank2 Lives
        int t2LifeX = 640;
        int t2LifeY = 720;
        int t2Offset = 25;
        for(int i = 0; i < t2Lives; i++){
            g.drawImage(lifeImg, t2LifeX + (i * t2Offset),t2LifeY,null);
        }
    }

    public void setBullets(ArrayList<Bullet> bullet){
        this.bullets = bullet;
    }
    public ArrayList<Bullet> getBullets(){
        return bullets;
    }

}

