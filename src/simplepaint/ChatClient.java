package simplepaint;






import java.awt.image.BufferedImage;
import java.io.*;
import javax.swing.ImageIcon;

/**
 * This class overrides some of the methods defined in the abstract
 * superclass in order to give more functionality to the client.
 *

 */
public class ChatClient extends AbstractClient
{
  //Instance variables **********************************************
  
  /**
   * The interface type variable.  It allows the implementation of 
   * the display method in the client.
   */
  ChatIF clientUI; 
  // BufferedImage will be converted to allow for serialization
  ImageIcon buffImage;
  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the chat client.
   *
   * @param host The server to connect to.
   * @param port The port number to connect on.
   * @param clientUI The interface type variable.
   */
  
  public ChatClient(String host, int port, ChatIF clientUI) 
    throws IOException 
  {
    super(host, port); //Call the superclass constructor
    this.clientUI = clientUI;
    openConnection();
  }

  
  //Instance methods ************************************************
    
  /**
   * This method handles all data that comes in from the server.
   *
   * @param msg The message from the server.
   */
  public void handleMessageFromServer(Object msg) 
  {
    if(msg instanceof ImageIcon){
        ImageIcon image = (ImageIcon) msg;
        clientUI.display(image);
    }else{
        clientUI.display(msg.toString());
    }
  }

  /**
   * This method handles all data coming from the UI            
   *
   * @param message The message from the UI.    
   */
  public void handleImageFromClientUI(BufferedImage bi)
  {
      try
      {
        buffImage = new ImageIcon(bi);
        sendToServer(buffImage);
      }
      catch(IOException e)
      {
        clientUI.display("Could not send message to server. Terminating client.");
        System.out.println(e);
        quit();
      }
      
  }
  
   /**
   * Hook method called each time an exception is thrown by the
   * client's thread that is waiting for messages from the server.
   * The method may be overridden by subclasses.
   *
   * @param exception the exception raised.
   */
  protected void connectionException(Exception exception) 
  {
      System.out.println("Server disconnected");
      //After implementing connection closed, call it here...
      connectionClosed();
  }
  
   /**
   * Hook method called after a connection has been established.
   * The default implementation does nothing.
   * It may be overridden by subclasses to do anything they wish.
   */
  protected void connectionEstablished() 
  { 
      System.out.println("Connected to: " + super.getHost());
  }
  
  public void handleMessageFromClientUI(String message)
  {
    try
    {
      //differentiate between local and server commands
      if(message.charAt(0) == '#'){
          handleCommandFromClientUI(message);
      }else{
        sendToServer(message);
      }
    }
    catch(IOException e)
    {
      clientUI.display
        ("Could not send message to server. Terminating client.");
      quit();
    }
  }
  
  public void handleCommandFromClientUI(String command){
     
    try{      
        if(command.startsWith("#getPort")){
            clientUI.display(String.valueOf(getPort()));
        }else if(command.startsWith("#quit")){
            quit();
        }else if(command.startsWith("#logOff")){
            closeConnection();
        }else if(command.startsWith("#logOn")){
            openConnection();
        }else if(command.startsWith("#setHost")){
            String[] commands = command.split(" ");
            setHost(commands[1]);
        }else if(command.startsWith("#getHost")){
            clientUI.display(getHost());
        }else if(command.startsWith("#setPort")){
            String[] commands = command.split(" ");
            int port = Integer.parseInt(commands[1]);
            setPort(port);  
        }else{
            clientUI.display("Unknown command");
        }
    }catch(IOException e){
        clientUI.display
        ("Could not send message to server. Terminating client.");
    }
      
  }
  
  /**
   * This method terminates the client.
   */
  public void quit()
  {
    try
    {
      closeConnection();
    }
    catch(IOException e) {}
    
    System.exit(0);
  }
}
//End of ChatClient class
