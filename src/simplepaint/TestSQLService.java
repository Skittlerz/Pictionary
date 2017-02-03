/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplepaint;

import java.util.ArrayList;

/**
 *
 * @author braun1792
 */
public class TestSQLService {
    
    public TestSQLService(){
        
        
    }
    
    
    public static void main(String[] args)
    {
        
        SQLService ss = new SQLService();
        ArrayList<String> res;
        res = ss.getCategories();
        System.out.println(res);
        ArrayList<String> res2;
        res2 = ss.getTargetByCategory(1);
        System.out.println(res2);
        
        String message ="#getTarget 1";
        int category;
        System.out.println(message.substring(message.indexOf(" ")+1));
        category = Integer.parseInt(message.substring(message.indexOf(" ")+1));
        res = ss.getTargetByCategory(category);
        System.out.println(res);
        System.out.println(res.size());
    }
    
}
