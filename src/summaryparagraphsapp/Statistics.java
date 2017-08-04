package summaryparagraphsapp;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
*
* @author Ngo Phuong Tuan
* Edited by: Nguyen Viet Long
* thống kê số lượng bài báo theo keyword
*/
public class Statistics {
   private String keyword;
   
   private ArrayList<Host> hosts = new ArrayList<>();
   private ArrayList<Time> times = new ArrayList<>();
   
   Statistics(){
	   keyword = "";
	   ArrayList<Host> hosts = new ArrayList<>();
	   ArrayList<Time> times = new ArrayList<>();
   }
   
   // Get statistics - by tuannp - edited by longnv
   void getStat(String keyword) throws ClassNotFoundException, SQLException {
       this.keyword = keyword;
       keyword = "%" + keyword + "%";//Edited by Nguyen Viet Long
       //lay id cua keyword
       Connection connection = ConnectionUtils.getMyConnection();
       String sql = "SELECT id, name FROM keyword WHERE name LIKE ?";
       PreparedStatement statement = connection.prepareStatement(sql);
       statement.setString(1, keyword);
       ResultSet rs = statement.executeQuery();
	
       while(rs.next()) {
	       int keywordId = rs.getInt("id");
	       String keywordName = rs.getString("name");
	       //các bài viết có keyword liên quan sẽ là các bài viết có id thuốc câu lệnh "SELECT websiteId FROM topicweb where topicId = " + topicId
	
		//thống kê theo host
	
		//liệt kê các host
		sql = "SELECT DISTINCT host FROM history_website WHERE id IN (SELECT websiteId FROM keyword_web WHERE keywordId = " + keywordId + ")";
		PreparedStatement statement1 = connection.prepareStatement(sql);
		ResultSet rs1 = statement1.executeQuery();
		
	       while (rs1.next()) {
			String h = rs1.getString("host");
			sql = "SELECT * FROM history_website WHERE host LIKE ? AND id IN (SELECT websiteId FROM keyword_web where keywordId = " + keywordId + ")";
			PreparedStatement statement2 = connection.prepareStatement(sql);
			statement2.setString(1, h);
	               ResultSet rs2 = statement2.executeQuery();
	               
	               int count = 0;
	               while(rs2.next()) {
	                   count++;
	               }
	               System.out.println(h + ": " + count);
			Host host = new Host(h,count,keywordName); // Edited by Nguyen Viet Long (add 1 variable)
	               hosts.add(host);
		}
	       
	       //thống kê theo thời gian
	
		//liệt kê các mốc thời gian
	       
		String newsql = "SELECT DISTINCT date FROM history_website WHERE id IN (SELECT websiteId FROM keyword_web WHERE keywordId = " + keywordId + ")";
		
		PreparedStatement statement3 = connection.prepareStatement(newsql);
		ResultSet rs3 = statement3.executeQuery();
		while (rs3.next()) {
			Date t = rs3.getDate("date");
			
			newsql = "SELECT * FROM history_website WHERE date LIKE ? AND id IN (SELECT websiteId FROM keyword_web WHERE keywordId = " + keywordId + ")";
			PreparedStatement statement4 = connection.prepareStatement(newsql);
			statement4.setDate(1, t);
	               ResultSet rs4 = statement4.executeQuery();
	               
	               int count1 = 0;
	               while(rs4.next()) {
	                   count1++;
	               }
	               System.out.println(t + ": " + count1);
			Time time = new Time(t,count1, keywordName); // Edited by Nguyen Viet Long (added 1 variable)
	               times.add(time);
		}
       }
   }
   
   ArrayList<Host> getHosts() {
	   return hosts;
   }
   
   ArrayList<Time> getTimes(){
	   return times;
   }
   
}
