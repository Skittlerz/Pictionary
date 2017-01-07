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

    public TestClass() {
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel top = new JPanel();
        JButton load = new JButton("Send Image");
        load.addActionListener(this);
        top.add(load);

        dp = new DrawingPanel();
        dp.setBackground(Color.CYAN);

        add(top, BorderLayout.NORTH);
        add(dp, BorderLayout.CENTER);
        setVisible(true);
    }

    public static void main(String[] args) {
        new TestClass();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        BufferedImage bi = dp.getScreenShot();
        //return bi;
        //dp.loadDrawing(bi);
    }
    
}
