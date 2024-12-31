package gamestates;
//Importamos las librerias y las constantes que vamos a usar en la clase Menu
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import main.Game;
import ui.MenuButton;
import utilz.LoadSave;

/**
 * Comienzo de la clase Menu hija de State y que implementa la interfaz StateMethods]
 * @author Santiago
 */
public class Menu extends State implements StateMethods {

    private MenuButton[] buttons = new MenuButton[4];
    private BufferedImage backgroundImg, backgroundImgPink;
    private int menuX, menuY, menuWidth, menuHeight;

    /**
     * Definimos el constructor de la clase Menu
     * @param game es la instancia del juego
     */
    public Menu(Game game) {
        super(game);
        loadButtons();
        loadBackground();
        backgroundImgPink = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND_IMG);

    }

    /**
     * Definimos un metodo para cargar las imagenes del background del menu
     */
    private void loadBackground() {
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND);
        menuWidth = (int) (backgroundImg.getWidth() * Game.SCALE);
        menuHeight = (int) (backgroundImg.getHeight() * Game.SCALE);
        menuX = Game.GAME_WIDTH / 2 - menuWidth / 2;
        menuY = (int) (25 * Game.SCALE);
    }

    /**
     * Definimos un metodo para cargar los botones del menu
     */
    private void loadButtons() {
        buttons[0] = new MenuButton(Game.GAME_WIDTH / 2, (int) (130 * Game.SCALE), 0, GameState.PLAYER_SELECTION);
        buttons[1] = new MenuButton(Game.GAME_WIDTH / 2, (int) (200 * Game.SCALE), 1, GameState.OPTIONS);
        buttons[2] = new MenuButton(Game.GAME_WIDTH / 2, (int) (270 * Game.SCALE), 3, GameState.CREDITS);
        buttons[3] = new MenuButton(Game.GAME_WIDTH / 2, (int) (340 * Game.SCALE), 2, GameState.QUIT);
    }

    /**
     * Definimos un metodo para actualizar el menu
     */
    @Override
    public void update() {
        for (MenuButton mb : buttons)
            mb.update();
    }

    /**
     * Definimos un metodo para dibujar el menu
     * @param g es el grafico para poder dibujar
     */
    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImgPink, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        g.drawImage(backgroundImg, menuX, menuY, menuWidth, menuHeight, null);

        for (MenuButton mb : buttons)
            mb.draw(g);
    }

    /**
     * Definimos un metodo para cuando se presiona el raton el menu
     * @param e es el evento del raton
     */
    @Override
    public void mousePressed(MouseEvent e) {
        for (MenuButton mb : buttons) {
            if (isIn(e, mb)) {
                mb.setMousePressed(true);
            }
        }
    }

    /**
     * Definimos un metodo para cuando se deja de presionar el raton
     * @param e es el evento del mouse
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        for (MenuButton mb : buttons) {
            if (isIn(e, mb)) {
                if (mb.isMousePressed())
                    mb.applyGamestate();
                if (mb.getState() == GameState.PLAYING)
                    game.getAudioPlayer().setLevelSong(game.getPlaying().getLevelManager().getLevelIndex());
                break;
            }
        }
        resetButtons();
    }

    /**
     * Definimos un metodo para resetear los botones del menu
     */
    private void resetButtons() {
        for (MenuButton mb : buttons)
            mb.resetBools();

    }

    /**
     * Definimos un metodo para cuando se mueve el raton en el menu
     * @param e es el evento de cuando se mueve el raton
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        for (MenuButton mb : buttons)
            mb.setMouseOver(false);

        for (MenuButton mb : buttons)
            if (isIn(e, mb)) {
                mb.setMouseOver(true);
                break;
            }

    }

    /**
     * Definimos un metodo para cuando se presiona una tecla en el menu
     * @param e es el evento de la tecla
     */
    @Override
    public void keyPressed(KeyEvent e) {
    }

    /**
     * Definimos un metodo para cuando se presiona el raton en el menu
     * @param e es el evento del raton
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    /**
     * Definimos un metodo para cuado se deja de presionar una tecla en el menu
     * @param e es el evento de la tecla
     */
    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }

}