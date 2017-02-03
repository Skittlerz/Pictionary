package simplepaint;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;


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
  final public static int DEFAULT_PORT = 5556;
  
  //Instance variables **********************************************
  
  /**
   * The instance of the client that created this ConsoleChat.
   */
  ChatClient client;
  //elements used for drawing window
  DrawingPanel dp;
  JButton load, red, green, blue, black, clear, send;
  JTextPane displayText;
  StyledDocument doc;
  SimpleAttributeSet keyWord;
  JTextField input;
  Label lblTimerCount, lblScoreCount;
  Pictionary game;
  
  private final static String newline = "\n";
  
  //Used for setting text color of username
  //the text color should be different for each user
  //thus we use static values. Instance will be used to
  //call a position in the TAG array
  static Color[] TAG = {Color.darkGray, Color.BLUE, Color.GREEN, Color.MAGENTA, 
  Color.ORANGE, Color.CYAN, Color.PINK};
  int num;
  
  
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
        Random r = new Random();
        num = r.nextInt(TAG.length-1);
        init();
        client= new ChatClient(host,port,user,room,this); 
    } 
    catch(IOException exception) 
    {
      System.out.println("Error: Can't setup connection!"
                + " Terminating client.");
      System.out.println(exception);
      System.exit(1);
    }
  }
  /**
   * Initialize GUI 
   */
  public void init()
  {
      
      setSize(1000, 700);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
      //Set color, clear and send image buttons
      JPanel top = new JPanel();
      //top.setLayout(new BoxLayout(top, BoxLayout.X_AXIS));
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
      
      //set the timer label
      Label lblTimer = new Label("Timer:");
      lblTimer.setFont(new Font("SansSerif", Font.PLAIN, 18));
      lblTimerCount = new Label("1:00");
      lblTimerCount.setFont(new Font("SansSerif", Font.PLAIN, 18));
      
      //set the score label
      Label lblScore = new Label("Score:");
      lblScore.setFont(new Font("SansSerif", Font.PLAIN, 18));
      lblScoreCount = new Label("100");
      lblScoreCount.setFont(new Font("SansSerif", Font.PLAIN, 18));
      
      load.addActionListener(this);
      black.addActionListener(this);
      red.addActionListener(this);
      green.addActionListener(this);
      blue.addActionListener(this);
      clear.addActionListener(this);
        
      top.add(lblTimer);
      top.add(lblTimerCount);
      top.add(Box.createRigidArea(new Dimension(35,0)));
      top.add(black);
      top.add(red);
      top.add(green);
      top.add(blue);
     // top.add(Box.createHorizontalGlue());
      top.add(clear);
      top.add(load);
      top.add(Box.createRigidArea(new Dimension(130,0)));
      top.add(lblScore);
      top.add(lblScoreCount);
    
      
      JPanel controls = new JPanel();
      controls.setLayout(new BoxLayout(controls, BoxLayout.Y_AXIS));
      
      //add text display
      displayText = new JTextPane();
      //DImensions(width, height)
      displayText.setPreferredSize(new Dimension(400,540));
      doc = displayText.getStyledDocument();
      keyWord = new SimpleAttributeSet();
      displayText.setEditable(false); // set textArea non-editable
      JScrollPane scroll = new JScrollPane(displayText);
      scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
      //add input text box
      input = new JTextField(5);
      input.addActionListener(this);
      //add button to send message
      send = new JButton("Send");
      send.addActionListener(this);
      //use seperate panel for input and send so they are side by side
      JPanel textInput = new JPanel();
      textInput.setLayout(new BoxLayout(textInput,BoxLayout.X_AXIS));
      textInput.add(input);
      textInput.add(send);
      //add components to boxlayout panel
      controls.add(scroll);
      controls.add(Box.createRigidArea(new Dimension(400,30)));
      //controls.add(enter);
      controls.add(textInput);
        
      dp = new DrawingPanel();
      dp.setBackground(Color.WHITE);

      add(top, BorderLayout.NORTH);
      add(controls, BorderLayout.EAST);
      add(dp, BorderLayout.CENTER);
      setVisible(true);
      setTitle("Pictionary - JSE");
  }

  @Override
    public void actionPerformed(ActionEvent ae) {
        //gets button that was clicked and executes appropriate action
        if(ae.getSource() == load)
        {
            BufferedImage bi = dp.getScreenShot();
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
        else if(ae.getSource() == send || ae.getSource() == input)
        {
            if(!validateTextBox())
            {   
                
                if(input.getText().startsWith("#getTarget") ||
                        input.getText().startsWith("#getCategories") ||
                        input.getText().startsWith("#guess"))
                {
                   
                    handlePictionary();
                }else
                {
                    handleMessage();
                }
                
            }
        }
       
    }
  
  //Instance methods ************************************************
    
   public void handleMessage(){
       
       Message msg = new Message();
       msg.setMessage(input.getText());
       msg.setTag(TAG[num]);
       client.handleMessageFromClientUI(msg);
       input.setText("");
   }
   
   public void handlePictionary(){
       
       if (this.game == null){
           display("No active game in progress. Type #play to start.");
           input.setText("");
       }else{
           game.setMessage(input.getText());
           System.out.println("handlePictionary: "+game.getMessage());
           input.setText("");
           client.handleMessageFromClientUI(game);
           
       }
       
   }
  
    public boolean validateTextBox(){
        return (input.getText().equals(""));
    }
    
    public void displayCountdown(String count){
        
        lblTimerCount.setText(count);
    }
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
    //displays in console text area 
    try{
    doc.insertString(doc.getLength(),message+newline, keyWord );
    }catch(Exception e){
        System.out.println(e);
    }
  }
  
  public void display(ImageIcon ii){
      DrawingPanel.loadDrawing(ii);
  }
  
  public void displayInput(String message){
      try{
        doc.insertString(doc.getLength(),">" + message+newline, keyWord );
      }catch(Exception e){
          System.out.println(e);
      }
      
  }
  
  public void display(Message m){
      try{
        StyleConstants.setForeground(keyWord, m.getTag());
        doc.insertString(doc.getLength(),m.getUserName()+": ", keyWord );
        StyleConstants.setForeground(keyWord, Color.BLACK);
        doc.insertString(doc.getLength(),m.getMessage()+newline, keyWord );
      }catch(Exception e){
          System.out.println(e);
      }
  }
  
  public void display(ArrayList<String> al)
  {
      try
      {
          if(al.size() > 1)
          {
            for(int i = 0; i < al.size();i++){
                StyleConstants.setForeground(keyWord, Color.BLACK);
                doc.insertString(doc.getLength(),(String.valueOf(i+1)+". "+al.get(i)+newline), keyWord );
            }
          }else
          {
               StyleConstants.setForeground(keyWord, Color.BLACK);
               doc.insertString(doc.getLength(),"Target is ", keyWord );
               StyleConstants.setForeground(keyWord, Color.BLUE);
               doc.insertString(doc.getLength(),(al.get(0)+newline), keyWord );
          }
      }catch(Exception e)
      {
          System.out.println(e.toString());
      }
  }
  
  public void display(Pictionary p){      
      this.game = p;
      try
      {
        if(!p.getMessage().equals(""))
        {   
            StyleConstants.setForeground(keyWord, Color.BLUE);
            doc.insertString(doc.getLength(),p.getMessage()+newline, keyWord);
        }
        if(!p.getAnswer().equals("")){
            StyleConstants.setForeground(keyWord, Color.BLUE);
            doc.insertString(doc.getLength(),p.getAnswer()+newline, keyWord);
        }
      }catch(Exception e)
      {
          System.out.println(e.toString());
      }
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
