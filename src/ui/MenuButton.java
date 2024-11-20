package ui;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import gamestates.GameState;
import utilz.LoadSave;
import static utilz.Constants.UI.Buttons.*;
public class MenuButton {
  private int xpos, ypos, row, index;
  private int xOffsetcenter = B_WIDHT / 2;
  private GameState state;
  private BufferedImage[] imgs;
  private boolean mouseOver, mousePressed;
  private Rectangle bounds;
  
  public MenuButton(int xpos, int ypos, int row, GameState state) {
    this.xpos = xpos;
    this.ypos = ypos;
    this.row = row;
    this.state = state;
    loadImgs();
    initBounds();
  }

  private void initBounds(){
    bounds = new Rectangle(xpos - xOffsetcenter, ypos, B_WIDHT, B_HEIGHT);

  }


  private void loadImgs() {
    imgs = new BufferedImage[3];
    BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.MENU_BUTTON);
    for (int i = 0 ; i < imgs.length; i ++){
      imgs[i] = temp.getSubimage(i * B_WIDHT_DEFAULT, row * B_HEIGHT_DEFAULT, B_WIDHT_DEFAULT, B_HEIGHT_DEFAULT);
    }
  }

  public void draw(Graphics g) {
    g.drawImage(imgs[index], xpos - xOffsetcenter, ypos , B_WIDHT, B_HEIGHT, null);
  }

  public void update(){

    index = 0;
    if (mouseOver){
      index = 1;
    }
    if (mousePressed){
      index = 2;
    }


  }

  public boolean getMouseOver(){
    return mouseOver;
  }

  public boolean getMousePressed(){
    return mousePressed;
  }

  public void setMouseOver(boolean mouseOver){
    this.mouseOver = mouseOver;
  }

  public void setMousePressed(boolean mousePressed){
    this.mousePressed = mousePressed;
  }

  public void applyGamestate(){
    GameState.state = state;
  }

  public void resetBools(){
    mouseOver = false;
    mousePressed = false; 
  }

  public boolean isMousePresed() {
    return mousePressed;
  }

  public Rectangle getBounds(){
    return bounds;
  }

}
