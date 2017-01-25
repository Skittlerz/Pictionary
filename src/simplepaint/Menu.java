/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplepaint;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.BoxLayout;
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
           
           launchMultiplayer("localhost", 5555, "Amanda", "common");
        }
        else if(ae.getSource() == multiplayer)
        {
           launchMultiplayer("localhost", 5555, "Amanda", "common");
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
      
      setSize(500, 500);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
      //Set color, clear and send image buttons
      JPanel south = new JPanel();
      
      singlePlayer = new JButton("Single Player Mode");
      singlePlayer.setBackground(Color.WHITE);
      singlePlayer.setForeground(Color.BLACK);
      
      multiplayer = new JButton("Multiplayer Mode");
      multiplayer.setBackground(Color.WHITE);
      multiplayer.setForeground(Color.BLACK);
      
      singlePlayer.addActionListener(this);
      multiplayer.addActionListener(this);
      
      south.add(singlePlayer);
      south.add(multiplayer);
      
      
      add(south, BorderLayout.SOUTH);
     
      setVisible(true);
      setTitle("Pictionary - Janky Student Edition");
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
