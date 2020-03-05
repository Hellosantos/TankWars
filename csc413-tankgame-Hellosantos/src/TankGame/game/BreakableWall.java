package TankGame.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BreakableWall extends Wall{

    private static BufferedImage breakableWallImage;
    private int health = 100;
    private boolean dead = false;

    public static void setBreakableWallImage(BufferedImage image){
       BreakableWall.breakableWallImage = image;
    }

    private void removeHealth(int value){
        if(health - value < 0 ){
            health = 0;
            dead = true;
        } else{
            health -= value;
        }
    }


   public BreakableWall(int x, int y, int width, int height, ID id,BufferedImage img){
        super(x,y, img, width,height, id,false);
        this.height = img.getHeight();
        this.width = img.getWidth();
    }

    public void breakWall(){
        dead = true;
    }

    @Override
    public void update() {

    }

    @Override
    public void drawImage(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        if(!dead) {
            g2d.drawImage(breakableWallImage, x, y, null);
        }
    }

    public Rectangle getBounds(){
        return new Rectangle(x,y,img.getWidth(),img.getHeight());
    }


    public boolean isDead(){
        return dead;
    }

    public void setDead(boolean dead){
        this.dead = dead;
    }

    public int getHealth(){
        return this.health;
    }


}
