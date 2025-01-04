package entities;
//Importamos las librerias y constantes que vamos a usar

import main.Game;
import utilz.Constants.PlayerConstants.*;
import utilz.LoadSave;
import static utilz.Constants.PlayerConstants.*;
import main.Game.*;


public enum PlayerCharacter {
    PIRATE(3, 7, 6, 2, 2, 4, 4, 0, 1, 2, 3, 4, 5, 6,
            LoadSave.PLAYER_PIRATE, 7, 7, 100, 100,
            24, 24, 40, 38),
    ORC(15, 8, 6, 3, 2, 3, 1, 0, 1, 2, 3, 4, 5, 6,
            LoadSave.PLAYER_ORC, 7, 15, 100, 100,
            24, 24,40 , 45),
    KIRBY(7, 8, 3, 4, 3, 4, 2, 0, 1, 2, 3, 4, 5, 6,
            LoadSave.PLAYER_KIRBY, 7, 7, 100, 100,
            24 , 24, 40, 38),;

    public final int spriteA_IDLE;
    public final int spriteA_RUNNING;
    public final int spriteA_JUMP;
    public final int spriteA_ATTACK;
    public final int spriteA_HIT;
    public final int spriteA_DEAD;
    public final int spriteA_FALLING;
    public final int rowIDLE, rowRUNNING, rowJUMP, rowFALLING, rowATTACK, rowHIT, rowDEAD;
    public final String playerAtlas;
    public final int rowAmount, colAmount, spriteWidth, spriteHeight;
    public final int hitboxW, hitboxH;

    public final int xDrawOffset, yDrawOffset;

    PlayerCharacter(int spriteA_IDLE, int spriteA_RUNNING, int spriteA_JUMP, int spriteA_FALLING, int spriteA_ATTACK, int spriteA_HIT, int spriteA_DEAD,
                    int rowIDLE, int rowRUNNING, int rowJUMP, int rowFALLING, int rowATTACK, int rowHIT, int rowDEAD, String playerAtlas, int rowAmount, int colAmount, int spriteWidth, int spriteHeight, int hitboxW, int hitboxH, int xDrawOffset, int yDrawOffset) {
        this.spriteA_IDLE = spriteA_IDLE;
        this.spriteA_RUNNING = spriteA_RUNNING;
        this.spriteA_JUMP = spriteA_JUMP;
        this.spriteA_FALLING = spriteA_FALLING;
        this.spriteA_ATTACK = spriteA_ATTACK;
        this.spriteA_HIT = spriteA_HIT;
        this.spriteA_DEAD = spriteA_DEAD;

        this.rowIDLE = rowIDLE;
        this.rowRUNNING = rowRUNNING;
        this.rowJUMP = rowJUMP;
        this.rowFALLING = rowFALLING;
        this.rowATTACK = rowATTACK;
        this.rowHIT = rowHIT;
        this.rowDEAD = rowDEAD;
        this.rowAmount = rowAmount;
        this.colAmount = colAmount;
        this.spriteWidth = spriteWidth;
        this.spriteHeight = spriteHeight;
        this.playerAtlas = playerAtlas;
        this.hitboxW = hitboxW;
        this.hitboxH = hitboxH;
        this.xDrawOffset = (int) (xDrawOffset * Game.SCALE);
        this.yDrawOffset = (int) (yDrawOffset * Game.SCALE);

    }

    /**
     * Definimos un metodo para saber la cantidad de sprites que tiene un estado del jugador
     * @param player_action es la accion que el jugador esta haciendo
     * @return la cantiddad de sprites que tenga accion que este haciendo el jugador
     */
    public int getSpriteAmount(int player_action) {
        return switch (player_action) {
            case DEAD -> spriteA_DEAD;
            case RUNNING -> spriteA_RUNNING;
            case IDLE -> spriteA_IDLE;
            case HIT -> spriteA_HIT;
            case JUMP -> spriteA_JUMP;
            case ATTACK -> spriteA_ATTACK;
            case FALLING -> spriteA_FALLING;
            default -> 1;
        };
    }

    /**
     * Definimos el metodo para saber el indice de la accion que este haciendo el jugador
     * @param player_action es la accion del jugador
     * @return el indice de la respectiva accion
     */
    public int getRowIndex(int player_action) {
        return switch (player_action) {
            case DEAD -> rowDEAD;
            case RUNNING -> rowRUNNING;
            case IDLE -> rowIDLE;
            case HIT -> rowHIT;
            case JUMP -> rowJUMP;
            case ATTACK -> rowATTACK;
            case FALLING -> rowFALLING;
            default -> 1;
        };
    }



}
