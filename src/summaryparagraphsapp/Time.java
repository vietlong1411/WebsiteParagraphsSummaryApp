package summaryparagraphsapp;

import java.sql.Date;

/**
*
* @author Ngo Phuong Tuan
*/
public class Time {
   private Date time;
   private int count;
   private String keyword;
   
   Time(Date t, int count, String keyword) {
       this.time = t;
       this.count = count;
       this.keyword = keyword;
   }
   
   void setTime(Date _time) {
	   time = _time;
   }
   
   void setCount(int _count) {
	   count = _count;
   }
   
   void setKeyword(String _keyword) {
	   keyword = _keyword;
   }
   
   Date getTime() {
	   return time;
   }
   
   int getCount() {
	   return count;
   }
   
   String getKeyword() {
	   return keyword;
   }
}
