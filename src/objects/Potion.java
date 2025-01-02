package objects;

//Importamos las librerias que vamos a usar en la clase potion
import main.Game;

/**
 * Comienzo de la clase Potion que representa una pocion en el juego, es hija de la clase GameObject
 * @author Santiago
 */
public class Potion extends GameObject {

    private float hoverOffset;
    private int maxHoverOffset, hoverDir = 1;

    /**
     * Definimos el constructor de la clase Potion
     * @param x es la coordenada en x de la pocion
     * @param y es la coordeanda en y de la pocion
     * @param objType es el tipo de objeto
     */
    public Potion(int x, int y, int objType) {
        super(x, y, objType);
        doAnimation = true;

        initHitbox(7, 14);

        xDrawOffset = (int) (3 * Game.SCALE);
        yDrawOffset = (int) (2 * Game.SCALE);

        maxHoverOffset = (int) (10 * Game.SCALE);
    }

    /**
     * Definimos un metodo para actualizar las pociones
     */
    public void update() {
        updateAnimationTick();
        updateHover();
    }

    /**
     * Definimos un metodo para hacer el efecto de que las pociones se mueven
     */
    private void updateHover() {
        hoverOffset += (0.075f * Game.SCALE * hoverDir);

        if (hoverOffset >= maxHoverOffset)
            hoverDir = -1;
        else if (hoverOffset < 0)
            hoverDir = 1;

        hitbox.y = y + hoverOffset;
    }
}