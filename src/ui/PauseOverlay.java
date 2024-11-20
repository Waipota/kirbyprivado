package ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gamestates.GameState;
import gamestates.Playing;
import main.Game;
import utilz.Constants;
import utilz.LoadSave;
import static utilz.Constants.UI.PauseButtons.*;
import static utilz.Constants.UI.URMButtons.*;

public class PauseOverlay {

	private Playing playing;
	private BufferedImage backgroundImg;
	private int bgX, bgY, bgW, bgH;
	private SoundButton musicButton, sfxButton;
  private URMButtons menub, replayb, unpauseb;


	public PauseOverlay(Playing playing) {
		this.playing = playing;
		loadBackground();
		createSoundButtons();
    createUrmButtons();
	}

  private void createUrmButtons() {
    int menux = (int) (313 * Game.SCALE);
    int replayx = (int) (387 * Game.SCALE);
    int unpausex = (int) (462 * Game.SCALE);
    int by = (int) (325 * Game.SCALE);
    menub = new URMButtons(menux, by, URM_SIZE, URM_SIZE, 2);
    replayb = new URMButtons(replayx, by, URM_SIZE, URM_SIZE, 1);
    unpauseb = new URMButtons(unpausex, by, URM_SIZE, URM_SIZE, 0);

  }

	private void createSoundButtons() {
		int soundX = (int) (450 * Game.SCALE);
		int musicY = (int) (140 * Game.SCALE);
		int sfxY = (int) (186 * Game.SCALE);
		musicButton = new SoundButton(soundX, musicY, SOUND_SIZE, SOUND_SIZE);
		sfxButton = new SoundButton(soundX, sfxY, SOUND_SIZE, SOUND_SIZE);

	}

	private void loadBackground() {
		backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.PAUSED_MENU);
		bgW = (int) (backgroundImg.getWidth() * Game.SCALE);
		bgH = (int) (backgroundImg.getHeight() * Game.SCALE);
		bgX = Game.GAME_WIDTH / 2 - bgW / 2;
		bgY = (int) (25 * Game.SCALE);

	}

	public void update() {

		musicButton.update();
		sfxButton.update();
    menub.update();
    replayb.update();
    unpauseb.update();
	}

	public void draw(Graphics g) {
		// Background
		g.drawImage(backgroundImg, bgX, bgY, bgW, bgH, null);

		// Sound buttons
		musicButton.draw(g);
		sfxButton.draw(g);
     

    //Urm buttons
    menub.draw(g);
    replayb.draw(g);
    unpauseb.draw(g);

	}

	public void mouseDragged(MouseEvent e) {
    //Codigo por terminar 
	}

	public void mousePressed(MouseEvent e) {
		if (isIn(e, musicButton))
			musicButton.setMousePressed(true);
		else if (isIn(e, sfxButton))
			sfxButton.setMousePressed(true);

    else if (isIn(e, menub)){
      menub.setMousePressed(true);
    }
    else if (isIn(e, replayb)){
      replayb.setMousePressed(true);
    }
    else if (isIn(e, unpauseb)){
      unpauseb.setMousePressed(true);
    }
	}

	public void mouseReleased(MouseEvent e) {
		if (isIn(e, musicButton)) {
			if (musicButton.getMousePressed())
				musicButton.setMuted(!musicButton.getMuted());

		} else if (isIn(e, sfxButton)) {
			if (sfxButton.getMousePressed())
				sfxButton.setMuted(!sfxButton.getMuted());
    }

    else if (isIn(e, menub)){
      if (menub.getMousePressed()){
        GameState.state = GameState.MENU;
      }
    }
    else if (isIn(e, replayb)){
      if (replayb.getMousePressed()){
        System.out.println("---Replay level---");
      }
    }

    else if (isIn(e, unpauseb)){
      if (unpauseb.getMousePressed()){
        playing.unpauseGame();
      }
    }
		musicButton.resetBools();
		sfxButton.resetBools();
    menub.resetBools();
    replayb.resetBools();
    unpauseb.resetBools();
	}

	public void mouseMoved(MouseEvent e) {
		musicButton.setMouseOver(false);
		sfxButton.setMouseOver(false);
    menub.setMouseOver(false);
    replayb.setMouseOver(false);
    unpauseb.setMouseOver(false);


		if (isIn(e, musicButton))
			musicButton.setMouseOver(true);
		else if (isIn(e, sfxButton))
			sfxButton.setMouseOver(true);
    else if (isIn(e, menub)){
      menub.setMouseOver(true);
    }
    else if (isIn(e, replayb)){
      replayb.setMouseOver(true);
    }
    else if (isIn(e, unpauseb)){
      unpauseb.setMouseOver(true);
    }
	}
  

	private boolean isIn(MouseEvent e, PauseButton b) {
		return b.getBounds().contains(e.getX(), e.getY());
	}

  
}