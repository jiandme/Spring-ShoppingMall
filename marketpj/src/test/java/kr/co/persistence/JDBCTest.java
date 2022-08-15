package kr.co.persistence;

import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Test;

public class JDBCTest {
	 static { 
	        try { 
	            Class.forName("oracle.jdbc.driver.OracleDriver"); 
	        } catch(Exception e) { 
	            e.printStackTrace(); 
	        } 
	    } 
	    
	    @Test 
	    public void testConnection() { 
	        try(Connection con = DriverManager.getConnection( 
	                "jdbc:oracle:thin:@192.168.1.178:1521/xepdb1", 
	                "HANTENG", 
	                "123890")){ 
	            System.out.println(con); 
	        } catch (Exception e) { 
	            fail(e.getMessage()); 
	        } 
	    
	    }    
}
