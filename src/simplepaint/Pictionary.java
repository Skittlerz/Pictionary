/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplepaint;

import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 *
 * @author braun1792
 */
public class Pictionary implements Serializable {
    //player that is drawing
    private String activePlayer;
    //players guessing; can be up to 3
    private ArrayList<String> otherPlayers;
    //info to be passed i.e. categories and answers
    private ArrayList<String> info;
    private ImageIcon drawing;
    private String answer;
    private String message;
    private Boolean win;
    private static final long serialVersionUID = 689412375;
    
    public Pictionary()
    {
        otherPlayers = new ArrayList<String>();
        info = new ArrayList<String>();
        activePlayer = "";
        answer = "";
        message = "";
        drawing = null;
        win = false;
    }
    
    public Pictionary(String p1){
        setActivePlayer(p1);
        otherPlayers = new ArrayList<String>();
        info = new ArrayList<String>();
        answer = "";
        message = "";
        drawing = null;
        win = false;
    }

    /**
     * @return the activePlayer
     */
    public String getActivePlayer() {
        return activePlayer;
    }

    /**
     * @param activePlayer the activePlayer to set
     */
    public void setActivePlayer(String activePlayer) {
        this.activePlayer = activePlayer;
    }

    /**
     * @return the otherPlayers
     */
    public ArrayList<String> getOtherPlayers() {
        return otherPlayers;
    }

    /**
     * @param otherPlayers the otherPlayers to set
     */
    public void setOtherPlayers(ArrayList<String> otherPlayers) {
        this.otherPlayers = otherPlayers;
    }
    
    public void addOtherPlayer(String otherPlayer){
        this.otherPlayers.add(otherPlayer);
    }

    /**
     * @return the drawing
     */
    public ImageIcon getDrawing() {
        return drawing;
    }

    /**
     * @param drawing the drawing to set
     */
    public void setDrawing(ImageIcon drawing) {
        this.drawing = drawing;
    }

    /**
     * @return the answer
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * @param answer the answer to set
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
    
    public void setWelcomeMessage(String p1){
        
        setMessage("Let's play janky Pictionary.\n"+p1+" is going to draw the clue."+
              " (Don't mess this up " + p1 + ")\n"+"Here's how to play:\n"+"#getCategories - "+
              "shows categories\n#getTarget [category number] - sets what you have to draw based "+
              "on your chosen category\n#guess [your guess] - use this command to submit a guess.\n");
    }
    /**
     * @return the win
     */
    public Boolean getWin() {
        return win;
    }

    /**
     * @param win the win to set
     */
    public void setWin(Boolean win) {
        this.win = win;
    }

    /**
     * @return the info
     */
    public ArrayList<String> getInfo() {
        return info;
    }

    /**
     * @param info the info to set
     */
    public void setInfo(ArrayList<String> info) {
        this.info = info;
    }
    
}
