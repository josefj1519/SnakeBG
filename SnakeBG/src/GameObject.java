import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;


public abstract class GameObject
  implements KeyListener, Serializable
{
  protected ArrayList<Integer> list = new ArrayList();
  protected Random r1 = new Random();
  protected int count;
  protected int array_limit; protected int random; protected Color[][] locations = new Color[51][21];
  boolean[][] apple = new boolean[51][21];
  boolean intersection;
  Color c1;
  
  public GameObject(int limit, Color[][] locations, Color c1) {
    count = 0;
    array_limit = limit;
    intersection = false;
    this.c1 = c1;
    this.locations = locations;
    
    for (int i = 0; i < apple.length; i++) {
      for (int j = 0; j < apple[i].length; j++) {
        apple[i][j] = false;
      }
    }
  }
  


  public abstract void moveAndDraw(Graphics2D paramGraphics2D);
  

  public void createArray(Graphics2D win)
  {
    if (count < array_limit) {
      for (int i = 0; i < locations.length; i++) {
        for (int j = 0; j < locations[i].length; j++) {
          if (locations[i][j].equals(c1)) {
            list.add(Integer.valueOf(i));
            list.add(Integer.valueOf(j));
          }
        }
      }
      count += 1;
    }
    if (list.size() != 0) {
      for (int i = 0; i < list.size(); i += 2) {
        win.setColor(c1);
        
        win.fill(SnakeComp.snakegrid[((Integer)list.get(i)).intValue()][((Integer)list.get(i + 1)).intValue()]);
      }
      
      removeArray();
    }
  }
  
  public void removeArray()
  {
    for (int i = 0; i < list.size(); i++)
    {
      if (intersection) {
        list.remove(i);
        list.remove(i);
        intersection = false;
        i -= 2;
      }
    }
  }
}
