package ui;

//Importamos las librerias y las constantes que vamos a usar en la clase MenuButton
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import gamestates.GameState;
import utilz.LoadSave;
import static utilz.Constants.UI.Buttons.*;

/**
 * Comienzo de la clase menubutton
 * @author Santiago
 */
public class MenuButton {
	private int xPos, yPos, rowIndex, index;
	private int xOffsetCenter = B_WIDTH / 2;
	private GameState state;
	private BufferedImage[] imgs;
	private boolean mouseOver, mousePressed;
	private Rectangle bounds;

	/**
	 * Definimos el constructor de la clase
	 * @param xPos es la posicion en x del boton
	 * @param yPos es la posicion en y del boton
	 * @param rowIndex es el indice de la columna
	 * @param state es el estado de juego
	 */
	public MenuButton(int xPos, int yPos, int rowIndex, GameState state) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.rowIndex = rowIndex;
		this.state = state;
		loadImgs();
		initBounds();
	}

	/**
	 * Definimos un metodo para iniciar los bounds del boton
	 */
	private void initBounds() {
		bounds = new Rectangle(xPos - xOffsetCenter, yPos, B_WIDTH, B_HEIGHT);
	}

	/**
	 * Definimos un metodo para cargar las imagenes de los botones
	 */
	private void loadImgs() {
		imgs = new BufferedImage[3];
		BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.MENU_BUTTONS);
		for (int i = 0; i < imgs.length; i++)
			imgs[i] = temp.getSubimage(i * B_WIDTH_DEFAULT, rowIndex * B_HEIGHT_DEFAULT, B_WIDTH_DEFAULT, B_HEIGHT_DEFAULT);
	}

	/**
	 * Definimos un metodo para dibujar el boton del menu
	 * @param g es el grafico para poder dibujar
	 */
	public void draw(Graphics g) {
		g.drawImage(imgs[index], xPos - xOffsetCenter, yPos, B_WIDTH, B_HEIGHT, null);
	}

	/**
	 * Definimos un metodo para actualizar el boton del menu
	 */
	public void update() {
		index = 0;
		if (mouseOver)
			index = 1;
		if (mousePressed)
			index = 2;
	}

	/**
	 * Definimos un metodo getter para mouseOver
	 * @return el valor booleando mouseOver
	 */
	public boolean isMouseOver() {
		return mouseOver;
	}

	/**
	 * Definimos el setter para mouseOver
	 * @param mouseOver el valor booleano de mouseOver
	 */
	public void setMouseOver(boolean mouseOver) {
		this.mouseOver = mouseOver;
	}

	/**
	 * Definimos un getter para mousePressed
	 * @return el valor booleano de mousePressed
	 */
	public boolean isMousePressed() {
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
	 * Definimos el getter para los bounds de boton
	 * @return los bounds del boton
	 */
	public Rectangle getBounds() {
		return bounds;
	}

	/**
	 * Definimos un metodo para setear el estado de juego
	 */
	public void applyGamestate() {
		GameState.state = state;
	}

	/**
	 * Definimos un metodo para resetar los valores de mouseOver y mousePressed a false
	 */
	public void resetBools() {
		mouseOver = false;
		mousePressed = false;
	}

	/**
	 * Definimos un getter del estado actual de juego
	 * @return el estado actual de juego
	 */
	public GameState getState() {
		return state;
	}

}