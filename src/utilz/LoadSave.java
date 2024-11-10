package utilz;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;

import javax.imageio.ImageIO;

import main.Game;

public class LoadSave {

	public static final String PLAYER_ATLAS = "player_sprites.png";
	public static final String LEVEL_ATLAS = "outside_sprites.png";
	public static final String LEVEL_ONE_DATA = "level_one_data.png";
  public static final String MENU_BUTTON = "button_atlas.png";
  public static final String MENU_BACKGROUND = "menu_background.png";


	public static BufferedImage GetSpriteAtlas(String fileName) {
    BufferedImage imagen = null;
  try {
    File file = new File("../Resources/" + fileName);
    imagen = ImageIO.read(file);
    if (imagen == null) {
      System.out.println("Error: No se pudo cargar la imagen ");
      return null;
    }
  } catch (IOException e) {
    e.printStackTrace();
  }

  return imagen;
	}

	public static int[][] GetLevelData() {
		int[][] lvlData = new int[Game.TILES_IN_HEIGHT][Game.TILES_IN_WIDTH];
		BufferedImage img = GetSpriteAtlas(LEVEL_ONE_DATA);

		for (int j = 0; j < img.getHeight(); j++)
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getRed();
				if (value >= 48)
					value = 0;
				lvlData[j][i] = value;
			}
		return lvlData;

	}
}