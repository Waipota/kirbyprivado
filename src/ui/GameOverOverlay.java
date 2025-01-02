package ui;

//Importamos las librerias y constantes que vamos a usar en la clase GameOverOverlay
import static utilz.Constants.UI.URMButtons.URM_SIZE;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gamestates.GameState;
import gamestates.Playing;
import main.Game;
import utilz.LoadSave;

/**
 * Comienzo de la clase GameOverOverlay que representa el menu de cuando se muere un jugador
 * @author Santiago
 */
public class GameOverOverlay {

  private Playing playing;
  private BufferedImage img;
  private int imgX, imgY, imgW, imgH;
  private UrmButton menu, play;

  /**
   * Definimos el constructor
   * @param playing es el estado de juego
   */
  public GameOverOverlay(Playing playing) {
    this.playing = playing;
    createImg();
    createButtons();
  }

  /**
   * Definimos un metodo para crear los botones
   */
  private void createButtons() {
    int menuX = (int) (335 * Game.SCALE);
    int playX = (int) (440 * Game.SCALE);
    int y = (int) (195 * Game.SCALE);
    play = new UrmButton(playX, y, URM_SIZE, URM_SIZE, 0);
    menu = new UrmButton(menuX, y, URM_SIZE, URM_SIZE, 2);

  }

  /**
   * Definimos un metodo para crear las imagenes
   */
  private void createImg() {
    img = LoadSave.GetSpriteAtlas(LoadSave.DEATH_SCREEN);
    imgW = (int) (img.getWidth() * Game.SCALE);
    imgH = (int) (img.getHeight() * Game.SCALE);
    imgX = Game.GAME_WIDTH / 2 - imgW / 2;
    imgY = (int) (100 * Game.SCALE);

  }

  /**
   * Definimos un metodo para dibujar el menu de game over
   * @param g es el grafico para poder dibujar
   */
  public void draw(Graphics g) {
    g.setColor(new Color(0, 0, 0, 200));
    g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);

    g.drawImage(img, imgX, imgY, imgW, imgH, null);

    menu.draw(g);
    play.draw(g);
  }

  /**
   * Definimos un metodo para actualizar el menu de game over
   */
  public void update() {
    menu.update();
    play.update();
  }

  /**
   * Definimos un metodo para determiar si el raton esta dentro del boton Urm
   * @param b es el boton urm
   * @param e es el evento de raton
   * @return true si esta dentro del boton, false si no esta dentro del boton
   */
  private boolean isIn(UrmButton b, MouseEvent e) {
    return b.getBounds().contains(e.getX(), e.getY());
  }

  /**
   * Definimos un metodo para detectar si raton se mueve
   * @param e es el evento del movimiento del raton
   */
  public void mouseMoved(MouseEvent e) {
    play.setMouseOver(false);
    menu.setMouseOver(false);

    if (isIn(menu, e))
      menu.setMouseOver(true);
    else if (isIn(play, e))
      play.setMouseOver(true);
  }

  /**
   * Definimos un metodo para cuando se suelta el raton
   * @param e es el evento de soltar el raton
   */
  public void mouseReleased(MouseEvent e) {
    if (isIn(menu, e)) {
      if (menu.getMousePressed()) {
        playing.resetAll();
        playing.setGamestate(GameState.MENU);
      }
    } else if (isIn(play, e))
      if (play.getMousePressed()) {
        playing.resetAll();
        playing.getGame().getAudioPlayer().setLevelSong(playing.getLevelManager().getLevelIndex());
      }

    menu.resetBools();
    play.resetBools();
  }

  /**
   * Definimos un metodo para detectar si se presiona el raton
   * @param e es el evento de presionar el raton
   */
  public void mousePressed(MouseEvent e) {
    if (isIn(menu, e))
      menu.setMousePressed(true);
    else if (isIn(play, e))
      play.setMousePressed(true);
  }

}