package TankGame.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PowerUp extends Unmovable {

    private static BufferedImage powerUpImage;
    private boolean dead = false;

    public static void setPowerUpImage(BufferedImage image){
        PowerUp.powerUpImage = image;
    }

    PowerUp(int x, int y, int width, int height, ID id, BufferedImage img){
        super(x,y, width, height, id, img);
    }

    @Override
    public void update() {

    }

    @Override
    public void drawImage(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        if(!dead){
            g2d.drawImage(powerUpImage,x,y,null);
        }
    }

    @Override
    public void collision() {

    }

    boolean isDead(){
        return dead;
    }

    void setDead(boolean dead){
        this.dead = dead;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x,y,img.getWidth(), img.getHeight());
    }
}
