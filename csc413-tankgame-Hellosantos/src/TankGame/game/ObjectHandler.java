package TankGame.game;

import java.awt.*;
import java.util.LinkedList;

public class ObjectHandler {

    LinkedList<GameObject> object = new LinkedList<>();

    public void update(){
        for(int i = 0; i < object.size(); i++){
            GameObject tempObject = object.get(i);
            tempObject.update();
        }
    }

    public void drawImage(Graphics g){
        for(int i = 0; i < object.size(); i++){
            GameObject tempObject = object.get(i);
            tempObject.drawImage(g);
        }
    }

    public void addObject(GameObject object){
        this.object.add(object);
    }

    public void removeObject(GameObject object){
        this.object.remove(object);
    }
}
