/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplepaint;

import java.awt.Color;

/**
 *
 * @author braun1792
 */
public class Test {
    
    static Color[] TAG = {Color.darkGray, Color.BLUE, Color.GREEN, Color.MAGENTA, 
  Color.ORANGE, Color.CYAN, Color.PINK};
    
    
    public static void main(String[] args){
        String message = "#intercom default  this is a test";
        
        String room = message.substring(message.indexOf(" "),message.indexOf(" ",message.indexOf(" ")+1));
        message = message.substring(message.indexOf(room)+room.length());
        
        System.out.println(room);
        System.out.println(message);
        System.out.println(TAG.length);
    }
    
}
