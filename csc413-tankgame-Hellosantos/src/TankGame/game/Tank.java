package TankGame.game;



import TankGame.GameConstants;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 *
 * @author anthony-pc
 */
public class Tank extends Movable{


    private int vx;
    private int vy;
    private int angle;
    protected int life = 3;
    protected int health = 200;
    private boolean isDead;
    private GameWorld object;
    private boolean isShooting;
    private long shootTime = System.nanoTime();
    private final long shootDelay = 300;
    ObjectHandler handler;

    private final int R = 2;
    private final float ROTATIONSPEED = 3.0f;



    private BufferedImage img;
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean ShootPressed;



   public Tank(int x, int y, int width, int height, ID id, ObjectHandler handler, BufferedImage img, int angle, int speed) {
        super(x,y, width, height, id, img, speed);
        this.img = img;
        this.handler = handler;
        this.height = img.getHeight();
        this.width = img.getWidth();
        this.angle = angle;
        this.isShooting = false;
   }

    public int getTankCenterX(){
        return x + img.getWidth()/2;
    }
    public int getTankCenterY(){
        return y + img.getHeight()/2;
    }

    public int getAngle(){
       return this.angle;
    }


    public void toggleUpPressed() {
        this.UpPressed = true;
    }

    public void toggleDownPressed() {
        this.DownPressed = true;
    }

    public void toggleRightPressed() {
        this.RightPressed = true;
    }

    public void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    public void toggleShootPressed(){
       this.ShootPressed = true;
    }

    public void unToggleShootPressed(){ this.ShootPressed = false;}

    public void unToggleUpPressed() {
        this.UpPressed = false;
    }

    public void unToggleDownPressed() {
        this.DownPressed = false;
    }

    public void unToggleRightPressed() {
        this.RightPressed = false;
    }

    public void unToggleLeftPressed() {
        this.LeftPressed = false;
    }

    public void update() {

        if (this.UpPressed) {
            this.moveForwards();
        }
        if (this.DownPressed) {
            this.moveBackwards();
        }
        if (this.LeftPressed) {
            this.rotateLeft();
        }
        if (this.RightPressed) {
            this.rotateRight();
        }if(this.ShootPressed){
            this.shoot();
        }

        healthCheck();
        collision();

    }

    public void collision(){
       for(int i = 0; i < handler.object.size(); i++) {
           GameObject tempObject = handler.object.get(i);

           if (tempObject.getId() == ID.BreakableWall) {
               if (getBounds().intersects((tempObject.getBounds()))) {
                   handler.removeObject(tempObject);
                   health -= 40;
                   if (vx > 0) {
                       x += vx * -1;
                   } else if (vx < 0) {
                       x += vx * -1;
                   }

               }

               if (getBounds2().intersects((tempObject.getBounds()))) {
                   handler.removeObject(tempObject);
                   health -= 40;
                   if (vy > 0) {
                       y += vy * -1;
                   } else if (vy < 0) {
                       y += vy * -1;
                   }
               }
           }

           if (tempObject.getId() == ID.WallB) {
               if (getBounds().intersects((tempObject.getBounds()))) {
                   if (vx > 0) {
                       x += vx * -1;
                   } else if (vx < 0) {
                       x += vx * -1;
                   }

               }

               if (getBounds2().intersects((tempObject.getBounds()))) {
                   if (vy > 0) {
                       y += vy * -1;
                   } else if (vy < 0) {
                       y += vy * -1;
                   }
               }
           }

           if(tempObject.getId() == ID.PowerUp){
               if(getBounds().intersects((tempObject.getBounds()))){
                    handler.removeObject(tempObject);
                    life++;
                    health = 200;
               }
           }
       }
    }


    private void rotateLeft() {
        this.angle -= this.ROTATIONSPEED;
    }

    private void rotateRight() {
        this.angle += this.ROTATIONSPEED;
    }

    private void moveBackwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));

        x -= vx;
        y -= vy;

        checkBorder();

    }

    private void moveForwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));

           x += vx;
           y += vy;

        checkBorder();
    }

    public boolean isDead(){
        if (this.life <= 0){
            return true;
        }
        return false;
    }




    private void checkBorder() {
        if (x < 30) {
            x = 30;
        }
        if (x >= GameWorld.worldWidth - 88) {
            x = GameWorld.worldWidth - 88;
        }
        if (y < 40) {
            y = 40;
        }
        if (y >= GameWorld.worldHeight - 80) {
            y = GameWorld.worldHeight - 80;
        }
    }

    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }



    public void drawImage(Graphics g) {

        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);
    }

    public void setHealth(int health){
       this.health = health;
    }


    // Horizontal Collision
    public Rectangle getBounds(){
        int bx = x + vx;
        int by = y;
        int bw = 64 + vx/6;
        int bh = 64;

        return new Rectangle(bx,by,bw,bh);
    }

    // Vertical Collision
    public Rectangle getBounds2(){
        int bx = x;
        int by = y + vy;
        int bw = 64;
        int bh = 64 + vy/6;

        return new Rectangle(bx,by,bw,bh);
    }

    public int getLife(){
       return this.life;
    }

    public int getHealth(){
       return this.health;
    }

    private void shoot(){
       long time = (System.nanoTime() - shootTime) / 1000000;
       if(time > shootDelay){
           this.isShooting = true;
           this.shootTime = System.nanoTime();
       }
    }

    private void healthCheck(){
       if(this.life > 0){
           if(this.health == 0){
               this.life--;
               this.health = 200;
           }
       }else{

       }
    }

    public boolean getIsShooting(){
       return this.isShooting;
    }

    public void setShooting(boolean shooting){
       this.isShooting = shooting;
    }

}
