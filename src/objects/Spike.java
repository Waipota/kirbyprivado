package objects;

//Importamos las librerias que vamos a usar
import main.Game;

/**
 * Comienzo de la clase Spike que es hija de GameObject que representa las spikes del juego
 * @author Santiago
 */
public class Spike extends GameObject{

    /**
     * Definimos el constructor de la clase Spike
     * @param x es la coordenada en x de la spike
     * @param y es la coordenada en y de la spike
     * @param objType es el tipo de objeto
     */
    public Spike(int x, int y, int objType) {
        super(x, y, objType);
        initHitbox(32, 16);
        xDrawOffset = 0;
        yDrawOffset = (int)(Game.SCALE * 16);
        hitbox.y += yDrawOffset;
    }
}