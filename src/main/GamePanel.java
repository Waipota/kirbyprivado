package main;

//Importamos las librerias y las constantes que vamos a usar en la clase de GamePanel
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import inputs.KeyboardInputs;
import inputs.MouseInputs;
import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;

/**
 * Comienzo de la clase GamePanel
 * @author Santiago
 */
public class GamePanel extends JPanel {

	private MouseInputs mouseInputs;
	private Game game;

	/**
	 * Definimos el constructor de la clase GamePanel
	 * @param game es el juego
	 */
	public GamePanel(Game game) {
		mouseInputs = new MouseInputs(this);
		this.game = game;
		setPanelSize();
		addKeyListener(new KeyboardInputs(this));
		addMouseListener(mouseInputs);
		addMouseMotionListener(mouseInputs);
	}

	/**
	 * Definimos un metodo para setear el tamaño del panel en el que cual se muestra todo el juego
	 */
	private void setPanelSize() {
		Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
		setPreferredSize(size);
	}


	/**
	 * Definimos un metodo para dibujar el juego
	 * @param g es el grafico para poder dibujar
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		game.render(g);
	}

	/**
	 * Definimos un getter para obtener el juego
	 * @return el juego
	 */
	public Game getGame() {
		return game;
	}

}