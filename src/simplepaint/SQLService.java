/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplepaint;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


/**
 *
 * @author braun1792
 */
public class SQLService {
    
    private String SQLCommand;
    FileWriter writer;
    BufferedWriter bufferedWriter;
    String timeStamp;
    ArrayList<String> results;
    
    
 
    public SQLService(){
        
        
	results = new ArrayList<>();
        
        try{
            writer = new FileWriter("MyLog.txt", true);
            bufferedWriter = new BufferedWriter(writer);
        }catch(Exception e){
            System.out.println(e.toString());
        }
    }
    
    public ArrayList<String> getSQLResults(String field, String criteria){
        
        
        //String field;
        //String criteria;
        
        //searches request string for field value and stores it (SQL column name)
        //field = request.substring(request.indexOf("Field=")+6,request.indexOf("Criteria="));
        //criteria = request.substring(request.indexOf("Criteria="+9));
       
        
        SQLCommand = "SELECT Target FROM Answers WHERE " + field + " = " + criteria;
        
        doWork("Target");
        
        try{
            //log sql query
            timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
            bufferedWriter.write(SQLCommand);
            bufferedWriter.newLine();
            bufferedWriter.write("Time: "+ timeStamp);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }catch(Exception e){
            System.out.println(e.toString());
        }
        
        return results;
    }
    
    public ArrayList<String> getCategories(){
        
         SQLCommand = "SELECT CategoryName FROM Categories";
         
         doWork("CategoryName");
         
          try{
            //log sql query
            timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
            bufferedWriter.write(SQLCommand);
            bufferedWriter.newLine();
            bufferedWriter.write("Time: "+ timeStamp);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }catch(Exception e){
            System.out.println(e.toString());
        }
         
         return results;
    }
    
    public void doWork(String columnName){
        
        //executes SQL statement and returns result set
        Statement stmt=null; 
        //stores table of data (database result set)
        ResultSet rset=null; 
        //stores info about the types and properties of columns
        ResultSetMetaData rsmd=null;
        //session with specific database
        Connection conn=null;
        
        try {
            //set up connection to database
            //oracledriver implements driver interface, allows it to use registerDriver()
            //JDBC drivers must be registered before you can establish a connection
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

            //parameters: JDBC driver, name of database, userID, password
            conn = DriverManager.getConnection("jdbc:oracle:thin:@bisoracle.siast.sk.ca:1521:ACAD","cistu026","databasefun");
            
            //("jdbc:oracle:thin:@bisoracle.siast.sk.ca:1521:ACAD","cistu026","databasefun");
            stmt = conn.createStatement();
            rset = stmt.executeQuery(SQLCommand);
            rsmd = rset.getMetaData();
            
            //get column count from the resultset metadata
            int columnCount = rsmd.getColumnCount();
            
            //parse through the entire result set
            while (rset.next()){
                
                results.add(rset.getString(columnName));  
                System.out.println(rset.getString(columnName));
            }
            
            //close all open connections and what not, no memory leaks here!
            rset.close();
            stmt.close();
            conn.close();
            bufferedWriter.close();
            
        }catch(Exception e){
            
            System.out.println("doWork(): "+e.toString());
            
            try{
                //logs any errors in the do work method
                timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
                bufferedWriter.write(e.toString());
                bufferedWriter.newLine();
                bufferedWriter.write("Time: "+ timeStamp);
                bufferedWriter.newLine();

                bufferedWriter.close();
                
            }catch (Exception ex){
                System.out.println(ex.toString());
            }
        }
        
    }

}
