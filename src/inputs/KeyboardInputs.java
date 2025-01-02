package inputs;

//Importamos las librerias y las constantes que vamos a usar en la clase KeyboardInputs
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import gamestates.GameState;
import main.GamePanel;


/**
 * Comienzo de la clase KeyboardInputs que implementa el KeyListener para poder detectar si se presiona una tecla del teclado
 */
public class KeyboardInputs implements KeyListener {

	private GamePanel gamePanel;

	/**
	 * Definimos el constructor de la clase KeyboardInputs
	 * @param gamePanel es el panel en el que se muestra todo el juego
	 */
	public KeyboardInputs(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}

	/**
	 * Definimos un metodo para detectar si se deja de presionar una tecla
	 * @param e es el evento de dejar de presionar la tecla
	 */
	@SuppressWarnings("incomplete-switch")
	@Override
	public void keyReleased(KeyEvent e) {
		switch (GameState.state) {
			case MENU -> gamePanel.getGame().getMenu().keyReleased(e);
			case PLAYING -> gamePanel.getGame().getPlaying().keyReleased(e);
			case CREDITS -> gamePanel.getGame().getCredits().keyReleased(e);
		}
	}

	/**
	 * Definimos un metodo para detectar si se presiona una tecla
	 * @param e es el evento de presionar una tecla
	 */
	@SuppressWarnings("incomplete-switch")
	@Override
	public void keyPressed(KeyEvent e) {
		switch (GameState.state) {
			case MENU -> gamePanel.getGame().getMenu().keyPressed(e);
			case PLAYER_SELECTION -> gamePanel.getGame().getPlayerSelection().keyPressed(e);
			case PLAYING -> gamePanel.getGame().getPlaying().keyPressed(e);
			case OPTIONS -> gamePanel.getGame().getGameOptions().keyPressed(e);
		}
	}

	/**
	 * Definimos un metodo para typear una tecla, no lo usamos en el programa pero viene con la clase
	 * @param e es el evento de typear una tecla
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		// Not In Use
	}
}