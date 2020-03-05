package TankGame.game;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Bullet extends Movable {

    private static BufferedImage bulletImage;
    private int damage;
    private int vx;
    private int vy;
    private int angle;
    private final int speed = 5;
    private boolean isVisible;
    private GameWorld worldObject;
    ObjectHandler handler;

    public static void setBulletImage(BufferedImage image){
        Bullet.bulletImage = image;
    }


    public Bullet (int x, int y, int width, int height, ID id, ObjectHandler handler, BufferedImage img, int speed, int angle, GameWorld gameWorld){
        super(x,y,width,height,id,img, speed);
        this.handler = handler;
        this.angle = angle;
        this.worldObject = gameWorld;
    }
    @Override
    public void update() {
        vx = (int) Math.round(speed * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(speed * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;

        collision();
    }

    public void collision(){
        for(int i = 0; i < handler.object.size(); i++){
            GameObject tempObject = handler.object.get(i);



            if(tempObject.getId() == ID.Wall){
                if(tempObject.getBounds().intersects(x,y,width,height) && isVisible){
                    this.isVisible = false;
                }
            }
            if(tempObject.getId() == ID.BreakableWall){
                if(tempObject.getBounds().intersects(x,y,width,height) && isVisible){
                    handler.removeObject(tempObject);
                    this.isVisible = false;
                }
            }
        }
    }



    public boolean isVisible(){
        return this.isVisible;
    }

    @Override
    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), 0, 0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(bulletImage, rotation, null);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x,y,img.getWidth(),img.getHeight());
    }
}
