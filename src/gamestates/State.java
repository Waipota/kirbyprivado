package gamestates;
//Importamos las librerias y las constantes que vamos a usar en la clase State
import java.awt.event.MouseEvent;

import audio.AudioPlayer;
import main.Game;
import ui.MenuButton;

/**
 * Comienzo de la clase state
 * @author Santiago
 */
public class State {

	protected Game game;

	/**
	 * Definimos el constructor de la clase State
	 * @param game es la instancia del juego
	 */
	public State(Game game) {
		this.game = game;
	}

	/**
	 * Definimos un metodo para determinar si el raton esta dentro del boton del menu
	 * @param e es el evento del raton
	 * @param mb es el boton del menu
	 * @return true si esta dentro del boton, false si no esta dentro del boton
	 */
	public boolean isIn(MouseEvent e, MenuButton mb) {
		return mb.getBounds().contains(e.getX(), e.getY());
	}

	/**
	 * Definimos un getter para el juego
	 * @return el juego
	 */
	public Game getGame() {
		return game;
	}

	/**
	 * Definimos un metodo para setear el estado en el que podemos estar que es o en el menu principal o jugando el juego, dependiendo del estado en el que estemos cambia la musica y los efectos
	 * @param state es el estado de juego en el que estamos
	 */
	@SuppressWarnings("incomplete-switch")
	public void setGamestate(GameState state) {
		switch (state) {
			case MENU -> game.getAudioPlayer().playSong(AudioPlayer.MENU_1);
			case PLAYING -> game.getAudioPlayer().setLevelSong(game.getPlaying().getLevelManager().getLevelIndex());
		}

		GameState.state = state;
	}

}