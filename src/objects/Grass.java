package objects;

/**
 * Comienzo de la clase Grass que representa la grama en el juego
 * @author Santiago
 */
public class Grass {

    private int x, y, type;

    /**
     * Definimos el constructor de la clase Grass
     * @param x es la coordenada en x de la grama
     * @param y es la coordenada en y de la grama
     * @param type es el tipo de grama que se tiene
     */
    public Grass(int x, int y, int type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    /**
     * Definimos un getter de la coordenada en x
     * @return el valor de la coordanada en x
     */
    public int getX() {
        return x;
    }

    /**
     * Definimos un getter de la coordenada en y
     * @return el valor de la coordenada en y
     */
    public int getY() {
        return y;

    }

    /**
     * Definimos un getter del tipo de grama que se tiene
     * @return el valor del tipo de grama
     */
    public int getType() {
        return type;
    }
}