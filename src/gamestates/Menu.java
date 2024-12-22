package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import main.Game;
import ui.MenuButton;
import utilz.LoadSave;

public class Menu extends State implements StateMethods {

  private MenuButton[] buttons = new MenuButton[3];
  private BufferedImage background, background_img;
  private int menux, menuy, menuwidth, menuheight;
	public Menu(Game game) {
		super(game);
    loadButtons();
    loadBackground();
    background_img = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND);
	}


  private void loadBackground(){
    background = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND);
    menuwidth = (int) (background.getWidth() * Game.SCALE);
    menuheight = (int) (background.getHeight() * Game.SCALE);
    menux =  (Game.GAME_WIDTH / 2 - menuwidth / 2);
    menuy = (int) (45 * Game.SCALE);
  }

  private void loadButtons(){
    buttons[0] = new MenuButton(Game.GAME_WIDTH / 2, (int) (150 * Game.SCALE), 0, GameState.PLAYING);
    buttons[1] = new MenuButton(Game.GAME_WIDTH / 2, (int) (220 * Game.SCALE), 1, GameState.OPTIONS);
    buttons[2] = new MenuButton(Game.GAME_WIDTH / 2, (int) (290 * Game.SCALE), 2, GameState.QUIT);

  }

	@Override
	public void update() {
    for (MenuButton mb: buttons){
      mb.update();
    }

	}

	@Override
	public void draw(Graphics g) {
    g.drawImage(background_img, 0, 0, Game.GAME_WIDTH, Game.GAME_WIDTH, null);
    g.drawImage(background, menux, menuy,menuwidth, menuheight, null);
    for (MenuButton mb: buttons){
      mb.draw(g);
    }

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
    for (MenuButton mb: buttons){
      if (isIn(e, mb)){
        mb.setMousePressed(true);
        break;
      }
    }


	}

	@Override
	public void mouseReleased(MouseEvent e) {

    for (MenuButton mb: buttons){
      if(isIn(e, mb)){
        if (mb.isMousePressed()){
          mb.applyGamestate();
          break;
        }
      }
    }
    resetButtons();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
    for (MenuButton mb : buttons){
      mb.setMouseOver(false);
    }
    for (MenuButton mb: buttons){
      if (isIn(e, mb)){
        mb.setMouseOver(true);
        break;
      }
    }
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
			GameState.state = GameState.PLAYING;

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
  public void resetButtons(){
    for (MenuButton mb: buttons){
      mb.resetBools();
    }
  }


}