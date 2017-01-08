package simplepaint;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 * This class constructs the UI for a chat client.  It implements the
 * chat interface in order to activate the display() method.
 * Warning: Some of the code here is cloned in ServerConsole 

 */
public class ClientConsole extends JFrame implements ActionListener, ChatIF 
{
  //Class variables *************************************************
  
  /**
   * The default port to connect on.
   */
  final public static int DEFAULT_PORT = 5555;
  
  //Instance variables **********************************************
  
  /**
   * The instance of the client that created this ConsoleChat.
   */
  ChatClient client;
  //elements used for drawing window
  DrawingPanel dp;
  JButton load, red, green, blue, black, clear;

  
  //Constructors ****************************************************

  /**
   * Constructs an instance of the ClientConsole UI.
   *
   * @param host The host to connect to.
   * @param port The port to connect on.
   */
  public ClientConsole(String host, int port) 
  {
    try 
    {
      client= new ChatClient(host, port, this);
      
      setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Set color, clear and send image buttons
        JPanel top = new JPanel();
        black = new JButton("Black");
        black.setBackground(Color.BLACK);
        black.setForeground(Color.WHITE);
        red = new JButton("Red");
        red.setBackground(Color.RED);
        red.setForeground(Color.WHITE);
        green = new JButton("Green");
        green.setBackground(Color.GREEN);
        green.setForeground(Color.WHITE);
        blue = new JButton("Blue");
        blue.setBackground(Color.BLUE);
        blue.setForeground(Color.WHITE);
        clear = new JButton("Clear");
        clear.setBackground(Color.DARK_GRAY);
        clear.setForeground(Color.WHITE);
        load = new JButton("Send Image");
        
        load.addActionListener(this);
        black.addActionListener(this);
        red.addActionListener(this);
        green.addActionListener(this);
        blue.addActionListener(this);
        clear.addActionListener(this);
        
        top.add(black);
        top.add(red);
        top.add(green);
        top.add(blue);
        top.add(clear);
        top.add(load);
        
        dp = new DrawingPanel();
        dp.setBackground(Color.WHITE);

        add(top, BorderLayout.NORTH);
        add(dp, BorderLayout.CENTER);
        setVisible(true);
    } 
    catch(IOException exception) 
    {
      System.out.println("Error: Can't setup connection!"
                + " Terminating client.");
      System.exit(1);
    }
  }

  @Override
    public void actionPerformed(ActionEvent ae) {
        //gets button that was clicked and executes appropriate action
        if(ae.getSource() == load)
        {
            BufferedImage bi = dp.getScreenShot();
            System.out.println(bi);
            client.handleImageFromClientUI(bi);
        }
        else if(ae.getSource() == black)
        {
            dp.setDrawColor(Color.BLACK);
        }
        else if(ae.getSource() == red)
        {
            dp.setDrawColor(Color.RED);
        }
        else if(ae.getSource() == green)
        {
            dp.setDrawColor(Color.GREEN);
        }
        else if(ae.getSource() == blue)
        {
            dp.setDrawColor(Color.BLUE);
        }
        else if(ae.getSource() == clear)
        {
            dp.clearDrawing();
        }
       
    }
  
  //Instance methods ************************************************
  
  /**
   * This method waits for input from the console.  Once it is 
   * received, it sends it to the client's message handler.
   */
  public void accept() 
  {
     
    try
    {
        BufferedReader fromConsole = 
        new BufferedReader(new InputStreamReader(System.in));
        String message;
        
      while (true) 
      {
        message = fromConsole.readLine();
        client.handleMessageFromClientUI(message);
      }
    } 
    catch (Exception ex) 
    {
      System.out.println
        ("Unexpected error while reading from console!");
    }
  }

  /**
   * This method overrides the method in the ChatIF interface.  It
   * displays a message onto the screen.
   *
   * @param message The string to be displayed.
   */
  public void display(String message) 
  {
    System.out.println("> " + message);
  }
  
  public void display(ImageIcon ii){
      DrawingPanel.loadDrawing(ii);
  }
  
  //Class methods ***************************************************
  
  /**
   * This method is responsible for the creation of the Client UI.
   *
   * @param args[0] The host to connect to.
   */
  public static void main(String[] args) 
  {
    String host = "";
    int port = 0;  //The port number

    try
    {
      host = args[0];
    }
    catch(ArrayIndexOutOfBoundsException e)
    {
      host = "localhost";
    }
    
    try
    {
        port = Integer.parseInt(args[1]);
    }catch(ArrayIndexOutOfBoundsException e){
        port = DEFAULT_PORT;
    }
    
    ClientConsole chat= new ClientConsole(host, port);
    chat.accept();  //Wait for console data
  }
}
//End of ConsoleChat class
