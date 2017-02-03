/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplepaint;

import java.io.Serializable;
import javax.swing.ImageIcon;

/**
 *
 * @author braun1792
 */
public class Pictionary implements Serializable {
    private String activePlayer;
    private String[] otherPlayers;
    private ImageIcon drawing;
    private String answer;
    private String message;
    private Boolean win = false;
    private static final long serialVersionUID = 689412375;
    
    public Pictionary(){}
    
    public Pictionary(String p1){
        setActivePlayer(p1);
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
    public String[] getOtherPlayers() {
        return otherPlayers;
    }

    /**
     * @param otherPlayers the otherPlayers to set
     */
    public void setOtherPlayers(String[] otherPlayers) {
        this.otherPlayers = otherPlayers;
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
    
}
