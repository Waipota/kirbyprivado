package objects;

//Importamos las librerias y constantes que vamos a usar en la clase GameContainer
import main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static utilz.Constants.ObjectConstants.*;

/**
 * Comienzo de la clase GameContainer hija de GameObject
 * @author Santiago
 */
public class GameContainer extends GameObject {

    /**
     * Definimos el constructor de la clase GameContainer
     * @param x es la coordenada en x del contenedor
     * @param y es la coordenada en y del contenedor
     * @param objType es el tipo de objeto
     */
    public GameContainer(int x, int y, int objType) {
        super(x, y, objType);
        createHitbox();
    }

    /**
     * Definimos un metodo para crear la hitbox del container
     */
    private void createHitbox() {
        if (objType == BOX){
            initHitbox(25, 18);
            xDrawOffset = (int) (7 * Game.SCALE);
            yDrawOffset = (int) ( 12 * Game.SCALE);
        }
        else {
          initHitbox(23, 25);
            xDrawOffset = (int) (8 * Game.SCALE);
            yDrawOffset = (int) (5 * Game.SCALE);
        }
        hitbox.y += yDrawOffset + (int) (2 * Game.SCALE);
        hitbox.x += (int) yDrawOffset / 2;
    }

    /**
     * Definimos un metodo para actualizar el contenedor
     */
    public void update(){
        if (doAnimation){
            updateAnimationTick();
        }
    }



}

