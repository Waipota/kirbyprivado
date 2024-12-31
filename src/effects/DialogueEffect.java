package effects;

//Importamos las constantes que se usan en la clase DialogueEffect
import static utilz.Constants.ANI_SPEED;
import static utilz.Constants.Dialogue.*;

/**
 * Comienzo de la clae de DialogueEffect
 * @author Santiago
 */
public class DialogueEffect {

    private int x, y, type;
    private int aniIndex, aniTick;
    private boolean active = true;

    /**
     * Definimos el constructor de la clase
     * @param x las coordenadas en x sobre en donde aparece el dialogo
     * @param y las coordenadas en y sobre en donde aparace el dialogo
     * @param type El tipo de dialogo que se quiere mostrar
     */
    public DialogueEffect(int x, int y, int type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    /**
     * Definimos un metodo para actualizar el dialogo
     */
    public void update() {
        aniTick++;
        if (aniTick >= ANI_SPEED) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(type)) {
                active = false;
                aniIndex = 0;
            }
        }
    }

    /**
     * Definimos un metodo para desactivar el dialogo
     */
    public void deactive() {
        active = false;
    }

    /**
     * Definimos un metodo para resetear el dialogo
     * @param x las coordenadas en x en donde aparece el dialogo
     * @param y las coordenadas en y en donde aparece el dialogo
     */
    public void reset(int x, int y) {
        this.x = x;
        this.y = y;
        active = true;
    }

    /**
     * Definimos un getter del indice de animacion del dialogo
     * @return el indice de animacion del dialogo
     */
    public int getAniIndex() {
        return aniIndex;
    }

    /**
     * Definimos un getter de las coordenadas en x
     * @return las coordenadas en x del dialogo
     */
    public int getX() {
        return x;
    }

    /**
     *Definimos un getter para las coordenadas en y del dialogo
     * @return las coordanadas y
     */
    public int getY() {
        return y;
    }

    /**
     * Definimos un getter para el tipo de dialogo
     * @return el tipo de dialogo
     */
    public int getType() {
        return type;
    }

    /**
     * Definimos un getter sobre el estado del dialogo (verdadero / falso)
     * @return el estado del dialogo
     */
    public boolean isActive() {
        return active;
    }
}