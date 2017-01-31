package simplepaint;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.ImageIcon;



public interface ChatIF 
{
  /**
   * Method that when overriden is used to display objects onto
   * a UI.
   */
  public abstract void display(String message);
  
  public abstract void display(ImageIcon image);
  
  public abstract void display(Message m);
  
  public abstract void display(ArrayList<String> al);
}
