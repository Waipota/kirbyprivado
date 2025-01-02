package ui;

//Importamos las librerias y constantes que vamos a usar en la clase
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gamestates.GameState;
import gamestates.Playing;
import main.Game;
import utilz.LoadSave;
import static utilz.Constants.UI.URMButtons.*;

/**
 * Comienzo de la clase PauseOverlay
 * @author Santiago
 */
public class PauseOverlay {

	private Playing playing;
	private BufferedImage backgroundImg;
	private int bgX, bgY, bgW, bgH;
	private AudioOptions audioOptions;
	private UrmButton menuB, replayB, unpauseB;

	/**
	 * Definimos el constructor de la clase
	 * @param playing es el estado de juego
	 */
	public PauseOverlay(Playing playing) {
		this.playing = playing;
		loadBackground();
		audioOptions = playing.getGame().getAudioOptions();
		createUrmButtons();
	}

	/**
	 * Definimos un metodo para crear los botones
	 */
	private void createUrmButtons() {
		int menuX = (int) (313 * Game.SCALE);
		int replayX = (int) (387 * Game.SCALE);
		int unpauseX = (int) (462 * Game.SCALE);
		int bY = (int) (325 * Game.SCALE);

		menuB = new UrmButton(menuX, bY, URM_SIZE, URM_SIZE, 2);
		replayB = new UrmButton(replayX, bY, URM_SIZE, URM_SIZE, 1);
		unpauseB = new UrmButton(unpauseX, bY, URM_SIZE, URM_SIZE, 0);
	}

	/**
	 * Definimos un metodo para cargar el background del menu de pausa
	 */
	private void loadBackground() {
		backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.PAUSE_BACKGROUND);
		bgW = (int) (backgroundImg.getWidth() * Game.SCALE);
		bgH = (int) (backgroundImg.getHeight() * Game.SCALE);
		bgX = Game.GAME_WIDTH / 2 - bgW / 2;
		bgY = (int) (25 * Game.SCALE);
	}

	/**
	 * Definimos un metodo para actualizar el menu de pausa
	 */
	public void update() {

		menuB.update();
		replayB.update();
		unpauseB.update();

		audioOptions.update();

	}

	/**
	 * Definimos un metodo para dibujar el menu de pausa
	 * @param g es el grafico para poder dibujar
	 */
	public void draw(Graphics g) {
		// Background
		g.drawImage(backgroundImg, bgX, bgY, bgW, bgH, null);

		// UrmButtons
		menuB.draw(g);
		replayB.draw(g);
		unpauseB.draw(g);

		audioOptions.draw(g);

	}

	/**
	 * Definimos un metodo para detectar cuando se arrastra el raton
	 * @param e es el evento de arrastrar el raton
	 */
	public void mouseDragged(MouseEvent e) {
		audioOptions.mouseDragged(e);
	}

	/**
	 * Definimos un metodo para detectar si se presiona el raton
	 * @param e es el evento de presionar el raton
	 */
	public void mousePressed(MouseEvent e) {
		if (isIn(e, menuB))
			menuB.setMousePressed(true);
		else if (isIn(e, replayB))
			replayB.setMousePressed(true);
		else if (isIn(e, unpauseB))
			unpauseB.setMousePressed(true);
		else
			audioOptions.mousePressed(e);
	}

	/**
	 * Definimos un metodo para detectar si se suelta el raton
	 * @param e es el evento de soltar el raton
 	 */
	public void mouseReleased(MouseEvent e) {
		if (isIn(e, menuB)) {
			if (menuB.getMousePressed()) {
				playing.resetAll();
				playing.setGamestate(GameState.MENU);
				playing.unpauseGame();
			}
		} else if (isIn(e, replayB)) {
			if (replayB.getMousePressed()) {
				playing.resetAll();
				playing.unpauseGame();
			}
		} else if (isIn(e, unpauseB)) {
			if (unpauseB.getMousePressed())
				playing.unpauseGame();
		} else
			audioOptions.mouseReleased(e);

		menuB.resetBools();
		replayB.resetBools();
		unpauseB.resetBools();

	}

	/**
	 * Definimos un metodo para mover el raton
	 * @param e es el evento de mover el raton
	 */
	public void mouseMoved(MouseEvent e) {
		menuB.setMouseOver(false);
		replayB.setMouseOver(false);
		unpauseB.setMouseOver(false);

		if (isIn(e, menuB))
			menuB.setMouseOver(true);
		else if (isIn(e, replayB))
			replayB.setMouseOver(true);
		else if (isIn(e, unpauseB))
			unpauseB.setMouseOver(true);
		else
			audioOptions.mouseMoved(e);
	}

	/**
	 * Definimos un metodo para determinar si el raton esta dentro del boton de pausa
	 * @param e es el evento del raton
	 * @param b es el boton de pausa
	 * @return true si el raton esta dentro del boton, false si no esta dentro del boton
	 */
	private boolean isIn(MouseEvent e, PauseButton b) {
		return b.getBounds().contains(e.getX(), e.getY());
	}

}