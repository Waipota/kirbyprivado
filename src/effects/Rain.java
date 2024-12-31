package effects;
//importamos las librerias y constantes que vamos a usar en la clase
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import main.Game;
import utilz.LoadSave;

/**
 * Comienzo de la clase Rain
 * @author Santiago
 */
public class Rain {

    private Point2D.Float[] drops;
    private Random rand;
    private float rainSpeed = 1.25f;
    private BufferedImage rainParticle;

    /**
     * Definimos el constructor de la clas rain
     */

    public Rain() {
        rand = new Random();
        drops = new Point2D.Float[1000];
        rainParticle = LoadSave.GetSpriteAtlas(LoadSave.RAIN_PARTICLE);
        initDrops();
    }

    /**
     * Definimos un metodo para iniciar las gotas de lluvia
     */
    private void initDrops() {
        for (int i = 0; i < drops.length; i++)
            drops[i] = getRndPos();
    }

    /**
     * Definimos un metodo para retornar una gota de agua
     * @return las gotas de agua
     */
    private Point2D.Float getRndPos() {
        return new Point2D.Float((int) getNewX(0), rand.nextInt(Game.GAME_HEIGHT));
    }

    /**
     * Definimos un metodo para actualizar las gotas de agua
     * @param xLvlOffset es el espacio de el pixel de la gota con el final de la imagen de la gota lo recibimos para poder dibujar la gota en el centro
     */
    public void update(int xLvlOffset) {
        for (Point2D.Float p : drops) {
            p.y += rainSpeed;
            if (p.y >= Game.GAME_HEIGHT) {
                p.y = -20;
                p.x = getNewX(xLvlOffset);
            }
        }
    }

    /**
     * Definimos un metodo para generar un punto aleatorio en el cual se genera la gota de agua
     * @param xLvlOffset el offset de la imagen de la gota de agua
     * @return la posicion de la nueva gota de agua
     */
    private float getNewX(int xLvlOffset) {
        float value = (-Game.GAME_WIDTH) + rand.nextInt((int) (Game.GAME_WIDTH * 3f)) + xLvlOffset;
        return value;
    }

    /**
     * Definimos un metodo para dibujar la gota de agua
     * @param g es el grafico de la gota, se usa para poder dibujar la gota o cualquier imagen que se quiera dibujar
     * @param xLvlOffset el offset de la imagen de la gota de agua
     */
    public void draw(Graphics g, int xLvlOffset) {
        for (Point2D.Float p : drops)
            g.drawImage(rainParticle, (int) p.getX() - xLvlOffset, (int) p.getY(), 3, 12, null);
    }

}