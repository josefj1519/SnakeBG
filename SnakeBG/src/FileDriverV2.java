import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.Serializable;
















public class FileDriverV2 implements Serializable
{
  private static final long serialVersionUID = 1L;
  
  ObjectInputStream in;
  ObjectOutputStream out;
  FileOutputStream fileout;
  FileInputStream filein;
  File file;
  FileWriter write;
  FileReader read;
  BufferedReader buffer;
  String filepath;
  String sfolder;
  String folder;
  
  public FileDriverV2(String sfolder, String folder)
  {
    this.sfolder = sfolder;
    this.folder = folder;
    
    file = new File(System.getProperty("user.home") + "//Desktop", folder);
    System.out.println(file);
    if (!file.exists()) {
      file.mkdir();
      System.out.println(" main File created");
    }
    else {
      System.out.println("main file exists");
    }
    
    file = new File(file, sfolder);
    System.out.println(file);
    if (!file.exists()) {
      file.mkdir();
      System.out.println("save File created");
    }
    else {
      System.out.println("save file exists");
    }
    
    filepath = (file.getAbsolutePath() + "\\");
    System.out.println(filepath);
  }
  


  public void saveObj(Object obj, String filename)
  {
    try
    {
      System.out.println("save obj:" + filepath + filename + ".ser");
      fileout = new FileOutputStream(filepath + filename + ".ser");
      out = new ObjectOutputStream(fileout);
      out.writeObject(obj);
      out.close();
      fileout.close();
    } catch (IOException i) {
      i.printStackTrace();
    }
  }
  




  public Object readObj(Object obj, String filename)
  {
    try
    {
      filein = new FileInputStream(filepath + filename + ".ser");
      System.out.println("read obj:" + filepath + filename + ".ser");
      in = new ObjectInputStream(filein);
      obj = in.readObject();
      in.close();
      filein.close();
      return obj;
    } catch (ClassNotFoundException c) {
      c.printStackTrace();
    } catch (IOException i) {
      i.printStackTrace();
    }
    
    return null;
  }
  

  public void saveText(String txt, String filename)
  {
    try
    {
      System.out.println("save txt:" + filepath + filename + ".txt");
      file = new File(filepath + filename + ".txt");
      write = new FileWriter(file);
      write.write(txt);
      write.flush();
      write.close();
    } catch (IOException i) {
      i.printStackTrace();
    }
  }
  

  public String readText(String txt, String filename)
  {
    try
    {
      System.out.println("read txt:" + filepath + filename + ".txt");
      file = new File(filepath + filename + ".txt");
      


      read = new FileReader(file);
      buffer = new BufferedReader(read);
      txt = buffer.readLine();
      buffer.close();
      return txt;
    }
    catch (FileNotFoundException ex) {
      ex.printStackTrace();
    } catch (IOException i) {
      i.printStackTrace();
    }
    
    return "error";
  }
  
  public void deleteText(String filename)
  {
    file = new File(filepath, filename + ".txt");
    file.delete();
  }
  

  public void deleteObj(String filename)
  {
    file = new File(filepath, filename + ".ser");
    

    file.delete();
  }
}
