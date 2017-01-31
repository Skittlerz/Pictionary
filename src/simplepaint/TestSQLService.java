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
    }
    
}
