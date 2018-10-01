import javax.swing.JFrame;






public class SnakeFrame
{
  
  public static void main(String[] args)
  {
    JFrame j1 = new JFrame();
    j1.setTitle("SNAKE BATTLEGROUNDS");
    j1.setSize(1290, 615);
    j1.setDefaultCloseOperation(3);
    j1.setLocationRelativeTo(null);
    
    j1.add(new SnakeComp());
    
    j1.setVisible(true);
  }
}
