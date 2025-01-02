package main;

//Importamos las librerias y las constantes que se van a usar en la clase de Game
import java.awt.Graphics;

import audio.AudioPlayer;
import entities.Player;
import gamestates.*;
import ui.AudioOptions;

/**
 * Comienzo de la clase Game que implementa runnable para poder tener el loop del juego
 * @author Santiago
 */
public class Game implements Runnable {

	private GamePanel gamePanel;
	private Thread gameThread;
	private final int FPS_SET = 120;
	private final int UPS_SET = 200;

	private Playing playing;
	private Menu menu;
	private Credits credits;
	private GameOptions gameOptions;
	private AudioOptions audioOptions;
	private AudioPlayer audioPlayer;
	private PlayerSelection playerSelection;
	public final static int TILES_DEFAULT_SIZE = 32;
	public final static float SCALE = 2f;
	public final static int TILES_IN_WIDTH = 26;
	public final static int TILES_IN_HEIGHT = 14;
	public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
	public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
	public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;

	private final boolean SHOW_FPS_UPS = true;

	/**
	 * Definimos el constructor de la clase Game
	 */
	public Game() {
		initClasses();
		gamePanel = new GamePanel(this);
		new GameWindow(gamePanel);
		gamePanel.requestFocusInWindow();
		startGameLoop();
	}

	/**
	 * Definimos un metodo para iniciar las clases que van a tener el juego que son el menu de audio, el audioplayer, el menu , el estado de playing, etc
	 */
	private void initClasses() {
		audioOptions = new AudioOptions(this);
		audioPlayer = new AudioPlayer();
		menu = new Menu(this);
		playing = new Playing(this);
		playerSelection = new PlayerSelection(this);
		credits = new Credits(this);
		gameOptions = new GameOptions(this);
	}

	/**
	 * Definimos un metodo para iniciar el game loop, usando threads
	 */
	private void startGameLoop() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	/**
	 * Definimos un metodo para actualizar el juego
	 */
	public void update() {
		switch (GameState.state) {
			case MENU -> menu.update();
			case PLAYER_SELECTION -> playerSelection.update();
			case PLAYING -> playing.update();
			case OPTIONS -> gameOptions.update();
			case CREDITS -> credits.update();
			case QUIT -> System.exit(0);
		}
	}

	/**
	 * Definimos un metodo para dibujar el juego, dependiendo del estado de juego en el cual estemos
	 * @param g es el grafico para poder dibujar
	 */
	@SuppressWarnings("incomplete-switch")
	public void render(Graphics g) {
		switch (GameState.state) {
			case MENU -> menu.draw(g);
			case PLAYER_SELECTION -> playerSelection.draw(g);
			case PLAYING -> playing.draw(g);
			case OPTIONS -> gameOptions.draw(g);
			case CREDITS -> credits.draw(g);
		}
	}

	/**
	 * Definimos un metodo para poder ejecutar el ciclo principal del juego, manejando la logica de la actualizacion y renderizado a una tasa especifica de fotogramaas por segundo y actualizaciones por segundo
	 */
	@Override
	public void run() {
		double timePerFrame = 1000000000.0 / FPS_SET;
		double timePerUpdate = 1000000000.0 / UPS_SET;

		long previousTime = System.nanoTime();

		int frames = 0;
		int updates = 0;
		long lastCheck = System.currentTimeMillis();

		double deltaU = 0;
		double deltaF = 0;

		while (true) {

			long currentTime = System.nanoTime();

			deltaU += (currentTime - previousTime) / timePerUpdate;
			deltaF += (currentTime - previousTime) / timePerFrame;
			previousTime = currentTime;

			if (deltaU >= 1) {

				update();
				updates++;
				deltaU--;

			}

			if (deltaF >= 1) {

				gamePanel.repaint();
				frames++;
				deltaF--;

			}

			if (SHOW_FPS_UPS)
				if (System.currentTimeMillis() - lastCheck >= 1000) {

					lastCheck = System.currentTimeMillis();
					System.out.println("FPS: " + frames + " | UPS: " + updates);
					frames = 0;
					updates = 0;

				}

		}
	}

	/**
	 * Definimos un metodo para cuando se pierde el enfoque de la pantalla
	 */
	public void windowFocusLost() {
		if (GameState.state == GameState.PLAYING)
			playing.getPlayer().resetDirBooleans();
	}

	/**
	 * Definimos un getter para obtener el menu del juego
	 * @return el menu del juego
	 */
	public Menu getMenu() {
		return menu;
	}

	/**
	 * Definimos un getter para obtener el estado de juego de jugar el juego
	 * @return el estado de jugar
	 */
	public Playing getPlaying() {
		return playing;
	}

	/**
	 * Definimos un getter para obtener los creditos del juego
	 * @return los creditos del juego
	 */
	public Credits getCredits() {
		return credits;
	}

	/**
	 * Definimos un getter para obtener el menu de las opciones del juego
	 * @return el menu de opciones
	 */
	public GameOptions getGameOptions() {
		return gameOptions;
	}

	/**
	 * Definimos un getter para obtener el menu de opciones de audio
	 * @return el menu de opciones de audio
	 */
	public AudioOptions getAudioOptions() {
		return audioOptions;
	}

	/**
	 * Definimos un getter para obtener el audio player que es el que se encarga de reproducir el audio del juego al igual que los efectos
	 * @return el audio player
	 */
	public AudioPlayer getAudioPlayer() {
		return audioPlayer;
	}

	/**
	 * Definimos un getter para obtener el menu de seleccion de personaje
	 * @return el menu de seleccion de personaje
	 */
	public PlayerSelection getPlayerSelection() {
		return playerSelection;

	}
}