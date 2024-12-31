package entities;

//Importamos las librerias y las constantes que se van a usar en la clase Pinkstar
import static utilz.Constants.EnemyConstants.*;
import static utilz.Constants.Dialogue.*;
import static utilz.HelpMethods.CanMoveHere;
import static utilz.HelpMethods.IsFloor;
import static utilz.Constants.Directions.*;

import gamestates.Playing;

/**
 * Comienzo de la clase pinkstar hija de la clase enemy
 */
public class Pinkstar extends Enemy {

    private boolean preRoll = true;
    private int tickSinceLastDmgToPlayer;
    private int tickAfterRollInIdle;
    private int rollDurationTick, rollDuration = 300;

    /**
     * Definimos el constructor de la clase pinkstar
     * @param x es la coordenada en x de la pinkstar
     * @param y es la coordenada en y de la pinkstar
     */
    public Pinkstar(float x, float y) {
        super(x, y, PINKSTAR_WIDTH, PINKSTAR_HEIGHT, PINKSTAR);
        initHitbox(17, 21);
    }

    /**
     * Definimos un metodo para actualizar la pinkstar
     * @param lvlData es la informacion del nivel en el que esta el jugador, cambia dependiendo del nivel en el que estemos
     * @param playing es el estado de jugar
     */

    public void update(int[][] lvlData, Playing playing) {
        updateBehavior(lvlData, playing);
        updateAnimationTick();
    }

    /**
     * Definimos un metodo para actualizar el comportamiento de la pinkstar
     * @param lvlData es la informacion del nivel en el que esta el jugador, cambia dependiendo del nivel
     * @param playing es el estado de jugar
     */
    private void updateBehavior(int[][] lvlData, Playing playing) {
        if (firstUpdate)
            firstUpdateCheck(lvlData);

        if (inAir)
            inAirChecks(lvlData, playing);
        else {
            switch (state) {
                case IDLE:
                    preRoll = true;
                    if (tickAfterRollInIdle >= 120) {
                        if (IsFloor(hitbox, lvlData))
                            newState(RUNNING);
                        else
                            inAir = true;
                        tickAfterRollInIdle = 0;
                        tickSinceLastDmgToPlayer = 60;
                    } else
                        tickAfterRollInIdle++;
                    break;
                case RUNNING:
                    if (canSeePlayer(lvlData, playing.getPlayer())) {
                        newState(ATTACK);
                        setWalkDir(playing.getPlayer());
                    }
                    move(lvlData, playing);
                    break;
                case ATTACK:
                    if (preRoll) {
                        if (aniIndex >= 3)
                            preRoll = false;
                    } else {
                        move(lvlData, playing);
                        checkDmgToPlayer(playing.getPlayer());
                        checkRollOver(playing);
                    }
                    break;
                case HIT:
                    if (aniIndex <= GetSpriteAmount(enemyType, state) - 2)
                        pushBack(pushBackDir, lvlData, 2f);
                    updatePushBackDrawOffset();
                    tickAfterRollInIdle = 120;

                    break;
            }
        }
    }

    /**
     * Definimos un metodo para checkear el daño hacia el jugador
     * @param player es el jugador
     */
    private void checkDmgToPlayer(Player player) {
        if (hitbox.intersects(player.getHitbox()))
            if (tickSinceLastDmgToPlayer >= 60) {
                tickSinceLastDmgToPlayer = 0;
                player.changeHealth(-GetEnemyDmg(enemyType), this);
            } else
                tickSinceLastDmgToPlayer++;
    }

    /**
     * Definimos un metodo para setear la direccion de caminar de la pinkstar
     * @param player es el jugador
     */
    private void setWalkDir(Player player) {
        if (player.getHitbox().x > hitbox.x)
            walkDir = RIGHT;
        else
            walkDir = LEFT;

    }

    /**
     * Definimos un metodo para mover la pinkstar
     * @param lvlData es la informacion del nivel en el que esta el jugador, cambiar dependiendo del nivel en el que estamos
     * @param playing es el estado de juego
     */
    protected void move(int[][] lvlData, Playing playing) {
        float xSpeed = 0;

        if (walkDir == LEFT)
            xSpeed = -walkSpeed;
        else
            xSpeed = walkSpeed;

        if (state == ATTACK)
            xSpeed *= 2;

        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData))
            if (IsFloor(hitbox, xSpeed, lvlData)) {
                hitbox.x += xSpeed;
                return;
            }

        if (state == ATTACK) {
            rollOver(playing);
            rollDurationTick = 0;
        }

        changeWalkDir();

    }

    /**
     * Definimos un metodo para checkear el rollover de la pinkstar
     * @param playing es el estado de juego
     */
    private void checkRollOver(Playing playing) {
        rollDurationTick++;
        if (rollDurationTick >= rollDuration) {
            rollOver(playing);
            rollDurationTick = 0;
        }
    }

    /**
     * Definimos el metodo para hacer el rollover de la pinkstar
     * @param playing es el estado de juego
     */
    private void rollOver(Playing playing) {
        newState(IDLE);
        playing.addDialogue((int) hitbox.x, (int) hitbox.y, QUESTION);
    }

}