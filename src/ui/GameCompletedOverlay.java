package ui;

//Importamos las librerias y constantes que vamos a usar en la clase
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gamestates.GameState;
import gamestates.Playing;
import main.Game;
import utilz.LoadSave;

/**
 * Comienzo de la clase GameCompletedOverlay
 * @author Santiago
 */
public class GameCompletedOverlay {
    private Playing playing;
    private BufferedImage img;
    private MenuButton quit, credit;
    private int imgX, imgY, imgW, imgH;

    /**
     * Definimos el constructor de la clase
     * @param playing es el estado de juego
     */
    public GameCompletedOverlay(Playing playing) {
        this.playing = playing;
        createImg();
        createButtons();
    }

    /**
     * Definimos un metodo para crear los botones
     */
    private void createButtons() {
        quit = new MenuButton(Game.GAME_WIDTH / 2, (int) (270 * Game.SCALE), 2, GameState.MENU);
        credit = new MenuButton(Game.GAME_WIDTH / 2, (int) (200 * Game.SCALE), 3, GameState.CREDITS);
    }

    /**
     * Definimos un metodo para crear las imagenes de los botones, y tambien sus dimensiones
     */
    private void createImg() {
        img = LoadSave.GetSpriteAtlas(LoadSave.GAME_COMPLETED);
        imgW = (int) (img.getWidth() * Game.SCALE);
        imgH = (int) (img.getHeight() * Game.SCALE);
        imgX = Game.GAME_WIDTH / 2 - imgW / 2;
        imgY = (int) (100 * Game.SCALE);

    }

    /**
     * Definimos un metodo para dibujar el menu de juego completado
     * @param g es el grafico para poder dibujar
     */
    public void draw(Graphics g) {
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);

        g.drawImage(img, imgX, imgY, imgW, imgH, null);

        credit.draw(g);
        quit.draw(g);
    }

    /**
     * Definimos un metodo para actualizar el menu de juego completado
     */
    public void update() {
        credit.update();
        quit.update();
    }

    /**
     * Definimos un metodo para determinar si el raton esta dentro del boton del menu
     * @param b es el boton del menu
     * @param e es el evento del raton
     * @return true si esta dentro del boton, false si no esta dentro del boton
     */
    private boolean isIn(MenuButton b, MouseEvent e) {
        return b.getBounds().contains(e.getX(), e.getY());
    }

    /**
     * Definimos un metodo para detectar si se mueve el raton
     * @param e es el evento de mover el raton
     */
    public void mouseMoved(MouseEvent e) {
        credit.setMouseOver(false);
        quit.setMouseOver(false);

        if (isIn(quit, e))
            quit.setMouseOver(true);
        else if (isIn(credit, e))
            credit.setMouseOver(true);
    }

    /**
     * Definimos un metodo para detecta si se suelta el raton
     * @param e es el evento de soltar el raton
     */
    public void mouseReleased(MouseEvent e) {
        if (isIn(quit, e)) {
            if (quit.isMousePressed()) {
                playing.resetAll();
                playing.resetGameCompleted();
                playing.setGamestate(GameState.MENU);

            }
        } else if (isIn(credit, e))
            if (credit.isMousePressed()) {
                playing.resetAll();
                playing.resetGameCompleted();
                playing.setGamestate(GameState.CREDITS);
            }

        quit.resetBools();
        credit.resetBools();
    }

    /**
     * Definimos un metodo para detectar si se presiona el raton
     * @param e es el evento de presionar el raton
     */
    public void mousePressed(MouseEvent e) {
        if (isIn(quit, e))
            quit.setMousePressed(true);
        else if (isIn(credit, e))
            credit.setMousePressed(true);
    }
}