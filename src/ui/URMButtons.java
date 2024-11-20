package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import static utilz.Constants.UI.URMButtons.*;

import utilz.LoadSave;
public class URMButtons extends PauseButton{  
  private BufferedImage[] imgs;
  private int rowindex, index;
  private boolean mouseOver, mousePressed, muted;

  public URMButtons(int x, int y, int width, int height, int rowindex) {
    super(x, y, width, height);
    this.rowindex = rowindex;
    loadImgs();
  }

  public void update(){
    if (mouseOver){
      index = 1;
    }
    if (mousePressed){
      index = 2;
    }

  }

  public void draw(Graphics g){
    g.drawImage(imgs[index], x, y, URM_SIZE, URM_SIZE, null);
  }
  
  public void loadImgs(){
    BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.URM_BUTTON);
    imgs = new BufferedImage[3];
    for (int i = 0; i < imgs.length ; i ++){
      imgs[i] = temp.getSubimage(i * URM_DEFAULT_SIZE, rowindex * URM_DEFAULT_SIZE, URM_DEFAULT_SIZE, URM_DEFAULT_SIZE);
    }
  }

  public void resetBools(){
    mouseOver = false;
    mousePressed = false;
  }
  
  public void setMouseOver(boolean mouseOver){
    this.mouseOver = mouseOver;
  }

  public void setMousePressed(boolean mousePressed){
    this.mousePressed = mousePressed;
  }
  
  public boolean getMouseOver(){
    return mouseOver;
  }

  public boolean getMousePressed(){
    return mousePressed;
  }

}
