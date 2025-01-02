package objects;

//Importamos las librerias y las constantes que se van a usar en la clase GameObject
import static utilz.Constants.ANI_SPEED;
import static utilz.Constants.ObjectConstants.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import main.Game;

/**
 * Comienzo de la clase GameObject que representa un objeto en el juego
 * @author Santiago
 */
public class GameObject {

    protected int x, y, objType;
    protected Rectangle2D.Float hitbox;
    protected boolean doAnimation, active = true;
    protected int aniTick, aniIndex;
    protected int xDrawOffset, yDrawOffset;

    /**
     * Definimos el constructor de la clase GameObject
     * @param x es la coordenada en x del objeto
     * @param y es la coordenada en y del objeto
     * @param objType es el tipo de objeto
     */
    public GameObject(int x, int y, int objType) {
        this.x = x;
        this.y = y;
        this.objType = objType;
    }

    /**
     * Definimos un metodo para actualizar la animacion del objeto
     */
    protected void updateAnimationTick() {
        aniTick++;
        if (aniTick >= ANI_SPEED) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(objType)) {
                aniIndex = 0;
                if (objType == BARREL || objType == BOX) {
                    doAnimation = false;
                    active = false;
                } else if (objType == CANNON_LEFT || objType == CANNON_RIGHT)
                    doAnimation = false;
            }
        }
    }

    /**
     * Definimos un metodo para resetear los valores de un objeto
     */
    public void reset() {
        aniIndex = 0;
        aniTick = 0;
        active = true;

        if (objType == BARREL || objType == BOX || objType == CANNON_LEFT || objType == CANNON_RIGHT)
            doAnimation = false;
        else
            doAnimation = true;
    }

    /**
     * Definimos un metodo para iniciar la hitbox del objeto
     * @param width es el ancho del objeto
     * @param height es el alto del objeto
     */
    protected void initHitbox(int width, int height) {
        hitbox = new Rectangle2D.Float(x, y, (int) (width * Game.SCALE), (int) (height * Game.SCALE));
    }

    /**
     * Definimos un metodo para dibujar la hitbox del objeto
     * @param g es el grafico para poder dibujar
     * @param xLvlOffset es el offset del nivel en el cual esta el jugador, cambia dependiendo del nivel
     */
    public void drawHitbox(Graphics g, int xLvlOffset) {
        g.setColor(Color.PINK);
        g.drawRect((int) hitbox.x - xLvlOffset, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
    }

    /**
     * Definimos un getter para obtener el tipo de objeto
     * @return el tipo de objeto
     */
    public int getObjType() {
        return objType;
    }

    /**
     * Definimos un getter para la hitbox del objeto
     * @return la hitbox del objeto
     */
    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

    /**
     * Definimos un getter del valor activo que representa si un objeto esta activo o no
     * @return el valor booleano de si el objeto esta activo
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Definimos un setter para el valor active del objeto
     * @param active es el valor booleano que se le va a asignar al objeto
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Definimos un setter de si el objeto esta haciendo su animacion
     * @param doAnimation es el valor booleano
     */
    public void setAnimation(boolean doAnimation) {
        this.doAnimation = doAnimation;
    }

    /**
     * Definimos un getter del xoffset del objeto
     * @return el valor del xoffset del objeto
     */
    public int getxDrawOffset() {
        return xDrawOffset;
    }

    /**
     * Definimos un getter del ydrawoffsetdel objeto
     * @return el ydrawoffset del objeto
     */
    public int getyDrawOffset() {
        return yDrawOffset;
    }

    /**
     * Definimos un getter del indice de animacion del objeto
     * @return el indice de animacion del objeto
     */
    public int getAniIndex() {
        return aniIndex;
    }

    /**
     * Definimos un getter para el aniTick del objeto
     * @return el valor del aniTick del objeto
     */
    public int getAniTick() {
        return aniTick;
    }

}