package inputs;

//Importamos las librerias y las constantes que vamos a usar en la clase de mouseInputs
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import gamestates.GameState;
import main.GamePanel;

/**
 * Comienzo de la clase MouseInputs que detecta las entradas del raton, la clase implementa el mouseListener y el mouseMotionListener
 * @author Santiago
 */
public class MouseInputs implements MouseListener, MouseMotionListener {

    private GamePanel gamePanel;

    /**
     * Definimos el constructor de la clase MouseInputs
     * @param gamePanel es el panel en el que esta todo el juego
     */
    public MouseInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    /**
     * Definimos un metodo para detectar si se dragea el raton
     * @param e es el evento de dragar el raton
     */
    @SuppressWarnings("incomplete-switch")
    @Override
    public void mouseDragged(MouseEvent e) {
        switch (GameState.state) {
            case PLAYING -> gamePanel.getGame().getPlaying().mouseDragged(e);
            case OPTIONS -> gamePanel.getGame().getGameOptions().mouseDragged(e);
        }
    }

    /**
     * Definimos el metodo para detectar si se movio el raton
     * @param e es el evento de move el raton
     */
    @SuppressWarnings("incomplete-switch")
    @Override
    public void mouseMoved(MouseEvent e) {
        switch (GameState.state) {
            case MENU -> gamePanel.getGame().getMenu().mouseMoved(e);
            case PLAYING -> gamePanel.getGame().getPlaying().mouseMoved(e);
            case OPTIONS -> gamePanel.getGame().getGameOptions().mouseMoved(e);
            case PLAYER_SELECTION -> gamePanel.getGame().getPlayerSelection().mouseMoved(e);
        }
    }

    /**
     * Definimos un metodo para detectar si se clickeo el raton
     * @param e es el evento de clickear el raton
     */
    @SuppressWarnings("incomplete-switch")
    @Override
    public void mouseClicked(MouseEvent e) {
        switch (GameState.state) {
            case PLAYING -> gamePanel.getGame().getPlaying().mouseClicked(e);
        }
    }

    /**
     * Definimos un metodo para detectar si se ha presionado el raton
     * @param e es el evento de presionar el raton
     */
    @SuppressWarnings("incomplete-switch")
    @Override
    public void mousePressed(MouseEvent e) {
        switch (GameState.state) {
            case MENU -> gamePanel.getGame().getMenu().mousePressed(e);
            case PLAYING -> gamePanel.getGame().getPlaying().mousePressed(e);
            case OPTIONS -> gamePanel.getGame().getGameOptions().mousePressed(e);
            case PLAYER_SELECTION -> gamePanel.getGame().getPlayerSelection().mousePressed(e);
        }
    }

    /**
     * Definimos un metodo para detectar si ha soltado el raton
     * @param e es el evento de soltar el raton
     */
    @SuppressWarnings("incomplete-switch")
    @Override
    public void mouseReleased(MouseEvent e) {
        switch (GameState.state) {
            case MENU -> gamePanel.getGame().getMenu().mouseReleased(e);
            case PLAYING -> gamePanel.getGame().getPlaying().mouseReleased(e);
            case OPTIONS -> gamePanel.getGame().getGameOptions().mouseReleased(e);
            case PLAYER_SELECTION -> gamePanel.getGame().getPlayerSelection().mouseReleased(e);
        }
    }

    /**
     * Definimos un metodo para detectar si se ha entrado al raton, no se usa en el programa
     * @param e es el evento de entrar al raton
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        // Not In use
    }

    /**
     * Definimos un metodo para detectar si se ha salido del raton
     * @param e es el evento de salir del raton
     */
    @Override
    public void mouseExited(MouseEvent e) {
        // Not In use
    }

}