/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplepaint;

/**
 *
 * @author acb
 */
public class TimeCounter {
    
    Thread timeThread;
    
    TimeCounter()
    {
        timeThread = new Thread();
    }
    
    public void start(int seconds)
    {
        
        for (int i = seconds; i>= 0; i--){
            
            try{
                timeThread.sleep(1000);
            }catch(InterruptedException ie){
                System.out.println(ie);
            }
        }
    }
    
    
}
