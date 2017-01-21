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
  
   public ChatClient(String host, int port, String userName, ChatIF clientUI) 
    throws IOException 
  {
    super(host, port); //Call the superclass constructor
    this.clientUI = clientUI;
    openConnection();
    Message user = new Message("#login " + userName);
    sendToServer(user);
  }
   
  public ChatClient(String host, int port, String userName, String room, ChatIF clientUI) 
    throws IOException 
  {
    super(host, port); //Call the superclass constructor
    this.clientUI = clientUI;
    openConnection();
    Message join = new Message("#join " + room);
    sendToServer(join);
    Message user = new Message("#login " + userName);
    sendToServer(user);
  }

  
  //Instance methods ************************************************
    
  /**
   * This method handles all data that comes in from the server.
   *
   * @param msg The message from the server.
   */
  public void handleMessageFromServer(Object msg) 
  {
    if(msg instanceof Message)
    {    
       clientUI.display(((Message)msg));
    }else if(msg instanceof ImageIcon){
       clientUI.display(((ImageIcon)msg));
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
  
  public void handleMessageFromClientUI(Object msg)
  {
    try
    {
        if(msg instanceof Message)
        {
            //differentiate between local and server commands
            if(((Message)msg).getMessage().charAt(0) == '#')
            {
                handleCommandFromClientUI(msg);
            }else
            {
                sendToServer(msg);
            }
            
        }
    }
    catch(IOException e)
    {
      clientUI.display("Could not send message to server. Terminating client.");
      quit();
    }
    
  }
  /**
   * Method called to handle commands. 
   * Commands must begin with #
   * @param command the command sent by user.
   */
  public void handleCommandFromClientUI(Object command){
     
    try{
        if(command instanceof Message)
        {
            if(((Message)command).getMessage().startsWith("#getPort"))
            {
                clientUI.display(String.valueOf(getPort()));
            }else if(((Message)command).getMessage().startsWith("#quit"))
            {
                quit();
            }else if(((Message)command).getMessage().startsWith("#logOff"))
            {
                closeConnection();
            }else if(((Message)command).getMessage().startsWith("#logOn"))
            {
                openConnection();
            }else if(((Message)command).getMessage().startsWith("#setHost"))
            {
                if(!isConnected())
                {
                    String[] commands = ((Message)command).getMessage().split(" ");
                    setHost(commands[1]);
                }else
                {
                    clientUI.display("Error: Must logoff to change host.");
                }
            }else if(((Message)command).getMessage().startsWith("#getHost"))
            {
                clientUI.display(getHost());
            }else if(((Message)command).getMessage().startsWith("#setPort"))
            {
                if(!isConnected()){
                    String[] commands = ((Message)command).getMessage().split(" ");
                    int port = Integer.parseInt(commands[1]);
                    setPort(port); 
                }else{
                    clientUI.display("Error: Must logoff to change port.");
                }
            }else if(((Message)command).getMessage().startsWith("#login"))
            {
                try{
                    sendToServer(command);
                }catch(IOException e){
                    clientUI.display("Could not send command to server. Terminating client.");
                    e.printStackTrace();
                    quit();
                }
            }
            else
            {
                sendToServer(command);
            }
        }
    }catch(IOException e){
        clientUI.display("Could not send message to server. Terminating client.");
    }
      
  }
  
  public void handleImageFromClientUI(BufferedImage bi){
      buffImage = new ImageIcon(bi);
      try{
         
        sendToServer(buffImage);
      }catch(IOException ioe){
          System.out.println(ioe);
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
