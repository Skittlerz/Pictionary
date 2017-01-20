package simplepaint;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;


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
  JButton load, red, green, blue, black, clear, send;
  JTextArea displayText;
  JTextField input;
  //TODO
  //try System.newline
  private final static String newline = "\n";
  //Used for setting text color of username
  //the text color should be different for each user
  //thus we use static values. Instance will be used to
  //call a position in the TAG array
  static Color[] TAG = {Color.BLUE, Color.RED, Color.GREEN};
  static int INSTANCE = 0;
  
  
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
        init();
        client= new ChatClient(host,port,this);
        INSTANCE++;
    } 
    catch(IOException exception) 
    {
      System.out.println("Error: Can't setup connection!"
                + " Terminating client.");
      System.exit(1);
    }
  }
  
  public ClientConsole(String host, int port, String user) 
  {
    try 
    {   
        init();
        client= new ChatClient(host,port,user,this);   
        INSTANCE++;
    } 
    catch(IOException exception) 
    {
      System.out.println("Error: Can't setup connection!"
                + " Terminating client.");
      System.exit(1);
    }
  }
  //This should be the constructor that is used
  //otherwise there will be errors with sendToRoom()
  //which is the default way to send a message
  public ClientConsole(String host, int port, String user, String room) 
  {
    try 
    {   
        init();
        client= new ChatClient(host,port,user,room,this); 
        INSTANCE++;
    } 
    catch(IOException exception) 
    {
      System.out.println("Error: Can't setup connection!"
                + " Terminating client.");
      System.exit(1);
    }
  }
  /**
   * Initialize GUI 
   */
  public void init(){
      
      setSize(800, 600);
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
      
      JPanel right = new JPanel();
      JPanel controls = new JPanel();
      controls.setLayout(new BoxLayout(controls, BoxLayout.Y_AXIS));
      //add text display
      displayText = new JTextArea(30,30);
      displayText.setEditable(false); // set textArea non-editable
      JScrollPane scroll = new JScrollPane(displayText);
      scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
      //add input text box
      JLabel enter = new JLabel("Send a message:");
      input = new JTextField(10);
      input.addActionListener(this);
      //add button to send message
      send = new JButton("Send");
      send.addActionListener(this);
      //add components to boxlayout panel
      controls.add(scroll);
      controls.add(enter);
      controls.add(input);
      controls.add(send);
        
      dp = new DrawingPanel();
      dp.setBackground(Color.WHITE);

      add(top, BorderLayout.NORTH);
      add(controls, BorderLayout.EAST);
      add(dp, BorderLayout.CENTER);
      setVisible(true);
      setTitle("Pictionary - Janky Student Edition");
  }

  @Override
    public void actionPerformed(ActionEvent ae) {
        //gets button that was clicked and executes appropriate action
        if(ae.getSource() == load)
        {
            BufferedImage bi = dp.getScreenShot();
            ImageIcon ii = new ImageIcon(bi);
            Message msg = new Message();
            msg.setImage(ii);
            System.out.println(bi);
            client.handleMessageFromClientUI(msg);
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
        else if(ae.getSource() == send || ae.getSource() == input)
        {
            Message msg = new Message();
            msg.setMessage(input.getText());
            client.handleMessageFromClientUI(msg);
            input.setText("");
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
        Message msg = new Message();
        msg.setMessage(message);
        client.handleMessageFromClientUI(msg);
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
    System.out.println(">>>>> " + message);
    //displays in console text area 
    System.out.println(displayText);
    displayText.append(message);
  }
  
  public void display(ImageIcon ii){
      DrawingPanel.loadDrawing(ii);
  }
  
  public void displayInput(String message){
      displayText.append(">" + message+newline);
  }
  
  public void display(Message m){
      displayText.append(m.getMessage()+newline);
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
    String user;
    String room;
    
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
    
    try
    {
        user = args[2];
    }catch(ArrayIndexOutOfBoundsException e)
    {
        user = "ANON";
    }
    
    try{
        room = args[3];
    }catch(ArrayIndexOutOfBoundsException e){
        room = "common";
    }
    
    ClientConsole chat= new ClientConsole(host, port, user, room);
    chat.accept();  //Wait for console data
  }
}
//End of ConsoleChat class
