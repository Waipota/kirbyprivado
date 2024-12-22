package objects;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;
import java.util.ArrayList;
import gamestates.Playing;
import utilz.LoadSave;
import static utilz.Constants.ObjectConstants.*;
public class ObjectManager {
    private Playing playing;
    private BufferedImage[][] potionImgs, containersImgs;
    private ArrayList<Potion> potions;
    private ArrayList<GameContainer> containers;


    public ObjectManager(Playing playing) {
        this.playing = playing;
        loadImgs();
        initArrays();

        potions.add(new Potion(300, 300, RED_POTION));
        potions.add(new Potion(400, 300, BLUE_POTION));

        containers.add(new GameContainer(500, 300, BARREL));
        containers.add(new GameContainer(600, 300, BOX));
    }


    private void initArrays(){
        potions = new ArrayList<>();
        containers = new ArrayList<>();
    }

    private void loadImgs() {
        BufferedImage potionSprite = LoadSave.GetSpriteAtlas(LoadSave.POTION_ATLAS);
        potionImgs = new BufferedImage[2][7];
        for (int j = 0; j < potionImgs.length; j++) {
            for (int i = 0; i < potionImgs[j].length; i++) {
                potionImgs[j][i] = potionSprite.getSubimage(12 * i, j * 16, 12, 16);
            }
        }

        BufferedImage containerSprite = LoadSave.GetSpriteAtlas(LoadSave.CONTAINER_ATLAS);
        containersImgs = new BufferedImage[2][8];
        for (int j = 0; j < containersImgs.length; j++) {
            for (int i = 0; i < containersImgs[j].length; i++) {
                containersImgs[j][i] = containerSprite.getSubimage(40 * i, j * 30, 40, 30);
            }
        }



    }

    public void update(){
        for (Potion potion : potions) {
            if (potion.getActive()){
                potion.update();
            }
        }

        for (GameContainer container : containers) {
            if (container.getActive()){
                container.update();
            }
        }
    }


    public void draw(Graphics g, int xLvlOffset) {
        drawPotions(g, xLvlOffset);
        drawContainers(g, xLvlOffset);

    }

    private void drawPotions(Graphics g, int xLvlOffset) {
        for (Potion p : potions)
            if (p.getActive()) {
                int type = 0;
                if (p.getObjectType() == RED_POTION)
                    type = 1;
                g.drawImage(potionImgs[type][p.getaniIndex()], (int) (p.getHitbox().x - p.getxDrawOffset() - xLvlOffset), (int) (p.getHitbox().y - p.getyDrawOffset()), POTION_WIDTH, POTION_HEIGHT,
                        null);
            }
    }


    private void drawContainers(Graphics g, int xLvlOffset) {
        for (GameContainer gc : containers)
            if (gc.getActive()) {
                int type = 0;
                if (gc.getObjectType() == BARREL)
                    type = 1;
                g.drawImage(containersImgs[type][gc.getaniIndex()], (int) (gc.getHitbox().x - gc.getxDrawOffset() - xLvlOffset), (int) (gc.getHitbox().y - gc.getyDrawOffset()), CONTAINER_WIDTH,
                        CONTAINER_HEIGHT, null);
            }
    }

}
