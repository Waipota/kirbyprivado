package audio;

//Importamos las librerias que vamos a usar en la clase de AudioPlayer
import java.io.IOException;
import java.net.URL;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Comienzo de la clase de el reproductor de los sonidos del juego
 * @author Santiago
 */
public class AudioPlayer {

    public static int MENU_1 = 0;
    public static int LEVEL_1 = 1;
    public static int LEVEL_2 = 2;

    public static int DIE = 0;
    public static int JUMP = 1;
    public static int GAMEOVER = 2;
    public static int LVL_COMPLETED = 3;
    public static int ATTACK_ONE = 4;
    public static int ATTACK_TWO = 5;
    public static int ATTACK_THREE = 6;

    private Clip[] songs, effects;
    private int currentSongId;
    private float volume = 0.5f;
    private boolean songMute, effectMute;
    private Random rand = new Random();

    /**
     * Definimos el constructor de la clase AudioPlayer
     */
    public AudioPlayer() {
        loadSongs();
        loadEffects();
        playSong(MENU_1);
    }


    /**
     * Definimos el metodo para cargar las canciones de los todos los niveles
     */
    private void loadSongs() {
        String[] names = { "menu", "level1", "level2" };
        songs = new Clip[names.length];
        for (int i = 0; i < songs.length; i++)
            songs[i] = getClip(names[i]);
    }

    /**
     * Definimos el metodo para cargar los sonidos de los efectos del juego (ataques, muerte , golpe, etc)
     */
    private void loadEffects() {
        String[] effectNames = { "die", "jump", "gameover", "lvlcompleted", "attack1", "attack2", "attack3" };
        effects = new Clip[effectNames.length];
        for (int i = 0; i < effects.length; i++)
            effects[i] = getClip(effectNames[i]);

        updateEffectsVolume();

    }

    /**
     * Definimos el metodo para leer los archivos de sonidos
     * @param name el nombre del archivo de audio que se quiere cargar
     * @return el objeto Clip que contiene el audio cargado o null en caso de que no se encuentre el archivo
     * @throws UnsupportedAudioFileException si el formato del archivo de audio no es compatible
     * @throws IOException si ocurre un error de entrada/salida al leer el archivo
     * @throws LineUnavailableException si no hay linea de audio disponible
     */

    private Clip getClip(String name) {
        URL url = getClass().getResource("/audio/" + name + ".wav");
        AudioInputStream audio;

        try {
            audio = AudioSystem.getAudioInputStream(url);
            Clip c = AudioSystem.getClip();
            c.open(audio);
            return c;

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {

            e.printStackTrace();
        }

        return null;

    }

    /**
     * Definimo el metodo para setear el nivel de volumen
     * @param volume la cantidad de volumen que se desea
     */
    public void setVolume(float volume) {
        this.volume = volume;
        updateSongVolume();
        updateEffectsVolume();
    }

    /**
     * Definimos el metodo para parar una cancion
     */
    public void stopSong() {
        if (songs[currentSongId].isActive())
            songs[currentSongId].stop();
    }

    /**
     * Definimos un metodo para setear la cancion dependiendo del nivel en el que nos encontremos
     * @param lvlIndex es el indice de cual nivel nos encontramos, dependiendo del indice del nivel vamos a reproducir una cancion u otra
     */
    public void setLevelSong(int lvlIndex) {
        if (lvlIndex % 2 == 0)
            playSong(LEVEL_1);
        else
            playSong(LEVEL_2);
    }

    /**
     *Definimos un metodo para Reproducir el efecto de nivel completado
     */

    public void lvlCompleted() {
        stopSong();
        playEffect(LVL_COMPLETED);
    }

    /**
     * Definimos un metodo para reproducir el efecto de golpe, usamos rand para que se cambie el sonido de golpe
     */
    public void playAttackSound() {
        int start = 4;
        start += rand.nextInt(3);
        playEffect(start);
    }

    /**
     * Definimos un metodo para reproducir un efecto en especifico
     * @param effect es el efecto que se desea reproducir
     */
    public void playEffect(int effect) {
        if (effects[effect].getMicrosecondPosition() > 0)
            effects[effect].setMicrosecondPosition(0);
        effects[effect].start();
    }

    /**
     * Definimos un metodo para reproducir una cancion en especifico
     * @param song es la cancion que se desea reproducir
     */
    public void playSong(int song) {
        stopSong();

        currentSongId = song;
        updateSongVolume();
        songs[currentSongId].setMicrosecondPosition(0);
        songs[currentSongId].loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * Definimos un metodo para mutear la musica del juego
     */
    public void toggleSongMute() {
        this.songMute = !songMute;
        for (Clip c : songs) {
            BooleanControl booleanControl = (BooleanControl) c.getControl(BooleanControl.Type.MUTE);
            booleanControl.setValue(songMute);
        }
    }

    /**
     * Definimos un metodo para mutear los efectos del juego
     */
    public void toggleEffectMute() {
        this.effectMute = !effectMute;
        for (Clip c : effects) {
            BooleanControl booleanControl = (BooleanControl) c.getControl(BooleanControl.Type.MUTE);
            booleanControl.setValue(effectMute);
        }
        if (!effectMute)
            playEffect(JUMP);
    }

    /**
     * Definimos un metodo para actualizar el volumen del juego
     */
    private void updateSongVolume() {

        FloatControl gainControl = (FloatControl) songs[currentSongId].getControl(FloatControl.Type.MASTER_GAIN);
        float range = gainControl.getMaximum() - gainControl.getMinimum();
        float gain = (range * volume) + gainControl.getMinimum();
        gainControl.setValue(gain);

    }

    /**
     * Definimos un metodo para actualizar el volumen de los efectos del juego
     */
    private void updateEffectsVolume() {
        for (Clip c : effects) {
            FloatControl gainControl = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
            float range = gainControl.getMaximum() - gainControl.getMinimum();
            float gain = (range * volume) + gainControl.getMinimum();
            gainControl.setValue(gain);
        }
    }

}