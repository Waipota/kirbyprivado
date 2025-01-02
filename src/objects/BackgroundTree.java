package objects;

//Importamos las librerias que vamos a usar en la clase de BackgroundTree
import java.util.Random;

/**
 * Comienzo de la clase BackgroundTree
 * @author Santiago
 */
public class BackgroundTree {

    private int x, y, type, aniIndex, aniTick;

    /**
     * Definimos el constructor de la clase
     * @param x es la coordenada en x de donde se quiere el arbol
     * @param y es la coordenada en y de donde se quiere el arbol
     * @param type es el tipo de arbol que se desea mostrar
     */
    public BackgroundTree(int x, int y, int type) {
        this.x = x;
        this.y = y;
        this.type = type;

        // Sets the aniIndex to a random value, to get some variations for the trees so
        // they all don't move in synch.
        Random r = new Random();
        aniIndex = r.nextInt(4);

    }

    /**
     * Definimos un metodo para actualizar los arboles del juego
     */
    public void update() {
        aniTick++;
        if (aniTick >= 35) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= 4)
                aniIndex = 0;
        }
    }

    /**
     * Definimos un getter para obtener el indice de animacion del arbol
     * @return el indice de animacion del arbol
     */
    public int getAniIndex() {
        return aniIndex;
    }

    /**
     * Definimos un setter para el indice de animacion del arbol
     * @param aniIndex es el indice de animacion del arbol
     */
    public void setAniIndex(int aniIndex) {
        this.aniIndex = aniIndex;
    }

    /**
     * Definimos un getter para obtener las coordenadas en x
     * @return las coordenadas en x
     */
    public int getX() {
        return x;
    }

    /**
     * Definimos un setter de las coordenadas en x de un arbol
     * @param x es la coordenada en x del arbol
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Definimos un getter de las coordenadas en y de un arbol
     * @return la coordenada en y
     */
    public int getY() {
        return y;
    }

    /**
     * Definimos un setter para las coordenadas en y
     * @param y es la coordenada en y
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Definimos un getter del tipo de arbol
     * @return el tipo de arbol
     */
    public int getType() {
        return type;
    }
}