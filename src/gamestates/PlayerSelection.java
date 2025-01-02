package gamestates;

//Importamos las librerias y las constantes que vamos a usar en la clase PlayerSelection
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

/**
 * Comienzo de la clase PlayerSelection hija de la clase State y que implementa la interfaz StateMethods
 * @author Santiago
 */
public class PlayerSelection extends State implements StateMethods{

    private BufferedImage backgroundImg, backgroundKirby;
    private int menuX, menuY, menuWidth, menuHeight;
    private MenuButton playButton;
    private CharacterAnimation[] characterAnimations;
    private int playerIndex = 0;

    /**
     * Definimos el constructor de la clase PlayerSelection
     * @param game es la instancia del juego
     */
    public PlayerSelection(Game game) {
        super(game);
        loadButtons();
        loadBackground();
        backgroundKirby = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND_IMG);

        loadCharAnimations();
    }

    /**
     * Definimos un metodo para cargar las animaciones de los personajes
     */
    private void loadCharAnimations(){
        characterAnimations = new CharacterAnimation[3];
        int i = 0;
        characterAnimations[i++] = new CharacterAnimation(PlayerCharacter.PIRATE);
        characterAnimations[i++] = new CharacterAnimation(PlayerCharacter.KIRBY);
        characterAnimations[i++] = new CharacterAnimation(PlayerCharacter.ORC);

    }


    /**
     * Definimos un metodo para cargar la imagen del background del menu de seleccion de personaje
     */
    private void loadBackground() {
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND);
        menuWidth = (int) (backgroundImg.getWidth() * Game.SCALE);
        menuHeight = (int) (backgroundImg.getHeight() * Game.SCALE);
        menuX = Game.GAME_WIDTH / 2 - menuWidth / 2;
        menuY = (int) (25 * Game.SCALE);
    }

    /**
     * Definimos un metodo para dibujar un personaje en el menu de seleccion de personajes
     * @param g es el grafico para poder dibujar
     * @param playerIndex es el indice del personaje
     * @param x es la coordenada en x del personaje
     * @param y es la coordenada en y del personaje
     */
    private void drawChar(Graphics g, int playerIndex, int x, int y){
        if (playerIndex < 0){
            playerIndex = characterAnimations.length - 1;
        }
        else if (playerIndex >= characterAnimations.length){
            playerIndex = 0;
        }
        characterAnimations[playerIndex].draw(g, x, y);

    }

    /**
     * Definimos un metodo para resetear los valores del boton de jugar
     */
    private void resetButtons(){
        playButton.resetBools();
    }


    /**
     * Definimos un metodo para cargar el boton de jugar
     */
    private void loadButtons(){
        playButton = new MenuButton(Game.GAME_WIDTH / 2, (int) (340 * Game.SCALE), 0, GameState.PLAYING);
    }


    /**
     * Definimos un metodo para actualizar el menu de seleccion de personaje, tanto el boton de jugar como cada uno de los personajes en el menu
     */
    public void update(){
        playButton.update();
        for (CharacterAnimation ch : characterAnimations) {
            ch.update();
        }
    }


    /**
     * Definimos un metodo para dibujar el menu de seleccion de personaje
     * @param g es el grafico para poder dibujar
     */
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


    /**
     * Definimos un metodo para detectar si el raton ha sido clickeado
     * @param e es el evento de clickear el raton
     */
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    /**
     * Definimos un metodo para detectar si el raton ha sido presionado
     * @param e es el evento de presionar el raton
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (isIn(e, playButton)){
            playButton.setMousePressed(true);
        }
    }

    /**
     * Definimos un metodo para detectar si se deja de presionar el raton
     * @param e es el evento de soltar el raton
     */
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

    /**
     * Definimos un metodo para detectar si se ha movido el raton
     * @param e es el evento de mover el raton
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        playButton.setMouseOver(false);
        if (isIn(e, playButton)){
            playButton.setMouseOver(true);
        }
    }

    /**
     * Definimos un metodo para detectar si se ha presionado una tecla
     * @param e es el evento de presionar una tecla
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT){
            deltaIndex(1);

        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT){
            deltaIndex(-1);
        }
    }

    /**
     * Definimos un metodo para que el valor del indice del personaje siempre este en un rango valido
     * @param value es el valor que se va a cambiar el indice del personaje
     */
    private void deltaIndex(int value){
        playerIndex += value;
        if (playerIndex < 0){
            playerIndex = characterAnimations.length - 1;
        }
        else if (playerIndex >= characterAnimations.length){
            playerIndex = 0;
        }
    }

    /**
     * Definimos un metodo para detectar si se ha dejado de presionar una tecla
     * @param e es el evento de dejar de presionar una tecla
     */
    @Override
    public void keyReleased(KeyEvent e) {

    }

    /**
     * Comienzo de la clase que se encarga de animar los personajes en el menu de seleccion de personaje
     * @author Santiago
     */
    public class CharacterAnimation {

        private final PlayerCharacter pc;
        private int aniTick, aniIndex;
        private int scale;
        private final BufferedImage[][] animations;

        /**
         * Definimos el constructor de la clase CharacterAnimation
         * @param pc es la instancia de un personaje
         */
        public CharacterAnimation(PlayerCharacter pc){
            this.pc = pc;
            this.scale = (int)(Game.SCALE * 3);
            animations = LoadSave.loadAnimations(pc);
        }

        /**
         * Definimos un getter para el personaje
         * @return el personaje
         */
        public PlayerCharacter getPlayerCharacter(){
            return pc;
        }

        /**
         * Definimos un metodo para actualizar las animaciones de los personajes
         */
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


        /**
         * Definimos un metodo para dibujar los personajes en el menu de seleccion de personaje
         * @param g es el grafico para poder dibujar
         * @param drawX es la coordenada en x en donde dibujar el personaje
         * @param drawY es la coordenada en y en donde dibujar el personaje
         */
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
