package sarang123;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.Scanner;


public class OPractise {

	public static <E> void main(String[] args) {
		
//		
	Scanner sc=new Scanner(System.in);
//	
//	int num=sc.nextInt();
//		int rev=0;
//		
//		while(num!=0) {
//			
//			rev=rev*10+num%10;
//			num=num/10;
//			
//		}
//System.out.println(rev);
//	String str=sc.next();
//	
//	String rev="";
//	
//	char a []=str.toCharArray();  //convert string into chrater arry
//	
//	int length = str.length();
//	
//	for (int i=length-1;i>=0;i--) {
//		
//		
//		rev=rev+str.charAt(i);
//	//	rev=rev+str.charAt(i);
//	}
//	
//	System.out.println(rev);
//	
	
	
	
	
	Hashtable< Integer, Book> map=new Hashtable<Integer, Book>();
//	
//	 map.put(100,"Amit");    
//     map.put(102,"Ravi");   
//      
//     map.put(103,"Rahul");
//     
//     System.out.println(map.getOrDefault(104, "not found"));
//     
//     System.out.println(map.getOrDefault(101, "not found"));
//	
//     System.out.println(("====================================="));
//     
//     map.putIfAbsent(101,"Vijay"); 
//     System.out.println("Updated Map: "+map);  
//     
//     System.out.println(("====================================="));
    
	ArrayList a=new ArrayList();
	
	a.add(1);
	a.add("s");
	
	
     String as="Sarang";
     String b="Bhomg";
     
     if(!as.equals(b)) {
    	a=new ArrayList<>();
     }
     else {
		System.out.println("fast");
	}
     
   
	System.out.println(a);
	}

}
