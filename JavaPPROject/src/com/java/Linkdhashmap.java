package com.java;

import java.util.LinkedHashMap;
import java.util.Set;

public class Linkdhashmap {

	public static void main(String[] args) {
		
		
		LinkedHashMap<String,String> h=new LinkedHashMap<String,String>();
		
		h.put("1", "sarang");
		h.put("2", "sagar");
		
		Set<String> set = h.keySet();
		
		for(String x:set) {
			System.out.println(x);
			
			System.out.println(h.get(x));
		}
		
	}
	
	
}
