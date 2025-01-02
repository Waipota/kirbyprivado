package objects;

//Importamos las librerias y las constantes que se van a usar en la clase de Projectile
import gamestates.Playing;
import main.Game;

import java.awt.geom.Rectangle2D;
import static utilz.Constants.Projectiles.*;

/**
 * Comienzo de la clase Projectile que representa un proyectil que disparan los cannons
 * @author Santiago
 */
public class Projectile {

    private Rectangle2D.Float hitbox;
    private int dir;
    private boolean active = true;

    /**
     * Definimos el constructor de la clase Projectile
     * @param x es la coordenada en x del proyectil
     * @param y es la coordenada en y del proyectil
     * @param dir es la direccion del proyectil
     */
    public Projectile(int x, int y, int dir){
        int xOffset = (int) (-3 * Game.SCALE);
        int yOffset = (int) (5 * Game.SCALE);
        if (dir == 1) {
            xOffset = (int) (29 * Game.SCALE);
        }

        hitbox = new Rectangle2D.Float(x + xOffset, y + yOffset, CANNON_BALL_WIDTH, CANNON_BALL_HEIGHT);
        this.dir = dir;
    }

    /**
     * Definimos el metodo para actualizar la posicion del proyectil
     */
    public void updatePos(){
        hitbox.x += dir * SPEED;
    }

    /**
     * Definimos el metodo para setear la posicion de un proyectil
     * @param x es la coordenada en x del proyectil
     * @param y es la coordenada en y del proyectil
     */
    public void setPos(int x, int y){
        hitbox.x = x;
        hitbox.y = y;
    }

    /**
     * Definimos un getter para obtener la hitbox del proyectil
     * @return la hitbox del proyectil
     */
    public Rectangle2D.Float getHitbox(){
        return hitbox;
    }

    /**
     * Definimos un getter para obtener el estado del proyectil
     * @return true si esta activa o false si no esta activa
     */
    public boolean getActive(){
        return active;
    }

    /**
     * Definimos un setter del estado del proyectil
     * @param active es el estado del proyectil
     */
    public void setActive(boolean active){
        this.active = active;
    }



}
