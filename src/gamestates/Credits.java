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
    private BufferedImage backgroundImg, creditsImg;
    private int bgX, bgY, bgW, bgH;
    private float bgYFloat;

    private ArrayList<ShowEntity> entitiesList;

    /**
     * Definimos el constructor de la clase Credits
     * @param game es la instancia del juego
     */
    public Credits(Game game) {
        super(game);
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND_IMG);
        creditsImg = LoadSave.GetSpriteAtlas(LoadSave.CREDITS);
        bgW = (int) (creditsImg.getWidth() * Game.SCALE);
        bgH = (int) (creditsImg.getHeight() * Game.SCALE);
        bgX = Game.GAME_WIDTH / 2 - bgW / 2;
        bgY = Game.GAME_HEIGHT;
        loadEntities();
    }

    /**
     * Definimos un metodo para cargar las entidades que van a estar en los creditos
     */
    private void loadEntities() {
        entitiesList = new ArrayList<>();
        entitiesList.add(new ShowEntity(getIdleAni(LoadSave.GetSpriteAtlas(LoadSave.PLAYER_PIRATE), 5, 64, 40), (int) (Game.GAME_WIDTH * 0.05), (int) (Game.GAME_HEIGHT * 0.8)));
        entitiesList.add(new ShowEntity(getIdleAni(LoadSave.GetSpriteAtlas(LoadSave.CRABBY_SPRITE), 9, 72, 32), (int) (Game.GAME_WIDTH * 0.15), (int) (Game.GAME_HEIGHT * 0.75)));
        entitiesList.add(new ShowEntity(getIdleAni(LoadSave.GetSpriteAtlas(LoadSave.PINKSTAR_ATLAS), 8, 34, 30), (int) (Game.GAME_WIDTH * 0.7), (int) (Game.GAME_HEIGHT * 0.75)));
        entitiesList.add(new ShowEntity(getIdleAni(LoadSave.GetSpriteAtlas(LoadSave.SHARK_ATLAS), 8, 34, 30), (int) (Game.GAME_WIDTH * 0.8), (int) (Game.GAME_HEIGHT * 0.8)));
    }

    /**
     * Definimos un metodo para obtener un arreglo de la animacion de cada personaje en su estado idle
     * @param atlas es la imagen de la cual vamos a obtener las subimagenes y vamos a guardar en el arreglo
     * @param spritesAmount es la cantidad de sprites que queremos guardar en el arreglo
     * @param width es el ancho de cada sprite
     * @param height es el alto de cada sprite
     * @return el arreglo de sprites
     */
    private BufferedImage[] getIdleAni(BufferedImage atlas, int spritesAmount, int width, int height) {
        BufferedImage[] arr = new BufferedImage[spritesAmount];
        for (int i = 0; i < spritesAmount; i++)
            arr[i] = atlas.getSubimage(width * i, 0, width, height);
        return arr;
    }

    /**
     * Definimos un metodo para actualizar los creditos
     */
    @Override
    public void update() {
        bgYFloat -= 0.2f;
        for (ShowEntity se : entitiesList)
            se.update();
    }

    /**
     * Definimos un metodo para dibujar los creditos en pantalla
     * @param g es el grafico para poder dibujar
     */
    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        g.drawImage(creditsImg, bgX, (int) (bgY + bgYFloat), bgW, bgH, null);

        for (ShowEntity se : entitiesList)
            se.draw(g);
    }

    /**
     * Definimsos un metodo para detectar las teclas
     * @param e es el evento de la tecla
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            bgYFloat = 0;
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