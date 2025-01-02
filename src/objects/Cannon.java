package objects;
//Importamos las librerias y constantes que vamos a usar en la clase Cannon
import main.Game;

/**
 * Comienzo de la clase Cannon hija de la clase GameObject
 * @author Santiago
 */
public class Cannon extends GameObject {

    private int tileY;

    /**
     * Definimos el constructor de la clase cannon
     * @param x las coordenadas en x del cannon
     * @param y las coordenadas en y del cannon
     * @param objType es el tipo de objeto que se tiene (cannon, potion , etc)
     */
    public Cannon(int x, int y, int objType) {
        super(x, y, objType);
        tileY = y / Game.TILES_SIZE;
        initHitbox(40, 26);
//		hitbox.x -= (int) (1 * Game.SCALE);
        hitbox.y += (int) (6 * Game.SCALE);
    }

    /**
     * Definimos el metodo para actualizar los cannons
     */
    public void update() {
        if (doAnimation)
            updateAnimationTick();
    }

    /**
     * Definimos un getter para obtener tileY
     * @return el valor de tileY
     */
    public int getTileY() {
        return tileY;
    }

}