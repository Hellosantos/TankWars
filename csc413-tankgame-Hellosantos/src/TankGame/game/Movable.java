package TankGame.game;

import java.awt.*;
import java.awt.image.BufferedImage;
import TankGame.game.GameObject;

public class Movable  extends GameObject{

    protected int speed;


    public Movable( int x, int y, int width, int height, ID id, BufferedImage img, int speed){
        super(x,y, width, height, id,img);
        this.speed = speed;
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
