package levels;

//Importamos las librerias y constantes que vamos a usar en la clase Level Manager
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import main.Game;
import utilz.LoadSave;

/**
 * Comienzo de la clase LevelManager
 * @author Santiago
 */
public class LevelManager {

	private Game game;
	private BufferedImage[] levelSprite;
	private BufferedImage[] waterSprite;
	private ArrayList<Level> levels;
	private int lvlIndex = 0, aniTick, aniIndex;

	/**
	 * Definimos el constructor de la clase LevelManager
	 * @param game es el juego
	 */
	public LevelManager(Game game) {
		this.game = game;
		importOutsideSprites();
		createWater();
		levels = new ArrayList<>();
		buildAllLevels();
	}

	/**
	 * Definimos un metodo para crear el agua
	 */
	private void createWater() {
		waterSprite = new BufferedImage[5];
		BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.WATER_TOP);
		for (int i = 0; i < 4; i++)
			waterSprite[i] = img.getSubimage(i * 32, 0, 32, 32);
		waterSprite[4] = LoadSave.GetSpriteAtlas(LoadSave.WATER_BOTTOM);
	}

	/**
	 * Definimos un metodo para cargar el siguiente nivel despues de que se completa uno
	 */
	public void loadNextLevel() {
		Level newLevel = levels.get(lvlIndex);
		game.getPlaying().getEnemyManager().loadEnemies(newLevel);
		game.getPlaying().getPlayer().loadLvlData(newLevel.getLevelData());
		game.getPlaying().setMaxLvlOffset(newLevel.getLvlOffset());
		game.getPlaying().getObjectManager().loadObjects(newLevel);
	}

	/**
	 * Definimos un metodo para construir todos los niveles
	 */
	private void buildAllLevels() {
		BufferedImage[] allLevels = LoadSave.GetAllLevels();
		for (BufferedImage img : allLevels)
			levels.add(new Level(img));
	}

	/**
	 * Definimos un metodo para crear el nivel con la imagen de levelatlas
	 */
	private void importOutsideSprites() {
		BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
		levelSprite = new BufferedImage[48];
		for (int j = 0; j < 4; j++)
			for (int i = 0; i < 12; i++) {
				int index = j * 12 + i;
				levelSprite[index] = img.getSubimage(i * 32, j * 32, 32, 32);
			}
	}

	/**
	 * Definimos un metodo para dibujar los niveles
	 * @param g es el grafico para poder dibujar
	 * @param lvlOffset es el offset del nivel en el cual esta el jugador
	 */
	public void draw(Graphics g, int lvlOffset) {
		for (int j = 0; j < Game.TILES_IN_HEIGHT; j++)
			for (int i = 0; i < levels.get(lvlIndex).getLevelData()[0].length; i++) {
				int index = levels.get(lvlIndex).getSpriteIndex(i, j);
				int x = Game.TILES_SIZE * i - lvlOffset;
				int y = Game.TILES_SIZE * j;
				if (index == 48)
					g.drawImage(waterSprite[aniIndex], x, y, Game.TILES_SIZE, Game.TILES_SIZE, null);
				else if (index == 49)
					g.drawImage(waterSprite[4], x, y, Game.TILES_SIZE, Game.TILES_SIZE, null);
				else
					g.drawImage(levelSprite[index], x, y, Game.TILES_SIZE, Game.TILES_SIZE, null);
			}
	}

	/**
	 * Definimos un metodo para actualizar el levelManager
	 */
	public void update() {
		updateWaterAnimation();
	}

	/**
	 * Definimos un metodo para actualizar la animacion del agua
	 */
	private void updateWaterAnimation() {
		aniTick++;
		if (aniTick >= 40) {
			aniTick = 0;
			aniIndex++;

			if (aniIndex >= 4)
				aniIndex = 0;
		}
	}

	/**
	 * Definimos un getter para obtener el actual nivel en el cual esta el jugador
	 * @return el nivel actual en el que esta el jugador
	 */
	public Level getCurrentLevel() {
		return levels.get(lvlIndex);
	}

	/**
	 * Definimos un getter para obtener la cantidad de niveles
	 * @return la cantidad de niveles
	 */
	public int getAmountOfLevels() {
		return levels.size();
	}

	/**
	 * Definimos un getter para obtener el indice del nivel en el cual esta el jugador
	 * @return el indice del nivel actual en el que esta el jugador
	 */
	public int getLevelIndex() {
		return lvlIndex;
	}

	/**
	 * Definimos un setter para setear el indice del nivel en el cual esta el jugador
	 * @param lvlIndex es el indice del nivel en el cual esta el jugador
	 */
	public void setLevelIndex(int lvlIndex) {
		this.lvlIndex = lvlIndex;
	}
}