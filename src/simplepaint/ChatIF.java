package simplepaint;

import java.awt.image.BufferedImage;



public interface ChatIF 
{
  /**
   * Method that when overriden is used to display objects onto
   * a UI.
   */
  public abstract void display(String message);
  
  public abstract void display(BufferedImage image);
}
