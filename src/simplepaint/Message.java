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
public class Message implements java.io.Serializable{
    
    String message;
    Color tag;
    String userName;
    private static final long serialVersionUID = 19870907;
    
    
   Message(){}
   
   Message(String  msg){
       this.message = msg;
   }
   
   public void setMessage(String msg){
       this.message = msg;
   }
   
   public String getMessage(){
       return this.message;
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
   
   public String getUserName(){
       return this.userName;
   }
   
}
