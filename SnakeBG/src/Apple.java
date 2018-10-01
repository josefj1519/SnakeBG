import java.util.ArrayList;

public class Apple extends GameObject
{
  boolean isEat;
  int random;
  int limit;
  
  public Apple(int limit, java.awt.Color[][] locations)
  {
    super(1, locations, java.awt.Color.RED);
    this.limit = limit;
  }
  

  public void moveAndDraw(java.awt.Graphics2D win)
  {
    createArray(win);
  }
  

  public void createArray(java.awt.Graphics2D win)
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
      
      if (list.size() != 0)
      {
        do {
          random = r1.nextInt(list.size());
        }
        while ((random % 2 != 0) || (random != 0));
        count += 1;
      }
    }
    
    removeArray();
    
    if (list.size() != 0) {
      win.setColor(c1);
      

      win.fill(SnakeComp.snakegrid[((Integer)list.get(random)).intValue()][((Integer)list.get(random + 1)).intValue()]);
    }
  }
  



  public void removeArray()
  {
    for (int i = 0; i < list.size(); i++)
    {
      if (intersection) {
        list.remove(i);
        list.remove(i);
        count -= 1;
        intersection = false;
        
        break;
      }
    }
  }
  
  public void keyPressed(java.awt.event.KeyEvent e) {}
  
  public void keyReleased(java.awt.event.KeyEvent e) {}
  
  public void keyTyped(java.awt.event.KeyEvent e) {}
}
