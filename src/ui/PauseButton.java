package ui;

//Importamos las librerias y constantes que vamos a usar en la clase PauseButton
import java.awt.Rectangle;

/**
 * Comienzo de la clase PauseButton
 * @author Santiago
 */
public class PauseButton {

	protected int x, y, width, height;
	protected Rectangle bounds;

	/**
	 * Definimos el constructor de la clase
	 * @param x es la posicion en x del boton de pausa
	 * @param y es la posicion en y del boton de pausa
	 * @param width es el ancho del boton de pausa
	 * @param height es el alto del boton de pausa
	 */
	public PauseButton(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		createBounds();
	}


	/**
	 * Definimos un metodo que crea los límites del rectángulo basado en las coordenadas y dimensiones.
	 */
	private void createBounds() {
		bounds = new Rectangle(x, y, width, height);
	}

	/**
	 * Definimos un metodo que obtiene la coordenada X del rectángulo.
	 *
	 * @return el valor de la coordenada X.
	 */
	public int getX() {
		return x;
	}

	/**
	 * Definimos un metodo que establece la coordenada X del rectángulo.
	 *
	 * @param x el valor de la nueva coordenada X.
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Definimos un metodo que obtiene la coordenada Y del rectángulo.
	 *
	 * @return el valor de la coordenada Y.
	 */
	public int getY() {
		return y;
	}

	/**
	 * Definimos un metodo que establece la coordenada Y del rectángulo.
	 *
	 * @param y el valor de la nueva coordenada Y.
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Definimos un metodo que obtiene el ancho del rectángulo
	 * @return el valor del ancho.
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Definimos un metodo que establece el ancho del rectángulo.
	 *
	 * @param width el valor del nuevo ancho.
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * Definimos un metodo que obtiene la altura del rectángulo.
	 *
	 * @return el valor de la altura.
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Definimos un metodo que establece la altura del rectángulo.
	 *
	 * @param height el valor de la nueva altura.
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * Definimos un metodo que obtiene los límites del rectángulo.
	 *
	 * @return los límites del rectángulo.
	 */
	public Rectangle getBounds() {
		return bounds;
	}

	/**
	 * Definimos un metodo que establece los límites del rectángulo.
	 *
	 * @param bounds los nuevos límites.
	 */
	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

}