import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Menu implements KeyListener
{
  private int width;
  private int height;
  int selection;
  int time;
  SoundDriver s1;
  
  public Menu(int width, int height)
  {
    this.width = width;
    this.height = height;
  }
  






  public void moveAndDraw(Graphics2D win)
  {
    time += 1;
    
    win.setColor(Color.BLACK);
    win.drawString("1v1", 640, 450);
    win.drawString("Level Creator", 787, 450);
    win.drawString("Instructions", 980, 450);
  }
  

  public void keyPressed(KeyEvent e)
  {
    if (e.getKeyCode() == 49) {
      selection = 1;
    }
    if (e.getKeyCode() == 50) {
      selection = 2;
    }
  }
  
  public void keyReleased(KeyEvent e) {}
  
  public void keyTyped(KeyEvent e) {}
}
