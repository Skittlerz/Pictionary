/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplepaint;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.text.SimpleAttributeSet;
import static simplepaint.ClientConsole.DEFAULT_PORT;
import static simplepaint.ClientConsole.TAG;

/**
 *
 * @author braun1792
 */
public class Menu extends JFrame implements ActionListener{
    
    JButton singlePlayer;
    JButton multiplayer;
    
 
    Menu(){
        init();
    }
    
    
    
     @Override
    public void actionPerformed(ActionEvent ae) {
        //gets button that was clicked and executes appropriate action
        if(ae.getSource() == singlePlayer)
        {
           
           launchMultiplayer("localhost", 5556, "Amanda", "common");
        }
        else if(ae.getSource() == multiplayer)
        {
           launchMultiplayer("localhost", 5556, "Amanda", "common");
        }
       
    }
    
    
    public void init(){
      
      try 
      {
          for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) 
          {
              if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
       } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
       }
      
      setSize(850, 740);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      //set the title screen image
      JPanel center = new JPanel();
      center.setSize(800,600);
      URL imageUrl = this.getClass().getResource("/Images/TitleScreen.png");
      ImageIcon icon = new ImageIcon(imageUrl);
      JLabel label = new JLabel();
      label.setIcon(icon);
      center.add(label);
        
      //Set the single player and multiplayer buttons
      JPanel south = new JPanel();
      
      singlePlayer = new JButton("Single Player Mode");
      singlePlayer.setPreferredSize(new Dimension(170,70));
      singlePlayer.setFont(new Font("SansSerif", Font.BOLD, 14));
      singlePlayer.setBackground(Color.DARK_GRAY);
      singlePlayer.setForeground(Color.WHITE);
      
      multiplayer = new JButton("Multiplayer Mode");
      multiplayer.setPreferredSize(new Dimension(170,70));
      multiplayer.setFont(new Font("SansSerif", Font.BOLD, 14));
      multiplayer.setBackground(Color.DARK_GRAY);
      multiplayer.setForeground(Color.WHITE);
      
      singlePlayer.addActionListener(this);
      multiplayer.addActionListener(this);
      
      south.add(singlePlayer);
      south.add(Box.createRigidArea(new Dimension(35,0)));
      south.add(multiplayer);
      
      add(center, BorderLayout.CENTER);
      add(south, BorderLayout.SOUTH);
     
      setVisible(true);
      setTitle("Pictionary - JSE");
    }
    
    public void launchMultiplayer(String h, int p, String u, String r){
        
        String host = "";
        int port = 0;  //The port number
        String user;
        String room;
        
        try
        {
          host = h;
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
          host = "localhost";
        }

        try
        {
            port = p;
        }catch(ArrayIndexOutOfBoundsException e){
            port = DEFAULT_PORT;
        }

        try
        {
            user = u;
        }catch(ArrayIndexOutOfBoundsException e)
        {
            user = "ANON";
        }

        try{
            room = r;
        }catch(ArrayIndexOutOfBoundsException e){
            room = "common";
        }
        
        ClientConsole chat= new ClientConsole(host, port, user, room);
        //chat.accept();  //Wait for console data
    }
    
    public static void main(String[] args) 
  {
    
      Menu m = new Menu();
  }
    
}
