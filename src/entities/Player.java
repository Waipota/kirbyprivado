package entities;

//Importamos las librerias y las constantes que vamos a usar en la clase de Player
import static utilz.Constants.PlayerConstants.*;
import static utilz.HelpMethods.*;
import static utilz.Constants.*;
import static utilz.Constants.Directions.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import audio.AudioPlayer;
import gamestates.Playing;
import main.Game;
import utilz.LoadSave;

/**
 * Comienzo de la clase player hija de la clase Entity
 * @author Santiago
 */
public class Player extends Entity {

	private BufferedImage[][] animations;
	private boolean moving = false, attacking = false;
	private boolean left, right, jump;
	private int[][] lvlData;
	private final PlayerCharacter playerCharacter;
	// Saltar / Gravedad

	private float jumpSpeed = -2.25f * Game.SCALE;
	private float fallSpeedAfterCollision = 0.5f * Game.SCALE;

	// StatusBarUI
	private BufferedImage statusBarImg;

	private int statusBarWidth = (int) (192 * Game.SCALE);
	private int statusBarHeight = (int) (58 * Game.SCALE);
	private int statusBarX = (int) (10 * Game.SCALE);
	private int statusBarY = (int) (10 * Game.SCALE);

	private int healthBarWidth = (int) (150 * Game.SCALE);
	private int healthBarHeight = (int) (4 * Game.SCALE);
	private int healthBarXStart = (int) (34 * Game.SCALE);
	private int healthBarYStart = (int) (14 * Game.SCALE);
	private int healthWidth = healthBarWidth;

	private int powerBarWidth = (int) (104 * Game.SCALE);
	private int powerBarHeight = (int) (2 * Game.SCALE);
	private int powerBarXStart = (int) (44 * Game.SCALE);
	private int powerBarYStart = (int) (34 * Game.SCALE);
	private int powerWidth = powerBarWidth;
	private int powerMaxValue = 200;
	private int powerValue = powerMaxValue;

	private int flipX = 0;
	private int flipW = 1;

	private boolean attackChecked;
	private Playing playing;

	private int tileY = 0;

	private boolean powerAttackActive;
	private int powerAttackTick;
	private int powerGrowSpeed = 15;
	private int powerGrowTick;

	/**
	 * Definimos el constructor de la clase Player
	 * @param playerCharacter es el personaje con el cual va a jugar en el juego
	 * @param playing es el estado de juego en el que va a estar el jugador
	 */
	public Player(PlayerCharacter playerCharacter, Playing playing) {
		super(0, 0, (int) (playerCharacter.spriteWidth * Game.SCALE), (int) (playerCharacter.spriteHeight * Game.SCALE));
		this.playing = playing;
		this.playerCharacter = playerCharacter;
		this.state = IDLE;
		this.maxHealth = 100;
		this.currentHealth = maxHealth;
		this.walkSpeed = Game.SCALE * 1.0f;
		animations = LoadSave.loadAnimations(playerCharacter);
		loadStatusBar();
		initHitbox(playerCharacter.hitboxW, playerCharacter.hitboxH);
		initAttackBox();
	}

	/**
	 * Definimos un metodo para cargar la imagen de la vida y el ataque especial
	 */
	private void loadStatusBar(){
		statusBarImg = LoadSave.GetSpriteAtlas(LoadSave.STATUS_BAR);

	}

	/**
	 * Definimos un metodo para setear el spawn del personaje
	 * @param spawn es el punto en el que vamos a spawnear a el jugador
	 */
	public void setSpawn(Point spawn) {
		this.x = spawn.x;
		this.y = spawn.y;
		hitbox.x = x;
		hitbox.y = y;
	}

	/**
	 * Definimos un metodo para iniciar la attackbox del jugador
	 */
	private void initAttackBox() {
		attackBox = new Rectangle2D.Float(x, y, (int) (35 * Game.SCALE), (int) (20 * Game.SCALE));
		resetAttackBox();
	}

	/**
	 * Definimos un metodo para actualizar el jugador
	 */
	public void update() {
		updateHealthBar();
		updatePowerBar();

		if (currentHealth <= 0) {
			if (state != DEAD) {
				state = DEAD;
				aniTick = 0;
				aniIndex = 0;
				playing.setPlayerDying(true);
				playing.getGame().getAudioPlayer().playEffect(AudioPlayer.DIE);

				// Check if player died in air
				if (!IsEntityOnFloor(hitbox, lvlData)) {
					inAir = true;
					airSpeed = 0;
				}
			} else if (aniIndex == playerCharacter.getSpriteAmount(DEAD) - 1 && aniTick >= ANI_SPEED - 1) {
				playing.setGameOver(true);
				playing.getGame().getAudioPlayer().stopSong();
				playing.getGame().getAudioPlayer().playEffect(AudioPlayer.GAMEOVER);
			} else {
				updateAnimationTick();

				// Fall if in air
				if (inAir)
					if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
						hitbox.y += airSpeed;
						airSpeed += GRAVITY;
					} else
						inAir = false;

			}

			return;
		}

		updateAttackBox();

		if (state == HIT) {
			if (aniIndex <= playerCharacter.getSpriteAmount(state) - 3)
				pushBack(pushBackDir, lvlData, 1.25f);
			updatePushBackDrawOffset();
		} else
			updatePos();

		if (moving) {
			checkPotionTouched();
			checkSpikesTouched();
			checkInsideWater();
			tileY = (int) (hitbox.y / Game.TILES_SIZE);
			if (powerAttackActive) {
				powerAttackTick++;
				if (powerAttackTick >= 35) {
					powerAttackTick = 0;
					powerAttackActive = false;
				}
			}
		}

		if (attacking || powerAttackActive)
			checkAttack();

		updateAnimationTick();
		setAnimation();
	}

	/**
	 * Definimos un metodo para revisar si el jugador esta dentro del agua, si esta dentro del agua el jugador se muere
	 */
	private void checkInsideWater() {
		if (IsEntityInWater(hitbox, playing.getLevelManager().getCurrentLevel().getLevelData()))
			currentHealth = 0;
	}

	/**
	 * Definimos un metodo para revisar si el jugador toco las spikes del nivel
	 */
	private void checkSpikesTouched() {
		playing.checkSpikesTouched(this);
	}

	/**
	 * Definimos un metodo para revisar si el jugador toco una pocion
	 */
	private void checkPotionTouched() {
		playing.checkPotionTouched(hitbox);
	}

	/**
	 * Definimos un metodo para revisar si el jugador esta atacando
	 */
	private void checkAttack() {
		if (attackChecked || aniIndex != 1)
			return;
		attackChecked = true;

		if (powerAttackActive)
			attackChecked = false;

		playing.checkEnemyHit(attackBox);
		playing.checkObjectHit(attackBox);
		playing.getGame().getAudioPlayer().playAttackSound();
	}

	/**
	 * Definimos un metodo para setear el attackbox en el lado derecho
	 */
	private void setAttackBoxOnRightSide() {
		attackBox.x = hitbox.x + hitbox.width - (int) (Game.SCALE * 5);
	}

	/**
	 * Definimos un metodo para setear el atatackbox en el lado izquierdo
	 */
	private void setAttackBoxOnLeftSide() {
		attackBox.x = hitbox.x - hitbox.width - (int) (Game.SCALE * 10);
	}

	/**
	 * Definimos un metodo para actualizar la attackbox del jugador
	 */
	private void updateAttackBox() {
		if (right && left) {
			if (flipW == 1) {
				setAttackBoxOnRightSide();
			} else {
				setAttackBoxOnLeftSide();
			}

		} else if (right || (powerAttackActive && flipW == 1))
			setAttackBoxOnRightSide();
		else if (left || (powerAttackActive && flipW == -1))
			setAttackBoxOnLeftSide();

		attackBox.y = hitbox.y + (Game.SCALE * 10);
	}

	/**
	 * Definimos un metodo para actualizar la barra de vida del jugador
	 */
	private void updateHealthBar() {
		healthWidth = (int) ((currentHealth / (float) maxHealth) * healthBarWidth);
	}

	/**
	 * Definimos un metodo actualizar la barra de poder
	 */
	private void updatePowerBar() {
		powerWidth = (int) ((powerValue / (float) powerMaxValue) * powerBarWidth);

		powerGrowTick++;
		if (powerGrowTick >= powerGrowSpeed) {
			powerGrowTick = 0;
			changePower(1);
		}
	}

	/**
	 * Definimos el metodo para dibujar el dibujar el jugador
	 * @param g es el grafico para poder dibujar
	 * @param lvlOffset es el offset del nivel
	 */
	public void render(Graphics g, int lvlOffset) {
		g.drawImage(animations[playerCharacter.getRowIndex(state)][aniIndex], (int) (hitbox.x - playerCharacter.xDrawOffset) - lvlOffset + flipX, (int) (hitbox.y - playerCharacter.yDrawOffset + (int) (pushDrawOffset)), width * flipW, height, null);
		drawHitbox(g, lvlOffset);
//		drawAttackBox(g, lvlOffset);
		drawUI(g);
	}

	/**
	 * Definimos el metodo para dibujar la barra de vida y la barra de poder
	 * @param g es el grafico para poder dibujar
	 */
	private void drawUI(Graphics g) {
		// Background ui
		g.drawImage(statusBarImg, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);

		// Health bar
		g.setColor(Color.red);
		g.fillRect(healthBarXStart + statusBarX, healthBarYStart + statusBarY, healthWidth, healthBarHeight);

		// Power Bar
		g.setColor(Color.yellow);
		g.fillRect(powerBarXStart + statusBarX, powerBarYStart + statusBarY, powerWidth, powerBarHeight);
	}

	/**
	 * Definimos el metodo actualizar la animacion del personaje
	 */
	private void updateAnimationTick() {
		aniTick++;
		if (aniTick >= ANI_SPEED) {
			aniTick = 0;
			aniIndex++;
			if (aniIndex >= playerCharacter.getSpriteAmount(state)) {
				aniIndex = 0;
				attacking = false;
				attackChecked = false;
				if (state == HIT) {
					newState(IDLE);
					airSpeed = 0f;
					if (!IsFloor(hitbox, 0, lvlData))
						inAir = true;
				}
			}
		}
	}

	/**
	 * Definimos el metodo para setear el estado de animacion del personaje dependiendo de lo que este haciendo
	 */
	private void setAnimation() {
		int startAni = state;

		if (state == HIT)
			return;

		if (moving)
			state = RUNNING;
		else
			state = IDLE;

		if (inAir) {
			if (airSpeed < 0)
				state = JUMP;
			else
				state = FALLING;
		}

		if (powerAttackActive) {
			state = ATTACK;
			aniIndex = 1;
			aniTick = 0;
			return;
		}

		if (attacking) {
			state = ATTACK;
			if (startAni != ATTACK) {
				aniIndex = 1;
				aniTick = 0;
				return;
			}
		}
		if (startAni != state)
			resetAniTick();
	}

	/**
	 * Definimos el metodo para resetear la animacion del personaje
	 */
	private void resetAniTick() {
		aniTick = 0;
		aniIndex = 0;
	}

	/**
	 * Definimos el metodo para actualizar la posicion del personaje
	 */

	private void updatePos() {
		moving = false;

		if (jump)
			jump();

		if (!inAir)
			if (!powerAttackActive)
				if ((!left && !right) || (right && left))
					return;

		float xSpeed = 0;

		if (left && !right) {
			xSpeed -= walkSpeed;
			flipX = width;
			flipW = -1;
		}
		if (right && !left) {
			xSpeed += walkSpeed;
			flipX = 0;
			flipW = 1;
		}

		if (powerAttackActive) {
			if ((!left && !right) || (left && right)) {
				if (flipW == -1)
					xSpeed = -walkSpeed;
				else
					xSpeed = walkSpeed;
			}

			xSpeed *= 3;
		}

		if (!inAir)
			if (!IsEntityOnFloor(hitbox, lvlData))
				inAir = true;

		if (inAir && !powerAttackActive) {
			if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
				hitbox.y += airSpeed;
				airSpeed += GRAVITY;
				updateXPos(xSpeed);
			} else {
				hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
				if (airSpeed > 0)
					resetInAir();
				else
					airSpeed = fallSpeedAfterCollision;
				updateXPos(xSpeed);
			}

		} else
			updateXPos(xSpeed);
		moving = true;
	}

	/**
	 * Definimos el metodo para que el personaje salte
	 */
	private void jump() {
		if (inAir)
			return;
		playing.getGame().getAudioPlayer().playEffect(AudioPlayer.JUMP);
		inAir = true;
		airSpeed = jumpSpeed;
	}

	/**
	 * Definimos un metodo para resetear los valores de inAir
	 */
	private void resetInAir() {
		inAir = false;
		airSpeed = 0;
	}

	/**
	 * Definimos un metodo para actualizar la posicion del personaje
	 * @param xSpeed es la velocidad del personaje
	 */
	private void updateXPos(float xSpeed) {
		if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData))
			hitbox.x += xSpeed;
		else {
			hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
			if (powerAttackActive) {
				powerAttackActive = false;
				powerAttackTick = 0;
			}
		}
	}

	/**
	 * Definimos un metodo para cambiar la vida del personaje
	 * @param value es el valor de vida que se le va a cambiar al personaje
	 */
	public void changeHealth(int value) {
		if (value < 0) {
			if (state == HIT)
				return;
			else
				newState(HIT);
		}

		currentHealth += value;
		currentHealth = Math.max(Math.min(currentHealth, maxHealth), 0);
	}

	/**
	 * Definimos un metodo para cambiar la vida del personaje cuando le pega un enemigo
	 * @param value es el valor que se la va a cambiar a la vida
	 * @param e es el enemigo que le pega al personaje
	 */
	public void changeHealth(int value, Enemy e) {
		if (state == HIT)
			return;
		changeHealth(value);
		pushBackOffsetDir = UP;
		pushDrawOffset = 0;

		if (e.getHitbox().x < hitbox.x)
			pushBackDir = RIGHT;
		else
			pushBackDir = LEFT;
	}

	/**
	 * Definimos el metodo para matar a el enemigo, le pone que la vida actual sea 0
	 */
	public void kill() {
		currentHealth = 0;
	}

	/**
	 * Definimos un metodo para cambiar el poder que tenga el jugador
	 * @param value es el valor que se le va a cambiar a el personaje
	 */
	public void changePower(int value) {
		powerValue += value;
		powerValue = Math.max(Math.min(powerValue, powerMaxValue), 0);
	}


	/**
	 * Definimos un metodo para cargar la data del nivel
	 * @param lvlData es la informacion del nivel en el que esta el jugador, cambiar dependiendo el nivel
	 */

	public void loadLvlData(int[][] lvlData) {
		this.lvlData = lvlData;
		if (!IsEntityOnFloor(hitbox, lvlData))
			inAir = true;
	}

	/**
	 * Definimos un metodo para resetear los valores de la direccion
	 */
	public void resetDirBooleans() {
		left = false;
		right = false;
	}

	/**
	 * Definimos el setter para el valor attacking
	 * @param attacking es el valor booleano que va a tener la variable attacking
	 */
	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
	}

	/**
	 * Definimos un getter del booleano left
	 * @return true si esta a la izquierda, false si no lo esta
	 */
	public boolean isLeft() {
		return left;
	}

	/**
	 * Definimos el setter para la variable left
	 * @param left el valor booleano
	 */
	public void setLeft(boolean left) {
		this.left = left;
	}

	/**
	 * Definimos un setter para el valor right
	 * @return el valor booleano de right
	 */
	public boolean isRight() {
		return right;
	}

	/**
	 * Definimos el setter de la variable right
	 * @param right es el valor booleano que va a tener la varible right
	 */
	public void setRight(boolean right) {
		this.right = right;
	}

	/**
	 * Definimos el setter de la variable jump
	 * @param jump el valor booleano
	 */
	public void setJump(boolean jump) {
		this.jump = jump;
	}

	/**
	 * Definimos un metodo para resetear los valores del jugador
	 */
	public void resetAll() {
		resetDirBooleans();
		inAir = false;
		attacking = false;
		moving = false;
		airSpeed = 0f;
		state = IDLE;
		currentHealth = maxHealth;
		powerAttackActive = false;
		powerAttackTick = 0;
		powerValue = powerMaxValue;

		hitbox.x = x;
		hitbox.y = y;
		resetAttackBox();

		if (!IsEntityOnFloor(hitbox, lvlData))
			inAir = true;
	}

	/**
	 * Definimos un metodo para resetear el attackbox el jugador
	 */
	private void resetAttackBox() {
		if (flipW == 1)
			setAttackBoxOnRightSide();
		else
			setAttackBoxOnLeftSide();
	}

	/**
	 * Definimos un getter para el atributo tiley
	 * @return el valor de tiley
	 */
	public int getTileY() {
		return tileY;
	}

	/**
	 * Definimos un metodo para hacer el powerAttack del personaje
	 */
	public void powerAttack() {
		if (powerAttackActive)
			return;
		if (powerValue >= 60) {
			powerAttackActive = true;
			changePower(-60);
		}

	}

	/**
	 * Definimos un getter para retornar el personaje que es el jugador
	 * @return el personaje que es el jugador
	 */
	public PlayerCharacter getPc() {
		return playerCharacter;
	}

}