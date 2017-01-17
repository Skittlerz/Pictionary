package simplepaint;



import java.io.*;


/**
 * This class overrides some of the methods in the abstract 
 * superclass in order to give more functionality to the server.

 */
public class EchoServer extends AbstractServer 
{
  //Class variables *************************************************
  
  /**
   * The default port to listen on.
   */
  final public static int DEFAULT_PORT = 5555;
  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   */
  public EchoServer(int port) 
  {
    super(port);
  }

  
  //Instance methods ************************************************
  
  /**
   * This method handles any messages received from the client.
   *
   * @param msg The message received from the client.
   * @param client The connection from which the message originated.
   */
  //This is where we can differentiate between different client requests
  public void handleMessageFromClient
    (Object msg, ConnectionToClient client)
  { 
      if(msg.toString().startsWith("#"))
      {
          handleServerCommand(msg,client);
      }else
      {
          System.out.println("Message received: " + msg + " from " + client);
          String room = client.getInfo("room").toString();
          System.out.println("Hi " + room);
          //this.sendToAllClients(msg);
          //Must use the Client Console constructor that initializes room
          //otherwise this method will not work here b/c room will not be set
          this.sendToRoom(msg, client);
      }
  }
    
  public void handleServerCommand(Object msg, ConnectionToClient client){
      String message = msg.toString();
      
      if(message.startsWith("#login")){
          String userName = message.split(" ")[1];
          client.setInfo("userName", userName);
          this.sendToAllClients(userName + " has arrived!");
      }else if(message.startsWith("#w")){
          String target = message.split(" ")[1];
          sendToAClient(msg,target);
      }else if(message.startsWith("#yell")){
          message = message.split(" ")[1];
          this.sendToAllClients(message);
      }else if(message.startsWith("#join")){
          String room = message.split(" ")[1];
          client.setInfo("room", room);
      }
  }
  //Method used for private messages
  public void sendToAClient(Object msg, String target)
  { 
    String message = msg.toString();
    String privateMessage = message.substring(message.indexOf(" ", message.indexOf(" ") + 1), message.length());
    //privateMessage = privateMessage.substring(privateMessage.indexOf(" "), privateMessage.length());
    //basic structure to communicate with any or all clients
    //similar implementations should go in EchoServer  
    Thread[] clientThreadList = getClientConnections();

    for (int i=0; i<clientThreadList.length; i++)
    {
        ConnectionToClient clientProxy = (ConnectionToClient)clientThreadList[i];
        
        if(clientProxy.getInfo("userName").equals(target))
        {
            try
            {
                clientProxy.sendToClient(privateMessage);
            }catch(Exception ex)
            {
                System.out.println("failed to send private message");
            }
        }
    }
  }
  
  public void sendToRoom(Object msg, ConnectionToClient client)
  { 
    //basic structure to communicate with all clients in the same room
    //similar implementations should go in EchoServer  
    Thread[] clientThreadList = getClientConnections();
    String room = client.getInfo("room").toString();
    //System.out.println(room);
    //String message = msg.toString();
    
    for (int i=0; i<clientThreadList.length; i++)
    {
        ConnectionToClient clientProxy = (ConnectionToClient)clientThreadList[i];
        //boolean yes = clientProxy.getInfo("room").equals(room);
        //System.out.println(yes);
        if(clientProxy.getInfo("room").equals(room)){
            try
            {
                clientProxy.sendToClient(msg);
            }catch(Exception ex)
            {
                System.out.println("failed to send message to room");
            }
        }
    }
  }
    
  /**
   * This method overrides the one in the superclass.  Called
   * when the server starts listening for connections.
   */
  protected void serverStarted()
  {
    System.out.println
      ("Server listening for connections on port " + getPort());
  }
  
  /**
   * This method overrides the one in the superclass.  Called
   * when the server stops listening for connections.
   */
  protected void serverStopped()
  {
    System.out.println
      ("Server has stopped listening for connections.");
  }
  
  //Class methods ***************************************************
  
  /**
   * This method is responsible for the creation of 
   * the server instance (there is no UI in this phase).
   *
   * @param args[0] The port number to listen on.  Defaults to 5555 
   *          if no argument is entered.
   */
  public static void main(String[] args) 
  {
    int port = 0; //Port to listen on

    try
    {
      port = Integer.parseInt(args[0]); //Get port from command line
    }
    catch(Throwable t)
    {
      port = DEFAULT_PORT; //Set port to 5555
    }
	
    EchoServer sv = new EchoServer(port);
    
    try 
    {
      sv.listen(); //Start listening for connections
      System.out.println("listen ended");
    } 
    catch (Exception ex) 
    {
      System.out.println("ERROR - Could not listen for clients!");
      ex.printStackTrace();
    }
  }
}
//End of EchoServer class
