package TankGame.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Unmovable extends GameObject{


    public Unmovable(int x, int y, int width, int height, ID id, BufferedImage img){
        super(x,y,width,height,id,img);
    }


    @Override
    public void update() {

    }

    @Override
    public void drawImage(Graphics g) {

    }

    public void collision() {

    }

    @Override
    public Rectangle getBounds() {
        return null;
    }
}
