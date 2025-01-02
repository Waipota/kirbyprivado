package ui;

//Importamos las librerias y las constantes que se van a usar en la clase
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gamestates.GameState;
import gamestates.Playing;
import main.Game;
import utilz.LoadSave;
import static utilz.Constants.UI.URMButtons.*;

/**
 * Comienzo de la clase levelcompletedoverlay
 * @author Santiago
 */
public class LevelCompletedOverlay {

  private Playing playing;
  private UrmButton menu, next;
  private BufferedImage img;
  private int bgX, bgY, bgW, bgH;

  /**
   * Definimos el constructor de la clase
   * @param playing es el estado de juego
   */
  public LevelCompletedOverlay(Playing playing) {
    this.playing = playing;
    initImg();
    initButtons();
  }

  /**
   * Definimos un metodo para iniciar los botones
   */
  private void initButtons() {
    int menuX = (int) (330 * Game.SCALE);
    int nextX = (int) (445 * Game.SCALE);
    int y = (int) (195 * Game.SCALE);
    next = new UrmButton(nextX, y, URM_SIZE, URM_SIZE, 0);
    menu = new UrmButton(menuX, y, URM_SIZE, URM_SIZE, 2);
  }

  /**
   * Definimos un metodo para iniciar las imagenes
   */
  private void initImg() {
    img = LoadSave.GetSpriteAtlas(LoadSave.COMPLETED_IMG);
    bgW = (int) (img.getWidth() * Game.SCALE);
    bgH = (int) (img.getHeight() * Game.SCALE);
    bgX = Game.GAME_WIDTH / 2 - bgW / 2;
    bgY = (int) (75 * Game.SCALE);
  }

  /**
   * Definimos un metodo para dibujar el menu de nivel completado
   * @param g es el grafico para poder dibujar
   */
  public void draw(Graphics g) {
    g.setColor(new Color(0, 0, 0, 200));
    g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);

    g.drawImage(img, bgX, bgY, bgW, bgH, null);
    next.draw(g);
    menu.draw(g);
  }

  /**
   * Definimos un metodo para actualizar el menu de nivel completado
   */
  public void update() {
    next.update();
    menu.update();
  }

  /**
   * Definimos un metodo para determinar si el raton esta dentro de un boton
   * @param b es el boton urm
   * @param e es el evento del raton
   * @return true si esta dentro del boton , false si no esta dentro del raton
   */
  private boolean isIn(UrmButton b, MouseEvent e) {
    return b.getBounds().contains(e.getX(), e.getY());
  }

  /**
   * Definimos un metodo para detectar movimientos del raton
   * @param e es el evento de mover el raton
   */
  public void mouseMoved(MouseEvent e) {
    next.setMouseOver(false);
    menu.setMouseOver(false);

    if (isIn(menu, e))
      menu.setMouseOver(true);
    else if (isIn(next, e))
      next.setMouseOver(true);
  }

  /**
   * Definimos un metodo para detectar si se suelta el raton
   * @param e es el evento de soltar el raton
   */
  public void mouseReleased(MouseEvent e) {
    if (isIn(menu, e)) {
      if (menu.getMousePressed()) {
        playing.resetAll();
        playing.setGamestate(GameState.MENU);
      }
    } else if (isIn(next, e))
      if (next.getMousePressed()) {
        playing.loadNextLevel();
        playing.getGame().getAudioPlayer().setLevelSong(playing.getLevelManager().getLevelIndex());
      }

    menu.resetBools();
    next.resetBools();
  }

  /**
   * Definimos un metodo para detectar si se presiono el raton
   * @param e es el evento de presionar el raton
   */
  public void mousePressed(MouseEvent e) {
    if (isIn(menu, e))
      menu.setMousePressed(true);
    else if (isIn(next, e))
      next.setMousePressed(true);
  }

}