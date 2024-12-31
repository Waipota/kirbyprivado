package entities;

//Importamos las librerias y las constantes que se van a usar en la clase de entidad
import static utilz.Constants.Directions.DOWN;
import static utilz.Constants.Directions.LEFT;
import static utilz.Constants.Directions.UP;
import static utilz.HelpMethods.CanMoveHere;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import main.Game;

/**
 * Comienzo de la clase de Entity
 * @author Santiago
 */
public abstract class Entity {

	protected float x, y;
	protected int width, height;
	protected Rectangle2D.Float hitbox;
	protected int aniTick, aniIndex;
	protected int state;
	protected float airSpeed;
	protected boolean inAir = false;
	protected int maxHealth;
	protected int currentHealth;
	protected Rectangle2D.Float attackBox;
	protected float walkSpeed;

	protected int pushBackDir;
	protected float pushDrawOffset;
	protected int pushBackOffsetDir = UP;

	/**
	 * Definimos el constructor de la clase Entity
	 * @param x es la coordenada x de la entidad
	 * @param y es la coordenada y de la entidad
	 * @param width es el ancho de la entidad
	 * @param height es el alto de la entidad
	 */
	public Entity(float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	/**
	 * Definimos un metodo para actualizar el offset de la entidad dependiendo de la direccion
	 */
	protected void updatePushBackDrawOffset() {
		float speed = 0.95f;
		float limit = -30f;

		if (pushBackOffsetDir == UP) {
			pushDrawOffset -= speed;
			if (pushDrawOffset <= limit)
				pushBackOffsetDir = DOWN;
		} else {
			pushDrawOffset += speed;
			if (pushDrawOffset >= 0)
				pushDrawOffset = 0;
		}
	}

	/**
	 * Definimos un metodo para pushear a la entidad
	 * @param pushBackDir es la direccion en la que se va a empujar a la entidad
	 * @param lvlData es la informacion del nivel en el que estamos
	 * @param speedMulti es el multiplicador de velocidad del empujon
	 */
	protected void pushBack(int pushBackDir, int[][] lvlData, float speedMulti) {
		float xSpeed = 0;
		if (pushBackDir == LEFT)
			xSpeed = -walkSpeed;
		else
			xSpeed = walkSpeed;

		if (CanMoveHere(hitbox.x + xSpeed * speedMulti, hitbox.y, hitbox.width, hitbox.height, lvlData))
			hitbox.x += xSpeed * speedMulti;
	}

	/**
	 * Definimos un metodo para dibujar la attackbox de la entidad
	 * @param g es el grafico para poder dibujar
	 * @param xLvlOffset es el offset del nivel en el que estamos, cambia dependiendo del nivel
	 */
	protected void drawAttackBox(Graphics g, int xLvlOffset) {
		g.setColor(Color.red);
		g.drawRect((int) (attackBox.x - xLvlOffset), (int) attackBox.y, (int) attackBox.width, (int) attackBox.height);
	}

	/**
	 * Definimos un metodo para dibujar la hitbox de la entidad
	 * @param g es el grafico para poder dibujar
	 * @param xLvlOffset es el offset del nivel en el que estamos, cambia dependiendo del nivel
	 */
	protected void drawHitbox(Graphics g, int xLvlOffset) {
		g.setColor(Color.PINK);
		g.drawRect((int) hitbox.x - xLvlOffset, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
	}

	/**
	 * Definimos un metodo para inicar la hitbox de la entidad
	 * @param width es el ancho de la hitbox
	 * @param height es el alto de la hitbox
	 */
	protected void initHitbox(int width, int height) {
		hitbox = new Rectangle2D.Float(x, y, (int) (width * Game.SCALE), (int) (height * Game.SCALE));
	}

	/**
	 * Definimos un getter de la hitbox de la entidad
	 * @return el hitbox de la entidad
	 */
	public Rectangle2D.Float getHitbox() {
		return hitbox;
	}

	/**
	 * Definimos un getter del estado de la entidad
	 * @return el numero que representa el estado
	 */
	public int getState() {
		return state;
	}

	/**
	 * Definimos un getter del indice de animacion de la entidad
	 * @return el indice de animacion de la entidad
	 */
	public int getAniIndex() {
		return aniIndex;
	}

	/**
	 * Definimos un metodo para cambiar el estado de la entidad
	 * @param state es el estado al que se quiere cambiar la entidad
	 */
	protected void newState(int state) {
		this.state = state;
		aniTick = 0;
		aniIndex = 0;
	}
}