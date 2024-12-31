package gamestates;

import entities.Player;
import entities.PlayerCharacter;
import main.Game;
import ui.MenuButton;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.Serializable;

import static utilz.Constants.ANI_SPEED;
import static utilz.Constants.PlayerConstants.IDLE;

public class PlayerSelection extends State implements StateMethods{

    private BufferedImage backgroundImg, backgroundKirby;
    private int menuX, menuY, menuWidth, menuHeight;
    private MenuButton playButton;
    private CharacterAnimation[] characterAnimations;
    private int playerIndex = 0;
    public PlayerSelection(Game game) {
        super(game);
        loadButtons();
        loadBackground();
        backgroundKirby = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND_IMG);

        loadCharAnimations();
    }

    private void loadCharAnimations(){
        characterAnimations = new CharacterAnimation[3];
        int i = 0;
        characterAnimations[i++] = new CharacterAnimation(PlayerCharacter.PIRATE);
        characterAnimations[i++] = new CharacterAnimation(PlayerCharacter.KIRBY);
        characterAnimations[i++] = new CharacterAnimation(PlayerCharacter.ORC);

    }


    private void loadBackground() {
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND);
        menuWidth = (int) (backgroundImg.getWidth() * Game.SCALE);
        menuHeight = (int) (backgroundImg.getHeight() * Game.SCALE);
        menuX = Game.GAME_WIDTH / 2 - menuWidth / 2;
        menuY = (int) (25 * Game.SCALE);
    }

    private void drawChar(Graphics g, int playerIndex, int x, int y){
        if (playerIndex < 0){
            playerIndex = characterAnimations.length - 1;
        }
        else if (playerIndex >= characterAnimations.length){
            playerIndex = 0;
        }
        characterAnimations[playerIndex].draw(g, x, y);

    }


    private void resetButtons(){
        playButton.resetBools();
    }


    private void loadButtons(){
        playButton = new MenuButton(Game.GAME_WIDTH / 2, (int) (340 * Game.SCALE), 0, GameState.PLAYING);
    }


    public void update(){
        playButton.update();
        for (CharacterAnimation ch : characterAnimations) {
            ch.update();
        }
    }


    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundKirby, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        g.drawImage(backgroundImg, menuX, menuY, menuWidth, menuHeight, null);
        playButton.draw(g);


        //center
        drawChar(g, playerIndex, menuX + menuWidth / 2 , menuY + menuHeight / 2);

        //left
        drawChar(g, playerIndex - 1, menuX , menuY + menuHeight / 2);

        //right
        drawChar(g, playerIndex + 1, menuX + menuWidth , menuY + menuHeight / 2);

    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (isIn(e, playButton)){
            playButton.setMousePressed(true);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (isIn(e, playButton)){
            if (playButton.isMouseOver()){
                game.getPlaying().setPlayerCharacter(characterAnimations[playerIndex].getPlayerCharacter());
                game.getAudioPlayer().setLevelSong(game.getPlaying().getLevelManager().getLevelIndex());
                playButton.applyGamestate();
            }
        }
        resetButtons();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        playButton.setMouseOver(false);
        if (isIn(e, playButton)){
            playButton.setMouseOver(true);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT){
            deltaIndex(1);

        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT){
            deltaIndex(-1);
        }
    }

    private void deltaIndex(int value){
        playerIndex += value;
        if (playerIndex < 0){
            playerIndex = characterAnimations.length - 1;
        }
        else if (playerIndex >= characterAnimations.length){
            playerIndex = 0;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
    public class CharacterAnimation {

        private final PlayerCharacter pc;
        private int aniTick, aniIndex;
        private int scale;
        private final BufferedImage[][] animations;
        public CharacterAnimation(PlayerCharacter pc){
            this.pc = pc;
            this.scale = (int)(Game.SCALE * 3);
            animations = LoadSave.loadAnimations(pc);
        }

        public PlayerCharacter getPlayerCharacter(){
            return pc;
        }

        public void update(){
            aniTick++;
            if (aniTick >= ANI_SPEED){
                aniTick = 0;
                aniIndex++;
                if (aniIndex >= pc.getSpriteAmount(IDLE)){
                    aniIndex = 0;
                }
            }
        }


        public void draw(Graphics g, int drawX, int drawY) {
            g.drawImage(animations[pc.getRowIndex(IDLE)][aniIndex],
                    drawX -  pc.spriteWidth * scale / 2,
                    drawY -  pc.spriteHeight * scale / 2,
                    pc.spriteWidth * scale  ,
                    pc.spriteHeight * scale ,
                    null);
        }



    }

}
