package ui;

//Importamos las librerias que vamos a usar en la clase SoundButton
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import utilz.LoadSave;
import static utilz.Constants.UI.PauseButtons.*;

/**
 * Comienzo de la clase Soundbutton
 * @author Santiago
 */
public class SoundButton extends PauseButton {

	private BufferedImage[][] soundImgs;
	private boolean mouseOver, mousePressed;
	private boolean muted;
	private int rowIndex, colIndex;

	/**
	 * Definimos el constructor de la clase SoundButton
	 * @param x es la posicion en x del boton de sonido
	 * @param y es la posicion en y del boton de sonido
	 * @param width es el ancho del boton de sonido
	 * @param height es el alto del boton de sonido
	 */
	public SoundButton(int x, int y, int width, int height) {
		super(x, y, width, height);

		loadSoundImgs();
	}

	/**
	 * Definimos un metodo para cargar las imagenes del boton de sonio
	 */
	private void loadSoundImgs() {
		BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.SOUND_BUTTONS);
		soundImgs = new BufferedImage[2][3];
		for (int j = 0; j < soundImgs.length; j++)
			for (int i = 0; i < soundImgs[j].length; i++)
				soundImgs[j][i] = temp.getSubimage(i * SOUND_SIZE_DEFAULT, j * SOUND_SIZE_DEFAULT, SOUND_SIZE_DEFAULT, SOUND_SIZE_DEFAULT);
	}

	/**
	 * Definimos un metodo para actualizar el boton de sonido
	 */
	public void update() {
		if (muted)
			rowIndex = 1;
		else
			rowIndex = 0;

		colIndex = 0;
		if (mouseOver)
			colIndex = 1;
		if (mousePressed)
			colIndex = 2;

	}

	/**
	 * Definimos un metodo para resetear los valores de mouseOver y mousePressed a false
	 */
	public void resetBools() {
		mouseOver = false;
		mousePressed = false;
	}

	/**
	 * Definimos un metodo para dibujar el boton de sonido
	 * @param g es el grafico para poder dibujar
	 */
	public void draw(Graphics g) {
		g.drawImage(soundImgs[rowIndex][colIndex], x, y, width, height, null);
	}

	/**
	 * Definimos setter para mouseOver
	 * @param mouseOver es el valor booleano de mouseOver
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

	/**
	 * Definimos un getter de muted
	 * @return el valor booleano de muted
	 */
	public boolean getMuted() {
		return muted;
	}

	/**
	 * Definimos un setter para muted
	 * @param muted el valor booleano de muted
	 */
	public void setMuted(boolean muted) {
		this.muted = muted;
	}

}