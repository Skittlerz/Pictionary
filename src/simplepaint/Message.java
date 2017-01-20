/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplepaint;

import java.awt.Color;
import javax.swing.ImageIcon;

/**
 *
 * @author braun1792
 */
public class Message {
    
    String message = null;
    ImageIcon image = null;
    Color tag = null;
    String userName = null;
    
    
   Message(){}
   
   public void setMessage(String msg){
       this.message = msg;
   }
   
   public String getMessage(){
       return this.message;
   }
   
   public void setImage(ImageIcon ii){
       this.image = ii;
   }
   
   public boolean isImage(){
       return (image != null);
   }
   
   public ImageIcon getImage(){
       return this.image;
   }
   
   public void setTag(Color c){
       this.tag = c;
   }
   
   public Color getTag(){
       return this.tag;
   }
   
   public void setUserName(String name){
       this.userName = name;
   }
}
