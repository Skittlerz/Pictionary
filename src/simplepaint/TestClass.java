/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplepaint;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author acb
 */

public class TestClass extends JFrame implements ActionListener {

    DrawingPanel dp;
    JButton load, black, red, blue, green, clear;

    public TestClass() {
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

    public static void main(String[] args) {
        new TestClass();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        
        if(ae.getSource() == load)
        {
          BufferedImage bi = dp.getScreenShot();
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
    }
    
}
