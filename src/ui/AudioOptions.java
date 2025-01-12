package ui;

//Importamos las librerias y constantes que vamos a usar en la clase de AudioOptions
import static utilz.Constants.UI.PauseButtons.SOUND_SIZE;
import static utilz.Constants.UI.VolumeButtons.SLIDER_WIDTH;
import static utilz.Constants.UI.VolumeButtons.VOLUME_HEIGHT;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import main.Game;

/**
 * Comienzo de la clase AudioOptions que representa el menu en el que se puede cambiar el volumen del juego
 * @author Santiago
 */
public class AudioOptions {

    private VolumeButton volumeButton;
    private SoundButton musicButton;
    private SoundButton sfxButton;

    private Game game;

    /**
     * Definimos el constructor de la clase
     * @param game es el juego
     */
    public AudioOptions(Game game) {
        this.game = game;
        createSoundButtons();
        createVolumeButton();
    }

    /**
     * Definimos un metodo para crear los botones del volumen
     */
    private void createVolumeButton() {
        int vX = (int) (309 * Game.SCALE);
        int vY = (int) (278 * Game.SCALE);
        volumeButton = new VolumeButton(vX, vY, SLIDER_WIDTH, VOLUME_HEIGHT);
    }

    /**
     * Definimos un metodo para crear los botones de sonido
     */
    private void createSoundButtons() {
        int soundX = (int) (450 * Game.SCALE);
        int musicY = (int) (140 * Game.SCALE);
        int sfxY = (int) (186 * Game.SCALE);
        musicButton = new SoundButton(soundX, musicY, SOUND_SIZE, SOUND_SIZE);
        sfxButton = new SoundButton(soundX, sfxY, SOUND_SIZE, SOUND_SIZE);
    }

    /**
     * Definimos un metodo para actualizar el menu de audio
     */
    public void update() {
        musicButton.update();
        sfxButton.update();

        volumeButton.update();
    }

    /**
     * Definimos un metodo para dibujar el menu de audio
     * @param g es el grafico para poder dibujar
     */
    public void draw(Graphics g) {
        // Sound buttons
        musicButton.draw(g);
        sfxButton.draw(g);

        // Volume Button
        volumeButton.draw(g);
    }

    /**
     * Definimos un metodo para cuando se arrastra el raton
     * @param e es el evento de arrastrar el raton
     */
    public void mouseDragged(MouseEvent e) {
        if (volumeButton.getMousePressed()) {
            float valueBefore = volumeButton.getFloatValue();
            volumeButton.changeX(e.getX());
            float valueAfter = volumeButton.getFloatValue();
            if (valueBefore != valueAfter)
                game.getAudioPlayer().setVolume(valueAfter);
        }
    }

    /**
     * Definimos un metodo para detectar si se se presiona el raton
     * @param e es el evento de presionar el raton
     */
    public void mousePressed(MouseEvent e) {
        if (isIn(e, musicButton))
            musicButton.setMousePressed(true);
        else if (isIn(e, sfxButton))
            sfxButton.setMousePressed(true);
        else if (isIn(e, volumeButton))
            volumeButton.setMousePressed(true);
    }

    /**
     * Definimos un metodo para cuando se suelta el raton
     * @param e e el evento de soltar el raton
     */
    public void mouseReleased(MouseEvent e) {
        if (isIn(e, musicButton)) {
            if (musicButton.getMousePressed()) {
                musicButton.setMuted(!musicButton.getMuted());
                game.getAudioPlayer().toggleSongMute();
            }

        } else if (isIn(e, sfxButton)) {
            if (sfxButton.getMousePressed()) {
                sfxButton.setMuted(!sfxButton.getMuted());
                game.getAudioPlayer().toggleEffectMute();
            }
        }

        musicButton.resetBools();
        sfxButton.resetBools();

        volumeButton.resetBools();
    }

    /**
     * Definimos un metodo para cuando se mueve el raton
     * @param e es el evento de mover el raton
     */
    public void mouseMoved(MouseEvent e) {
        musicButton.setMouseOver(false);
        sfxButton.setMouseOver(false);

        volumeButton.setMouseOver(false);

        if (isIn(e, musicButton))
            musicButton.setMouseOver(true);
        else if (isIn(e, sfxButton))
            sfxButton.setMouseOver(true);
        else if (isIn(e, volumeButton))
            volumeButton.setMouseOver(true);
    }

    /**
     * Definimos un metodo para determinar si el raton esta dentro del boton de pausa
     * @param e es el evento del raton
     * @param b es el boton de pausa
     * @return true si esta dentro del boton , false si no esta dentro del boton
     */
    private boolean isIn(MouseEvent e, PauseButton b) {
        return b.getBounds().contains(e.getX(), e.getY());
    }

}