package gamestates;

//Importamos las librerias y las constantes que se van a usar en la clase
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import main.Game;
import ui.AudioOptions;
import ui.PauseButton;
import ui.UrmButton;
import utilz.LoadSave;
import static utilz.Constants.UI.URMButtons.*;

/**
 * Comienzo de la clae de GameOptions que representa el estado de estar dentro del menu de opciones
 * @author Santiago
 */
public class GameOptions extends State implements StateMethods {

    private AudioOptions audioOptions;
    private BufferedImage backgroundImg, optionsBackgroundImg;
    private int bgX, bgY, bgW, bgH;
    private UrmButton menuB;

    /**
     * Definimos el constructor de la clase GameOptions
     * @param game es la instancia del juego
     */
    public GameOptions(Game game) {
        super(game);
        loadImgs();
        loadButton();
        audioOptions = game.getAudioOptions();
    }

    /**
     * Definimos un metodo para cargar los botones del menu de opciones
     */
    private void loadButton() {
        int menuX = (int) (387 * Game.SCALE);
        int menuY = (int) (325 * Game.SCALE);

        menuB = new UrmButton(menuX, menuY, URM_SIZE, URM_SIZE, 2);
    }

    /**
     * Definimos un metodo para cargar las imagenes del menu de opciones
     */
    private void loadImgs() {
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND_IMG);
        optionsBackgroundImg = LoadSave.GetSpriteAtlas(LoadSave.OPTIONS_MENU);

        bgW = (int) (optionsBackgroundImg.getWidth() * Game.SCALE);
        bgH = (int) (optionsBackgroundImg.getHeight() * Game.SCALE);
        bgX = Game.GAME_WIDTH / 2 - bgW / 2;
        bgY = (int) (33 * Game.SCALE);
    }

    /**
     * Defiinimos el metodo para actualizar el menu de opciones
     */
    @Override
    public void update() {
        menuB.update();
        audioOptions.update();
    }

    /**
     * Definimos el metodo para dibujar el menu de opciones
     * @param g es el grafico para poder dibujar
     */
    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        g.drawImage(optionsBackgroundImg, bgX, bgY, bgW, bgH, null);

        menuB.draw(g);
        audioOptions.draw(g);
    }

    /**
     * Definimos un metodo para cuando se presiona el raton y se mueve estando presionado
     * @param e es el evento del movimiento del raton
     */
    public void mouseDragged(MouseEvent e) {
        audioOptions.mouseDragged(e);
    }

    /**
     * Definimos un metodo para los clicks del raton
     * @param e es el evento del click del raton
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (isIn(e, menuB)) {
            menuB.setMousePressed(true);
        } else
            audioOptions.mousePressed(e);
    }

    /**
     * Definimos un metodo para cuando se deja de hacer click al raton
     * @param e es el evento del raton
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if (isIn(e, menuB)) {
            if (menuB.getMousePressed())
                GameState.state = GameState.MENU;
        } else
            audioOptions.mouseReleased(e);
        menuB.resetBools();
    }

    /**
     * Definimos un metodo para los movimiento del raton
     * @param e es el evento del raton
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        menuB.setMouseOver(false);

        if (isIn(e, menuB))
            menuB.setMouseOver(true);
        else
            audioOptions.mouseMoved(e);
    }

    /**
     * Definimos un metodo para cuando se presiona una tecla
     * @param e es el evento de tocar la tecla
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            GameState.state = GameState.MENU;
    }

    /**
     * Definimos un metodo para cuando se deja de presionar una tecla
     * @param e es el evento de la tecla
     */
    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    /**
     * Definimos un metodo para cuando se hace click al raton
     * @param e es el evento del raton
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    /**
     * Definimos un metodo para determinar si el raton esta dentro del boton de pausa
     * @param e es el evento del raton
     * @param b es el boton de pausa
     * @return true si esta dentro del boton de pausa, false si no esta dentro del boton de pausa
     */
    private boolean isIn(MouseEvent e, PauseButton b) {
        return b.getBounds().contains(e.getX(), e.getY());
    }

}