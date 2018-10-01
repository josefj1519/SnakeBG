import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;











public abstract class GameDriverV3
  extends Canvas
{
  protected BufferedImage back;
  protected int FramesPerSecond = 60;
  protected long timer = 1000 / FramesPerSecond;
  protected Timer t1 = new Timer();
  
  public GameDriverV3(int frames)
  {
    FramesPerSecond = frames;
  }
  



  public GameDriverV3()
  {
    setVisible(true);
    t1.scheduleAtFixedRate(new GameDriverV3.ThreadTimer(this), 0L, timer);
    
    setFocusable(true);
  }
  
  public void update(Graphics window) {
    paint(window);
  }
  

  public void paint(Graphics window)
  {
    if (back == null)
      back = ((BufferedImage)createImage(getWidth(), getHeight()));
    Graphics2D graphToBack = back.createGraphics();
    
    draw(graphToBack);
    
    Graphics2D win2D = (Graphics2D)window;
    win2D.drawImage(back, null, 0, 0);
  }
  
  public abstract void draw(Graphics2D paramGraphics2D);
  
  private class ThreadTimer extends TimerTask
  {
    GameDriverV3 driver;
    
    public ThreadTimer(GameDriverV3 gameDrive)
    {
      driver = gameDrive;
    }
    
    public void run()
    {
      driver.repaint();
      System.gc();
    }
  }
  
  public class timerDriver extends Thread {
    int delay;
    
    public timerDriver(int _delayInMiliseconds) { delay = _delayInMiliseconds; }
    



    public void run() {}
  }
  


  public BufferedImage addImage(String name)
  {
    BufferedImage img = null;
    try
    {
      img = ImageIO.read(getClass().getResource(name));
    }
    catch (IOException e) {
      System.out.println(e);
    }
    
    return img;
  }
}
