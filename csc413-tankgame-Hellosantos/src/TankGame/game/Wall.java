package TankGame.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Wall extends Unmovable {

    private boolean is_background;
    private static BufferedImage floorImage;

    private static BufferedImage unbreakableWall;

    private static BufferedImage unreabkableWallBorder;

    public static void setUnreabkableWallBorder(BufferedImage image){
        unreabkableWallBorder = image;
    }

    public static void setFloorImage(BufferedImage image){
        floorImage = image;
    }

    public static void setUnbreakableWall(BufferedImage image){
        unbreakableWall = image;
    }

    public Wall(int x, int y, BufferedImage img, int width, int height, ID id,  boolean is_background){
        super(x,y, width, height, id, img);
        this.height = img.getHeight();
        this.width = img.getWidth();
        this.is_background = is_background;
    }


    @Override
    public void update() {

    }


    @Override
    public void drawImage(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        if(this.is_background){
            g2d.drawImage(floorImage,x,y,null);
        }else{
            g2d.drawImage(unbreakableWall,x,y,null);
            g2d.drawImage(unreabkableWallBorder,x,y,null);
        }
    }



   public Rectangle getBounds(){
        return new Rectangle(x,y,img.getWidth(),img.getHeight());
   }
}
