package utilz;

//Importamos las librerias y constantes que vamos a usar en la clase de help methods
import java.awt.geom.Rectangle2D;

import main.Game;
import objects.Projectile;

/**
 * Definimos la clase de HelpMethods que contiene metodos que se usan en varias partes del programa
 * @author Santiago
 */
public class HelpMethods {

	/**
	 * Definimos un metodo para determinar si una entidad se puede mover a un sitio del nivel
	 * @param x es la posicion en x
	 * @param y es la posicion en y
	 * @param width es el ancho del jugador
	 * @param height es el alto del jugador
	 * @param lvlData es la informacion del nivel en el cual esta el jugador, cambia dependiendo del nivel
	 * @return true si el jugador se puede mover a el sitio en cuestion, false si no se puede mover al sitio
	 */
	public static boolean CanMoveHere(float x, float y, float width, float height, int[][] lvlData) {
		if (!IsSolid(x, y, lvlData))
			if (!IsSolid(x + width, y + height, lvlData))
				if (!IsSolid(x + width, y, lvlData))
					if (!IsSolid(x, y + height, lvlData))
						return true;
		return false;
	}

	/**
	 * Definimos un metodo para determinar si una estructura es solida
	 * @param x es la posicion en x
	 * @param y es la posicion en y
	 * @param lvlData es la informacion del nivel en el cual esta el jugador, cambia dependiendo del nivel
	 * @return true si es solido, false si no lo es
	 */
	private static boolean IsSolid(float x, float y, int[][] lvlData) {
		int maxWidth = lvlData[0].length * Game.TILES_SIZE;
		if (x < 0 || x >= maxWidth)
			return true;
		if (y < 0 || y >= Game.GAME_HEIGHT)
			return true;
		float xIndex = x / Game.TILES_SIZE;
		float yIndex = y / Game.TILES_SIZE;

		return IsTileSolid((int) xIndex, (int) yIndex, lvlData);
	}

	/**
	 * Definimos un metodo para determinar si un proyectil de los cannons le esta pegando a el nivel
	 * @param p es el proyectil que dispara el cannon
	 * @param lvlData es la informacion del nivel en el cual esta el jugador , cambia dependiendo del nivel
	 * @return true si el proyectil esta pegando a el nivel, false si no le esta pegando a el nivel
	 */
	public static boolean IsProjectileHittingLevel(Projectile p, int[][] lvlData) {
		return IsSolid(p.getHitbox().x + p.getHitbox().width / 2, p.getHitbox().y + p.getHitbox().height / 2, lvlData);
	}

	/**
	 * Definimos un metodo para determinar si una entidad esta dentro del agua
	 * @param hitbox es la hitbox del jugador
	 * @param lvlData es la informacion del jugador
	 * @return true si el jugador esta dentro del agua, false si no esta dentro del agua
	 */
	public static boolean IsEntityInWater(Rectangle2D.Float hitbox, int[][] lvlData) {
		if (GetTileValue(hitbox.x, hitbox.y + hitbox.height, lvlData) != 48)
			if (GetTileValue(hitbox.x + hitbox.width, hitbox.y + hitbox.height, lvlData) != 48)
				return false;
		return true;
	}

	/**
	 * Definimos un metodo para obtener el valor del tile actual (la altura en y)
	 * @param xPos es la posicion en x
	 * @param yPos es la posicion en y
	 * @param lvlData es la informacion del nivel
	 * @return el valor del tile actual
	 */
	private static int GetTileValue(float xPos, float yPos, int[][] lvlData) {
		int xCord = (int) (xPos / Game.TILES_SIZE);
		int yCord = (int) (yPos / Game.TILES_SIZE);
		return lvlData[yCord][xCord];
	}

	/**
	 * Definimos un metodo para determinar sin un tile es solido
	 * @param xTile es el tile en x
	 * @param yTile es el tile en y
	 * @param lvlData es la informacion del nivel en el cual esta el jugador, cambia dependiendo del nivel
	 * @return true si el tile es solido, false si el tile no es solido
	 */
	public static boolean IsTileSolid(int xTile, int yTile, int[][] lvlData) {
		int value = lvlData[yTile][xTile];

		switch (value) {
			case 11, 48, 49:
				return false;
			default:
				return true;
		}

	}

	/**
	 * Definimos un metodo para determinar la posicion de una entidad al lado del muro
	 * @param hitbox es la hitbox del jugador
	 * @param xSpeed es la velocidad del jugador
	 * @return el valor de la posicion al lado del muro
	 */
	public static float GetEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed) {
		int currentTile = (int) (hitbox.x / Game.TILES_SIZE);
		if (xSpeed > 0) {
			// Right
			int tileXPos = currentTile * Game.TILES_SIZE;
			int xOffset = (int) (Game.TILES_SIZE - hitbox.width);
			return tileXPos + xOffset - 1;
		} else
			// Left
			return currentTile * Game.TILES_SIZE;
	}

	/**
	 * Definimos un metodo para obtener posicion de una entidad en y arriba o abajo del muro
	 * @param hitbox es la hitbox del jugado r
	 * @param airSpeed es la velocidad aerea del jugador
	 * @return el valor de la posicion del jugador
	 */
	public static float GetEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed) {
		int currentTile = (int) (hitbox.y / Game.TILES_SIZE);
		if (airSpeed > 0) {
			// Falling - touching floor
			int tileYPos = currentTile * Game.TILES_SIZE;
			int yOffset = (int) (Game.TILES_SIZE - hitbox.height);
			return tileYPos + yOffset - 1;
		} else
			// Jumping
			return currentTile * Game.TILES_SIZE;

	}

	/**
	 * Definimos un metodo determinar si una entidad esta encima del piso
	 * @param hitbox es la hitbox de la entidad
	 * @param lvlData es la informacion del nivel actual, cambia dependiendo del nivel
	 * @return
	 */
	public static boolean IsEntityOnFloor(Rectangle2D.Float hitbox, int[][] lvlData) {
		if (!IsSolid(hitbox.x, hitbox.y + hitbox.height + 1, lvlData))
			if (!IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData))
				return false;
		return true;
	}

	/**
	 * Definimos un metodo para determinar si una entidad esta pisando un piso
	 * @param hitbox es la hitbox de la entidad
	 * @param xSpeed es la velocidad de la entidad
	 * @param lvlData es la informacion del nivel en el cual esta el jugador, cambia dependiendo del nivel
	 * @return true si es un piso, false si no es un piso
	 */
	public static boolean IsFloor(Rectangle2D.Float hitbox, float xSpeed, int[][] lvlData) {
		if (xSpeed > 0)
			return IsSolid(hitbox.x + hitbox.width + xSpeed, hitbox.y + hitbox.height + 1, lvlData);
		else
			return IsSolid(hitbox.x + xSpeed, hitbox.y + hitbox.height + 1, lvlData);
	}

	/**
	 * Definimos un metodo para determinar is es un piso pero sin la velocidad del personaje
	 * @param hitbox es la hitbox de la entidad
	 * @param lvlData es la informacion del nivel en el cual esta el jugador, cambia dependiendo del nivel
	 * @return true si es piso, false si no es piso
	 */
	public static boolean IsFloor(Rectangle2D.Float hitbox, int[][] lvlData) {
		if (!IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData))
			if (!IsSolid(hitbox.x, hitbox.y + hitbox.height + 1, lvlData))
				return false;
		return true;
	}

	/**
	 * Definimos un metodo para determinar si un cannon puede ver a un jugador
	 * @param lvlData es la informacion del nivel actual en el cual esta el jugador , cambia dependiendo del nivel
	 * @param firstHitbox es la primera hitbox que se quiere usar
	 * @param secondHitbox es la segunda hitbox que se quiere usar
	 * @param yTile es la tile en y
	 * @return true si el cannon puede ver al jugador, false si no lo puede ver
	 */
	public static boolean CanCannonSeePlayer(int[][] lvlData, Rectangle2D.Float firstHitbox, Rectangle2D.Float secondHitbox, int yTile) {
		int firstXTile = (int) (firstHitbox.x / Game.TILES_SIZE);
		int secondXTile = (int) (secondHitbox.x / Game.TILES_SIZE);

		if (firstXTile > secondXTile)
			return IsAllTilesClear(secondXTile, firstXTile, yTile, lvlData);
		else
			return IsAllTilesClear(firstXTile, secondXTile, yTile, lvlData);
	}

	/**
	 * Definimos un metodo para determinar si las tiles esta libres
	 * @param xStart es la posicion en x del inicio
	 * @param xEnd es la posicion en x del final
	 * @param y es la posicion en y
	 * @param lvlData es la informacion del nivel en el cual esta el jugador, cambia dependiendo del nivel
	 * @return true si estan libres, false si no estan libres
	 */
	public static boolean IsAllTilesClear(int xStart, int xEnd, int y, int[][] lvlData) {
		for (int i = 0; i < xEnd - xStart; i++)
			if (IsTileSolid(xStart + i, y, lvlData))
				return false;
		return true;
	}

	/**
	 * Definimos un metodo para determinar si las tiles son caminables
	 * @param xStart es la posicion en x del inicio
	 * @param xEnd es la posicion en y del final
	 * @param y es la posicion en y
	 * @param lvlData es la informacion del nivel en el cual esta el jugador, cambia dependiendo del nivel
	 * @return true si las tiles son caminables, false si no lo son
	 */
	public static boolean IsAllTilesWalkable(int xStart, int xEnd, int y, int[][] lvlData) {
		if (IsAllTilesClear(xStart, xEnd, y, lvlData))
			for (int i = 0; i < xEnd - xStart; i++) {
				if (!IsTileSolid(xStart + i, y + 1, lvlData))
					return false;
			}
		return true;
	}

	/**
	 * Definimos un metodo para determinar si la vista esta libre
	 * @param lvlData es la informacion actual del nivel en el cual esta el jugador, cambia dependiendo del nivel
	 * @param enemyBox es la hitbox del enemigo,
	 * @param playerBox es la hitbox del jugador
	 * @param yTile es la tile en y
	 * @return true si la vista esta libre, false si no esta libre
	 */
	public static boolean IsSightClear(int[][] lvlData, Rectangle2D.Float enemyBox, Rectangle2D.Float playerBox, int yTile) {
		int firstXTile = (int) (enemyBox.x / Game.TILES_SIZE);

		int secondXTile;
		if (IsSolid(playerBox.x, playerBox.y + playerBox.height + 1, lvlData))
			secondXTile = (int) (playerBox.x / Game.TILES_SIZE);
		else
			secondXTile = (int) ((playerBox.x + playerBox.width) / Game.TILES_SIZE);

		if (firstXTile > secondXTile)
			return IsAllTilesWalkable(secondXTile, firstXTile, yTile, lvlData);
		else
			return IsAllTilesWalkable(firstXTile, secondXTile, yTile, lvlData);
	}

}