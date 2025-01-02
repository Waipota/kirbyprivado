package ui;
//Importamos las librerias y las constantes que vamos a usar en la clase VolumeButton
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import utilz.LoadSave;
import static utilz.Constants.UI.VolumeButtons.*;

/**
 * Comienzo de la clase VolumeButton hija de PauseButton
 * @author Santiago
 */
public class VolumeButton extends PauseButton {

	private BufferedImage[] imgs;
	private BufferedImage slider;
	private int index = 0;
	private boolean mouseOver, mousePressed;
	private int buttonX, minX, maxX;
	private float floatValue = 0;


	/**
	 * Definimos el constructor de la clase
	 * @param x es la posicion en x del boton
	 * @param y es la posicion en y del boton
	 * @param width es el ancho del boton
	 * @param height es el alto del boton
	 */
	public VolumeButton(int x, int y, int width, int height) {
		super(x + width / 2, y, VOLUME_WIDTH , height);
		bounds.x -= VOLUME_WIDTH / 2;
		buttonX = x + width / 2;
		this.x = x;
		this.width = width;
		minX = x + VOLUME_WIDTH  / 2;
		maxX = x + width - VOLUME_WIDTH / 2;
		loadImgs();
	}

	/**
	 * Definimos un metodo para cargar las imagenes del boton
	 */
	private void loadImgs() {
		BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.VOLUME_BUTTONS);
		imgs = new BufferedImage[3];
		for (int i = 0; i < imgs.length; i++)
			imgs[i] = temp.getSubimage(i * VOLUME_DEFAULT_WIDTH, 0, VOLUME_DEFAULT_WIDTH, VOLUME_DEFAULT_HEIGHT);

		slider = temp.getSubimage(3 * VOLUME_DEFAULT_WIDTH, 0, SLIDER_DEFAULT_WIDTH, VOLUME_DEFAULT_HEIGHT);
		
	}

	/**
	 * Definimos un metodo para actualizar el boton
	 */
	public void update() {
		index = 0;
		if (mouseOver)
			index = 1;
		if (mousePressed)
			index = 2;

	}

	/**
	 * Definimos un metodo para dibujar el boton
	 * @param g es el grafico para poder dibujar
	 */
	public void draw(Graphics g) {

		g.drawImage(slider, x, y, width, height, null);
		g.drawImage(imgs[index], buttonX - VOLUME_WIDTH / 2, y, VOLUME_WIDTH, height, null);

	}

	/**
	 * Definimos un metodo para cambiar el valor de la posicion en x
	 * @param x el valor de la nueva posicion de x
	 */
	public void changeX(int x) {
		if (x < minX)
			buttonX = minX;
		else if (x > maxX)
			buttonX = maxX;
		else
			buttonX = x;
		updateFloatValue();

		bounds.x = buttonX - VOLUME_WIDTH / 2;

	}

	/**
	 * Definimos un metodo para actualizar el volumen real que suena en el juego
	 */
	private void updateFloatValue() {
		float range = maxX - minX;
		float value = buttonX - minX;
		floatValue = value / range;

	}


	/**
	 * Definimos un metodo que resetea los booleanos de mouseOver y mousePressed a false
	 */
	public void resetBools() {
		mouseOver = false;
		mousePressed = false;
	}

	/**
	 * Definimos un setter para mouseOver
	 * @param mouseOver el valor booleano de mouseOver
	 */
	public void setMouseOver(boolean mouseOver) {
		this.mouseOver = mouseOver;
	}

	/**
	 * Definimos un getter para el valor de mousePressed
	 * @return el valor booleano de mousePressed
	 */
	public boolean getMousePressed() {
		return mousePressed;
	}

	/**
	 * Definimos un setter para mousePressed
	 * @param mousePressed el valor de mousePressed
	 */
	public void setMousePressed(boolean mousePressed) {
		this.mousePressed = mousePressed;
	}

	/**
	 * Definimos un getter para floatValue
	 * @return el valor de floatValue
	 */
	public float getFloatValue() {
		return floatValue;
	}
}