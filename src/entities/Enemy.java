package entities;

//Importamos las librerias que se van a usar en la clase Enemy
import static utilz.Constants.EnemyConstants.*;
import static utilz.HelpMethods.*;

import java.awt.geom.Rectangle2D;

import gamestates.Playing;

import static utilz.Constants.Directions.*;
import static utilz.Constants.*;

import main.Game;

/**
 * Comienzo de la clase abstracta que representa a un enemigo en el juego
 * @author Santiago
 */
public abstract class Enemy extends Entity {
    protected int enemyType;
    protected boolean firstUpdate = true;
    protected int walkDir = LEFT;
    protected int tileY;
    protected float attackDistance = Game.TILES_SIZE;
    protected boolean active = true;
    protected boolean attackChecked;
    protected int attackBoxOffsetX;

    /**
     * Definimos el constructor de la clase de Enemy
     * @param x es la coordenada en x del enemigo
     * @param y es la coordenada en y del enemigo
     * @param width es el ancho del enemigo
     * @param height es el alto del enemigo
     * @param enemyType es el tipo de enemigo que se esta trabando (crabby, shark, etc)
     */
    public Enemy(float x, float y, int width, int height, int enemyType) {
        super(x, y, width, height);
        this.enemyType = enemyType;

        maxHealth = GetMaxHealth(enemyType);
        currentHealth = maxHealth;
        walkSpeed = Game.SCALE * 0.35f;
    }

    /**
     * Definimos un metodo para actualizar la attackbox del enemigo
     */
    protected void updateAttackBox() {
        attackBox.x = hitbox.x - attackBoxOffsetX;
        attackBox.y = hitbox.y;
    }

    /**
     * Definimos un metodo actualizar los momentos en los que el enemigo cambia su direccion de caminar
     */
    protected void updateAttackBoxFlip() {
        if (walkDir == RIGHT)
            attackBox.x = hitbox.x + hitbox.width;
        else
            attackBox.x = hitbox.x - attackBoxOffsetX;

        attackBox.y = hitbox.y;
    }

    /**
     * Definimos un metodo para iniciar la attackbox del enemigo
     * @param w es el ancho del enemigo
     * @param h es el alto del personaje
     * @param attackBoxOffsetX es el offset del enemigo, cambia dependiendo del tipo de enemigo
     */
    protected void initAttackBox(int w, int h, int attackBoxOffsetX) {
        attackBox = new Rectangle2D.Float(x, y, (int) (w * Game.SCALE), (int) (h * Game.SCALE));
        this.attackBoxOffsetX = (int) (Game.SCALE * attackBoxOffsetX);
    }

    /**
     * Definimos un metodo para checkear la primera actualizacion del enemigo
     * @param lvlData es la informacion del nivel, cambia dependiendo del nivel en el que estemos
     */
    protected void firstUpdateCheck(int[][] lvlData) {
        if (!IsEntityOnFloor(hitbox, lvlData))
            inAir = true;
        firstUpdate = false;
    }

    /**
     * Definimos un metodo para checkear si el enemigo se encuentra en el aire
     * @param lvlData es la informacion del nivel, cambia dependiendo del nivel en el que estemos
     * @param playing es el estado de juego playing, que tiene la informacion del jugador, etc
     */
    protected void inAirChecks(int[][] lvlData, Playing playing) {
        if (state != HIT && state != DEAD) {
            updateInAir(lvlData);
            playing.getObjectManager().checkSpikesTouched(this);
            if (IsEntityInWater(hitbox, lvlData))
                hurt(maxHealth);
        }
    }

    /**
     * Definimos un metodo para actualizar el enemigo en el aire
     * @param lvlData es la informacion del nivel , cambia dependiendo del nivel en el que estemos
     */
    protected void updateInAir(int[][] lvlData) {
        if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
            hitbox.y += airSpeed;
            airSpeed += GRAVITY;
        } else {
            inAir = false;
            hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
            tileY = (int) (hitbox.y / Game.TILES_SIZE);
        }
    }

    /**
     * Definimos un metodo para mover el enemigo
     * @param lvlData es la informacion del nivel , cambia dependiendo del nivel en el que estemos
     */
    protected void move(int[][] lvlData) {
        float xSpeed = 0;

        if (walkDir == LEFT)
            xSpeed = -walkSpeed;
        else
            xSpeed = walkSpeed;

        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData))
            if (IsFloor(hitbox, xSpeed, lvlData)) {
                hitbox.x += xSpeed;
                return;
            }

        changeWalkDir();
    }

    /**
     * Definimos un metodo para que el enemigo cambie su mirada hacia el jugador
     * @param player es la instancia de la clase jugador, es el jugador que esta en la partida
     */
    protected void turnTowardsPlayer(Player player) {
        if (player.hitbox.x > hitbox.x)
            walkDir = RIGHT;
        else
            walkDir = LEFT;
    }

    /**
     * Definimos el metodo para determinar si el enemigo puede ver el jugador
     * @param lvlData es la informacion del nivel , cambia dependiendo del nivel en el que estemos
     * @param player es el jugador
     * @return true si el enemigo si puede ver el jugador, false si no lo puede ver
     */
    protected boolean canSeePlayer(int[][] lvlData, Player player) {
        int playerTileY = (int) (player.getHitbox().y / Game.TILES_SIZE);
        if (playerTileY == tileY)
            if (isPlayerInRange(player)) {
                if (IsSightClear(lvlData, hitbox, player.hitbox, tileY))
                    return true;
            }
        return false;
    }

    /**
     * Definimos un metodo para determinar si el jugador esta en el rango para que el enemigo le pegue
     * @param player es el jugador
     * @return true si esta en el rango para pegarle, false si no esta en el rango
     */
    protected boolean isPlayerInRange(Player player) {
        int absValue = (int) Math.abs(player.hitbox.x - hitbox.x);
        return absValue <= attackDistance * 5;
    }

    /**
     * Definimos un metodo para determinar si el jugador esta la suficientemente cerca para que el enemigo le pegue al jugador
     * @param player es el jugador
     * @return true si esta en los suficientemente cerca para pegarle al jugador, false si no esta cerca para pegarle
     */
    protected boolean isPlayerCloseForAttack(Player player) {
        int absValue = (int) Math.abs(player.hitbox.x - hitbox.x);
        switch (enemyType) {
            case CRABBY -> {
                return absValue <= attackDistance;
            }
            case SHARK -> {
                return absValue <= attackDistance * 2;
            }
        }
        return false;
    }

    /**
     * Definimos un metodo para hacerle daño al jugador
     * @param amount es la cantidad de daño que le hace el enemigo al jugador
     */
    public void hurt(int amount) {
        currentHealth -= amount;
        if (currentHealth <= 0)
            newState(DEAD);
        else {
            newState(HIT);
            if (walkDir == LEFT)
                pushBackDir = RIGHT;
            else
                pushBackDir = LEFT;
            pushBackOffsetDir = UP;
            pushDrawOffset = 0;
        }
    }

    /**
     * Definimos un metodo para verificar si un enemigo ha sido golpeado por el jugador
     * @param attackBox es el attackbox del enemigo
     * @param player es el jugador
     */
    protected void checkPlayerHit(Rectangle2D.Float attackBox, Player player) {
        if (attackBox.intersects(player.hitbox))
            player.changeHealth(-GetEnemyDmg(enemyType), this);
        else {
            if (enemyType == SHARK)
                return;
        }
        attackChecked = true;
    }

    /**
     * Definimos un metodo para animar el enemigo
     */
    protected void updateAnimationTick() {
        aniTick++;
        if (aniTick >= ANI_SPEED) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(enemyType, state)) {
                if (enemyType == CRABBY || enemyType == SHARK) {
                    aniIndex = 0;

                    switch (state) {
                        case ATTACK, HIT -> state = IDLE;
                        case DEAD -> active = false;
                    }
                } else if (enemyType == PINKSTAR) {
                    if (state == ATTACK)
                        aniIndex = 3;
                    else {
                        aniIndex = 0;
                        if (state == HIT) {
                            state = IDLE;

                        } else if (state == DEAD)
                            active = false;
                    }
                }
            }
        }
    }

    /**
     * Definimos un metodo para cammbiar la direccion en la que camina un enemigo
     */
    protected void changeWalkDir() {
        if (walkDir == LEFT)
            walkDir = RIGHT;
        else
            walkDir = LEFT;
    }

    /**
     * Definimos un metodo para resetear los valores del enemigo
     */
    public void resetEnemy() {
        hitbox.x = x;
        hitbox.y = y;
        firstUpdate = true;
        currentHealth = maxHealth;
        newState(IDLE);
        active = true;
        airSpeed = 0;

        pushDrawOffset = 0;

    }

    /**
     * Definimos un metodo para cambiar la animacion en la que se muestra la animacion del enemigo
     * @return en caso de que la direccin del enemigo sea la derecha devolvemos el ancho del enemigo, 0 en caso de que la direccion del enemigo sea la izquierda
     */
    public int flipX() {
        if (walkDir == RIGHT)
            return width;
        else
            return 0;
    }

    /**
     * Definimos un metodo para cambiar la animacion del enemigo
     * @return si la direccion es derecha -1, en caso de que la direccion del enemigo sea la izquierda 1
     */
    public int flipW() {
        if (walkDir == RIGHT)
            return -1;
        else
            return 1;
    }

    /**
     * Definimos un getter para determinar si el enemigo se encuentra activo
     * @return true si esta activo, false si no esta activo el enemigo
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Definimos un getter para el offset del enemigo cuando se le empuja
     * @return el valor el offset del enemigo
     */
    public float getPushDrawOffset() {
        return pushDrawOffset;
    }

}