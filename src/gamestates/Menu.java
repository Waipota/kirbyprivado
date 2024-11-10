package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import main.Game;
import ui.MenuButton;

public class Menu extends State implements StateMethods {

  private MenuButton[] buttons = new MenuButton[3];
	public Menu(Game game) {
		super(game);
    loadButtons();
	}

  private void loadButtons(){
    buttons[0] = new MenuButton(Game.GAME_WIDTH / 2, (int) (150 * Game.SCALE), 0, GameState.PLAYING);
    buttons[1] = new MenuButton(Game.GAME_WIDTH / 2, (int) (220 * Game.SCALE), 0, GameState.OPTIONS);
    buttons[2] = new MenuButton(Game.GAME_WIDTH / 2, (int) (290 * Game.SCALE), 0, GameState.QUIT);

  }

	@Override
	public void update() {
    for (MenuButton mb: buttons){
      mb.update();
    }

	}

	@Override
	public void draw(Graphics g) {
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
        if (mb.isMousePresed()){
          mb.applyGamestate();
        }
      }
    }
    resetButtons();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

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