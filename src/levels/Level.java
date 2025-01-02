package levels;
//Importamos las librerias y las constantes que se van a usar en el programa
import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.Crabby;
import entities.Pinkstar;
import entities.Shark;
import main.Game;
import objects.BackgroundTree;
import objects.Cannon;
import objects.GameContainer;
import objects.Grass;
import objects.Potion;
import objects.Spike;

import static utilz.Constants.EnemyConstants.*;
import static utilz.Constants.ObjectConstants.*;

/**
 * Comienzo de la clase Level que representa un nivel del juego
 * @author Santiago
 */
public class Level {

	private BufferedImage img;
	private int[][] lvlData;

	private ArrayList<Crabby> crabs = new ArrayList<>();
	private ArrayList<Pinkstar> pinkstars = new ArrayList<>();
	private ArrayList<Shark> sharks = new ArrayList<>();
	private ArrayList<Potion> potions = new ArrayList<>();
	private ArrayList<Spike> spikes = new ArrayList<>();
	private ArrayList<GameContainer> containers = new ArrayList<>();
	private ArrayList<Cannon> cannons = new ArrayList<>();
	private ArrayList<BackgroundTree> trees = new ArrayList<>();
	private ArrayList<Grass> grass = new ArrayList<>();

	private int lvlTilesWide;
	private int maxTilesOffset;
	private int maxLvlOffsetX;
	private Point playerSpawn;

	/**
	 * Definimos el constructor de la clase Level
	 * @param img es la imagen del nivel en el cual esta el jugador
	 */
	public Level(BufferedImage img) {
		this.img = img;
		lvlData = new int[img.getHeight()][img.getWidth()];
		loadLevel();
		calcLvlOffsets();
	}

	/**
	 * Definimos un metodo para cargar el nivel usando colores en la imagen del nivel se crea en nivel, en donde esten ciertos colores aparecen objetos, enemigos, etc
	 */
	private void loadLevel() {

		// Looping through the image colors just once. Instead of one per
		// object/enemy/etc..
		// Removed many methods in HelpMethods class.

		for (int y = 0; y < img.getHeight(); y++)
			for (int x = 0; x < img.getWidth(); x++) {
				Color c = new Color(img.getRGB(x, y));
				int red = c.getRed();
				int green = c.getGreen();
				int blue = c.getBlue();

				loadLevelData(red, x, y);
				loadEntities(green, x, y);
				loadObjects(blue, x, y);
			}
	}

	/**
	 * Definimos un metodo para cargar la data del nivel, cambia dependiendo del nivel en el cual estemos
	 * @param redValue es el valor con el cual vamos a cargar diferentes imagenes
	 * @param x son las coordenadas en x
	 * @param y son las coordenadas en y
	 */
	private void loadLevelData(int redValue, int x, int y) {
		if (redValue >= 50)
			lvlData[y][x] = 0;
		else
			lvlData[y][x] = redValue;
		switch (redValue) {
			case 0, 1, 2, 3, 30, 31, 33, 34, 35, 36, 37, 38, 39 ->
					grass.add(new Grass((int) (x * Game.TILES_SIZE), (int) (y * Game.TILES_SIZE) - Game.TILES_SIZE, getRndGrassType(x)));
		}
	}

	/**
	 * Definimos un metodo para obtener el tipo de grama
	 * @param xPos es la posicion en x
	 * @return el valor del tipo de grama
	 */
	private int getRndGrassType(int xPos) {
		return xPos % 2;
	}

	/**
	 * Definimos un metodo para cargar las entidades en el nivel, cambia dependiendo del nivel en el que estemos
	 * @param greenValue el valor de verde que es en el cual van a aparecer los enemigos
	 * @param x son las coordenadas en x en donde van a aparecer los enemigos
	 * @param y son las coordenadas en y en donde van a aparecer los enemigos
	 */
	private void loadEntities(int greenValue, int x, int y) {
		switch (greenValue) {
			case CRABBY -> crabs.add(new Crabby(x * Game.TILES_SIZE, y * Game.TILES_SIZE));
			case PINKSTAR -> pinkstars.add(new Pinkstar(x * Game.TILES_SIZE, y * Game.TILES_SIZE));
			case SHARK -> sharks.add(new Shark(x * Game.TILES_SIZE, y * Game.TILES_SIZE));
			case 100 -> playerSpawn = new Point(x * Game.TILES_SIZE, y * Game.TILES_SIZE);
		}
	}

	/**
	 * Definimos un metodo para cargar los objetos en el nivel, cambia dependiendo del nivel en el que estemos
	 * @param blueValue es el valor blue que es en el cual aparecen los objetos
	 * @param x son las coordenadas en x en donde van a aparecer los objetos
	 * @param y son las coordenadas en y en donde van a aparecer los objetos
	 */
	private void loadObjects(int blueValue, int x, int y) {
		switch (blueValue) {
			case RED_POTION, BLUE_POTION -> potions.add(new Potion(x * Game.TILES_SIZE, y * Game.TILES_SIZE, blueValue));
			case BOX, BARREL -> containers.add(new GameContainer(x * Game.TILES_SIZE, y * Game.TILES_SIZE, blueValue));
			case SPIKE -> spikes.add(new Spike(x * Game.TILES_SIZE, y * Game.TILES_SIZE, SPIKE));
			case CANNON_LEFT, CANNON_RIGHT -> cannons.add(new Cannon(x * Game.TILES_SIZE, y * Game.TILES_SIZE, blueValue));
			case TREE_ONE, TREE_TWO, TREE_THREE -> trees.add(new BackgroundTree(x * Game.TILES_SIZE, y * Game.TILES_SIZE, blueValue));
		}
	}

	/**
	 * Definimos un metodo para calcular el offset del nivel, cambia dependiendo del nivel
	 */
	private void calcLvlOffsets() {
		lvlTilesWide = img.getWidth();
		maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
		maxLvlOffsetX = Game.TILES_SIZE * maxTilesOffset;
	}

	/**
	 * Definimos un metodo para obtener el indice de sprites del nivel
	 * @param x es el indice en x
	 * @param y es el indice en y
	 * @return el valor del arreglo en la posicion determinada
	 */
	public int getSpriteIndex(int x, int y) {
		return lvlData[y][x];
	}

	/**
	 * Definimos un getter de la informacion del nivel
	 * @return la informacion del nivel en el que esta el jugador
	 */
	public int[][] getLevelData() {
		return lvlData;
	}

	/**
	 * Definimos un getter para obtener el offset del nivel
	 * @return el offset del nivel en el cual esta el jugador
	 */
	public int getLvlOffset() {
		return maxLvlOffsetX;
	}

	/**
	 * Definimos un getter para obtener el punto en el cual aparece el jugador
	 * @return el punto en el cual aparece el jugador
	 */
	public Point getPlayerSpawn() {
		return playerSpawn;
	}

	/**
	 * Definimo un getter para obtener el arreglo de crabbies que tenga el nivel en el cual este el jugador
	 * @return el arreglo de crabbies
	 */
	public ArrayList<Crabby> getCrabs() {
		return crabs;
	}

	/**
	 * Definimos un getter para obtener el arreglo de sharks que tenga el nivel en el cual este el jugador
	 * @return el arreglo de sharks
	 */
	public ArrayList<Shark> getSharks() {
		return sharks;
	}

	/**
	 * Definimos un getter para obtener el arreglo de pociones que tenga el nivel en el cual esta el jugador
	 * @return el arreglo de pociones
	 */
	public ArrayList<Potion> getPotions() {
		return potions;
	}

	/**
	 * Definimos un getter para obtener el arreglo de contenedores en el nivel en el cual esta el jugador,
	 * @return el arreglo de contenedores
	 */
	public ArrayList<GameContainer> getContainers() {
		return containers;
	}

	/**
	 * Definimos un getter para obtener el arreglo de spikes en el nivel en el cual esta el jugador
	 * @return el arreglo de spikes
	 */
	public ArrayList<Spike> getSpikes() {
		return spikes;
	}

	/**
	 * Definimos un getter para obtener los cannons en el nivel en el cual esta el jugador
	 * @return el arreglo de cannons
	 */
	public ArrayList<Cannon> getCannons() {
		return cannons;
	}

	/**
	 * Definimos un getter para obtener las pinkstar que estan en el nivel en el cual esta el jugador, cambia dependiendo del nivel
	 * @return el arreglo de pinkstars
	 */
	public ArrayList<Pinkstar> getPinkstars() {
		return pinkstars;
	}

	/**
	 * Definimos un getter para obtener los arboles que estan en el nivel en el cual esta el jugador, cambia dependiendo del nivel
	 * @return el arreglo de arboles
	 */
	public ArrayList<BackgroundTree> getTrees() {
		return trees;
	}

	/**
	 * Definimos un metodo para obtener la grama en el nivel en el cual esta el jugador, cambia dependiendo del nivel
	 * @return el arreglo de grama
	 */
	public ArrayList<Grass> getGrass() {
		return grass;
	}

}