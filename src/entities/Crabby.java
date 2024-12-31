package entities;

//Importamos las librerias y constantes que se van a usar en la clase crabby
import static utilz.Constants.EnemyConstants.*;
import static utilz.HelpMethods.IsFloor;
import static utilz.Constants.Dialogue.*;

import gamestates.Playing;

/**
 * Comienzo de la clase crabby
 * @author Santiago
 */
public class Crabby extends Enemy {

    /**
     * Definimos el constructor de la clase Crabby
      * @param x las coordenadas en x del cangrejo
     * @param y las coordenadas en y del cangrejo
     * @return una instancia de la clase cangrejo
     */
    public Crabby(float x, float y) {
        super(x, y, CRABBY_WIDTH, CRABBY_HEIGHT, CRABBY);
        initHitbox(22, 19);
        initAttackBox(82, 19, 30);
    }

    /**
     * Definimos un metodo para actualizar cada instancia de la clase cangrejo
     * @param lvlData es el nivel en el que estamos
     * @param playing es el estado de juego de playing, es el estado de juego de cuando estamos jugando el juego
     */
    public void update(int[][] lvlData, Playing playing) {
        updateBehavior(lvlData, playing);
        updateAnimationTick();
        updateAttackBox();
    }

    /**
     * Definimos un metodo para actualizar el comportamiento del cangrejo
     * @param lvlData es el nivel en el que estamos
     * @param playing es el estado de juego de playing, es el estado de juego de cuando estamos jugando el juego
     */
    private void updateBehavior(int[][] lvlData, Playing playing) {
        if (firstUpdate)
            firstUpdateCheck(lvlData);

        if (inAir) {
            inAirChecks(lvlData, playing);
        } else {
            switch (state) {
                case IDLE:
                    if (IsFloor(hitbox, lvlData))
                        newState(RUNNING);
                    else
                        inAir = true;
                    break;
                case RUNNING:
                    if (canSeePlayer(lvlData, playing.getPlayer())) {
                        turnTowardsPlayer(playing.getPlayer());
                        if (isPlayerCloseForAttack(playing.getPlayer()))
                            newState(ATTACK);
                    }
                    move(lvlData);

                    if (inAir)
                        playing.addDialogue((int) hitbox.x, (int) hitbox.y, EXCLAMATION);

                    break;
                case ATTACK:
                    if (aniIndex == 0)
                        attackChecked = false;
                    if (aniIndex == 3 && !attackChecked)
                        checkPlayerHit(attackBox, playing.getPlayer());
                    break;
                case HIT:
                    if (aniIndex <= GetSpriteAmount(enemyType, state) - 2)
                        pushBack(pushBackDir, lvlData, 2f);
                    updatePushBackDrawOffset();
                    break;
            }
        }
    }

}