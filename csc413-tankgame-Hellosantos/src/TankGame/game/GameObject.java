package TankGame.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GameObject {
    protected int x;
    protected int y;
    protected ID id;
    protected int height;
    protected int width;
    protected BufferedImage img;

    public GameObject(int x, int y, int width, int height, ID id, BufferedImage img){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.id = id;
        this.img = img;
    }





    // Setters
    public void setX(int x){
        this.x = x;
    }

   public void setY(int y){
        this.y = y;
    }

    public void setWidth(int width){
        this.width = width;
    }

    public void setHeight(int height){
        this.height = height;
    }

    public void setId(){
        this.id = id;
    }

    public void setImage(BufferedImage img){
        this.img = img;
    }

    //Getters
    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }


    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public ID getId(){
        return id;
    }

    public Image getImage(){
        return img;
    }

    public abstract void update();
    public abstract void drawImage(Graphics g);
    public abstract Rectangle getBounds();

}
