import java.awt.event.KeyEvent;

public class SnakeMove extends GameObject implements java.awt.event.KeyListener
{
  boolean up;
  boolean down;
  boolean left;
  boolean right;
  boolean collision;
  boolean mashing = false;
  private int isUp;
  private int isDown;
  private int isLeft;
  private int isRight;
  
  public SnakeMove(int up, int down, int left, int right, int boost, int x, int y, java.awt.Color c1) {
    super(0, null, null);
    
    isUp = up;
    isDown = down;
    isLeft = left;
    isRight = right;
    isBoost = boost;
    positionX = x;
    positionY = y;
    permX = x;
    permY = y;
    time = 0;
    delay = 7;
    mashcount = 0;
    life = 3;
    snakeLength = 26;
    this.c1 = c1;
    collision = false;
  }
  
  private int isBoost;
  int positionX;
  int positionY;
  int permX;
  public void moveAndDraw(java.awt.Graphics2D win) {
    time += 1;
    
    if (collision) {
      up = false;right = false;left = false;down = false;
    }
    

    if (time % 2000 == 0) {
      boostcount += 1;
    }
    if (boostcount > 3) {
      boostcount = 3;
    }
    if (time % 200 == 0) {
      delay = 7;
    }
    
    if ((time % delay == 0) && 
      (!mashing)) {
      if (up) {
        positionY -= 1;
      }
      if (down) {
        positionY += 1;
      }
      if (left) {
        positionX -= 1;
      }
      
      if (right) {
        positionX += 1;
      }
    }
    

    if (positionX > 50)
      positionX = 0;
    if (positionX < 0)
      positionX = 50;
    if (positionY > 20)
      positionY = 0;
    if (positionY < 0) {
      positionY = 20;
    }
    
    list.add(Integer.valueOf(positionX));
    list.add(Integer.valueOf(positionY));
    

    createArray(win);
  }
  
  int permY;
  int snakeLength;
  int delay;
  int time;
  int boostcount;
  int mashcount;
  int life;
  java.awt.Color c1;
  public void createArray(java.awt.Graphics2D win) { for (int i = 0; i < snakeLength; i += 2)
    {
      if (i > list.size() - 1) {
        break;
      }
      win.setColor(c1);
      win.fill(SnakeComp.snakegrid[((Integer)list.get(i)).intValue()][((Integer)list.get(i + 1)).intValue()]);
      
      if (snakeLength >= 26) {
        win.setColor(java.awt.Color.YELLOW);
        win.fill(SnakeComp.snakegrid[((Integer)list.get(0)).intValue()][((Integer)list.get(1)).intValue()]);
        win.fill(SnakeComp.snakegrid[positionX][positionY]);
      }
    }
    

    removeArray();
  }
  

  public void removeArray()
  {
    if ((snakeLength != 0) && (list.size() >= 2)) {
      for (int j = 0; j < list.size(); j++)
      {
        if (list.size() < snakeLength) {
          break;
        }
        list.remove(j);
        list.remove(j);
      }
    }
  }
  



  public void keyPressed(KeyEvent e)
  {
    if ((e.getKeyCode() == isUp) && (!mashing))
    {
      up = true;
      
      left = false;right = false;down = false;
    }
    if ((e.getKeyCode() == isDown) && (!mashing))
    {
      down = true;
      
      left = false;right = false;up = false;
    }
    if ((e.getKeyCode() == isLeft) && (!mashing))
    {
      left = true;
      
      up = false;down = false;right = false;
    }
    if ((e.getKeyCode() == isRight) && (!mashing))
    {
      right = true;
      
      up = false;down = false;left = false;
    }
  }
  





  public void keyReleased(KeyEvent e)
  {
    if (e.getKeyCode() == isBoost) {
      delay = 4;
      time = 0;
      boostcount -= 1;
    }
  }
  
  public void keyTyped(KeyEvent e) {}
}
