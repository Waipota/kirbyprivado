package ui;

//Importamos las librerias y las constantes que vamos a usar en la clase UrmButton
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import utilz.LoadSave;
import static utilz.Constants.UI.URMButtons.*;

/**
 * Comienzo de la clase UrmButton hija de la clase PauseButton
 * @author Santiago
 */
public class UrmButton extends PauseButton {
	private BufferedImage[] imgs;
	private int rowIndex, index;
	private boolean mouseOver, mousePressed;

	/**
	 * Definimos el constructor de la clase
	 * @param x es la posicion en x del boton
	 * @param y es la posicion en y del boton
	 * @param width es el ancho del boton
	 * @param height es el alto del boton
	 * @param rowIndex es el indice de
	 */
	public UrmButton(int x, int y, int width, int height, int rowIndex) {
		super(x, y, width, height);
		this.rowIndex = rowIndex;
		loadImgs();
	}

	/**
	 * Definimos un metodo para cargar las imagenes
	 */
	private void loadImgs() {
		BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.URM_BUTTONS);
		imgs = new BufferedImage[3];
		for (int i = 0; i < imgs.length; i++)
			imgs[i] = temp.getSubimage(i * URM_DEFAULT_SIZE, rowIndex * URM_DEFAULT_SIZE, URM_DEFAULT_SIZE, URM_DEFAULT_SIZE);

	}

	/**
	 * Definimos un metodo para actualizar el boton URM
	 */
	public void update() {
		index = 0;
		if (mouseOver)
			index = 1;
		if (mousePressed)
			index = 2;

	}

	/**
	 * Definimos un metodo para dibujar el boton URM
	 * @param g es el grafico para poder dibujar
	 */
	public void draw(Graphics g) {
		g.drawImage(imgs[index], x, y, URM_SIZE, URM_SIZE, null);
	}

	/**
	 * Definimos un metodo para resetar mouseOver y mousePressed a false
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
	 * Definimos un getter para mousePressed
	 * @return el valor booleano de mousePressed
	 */
	public boolean getMousePressed() {
		return mousePressed;
	}

	/**
	 * Definimos un setter para mousePressed
	 * @param mousePressed el valor booleano de mousePressed
	 */
	public void setMousePressed(boolean mousePressed) {
		this.mousePressed = mousePressed;
	}

}