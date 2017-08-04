package summaryparagraphsapp;

/**
*
* @author Ngo Phuong Tuan
*
*/
public class Host {
   private String name;
   private int count;
   private String keyword;
   
   Host(String name, int count, String keyword) {
       this.name = name;
       this.count = count;
       this.keyword = keyword;
   }
   
   void setName(String _name) {
	   name = _name;
   }
   
   void setCount(int _count) {
	   count = _count;
   }
   
   void setKeyword(String _keyword) {
	   keyword = _keyword;
   }
   
   String getName() {
	   return name;
   }
   
   int getCount() {
	   return count;
   }
   
   String getKeyword() {
	   return keyword;
   }
}
