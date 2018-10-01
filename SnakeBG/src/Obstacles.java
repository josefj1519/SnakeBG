import java.awt.Color;
import java.awt.event.KeyEvent;

public class Obstacles extends GameObject
{
  public Obstacles(int limit, Color[][] color)
  {
    super(limit, color, Color.WHITE);
  }
  


  public void moveAndDraw(java.awt.Graphics2D win)
  {
    createArray(win);
  }
  
  public void keyPressed(KeyEvent arg0) {}
  
  public void keyReleased(KeyEvent arg0) {}
  
  public void keyTyped(KeyEvent arg0) {}
}
