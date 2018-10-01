import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.JFrame;

public class SnakeComp
  extends GameDriverV3
  implements KeyListener, MouseListener, MouseMotionListener, Serializable
{
  boolean[][] objects = new boolean[51][21];
  Object[] obj = new Object[4];
  boolean[] died = new boolean[2];
  int gameState;
  int snakenum; int time; int delay; int ypos; int count0; int lvl; int mousex; int mousey; boolean invinframe; boolean back; boolean playtest; boolean creategame; boolean save; boolean key; boolean[] mouseIn = new boolean[3];
  SnakeMove[] snake;
  GameObject[] game;
  Menu m1;
  static Rectangle[][] snakegrid = new Rectangle[51][21];
  static Rectangle background = new Rectangle(0, 0, 1290, 615);
  Rectangle demo = new Rectangle(40, 10, 30, 35);
  Font[] txt = new Font[4];
  Color[][] gridcolor = new Color[51][21];
  boolean[][] gameselected = new boolean[51][21];
  boolean[][] gridclicked = new boolean[51][21];
  String[][] lvlnums = new String[51][21];
  boolean[] opclick = new boolean[6];
  Rectangle[] options = new Rectangle[6];
  String[] text = new String[6];
  int[] count = new int[6];
  JFrame creator = new JFrame();
  BufferedImage instruction;
  BufferedImage titlebox;
  BufferedImage title;
  BufferedImage[] button;
  BufferedImage[] heart;
  BufferedImage h1;
  BufferedImage b1;
  SoundDriver s1;
  FileDriverV2 f1;
  boolean selectgame;
  private boolean play;
  Rectangle[] rect = new Rectangle[3];
  int imgcount = 0;
  boolean ded;
  
  public SnakeComp() { 
	txt[0] = new Font("Monospaced", 1, 60);
    txt[1] = new Font("Monospaced", 0, 30);
    txt[2] = new Font("Monospaced", 2, 30);
    txt[3] = new Font("Monospaced", 0, 15);
    f1 = new FileDriverV2("snakesaves", "JosefJankowskiSnake");
    lvl = 0;
    save = false;selectgame = true;
    instruction = addImage("inst.png");
    titlebox = addImage("titilebox.png");
    title = addImage("snaketitle.png");
    button = new BufferedImage[2];
    b1 = addImage("buttonsnake.png");
    button[0] = b1.getSubimage(0, 0, b1.getWidth(), b1.getHeight() / 2);
    button[1] = b1.getSubimage(0, b1.getHeight() / 2, b1.getWidth(), b1.getHeight() / 2);
    h1 = addImage("heart.png");
    heart = new BufferedImage[5];
    


    for (int i = 0; i < heart.length; i++) {
      heart[i] = h1.getSubimage(h1.getWidth() / 5 * i, 0, h1.getWidth() / 5, h1.getHeight());
    }
    

    gameState = 0;
    snakenum = 1;
    invinframe = false;
    time = 0;delay = 20;
    


    for (int i = 0; i < snakegrid.length; i++) {
      for (int j = 0; j < snakegrid[i].length; j++) {
        snakegrid[i][j] = new Rectangle(25 * i, 25 * j + 50, 25, 25);
      }
    }
    

    creategame = true;
    count0 = 0;
    text[0] = "Snake1";
    text[1] = "Snake2";
    text[2] = "Obstacles";
    text[3] = "Food";
    text[4] = "Play test";
    text[5] = "Save";
    
    game = new GameObject[2];
    snake = new SnakeMove[2];
    
    for (int i = 0; i < options.length; i++) {
      options[i] = new Rectangle(i * 200 + 50, 10, 100, 35);
      opclick[i] = false;
      count[i] = 0;
    }
    for (int i = 0; i < gridclicked.length; i++) {
      for (int j = 0; j < gridclicked[i].length; j++) {
        gridclicked[i][j] = false;
        gridcolor[i][j] = Color.BLACK;
        lvlnums[i][j] = ""+(i + j * 51);
      }
    }
    m1 = new Menu(1290, 615);
    
    game = new GameObject[2];
    

    snake = new SnakeMove[2];
    snake[0] = new SnakeMove(87, 83, 65, 68, 69, 1, 13, Color.BLUE);
    snake[1] = new SnakeMove(73, 75, 74, 76, 79, 48, 13, Color.GREEN);
    snake[0].mashing = false;
    snake[1].mashing = false;
    

    String[] str = new String[3];
    str[0] = "chiptune1.wav";
    str[1] = "menunoise.wav";
    str[2] = "eat.wav";
    s1 = new SoundDriver(str);
    
    for (int i = 0; i < snake.length; i++) {
      addKeyListener(snake[i]);
    }
    addKeyListener(this);
    addKeyListener(m1);
    addMouseListener(this);
    addMouseMotionListener(this);
    
    ypos = 400;
    if ("error".equals(f1.readText(null, "Level"))) {
      lvl = 0;
    }
    else {
      lvl = Integer.parseInt(f1.readText(null, "Level"));
    }
    for (int i = 0; i < rect.length; i++) {
      rect[i] = new Rectangle(i * 200 + 555, 380, 180, 130);
    }
  }
  
  public void reset()
  {
    gameState = 1;
    snake[0].snakeLength = 26;
    snake[1].snakeLength = 26;
  }
  


  public void drawBackground(Graphics2D win)
  {
    win.setColor(Color.BLACK);
    win.fill(background);
    

    for (int i = 0; i < snakegrid.length; i++) {
      for (int j = 0; j < snakegrid[i].length; j++) {
        win.fill(snakegrid[i][j]);
      }
    }
  }
  

  public void draw(Graphics2D win)
  {

    drawBackground(win);
    win.setFont(txt[3]);
    
    if (m1.selection == 1) {
      gameState = 1;
    }
    
    if (m1.selection == 2) {
      gameState = 2;
    }
    
    if (gameState == 3) {
      win.setColor(Color.GREEN);
      win.setFont(txt[0]);
      win.drawString("Instructions:", 40, 95);
      
      win.drawImage(instruction, null, 0, 0);
      if (back) {
        gameState = 0;
      }
    }
    

    if (gameState == 1) {
      snakenum = 1;
      
      if (selectgame)
      {
        for (int i = 0; i < gridclicked.length; i++) {
          for (int j = 0; j < gridclicked[i].length; j++)
          {
            win.setColor(Color.WHITE);
            win.draw(snakegrid[i][j]);
            

            win.setColor(Color.WHITE);
            win.drawString("BEHOLD THE GLORY!", 300, 25);
            if (lvl >= j * 51 + i * 4 + 4) {
              objects[i][j] = true;
              win.setColor(Color.BLUE);
            }
            win.drawString(lvlnums[i][j], 25 * i + 5, 25 * j + 65);
          }
        }
        key = false;
        
        selectGame(win);
        if (back) {
          selectgame = true;
          play = false;
          gameState = 0;
          back = false;
        }
      }
      
      if (play) {
        time += 1;
        





        if (time % delay == 0) {
          invinframe = false;
        }
        


        getHeart(win);
        if (!key)
        {

          for (int i = 0; i < snake.length; i++) {
            addKeyListener(snake[i]);
          }
          key = true;
        }
        for (int i = 0; i < snake.length; i++) {
          snake[i].moveAndDraw(win);
        }
        
        for (int i = 0; i < snake.length; i++) {
          game[i].moveAndDraw(win);
        }
        
        if (snake[0].mashing) {
          time = time % 60;
          

          win.drawString("MASHING!!!", 600, 30);
          win.drawString(""+snake[0].mashcount, 300, 30);
          win.drawString(""+snake[1].mashcount, 900, 30);
        }
        
        getIntersection(win);
        for (int i = 0; i < gridclicked.length; i++) {
          for (int j = 0; j < gridclicked[i].length; j++)
          {
            win.setColor(Color.BLACK);
            win.draw(snakegrid[i][j]);
          }
        }
      }
      


      if ((snake[0].life == 0) || (snake[1].life == 0)) {
        drawBackground(win);
        win.setFont(txt[0]);
        
        if (snake[0].life == 0) {
          win.setColor(Color.PINK);
          win.drawString("Pink Won!", 100, 100);
        }
        else {
          win.setColor(Color.GREEN);
          win.drawString("Green Won!", 100, 100);
        }
      }
      


      if (back) {
        snake[0].life = 3;
        snake[1].life = 3;
        
        selectgame = true;
        play = false;
        gameState = 1;
        back = false;
      }
    }
    





    if (gameState == 2) {
      for (int i = 0; i < snakegrid.length; i++) {
        for (int j = 0; j < snakegrid[i].length; j++) {
          win.setColor(Color.WHITE);
          win.draw(snakegrid[i][j]);
        }
      }
      time += 1;
      if (time % delay == 0) {
        invinframe = false;
      }
      if (creategame) {
        createGame(win);
        if (back) {
          gameState = 0;
          back = false;
        }
      }
      if (playtest) {
        creategame = false;
        playTest(win);
        getIntersection(win);
        if (back) {
          count0 = 0;
          opclick[4] = false;
          creategame = true;
          playtest = false;
          back = false;
        }
      }
      
      if (save) {
        saveGame();
        
        save = false;
      }
    }
    



    if (gameState == 0) {
      m1.selection = 0;
      

      if (mouseIn[0]) {
        win.drawImage(button[1], null, 400, 350);
      }
      
      if (mouseIn[1]) {
        win.drawImage(button[1], null, 600, 350);
      }
      
      if (mouseIn[2])
      {
        win.drawImage(button[1], null, 800, 350);
      }
      if (!mouseIn[0])
        win.drawImage(button[0], null, 400, 350);
      if (!mouseIn[1])
        win.drawImage(button[0], null, 600, 350);
      if (!mouseIn[2])
        win.drawImage(button[0], null, 800, 350);
      m1.moveAndDraw(win);
      win.drawImage(titlebox, null, 270, -40);
      if (ypos != 60) {
        ypos -= 5;
      }
      if ((ypos == 60) && (!s1.isPlaying(0))) {
        s1.loop(0);
      }
      win.drawImage(title, null, 340, ypos);
    }
  }
  


  public void getHeart(Graphics2D win)
  {
    win.setColor(Color.GREEN);
    win.drawString(""+snake[0].snakeLength / 13, 160, 37);
    win.setColor(Color.PINK);
    win.drawString(""+snake[1].snakeLength / 13, 1075, 37);
    for (int j = 0; j < 2; j++) {
      for (int i = 0; i < snake[j].life; i++)
      {
        if ((j == 0) && (died[j] == false)) {
          win.drawImage(heart[0], null, 50 + i * 30, 15);
        }
        else if (died[j] == false) {
          win.drawImage(heart[0], null, 1100 + i * 30, 15);
        }
        
        if (died[j] != false) {
          time += 1;
          
          if ((time % 30 == 0) && (imgcount < 4)) {
            imgcount += 1;
          }
          
          if (j == 0)
          {

            if ((imgcount != 3) && (imgcount != 4))
            {
              win.drawImage(heart[imgcount], null, 50 + i * 30, 15);
            }
            else {
              win.drawImage(heart[3], null, 40 + i * 30, 15);
              win.drawImage(heart[4], null, 60 + i * 30, 15);
              if (time % 30 == 0) {
                died[j] = false;
                imgcount = 0;

              }
              

            }
            


          }
          else if ((imgcount != 3) && (imgcount != 4)) {
            win.drawImage(heart[imgcount], null, 1100 + i * 30, 15);
          }
          else {
            win.drawImage(heart[3], null, 1090 + i * 30, 15);
            win.drawImage(heart[4], null, 1110 + i * 30, 15);
            if (time % 60 == 0) {
              died[j] = false;
              imgcount = 0;
            }
          }
        }
      }
    }
  }
  








  public void selectGame(Graphics2D win)
  {
    for (int i = 0; i < gridclicked.length; i++) {
      for (int j = 0; j < gridclicked[i].length; j++)
      {
    	  
        if (gameselected[i][j])
        {
        	System.out.println("" + (i * 4 + j * 51));
          snake[0] = ((SnakeMove)f1.readObj(snake[0], "" + ((i * 4) + (j * 51))));
          snake[1] = ((SnakeMove)f1.readObj(snake[1], ""+((i * 4) + (j * 51 + 1))));
          game[0] = ((GameObject)f1.readObj(game[0],""+ ((i * 4 )+ (j * 51 + 2))));
          game[1] = ((GameObject)f1.readObj(game[1], ""+((i * 4) +(j * 51 + 3))));
          
          gameselected[i][j] = false;
          play = true;
          selectgame = false;
          
          i = gridclicked.length;
          break;
        }
      }
    }
  }
  



  public void createGame(Graphics2D win)
  {
    for (int i = 0; i < options.length; i++)
    {

      win.setColor(Color.WHITE);
      win.draw(options[i]);
    }
    for (int i = 0; i < gridclicked.length; i++) {
      for (int j = 0; j < gridclicked[i].length; j++) {
        if (gridclicked[i][j] != false) {
          win.setColor(gridcolor[i][j]);
          win.fill(snakegrid[i][j]);
        }
      }
    }
    

    for (int i = 0; i < options.length; i++) {
      if (opclick[i] != false) {
        win.setColor(Color.BLUE);
        win.fill(options[i]);
        if (i == 4) {
          playtest = true;
          creategame = false;
          opclick[i] = false;
        }
        
        if (i == 5) {
          save = true;
          opclick[i] = false;
        }
      }
      
      win.setColor(Color.YELLOW);
      win.drawString(text[i], i * 200 + 70, 25);
    }
  }
  


  public void playTest(Graphics2D win)
  {
    win.setColor(Color.BLACK);
    win.fill(background);
    if (count0 < 1)
    {
      for (int i = 0; i < gridcolor.length; i++) {
        for (int j = 0; j < gridcolor[i].length; j++) {
          if (gridcolor[i][j].equals(Color.GREEN))
            snake[0] = new SnakeMove(87, 83, 65, 68, 69, i, j, Color.GREEN);
          if (gridcolor[i][j].equals(Color.PINK))
            snake[1] = new SnakeMove(73, 75, 74, 76, 79, i, j, Color.PINK);
        }
      }
      snake[0].mashing = false;
      snake[1].mashing = false;
      

      game[0] = new Apple(count[2], gridcolor);
      game[1] = new Obstacles(count[3], gridcolor);
      for (int i = 0; i < snake.length; i++) {
        addKeyListener(snake[i]);
      }
      addKeyListener(this);
      
      count0 += 1;
    }
    
    for (int i = 0; i < snake.length; i++) {
      snake[i].moveAndDraw(win);
    }
    for (int i = 0; i < game.length; i++) {
      game[i].moveAndDraw(win);
    }
  }
  
  
  public void saveGame()
  {
    if (save)
    {
      for (int i = 0; i < gridcolor.length; i++) {
        for (int j = 0; j < gridcolor[i].length; j++) {
          if (gridcolor[i][j].equals(Color.GREEN))
            snake[0] = new SnakeMove(87, 83, 65, 68, 69, i, j, Color.GREEN);
          if (gridcolor[i][j].equals(Color.PINK))
            snake[1] = new SnakeMove(73, 75, 74, 76, 79, i, j, Color.PINK);
        }
      }
      snake[0].mashing = false;
      snake[1].mashing = false;
      

      game[0] = new Apple(count[2], gridcolor);
      game[1] = new Obstacles(count[3], gridcolor);
      



      obj[0] = snake[0];
      obj[1] = snake[1];
      obj[2] = game[0];
      obj[3] = game[1];
      






      for (int i = lvl; i < obj.length + lvl; i++)
      {
        f1.saveObj(obj[(i - lvl)], ""+i);
      }
      
      lvl += 4;
      f1.saveText(""+lvl, "Level");
      save = false;
    }
  }
  

  public void getIntersection(Graphics2D win)
  {
    if ((snake[0].mashcount >= 20) && (snake[1].mashcount < 20))
    {

      death(1);
      snake[0].mashcount = 0;
      snake[1].mashcount = 0;
      snake[0].mashing = false;
      snake[1].mashing = false;
    }
    if ((snake[1].mashcount >= 20) && (snake[0].mashcount < 20))
    {
      death(0);
      snake[0].mashcount = 0;
      snake[1].mashcount = 0;
      snake[0].mashing = false;
      snake[1].mashing = false;
    }
    

    for (int i = 0; i < snake.length; i++)
    {

      if ((snake[i].positionX == ((Integer)game[0].list.get(game[0].random)).intValue()) && (snake[i].positionY == ((Integer)game[0].list.get(game[0].random + 1)).intValue()))
      {
        s1.play(2);
        game[0].intersection = true;
        snake[i].snakeLength += 13;
      }
      else
      {
        game[1].intersection = false;
      }
      

      for (int z = 0; z < game[1].list.size() - 1; z++) {
        if ((snake[i].positionX == ((Integer)game[1].list.get(z)).intValue()) && (snake[i].positionY == ((Integer)game[1].list.get(z + 1)).intValue()))
        {

          if (snake[i].up) {
            snake[i].positionY += 1;
            snake[i].up = false;
          }
          if (snake[i].down) {
            snake[i].positionY -= 1;
            snake[i].down = false;
          }
          if (snake[i].right) {
            snake[i].positionX -= 1;
            snake[i].right = false;
          }
          if (snake[i].left) {
            snake[i].positionX += 1;
            snake[i].left = false;
          }
        }
        else {
          game[1].intersection = false;
        }
      }
    }
    




    for (int i = 0; i < snake.length - snakenum; i++)
    {


      if ((snake[i].positionX == snake[(i + 1)].positionX) && (snake[i].positionY == snake[(i + 1)].positionY) && (!snake[0].mashing)) {
        compareLength(win);
        s1.play(2);
      }
      
      if ((snake[i].positionX == ((Integer)snake[(i + snakenum)].list.get(0)).intValue()) && (snake[i].positionY == ((Integer)snake[(i + snakenum)].list.get(1)).intValue()) && (!snake[0].mashing)) {
        compareLength(win);
        s1.play(2);
      }
      if ((snake[(i + snakenum)].positionX == ((Integer)snake[i].list.get(0)).intValue()) && (snake[(i + snakenum)].positionY == ((Integer)snake[i].list.get(1)).intValue()) && (!snake[0].mashing)) {
        compareLength(win);
        s1.play(2);
      }
    }
  }
  


  public void compareLength(Graphics2D win)
  {
    for (int i = 0; i < snake.length - snakenum; i++)
    {


      if ((snake[i].snakeLength > snake[(i + snakenum)].snakeLength) && (!invinframe)) {
        time = 0;
        
        invinframe = true;
        death(1);

      }
      else if ((snake[i].snakeLength < snake[(i + snakenum)].snakeLength) && (!invinframe)) {
        time = 0;
        invinframe = true;
        
        death(0);

      }
      else if ((snake[i].snakeLength == snake[(i + snakenum)].snakeLength) && (!invinframe))
      {


        time = 0;
        

        snake[i].mashing = true;
        snake[(i + 1)].mashing = true;
        for (int z = 0; z < snake.length - 1; z++)
        {
          if ((snake[z].mashcount >= 20) && (snake[(z + 1)].mashcount < 20))
          {

            death(1);
            snake[z].mashcount = 0;
            snake[(z + 1)].mashcount = 0;
            snake[0].mashing = false;
            snake[1].mashing = false;
          }
          if ((snake[(z + 1)].mashcount >= 20) && (snake[z].mashcount < 20))
          {
            death(0);
            snake[z].mashcount = 0;
            snake[(z + 1)].mashcount = 0;
            snake[0].mashing = false;
            snake[1].mashing = false;
          }
        }
        
        invinframe = true;
      }
    }
  }
  



  public void death(int num)
  {
    died[num] = true;
    if (snake[num].life >= 0)
    {

      snake[0].mashcount = 0;
      snake[1].mashcount = 0;
      snake[num].positionX = snake[num].permX;
      snake[num].positionY = snake[num].permY;
      snake[num].snakeLength = 26;
      died[num] = true;
      snake[num].life -= 1;
    }
    


    if ((snake[num].life == 0) && (gameState != 2)) {
      for (int i = 0; i < snake.length; i++) {
        play = false;
        snake[i].mashing = false;
        snake[i].mashcount = 0;
        snake[i].positionX = snake[i].permX;
        snake[i].positionY = snake[i].permY;
      }
      

      reset();
    }
    else if (gameState == 2) {
      snake[1].life = 3;
      snake[0].life = 3;
    }
  }
  











  public void keyPressed(KeyEvent e)
  {
    if (e.getKeyCode() == 8) {
      back = true;
    }
  }
  



  public void keyReleased(KeyEvent e)
  {
    if ((snake[0].mashing) || (snake[1].mashing)) {
      if (e.getKeyCode() == 81) {
        snake[0].mashcount += 1;
      }
      
      if (e.getKeyCode() == 85) {
        snake[1].mashcount += 1;
      }
    }
  }
  





  public void keyTyped(KeyEvent e) {}
  




  public void mouseClicked(MouseEvent e)
  {
    s1.play(1);
    if ((e.getButton() == 1) && (gameState == 0)) {
      for (int i = 0; i < rect.length; i++) {
        if (rect[i].getBounds().contains(e.getX(), e.getY())) {
          if (i == 0) {
            gameState = 1;
            break;
          }
          if (i == 1) {
            gameState = 2;
            break;
          }
          if (i == 2) {
            gameState = 3;
            break;
          }
        }
      }
    }
    if ((e.getButton() == 3) && (gameState == 2))
    {
      for (int i = 0; i < snakegrid.length; i++) {
        for (int j = 0; j < snakegrid[i].length; j++) {
          if ((e.getX() < i * 25 + 25) && (e.getY() < j * 25 + 75) && (e.getY() > 50) && (gridclicked[i][j] != false))
          {
            gridcolor[i][j] = Color.BLACK;
            gridclicked[i][j] = false;
            
            if (opclick[0] != false) {
              count[0] -= 1;
            }
            if (opclick[1] != false) {
              count[1] -= 1;
            }
            



            i = snakegrid.length;
            break;
          }
        }
      }
    }
    

    if ((e.getButton() == 1) && (gameState == 1) && (!play))
    {
      for (int i = 0; i < snakegrid.length; i++) {
        for (int j = 0; j < snakegrid[i].length; j++) {
          if ((e.getX() < i * 25 + 25) && (e.getY() < j * 25 + 75) && (e.getY() > 50) && 
            (objects[i][j] != false)) {
            gameselected[i][j] = true;
            i = snakegrid.length;
            break;
          }
        }
      }
    }
    


    if ((e.getButton() == 3) && (gameState == 1) && (!play)) {
      for (int i = 0; i < snakegrid.length; i++) {
        for (int j = 0; j < snakegrid[i].length; j++) {
          if ((e.getX() < i * 25 + 25) && (e.getY() < j * 25 + 75) && (e.getY() > 50) && 
            (objects[i][j] != false)) {
            for (int z = 0; z < 4; z++)
            {
              f1.deleteObj(""+i * 4 + j * 51 + z);
            }
            objects[i][j] = false;
            lvl -= 4;
            f1.saveText(""+lvl, "Level");
          }
        }
      }
    }
    
    if ((e.getButton() == 1) && (gameState == 2))
    {
      for (int i = 0; i < options.length; i++) {
        if ((e.getX() > i * 200 + 50) && (e.getX() < i * 200 + 150) && (e.getY() < 35) && (e.getY() > 10)) {
          for (int j = 0; j < options.length; j++) {
            opclick[j] = false;
          }
          opclick[i] = true;
        }
      }
      
      for (int i = 0; i < snakegrid.length; i++) {
        for (int j = 0; j < snakegrid[i].length; j++) {
          if ((e.getX() < i * 25 + 25) && (e.getY() < j * 25 + 75) && (e.getY() > 50))
          {
            for (int z = 0; z < options.length; z++)
            {
              if (opclick[z] != false)
              {

                if ((z == 0) && (count[z] <= 1))
                {
                  gridcolor[i][j] = Color.GREEN;
                  gridclicked[i][j] = true;
                  count[z] += 1;
                }
                if ((z == 1) && (count[z] <= 1)) {
                  gridcolor[i][j] = Color.PINK;
                  count[z] += 1;
                  gridclicked[i][j] = true;
                }
                if (z == 2) {
                  gridcolor[i][j] = Color.WHITE;
                  count[z] += 1;
                  gridclicked[i][j] = true;
                }
                if (z == 3) {
                  gridcolor[i][j] = Color.RED;
                  gridclicked[i][j] = true;
                  count[z] += 1;
                }
                
                if (z == 4) {
                  playtest = true;
                  creategame = false;
                  opclick[z] = false;
                  count[z] += 1;
                }
                
                if (z == 5) {
                  save = true;
                  count[z] += 1;
                  opclick[z] = false;
                }
              }
            }
            i = snakegrid.length;
            break;
          }
        }
      }
    }
  }
  
  public void mouseEntered(MouseEvent e) {}
  
  public void mouseExited(MouseEvent e) {}
  
  public void mousePressed(MouseEvent e) {}
  
  public void mouseReleased(MouseEvent e) {}
  
  public void mouseDragged(MouseEvent e) {}
  
  public void mouseMoved(MouseEvent e) {
	  
	  for (int i = 0; i < rect.length; i++) {
	    	
	        if (rect[i].getBounds().contains(e.getPoint())) {
	          mouseIn[i] = true;
	        }
	        else {
	          mouseIn[i] = false;
	        }
	      }

  }
}
