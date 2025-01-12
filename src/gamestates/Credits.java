package gamestates;

//Importamos las librerias y constantes que se van a usar en la clase Credits
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import main.Game;
import utilz.LoadSave;

/**
 * Comienzo de la clase Credits hija de State  y que implementa los metodos de StateMethods
 * @author Santiago
 */
public class Credits extends State implements StateMethods {
    private BufferedImage imgHelp, backGroundImg; //Definimos la imagen que vamos a mostrar en los creditos
    private ArrayList<ShowEntity> entitiesList;

    /**
     * Definimos el constructor de la clase Credits
     * @param game es la instancia del juego
     */
    public Credits(Game game) {
        super(game);
        imgHelp = LoadSave.GetSpriteAtlas(LoadSave.ACERCA);
        backGroundImg = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND_IMG); //Cargamos la imagen de la explicacion sobre como jugar el juego
    }

    /**
     * Definimos un metodo para cargar las entidades que van a estar en los creditos
     */
    /*
    private void loadEntities() {
        entitiesList = new ArrayList<>();
        entitiesList.add(new ShowEntity(getIdleAni(LoadSave.GetSpriteAtlas(LoadSave.CRABBY_SPRITE), 9, 72, 32), (int) (Game.GAME_WIDTH * 0.15), (int) (Game.GAME_HEIGHT * 0.75)));
        entitiesList.add(new ShowEntity(getIdleAni(LoadSave.GetSpriteAtlas(LoadSave.PINKSTAR_ATLAS), 8, 34, 30), (int) (Game.GAME_WIDTH * 0.7), (int) (Game.GAME_HEIGHT * 0.75)));
        entitiesList.add(new ShowEntity(getIdleAni(LoadSave.GetSpriteAtlas(LoadSave.SHARK_ATLAS), 8, 34, 30), (int) (Game.GAME_WIDTH * 0.8), (int) (Game.GAME_HEIGHT * 0.8)));
    }

*/

    /**
     * Definimos un metodo para obtener un arreglo de la animacion de cada personaje en su estado idle
     * @param atlas es la imagen de la cual vamos a obtener las subimagenes y vamos a guardar en el arreglo
     * @param spritesAmount es la cantidad de sprites que queremos guardar en el arreglo
     * @param width es el ancho de cada sprite
     * @param height es el alto de cada sprite
     * @return el arreglo de sprites

    private BufferedImage[] getIdleAni(BufferedImage atlas, int spritesAmount, int width, int height) {
        BufferedImage[] arr = new BufferedImage[spritesAmount];
        for (int i = 0; i < spritesAmount; i++)
            arr[i] = atlas.getSubimage(width * i, 0, width, height);
        return arr;
    }

    */

    /**
     * Definimos un metodo para actualizar los creditos


    */

    /**
     * Definimos un metodo para actualizar los creditos pero no lo usamos
     */
    @Override
    public void update() {

    }

    /**
     * Definimos un metodo para dibujar los creditos en pantalla
     * @param g es el grafico para poder dibujar
     */
    @Override
    public void draw(Graphics g) {
        // Obtén las dimensiones originales de la imagen
        int originalWidth = imgHelp.getWidth(null);
        int originalHeight = imgHelp.getHeight(null);

        // Calcula la relación de aspecto
        float aspectRatio = (float) originalWidth / originalHeight;

        // Define la nueva altura deseada (por ejemplo, la altura del juego)
        int newHeight = Game.GAME_HEIGHT;

        // Calcula el nuevo ancho manteniendo la relación de aspecto
        int newWidth = (int) (newHeight * aspectRatio);

        // Dibuja la imagen escalada
        g.drawImage(backGroundImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        g.drawImage(imgHelp, 500, 0, newWidth, newHeight, null);
    }


    /**
     * Definimsos un metodo para detectar las teclas
     * @param e es el evento de la tecla
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            setGamestate(GameState.MENU);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    /**
     * Comienzo de la clase para poder mostrar los personajes en los creditos
     * @author Santiago
     */
    private class ShowEntity {
        private BufferedImage[] idleAnimation;
        private int x, y, aniIndex, aniTick;

        /**
         * Definimos un metodo para mostrar los personajes en los creditos
         * @param idleAnimation es el arreglo de animacion de idle de cada personaje
         * @param x son las coordenadas en x
         * @param y son las coordenadas en y
         */
        public ShowEntity(BufferedImage[] idleAnimation, int x, int y) {
            this.idleAnimation = idleAnimation;
            this.x = x;
            this.y = y;
        }

        /**
         * Definimos un metodo para dibujar los personajes
         * @param g es el grafico para poder dibujar
         */
        public void draw(Graphics g) {
            g.drawImage(idleAnimation[aniIndex], x, y, (int) (idleAnimation[aniIndex].getWidth() * 4), (int) (idleAnimation[aniIndex].getHeight() * 4), null);
        }

        /**
         * Definimos el metodo para actualizar a los personajes
         */
        public void update() {
            aniTick++;
            if (aniTick >= 25) {
                aniTick = 0;
                aniIndex++;
                if (aniIndex >= idleAnimation.length)
                    aniIndex = 0;
            }

        }
    }

}