package simplepaint;



import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.ImageIcon;
import javax.swing.Timer;


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
  final public static int DEFAULT_PORT = 5556;
  
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
    if(msg instanceof Message)
    {
       
        Message m = (Message)msg;
        
        if(m.getMessage().startsWith("#"))
        {
            handleServerCommand(msg,client);
        }else
        {
            //Must use the Client Console constructor that initializes room
            //otherwise this method will not work here b/c room will not be set
            ((Message)msg).setUserName(client.getInfo("userName").toString());
            this.sendToRoom(msg, client);
        }
    }else if (msg instanceof ImageIcon)
    {
        this.sendToRoom(msg, client);
    }
  }
    
  public void handleServerCommand(Object msg, ConnectionToClient client){
      
        if(msg instanceof Message)
        {
          
            String message = ((Message)msg).getMessage();
      
            if(message.startsWith("#login"))
            {
                String userName = message.split(" ")[1];
                client.setInfo("userName", userName);
                Message m = new Message();
                m.setTag(Color.BLACK);
                m.setUserName("Server");
                m.setMessage(userName + " has arrived!");
                this.sendToAllClients(m);
                
            }else if(message.startsWith("#w"))
            {
                ((Message)msg).setUserName(client.getInfo("userName").toString());
                String target = message.split(" ")[1];
                sendToAClient(msg,target);
                
            }else if(message.startsWith("#yell"))
            {
                ((Message)msg).setUserName(client.getInfo("userName").toString());
                message = message.substring(message.indexOf(" "));
                ((Message) msg).setMessage(message);
                this.sendToAllClients(msg);
                
            }else if(message.startsWith("#join"))
            {
                String room = message.split(" ")[1];
                client.setInfo("room", room);
                
            }else if(message.startsWith("#intercom"))
            {
                ((Message)msg).setUserName(client.getInfo("userName").toString());
                String room = message.substring(message.indexOf(" ")+1,message.indexOf(" ",
                        message.indexOf(" ")+1));
                message = message.substring(message.indexOf(room)+room.length());
                ((Message)msg).setMessage(message);
                sendToRoom(msg, room);
                
            }else if(message.startsWith("#start"))
            {   
            }else if(message.startsWith("#locate"))
            {   
                String targetUser = message.substring(message.indexOf(" ")+1);
                System.out.println("Locate method: "+ targetUser);
                findUser(client, targetUser);
            }else
            {
                try
                {
                    client.sendToClient("Unknown Command");
                }catch(Exception ex)
                {
                    System.out.println("Failed to send unknown command message.");
                }
            }
        }
  }
  
    
  //Method used for private messages
  public void sendToAClient(Object msg, String target)
  { 
    if(msg instanceof Message)
    {
    
        String message = ((Message)msg).getMessage();
        String privateMessage = message.substring(message.indexOf(" ", message.indexOf(" ") + 1), message.length());
        ((Message)msg).setMessage(privateMessage);
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
                    clientProxy.sendToClient(msg);
                }catch(Exception ex)
                {
                    System.out.println("failed to send private message");
                }
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
 
    for (int i=0; i<clientThreadList.length; i++)
    {
        ConnectionToClient clientProxy = (ConnectionToClient)clientThreadList[i];
       
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
  //Overloaded method
  //Used to communicate with all clients in the same room
  //Takes string room = room to send message to  
   public void sendToRoom(Object msg, String room)
  {  
    Thread[] clientThreadList = getClientConnections();
   
    for (int i=0; i<clientThreadList.length; i++)
    {
        ConnectionToClient clientProxy = (ConnectionToClient)clientThreadList[i];
       
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
   
  public void findUser(ConnectionToClient client, String target)
  { 
        String room = ""; 
        Message m = new Message();
        Boolean found = false;
        
        
        m.setUserName("Server");
        m.setTag(Color.BLACK);
       
        Thread[] clientThreadList = getClientConnections();

        outerloop:
        for (Thread clientThreadList1 : clientThreadList) {
                
            ConnectionToClient clientProxy = (ConnectionToClient) clientThreadList1;
            
            if(clientProxy.getInfo("userName").equals(target))
                {
                    room = clientProxy.getInfo("room").toString();
                    found = true;
                    break outerloop;
                }
        }
            
        if(found)
        {
            m.setMessage(target + " was found in room " + room);
        }else
        {
            m.setMessage(target + " is not currently on the server");
        }
            
        try
        {
            client.sendToClient(m);
        }catch(Exception e){
            System.out.println("Failed to look for user.");
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
  
  class countdownTask implements ActionListener {
        
        private int counter = 0;
        @Override
        public void actionPerformed(ActionEvent e) {
            
            //label.setText(""+counter++);

            if (counter == 10){}
                //timer.removeActionListener(this);
        }
      
    }
}
//End of EchoServer class
