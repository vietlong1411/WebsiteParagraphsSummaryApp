package summaryparagraphsapp;

/**
 * Author: Nguyen Viet Long
 * Get connection to database
 */

import java.sql.Connection;
import java.sql.SQLException;

import org.jfree.ui.RefineryUtilities;
 
public class ConnectionUtils {
 
  public static Connection getMyConnection() throws SQLException,
          ClassNotFoundException {
	  // Use MySQL database
      return MySQLConnUtils.getMySQLConnection();
  }
 
  //
  // Test Connection ...
  //
  public static void main(String[] args) throws SQLException,
          ClassNotFoundException {
 
      /*System.out.println("Get connection ... ");
 
      // Get connection to database
      Connection conn = ConnectionUtils.getMyConnection();
 
      System.out.println("Get connection " + conn);
 
      System.out.println("Done!");*/

  }
 
}