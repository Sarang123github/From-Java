package com.java;

import java.util.HashMap;
import java.util.Set;

import javax.sql.rowset.serial.SQLOutputImpl;

public class Map {

	public static void main(String[] args) {
		
		HashMap<Integer, String> a=new HashMap<Integer, String>();
		
		a.put(1, "sarang");
		a.put(2, "sagar");
		a.put(3, "babarao");
		
	   Set<Integer> set = a.keySet();
	   
	   for ( Integer map : set) {
		   System.out.println("before invoke ifabset method");
		   System.out.println("keys >>"+ map);
		   System.out.println("values >>"+ a.get(map));
		   System.out.println("----------------------------------------------------------");
	   }


//	   ifabset method
	   
	   a.putIfAbsent(4, "amit");
	   
	  Set<Integer> set2 = a.keySet();
	  
	  for(Integer x:set2) {
		  System.out.println("After invoke ifabset method");
		  System.out.println("keys >>"+ x);
		  System.out.println("values >>"+a.get(x ));
		  
		  System.out.println("----------------------------------------------------------");
		  
		  
	  }
	  a.remove(1);
	  System.out.println(a);
	  
	  System.out.println("----------------------------------------------------------");
	  
	  a.remove(2);
	  System.out.println(a);
	  
	   
	
}
}