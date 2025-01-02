package objects;

//Importamos las librerias y constantes que vamos a usar en la clase ObjectManager
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.Enemy;
import entities.Player;
import gamestates.Playing;
import levels.Level;
import main.Game;
import utilz.LoadSave;
import static utilz.Constants.ObjectConstants.*;
import static utilz.HelpMethods.CanCannonSeePlayer;
import static utilz.HelpMethods.IsProjectileHittingLevel;
import static utilz.Constants.Projectiles.*;

/**
 * Comienzo de la clase ObjectManager que maneja todos los objetos que aparecen en el juego
 * @author Santiago
 */
public class ObjectManager {

    private Playing playing;
    private BufferedImage[][] potionImgs, containerImgs;
    private BufferedImage[] cannonImgs, grassImgs;
    private BufferedImage[][] treeImgs;
    private BufferedImage spikeImg, cannonBallImg;
    private ArrayList<Potion> potions;
    private ArrayList<GameContainer> containers;
    private ArrayList<Projectile> projectiles = new ArrayList<>();

    private Level currentLevel;

    /**
     * Definimos el constructor de la clase ObjectManager
     * @param playing es el estado de juego
     */
    public ObjectManager(Playing playing) {
        this.playing = playing;
        currentLevel = playing.getLevelManager().getCurrentLevel();
        loadImgs();
    }

    /**
     * Definimos un metodo para verificar si el jugador toca una spike, si lo toca mata a el jugador
     * @param p es el jugador
     */
    public void checkSpikesTouched(Player p) {
        for (Spike s : currentLevel.getSpikes())
            if (s.getHitbox().intersects(p.getHitbox()))
                p.kill();
    }

    /**
     * Definimos un metodo para verificar si un enemigo toca un spike, si lo toca mata a el enemigo
     * @param e es el enemigo
     */
    public void checkSpikesTouched(Enemy e) {
        for (Spike s : currentLevel.getSpikes())
            if (s.getHitbox().intersects(e.getHitbox()))
                e.hurt(200);
    }

    /**
     * Definimos un metodo para verificar si un jugador toca un objeto
     * @param hitbox es la hitbox del jugador
     */
    public void checkObjectTouched(Rectangle2D.Float hitbox) {
        for (Potion p : potions)
            if (p.isActive()) {
                if (hitbox.intersects(p.getHitbox())) {
                    p.setActive(false);
                    applyEffectToPlayer(p);
                }
            }
    }

    /**
     * Definimos un metodo para aplicar los efectos de las pociones a un jugador
     * @param p es la pocion que se le va a aplicar el efecto al jugador
     */
    public void applyEffectToPlayer(Potion p) {
        if (p.getObjType() == RED_POTION)
            playing.getPlayer().changeHealth(RED_POTION_VALUE);
        else
            playing.getPlayer().changePower(BLUE_POTION_VALUE);
    }

    /**
     * Definimos un metodo para verificar si un jugador le pega a un objeto
     * @param attackbox es la attackbox del jugador
     */
    public void checkObjectHit(Rectangle2D.Float attackbox) {
        for (GameContainer gc : containers)
            if (gc.isActive() && !gc.doAnimation) {
                if (gc.getHitbox().intersects(attackbox)) {
                    gc.setAnimation(true);
                    int type = 0;
                    if (gc.getObjType() == BARREL)
                        type = 1;
                    potions.add(new Potion((int) (gc.getHitbox().x + gc.getHitbox().width / 2), (int) (gc.getHitbox().y - gc.getHitbox().height / 2), type));
                    return;
                }
            }
    }

    /**
     * Definimos un metodo para cargar los objetos
     * @param newLevel es el nuevo nivel en el cual se van a cargar los nuevos objetos
     */
    public void loadObjects(Level newLevel) {
        currentLevel = newLevel;
        potions = new ArrayList<>(newLevel.getPotions());
        containers = new ArrayList<>(newLevel.getContainers());
        projectiles.clear();
    }

    /**
     * Definimos un metodo para cargar las imagenes de los objetos, las pociones, spikes, etc
     */
    private void loadImgs() {
        BufferedImage potionSprite = LoadSave.GetSpriteAtlas(LoadSave.POTION_ATLAS);
        potionImgs = new BufferedImage[2][7];

        for (int j = 0; j < potionImgs.length; j++)
            for (int i = 0; i < potionImgs[j].length; i++)
                potionImgs[j][i] = potionSprite.getSubimage(12 * i, 16 * j, 12, 16);

        BufferedImage containerSprite = LoadSave.GetSpriteAtlas(LoadSave.CONTAINER_ATLAS);
        containerImgs = new BufferedImage[2][8];

        for (int j = 0; j < containerImgs.length; j++)
            for (int i = 0; i < containerImgs[j].length; i++)
                containerImgs[j][i] = containerSprite.getSubimage(40 * i, 30 * j, 40, 30);

        spikeImg = LoadSave.GetSpriteAtlas(LoadSave.TRAP_ATLAS);

        cannonImgs = new BufferedImage[7];
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.CANNON_ATLAS);

        for (int i = 0; i < cannonImgs.length; i++)
            cannonImgs[i] = temp.getSubimage(i * 40, 0, 40, 26);

        cannonBallImg = LoadSave.GetSpriteAtlas(LoadSave.CANNON_BALL);
        treeImgs = new BufferedImage[2][4];
        BufferedImage treeOneImg = LoadSave.GetSpriteAtlas(LoadSave.TREE_ONE_ATLAS);
        for (int i = 0; i < 4; i++)
            treeImgs[0][i] = treeOneImg.getSubimage(i * 39, 0, 39, 92);

        BufferedImage treeTwoImg = LoadSave.GetSpriteAtlas(LoadSave.TREE_TWO_ATLAS);
        for (int i = 0; i < 4; i++)
            treeImgs[1][i] = treeTwoImg.getSubimage(i * 62, 0, 62, 54);

        BufferedImage grassTemp = LoadSave.GetSpriteAtlas(LoadSave.GRASS_ATLAS);
        grassImgs = new BufferedImage[2];
        for (int i = 0; i < grassImgs.length; i++)
            grassImgs[i] = grassTemp.getSubimage(32 * i, 0, 32, 32);
    }

    /**
     * Definimos un metodo para actualizar el objectManager
     * @param lvlData es la infomacion del nivel en el que esta el jugador, cambia depediendo del nivel
     * @param player es el jugador
     */
    public void update(int[][] lvlData, Player player) {
        updateBackgroundTrees();
        for (Potion p : potions)
            if (p.isActive())
                p.update();

        for (GameContainer gc : containers)
            if (gc.isActive())
                gc.update();

        updateCannons(lvlData, player);
        updateProjectiles(lvlData, player);

    }

    /**
     * Definimos un metodo para actualizar los arboles
     */
    private void updateBackgroundTrees() {
        for (BackgroundTree bt : currentLevel.getTrees())
            bt.update();
    }

    /**
     * Definimos un metodo para actualizar los proyectiles que lanzan los cannons
     * @param lvlData es la informacion del nivel en el que esta el jugador, cambia dependiendo del nivel
     * @param player es el jugador
     */
    private void updateProjectiles(int[][] lvlData, Player player) {
        for (Projectile p : projectiles)
            if (p.getActive()) {
                p.updatePos();
                if (p.getHitbox().intersects(player.getHitbox())) {
                    player.changeHealth(-25);
                    p.setActive(false);
                } else if (IsProjectileHittingLevel(p, lvlData))
                    p.setActive(false);
            }
    }

    /**
     * Definimos un metodo para determinar si un jugador esta en el rango para que le dispare un cannon
     * @param c es el cannon
     * @param player es el jugador
     * @return true si el jugador esta en el rango para que le dispare el cannon , false si no esta en el rango
     */
    private boolean isPlayerInRange(Cannon c, Player player) {
        int absValue = (int) Math.abs(player.getHitbox().x - c.getHitbox().x);
        return absValue <= Game.TILES_SIZE * 5;
    }

    /**
     * Definimos un metodo para determinar si el jugador esta al frente de un cannon
     * @param c es el cannon
     * @param player es el player
     * @return true si el jugador esta al frente de un cannon, false si no esta al frente del cannon
     */
    private boolean isPlayerInfrontOfCannon(Cannon c, Player player) {
        if (c.getObjType() == CANNON_LEFT) {
            if (c.getHitbox().x > player.getHitbox().x)
                return true;

        } else if (c.getHitbox().x < player.getHitbox().x)
            return true;
        return false;
    }

    /**
     * Definimos un metodo para acutualizar los cannons en el nivel
     * @param lvlData es la informacion del nivel, cambia dependiendo del nivel
     * @param player es el jugador
     */
    private void updateCannons(int[][] lvlData, Player player) {
        for (Cannon c : currentLevel.getCannons()) {
            if (!c.doAnimation)
                if (c.getTileY() == player.getTileY())
                    if (isPlayerInRange(c, player))
                        if (isPlayerInfrontOfCannon(c, player))
                            if (CanCannonSeePlayer(lvlData, player.getHitbox(), c.getHitbox(), c.getTileY()))
                                c.setAnimation(true);

            c.update();
            if (c.getAniIndex() == 4 && c.getAniTick() == 0)
                shootCannon(c);
        }
    }

    /**
     * Definimos un metodo para disparar los cannons
     * @param c es el cannon que se va a disparar
     */
    private void shootCannon(Cannon c) {
        int dir = 1;
        if (c.getObjType() == CANNON_LEFT)
            dir = -1;

        projectiles.add(new Projectile((int) c.getHitbox().x, (int) c.getHitbox().y, dir));
    }

    /**
     * Definimos un metodo para dibujar todos los objetos
     * @param g es el grafico para poder dibujar
     * @param xLvlOffset es el offset del nivel, cambia dependiendo del nivel
     */
    public void draw(Graphics g, int xLvlOffset) {
        drawPotions(g, xLvlOffset);
        drawContainers(g, xLvlOffset);
        drawTraps(g, xLvlOffset);
        drawCannons(g, xLvlOffset);
        drawProjectiles(g, xLvlOffset);
        drawGrass(g, xLvlOffset);
    }

    /**
     * Definimos un metodo para dibujar la grama en el nivel
     * @param g es el grafico para poder dibujar
     * @param xLvlOffset es el offset del nivel,cambia dependiendo del nivel
     */
    private void drawGrass(Graphics g, int xLvlOffset) {
        for (Grass grass : currentLevel.getGrass())
            g.drawImage(grassImgs[grass.getType()], grass.getX() - xLvlOffset, grass.getY(), (int) (32 * Game.SCALE), (int) (32 * Game.SCALE), null);
    }

    /**
     * Definimos un metodo para dibujar los arboles
     * @param g es el grafico para poder dibujar
     * @param xLvlOffset es el offset del nivel, cambia dependiendo del nivel
     */
    public void drawBackgroundTrees(Graphics g, int xLvlOffset) {
        for (BackgroundTree bt : currentLevel.getTrees()) {

            int type = bt.getType();
            if (type == 9)
                type = 8;
            g.drawImage(treeImgs[type - 7][bt.getAniIndex()], bt.getX() - xLvlOffset + GetTreeOffsetX(bt.getType()), (int) (bt.getY() + GetTreeOffsetY(bt.getType())), GetTreeWidth(bt.getType()),
                    GetTreeHeight(bt.getType()), null);
        }
    }

    /**
     * Definimos un metodo para dibujar los proyectiles
     * @param g es el grafico para poder dibujar
     * @param xLvlOffset es el offset del nivel, cambia dependiendo del nivel
     */
    private void drawProjectiles(Graphics g, int xLvlOffset) {
        for (Projectile p : projectiles)
            if (p.getActive())
                g.drawImage(cannonBallImg, (int) (p.getHitbox().x - xLvlOffset), (int) (p.getHitbox().y), CANNON_BALL_WIDTH, CANNON_BALL_HEIGHT, null);
    }

    /**
     * Definimos un metodo para dibujar los cannons
     * @param g es el grafico para poder dibujar
     * @param xLvlOffset es el offset del nivel, cambia dependiendo del nivel
     */
    private void drawCannons(Graphics g, int xLvlOffset) {
        for (Cannon c : currentLevel.getCannons()) {
            int x = (int) (c.getHitbox().x - xLvlOffset);
            int width = CANNON_WIDTH;

            if (c.getObjType() == CANNON_RIGHT) {
                x += width;
                width *= -1;
            }
            g.drawImage(cannonImgs[c.getAniIndex()], x, (int) (c.getHitbox().y), width, CANNON_HEIGHT, null);
        }
    }

    /**
     * Definimos un metodo para dibujar las trampas
     * @param g es el grafico para poder dibujar
     * @param xLvlOffset es el offset del nivel, cambia dependiendo del nivel
     */
    private void drawTraps(Graphics g, int xLvlOffset) {
        for (Spike s : currentLevel.getSpikes())
            g.drawImage(spikeImg, (int) (s.getHitbox().x - xLvlOffset), (int) (s.getHitbox().y - s.getyDrawOffset()), SPIKE_WIDTH, SPIKE_HEIGHT, null);

    }

    /**
     * Definimos un metodo para dibujar los contenedores
     * @param g es el grafico para poder dibujar
     * @param xLvlOffset es el offset del nivel, cambia dependiendo del nivel
     */
    private void drawContainers(Graphics g, int xLvlOffset) {
        for (GameContainer gc : containers)
            if (gc.isActive()) {
                int type = 0;
                if (gc.getObjType() == BARREL)
                    type = 1;
                g.drawImage(containerImgs[type][gc.getAniIndex()], (int) (gc.getHitbox().x - gc.getxDrawOffset() - xLvlOffset), (int) (gc.getHitbox().y - gc.getyDrawOffset()), CONTAINER_WIDTH,
                        CONTAINER_HEIGHT, null);
            }
    }

    /**
     * Definimos un metodo para dibujar las pociones
     * @param g es el grafico para poder dibujar
     * @param xLvlOffset es el offset del nivel, cambia dependiendo del nivel
     */
    private void drawPotions(Graphics g, int xLvlOffset) {
        for (Potion p : potions)
            if (p.isActive()) {
                int type = 0;
                if (p.getObjType() == RED_POTION)
                    type = 1;
                g.drawImage(potionImgs[type][p.getAniIndex()], (int) (p.getHitbox().x - p.getxDrawOffset() - xLvlOffset), (int) (p.getHitbox().y - p.getyDrawOffset()), POTION_WIDTH, POTION_HEIGHT,
                        null);
            }
    }

    /**
     * Definimos un metodo para resetear todos los objetos
     */
    public void resetAllObjects() {
        loadObjects(playing.getLevelManager().getCurrentLevel());
        for (Potion p : potions)
            p.reset();
        for (GameContainer gc : containers)
            gc.reset();
        for (Cannon c : currentLevel.getCannons())
            c.reset();
    }
}