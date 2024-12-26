package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gamestates.GameState;
import gamestates.Playing;
import main.Game;
import utilz.LoadSave;

import static utilz.Constants.UI.URMButtons.URM_SIZE;

public class GameOverOverlay {
  private Playing playing; 
  private BufferedImage img;
  private int imgW, imgH, imgX, imgY;
  private UrmButton menu, play;
  public GameOverOverlay(Playing playing){
    this.playing = playing; 
    createImg();
    createButtuons();
  }


  private void createButtuons(){
    int menuX = (int) (335 * Game.SCALE);
    int playx = (int) (440 * Game.SCALE);
    int y = (int) (195 * Game.SCALE);
    play = new UrmButton(playx, y, URM_SIZE, URM_SIZE, 0);
    menu = new UrmButton(menuX, y, URM_SIZE, URM_SIZE, 2);
  }


  private void createImg(){
    img = LoadSave.GetSpriteAtlas(LoadSave.DEATH_SCREEN);
    imgW = (int) (img.getWidth() * Game.SCALE);
    imgH = (int) (img.getHeight() * Game.SCALE);
    imgX = Game.GAME_WIDTH / 2 - imgW / 2;
    imgY = (int) (100 * Game.SCALE);
  }


  public void draw(Graphics g){
    g.setColor(new Color(0, 0, 0, 200));
    g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
    g.drawImage(img, imgX, imgY, imgW, imgH, null);
    menu.draw(g);
    play.draw(g);
  }

  public void keyPressed(KeyEvent e) {

    if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
      playing.resetAll();
      GameState.state = GameState.MENU;
    }

  }
  private boolean isIn(UrmButton b, MouseEvent e) {
    return b.getBounds().contains(e.getX(), e.getY());
  }

  public void mouseMoved(MouseEvent e) {
    play.setMouseOver(false);
    menu.setMouseOver(false);

    if (isIn(menu, e))
      menu.setMouseOver(true);
    else if (isIn(play, e))
      play.setMouseOver(true);
  }

  public void mouseReleased(MouseEvent e) {
    if (isIn(menu, e)) {
      if (menu.getMousePressed()) {
        playing.resetAll();
        GameState.state = GameState.MENU;
      }
    } else if (isIn(play, e))
      if (play.getMousePressed())
        playing.resetAll();

    menu.resetBools();
    play.resetBools();
  }

  public void mousePressed(MouseEvent e) {
    if (isIn(menu, e))
      menu.setMousePressed(true);
    else if (isIn(play, e))
      play.setMousePressed(true);
  }

  public void update(){
    menu.update();
    play.update();
  }

}

