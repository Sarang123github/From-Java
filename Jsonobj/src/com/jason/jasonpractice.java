package com.jason;

import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONStreamAware;
import org.json.simple.JSONValue;

import netscape.javascript.JSObject;

public class jasonpractice {

	public static void main(String[] args) {

		// Java JSON Encode
		
		/*
		 * JSONObject obj=new JSONObject();
		 * 
		 * 
		 * obj.put("id", 1); obj.put("name", "sarang"); obj.put("Lastname", "BHong");
		 * 
		 * 
		 * System.out.println(obj);
		 */System.out.println("===========================================================");	
		  /*
		  
			  Hashtable obj=new Hashtable();
			  
			  obj.put("id",new Integer(1) ); obj.put("name", "sarang"); obj.put("Lastname",
			  "BHong");
			 */

		/*
		 * String jsonString = JSONValue.toJSONString(obj);
		 * 
		 * System.out.println(jsonString);
		 */
		/*
		 * HashMap<Integer, String> a=new HashMap<Integer, String>();
		 * 
		 * a.put(1, "sarang"); a.put(2, "sagar");
		 * 
		 * String jsonString = JSONValue.toJSONString(a);
		 * 
		 * System.out.println(jsonString);
		 */
		System.out.println("===========================================================");	
		/*
		 * JSONArray n=new JSONArray();
		 * 
		 * n.add(1); n.add("sarang"); n.add("Bhong");
		 * 
		 * String jsonString = JSONValue.toJSONString(n);
		 * System.out.println(jsonString);
		 */

		/*
		 * JSONArray array=new JSONArray();
		 * 
		 * array.add(1); array.add("sarang");
		 * 
		 * String jsonString = JSONValue.toJSONString(array);
		 * 
		 * System.out.println(jsonString);
		 */
		
	System.out.println("===========================================================");	
		
		
		// Let's see a simple example to decode JSON string in java.

	//	String s = "{\"name\":\"sonoo\",\"salary\":600000.0,\"age\":27}";
		 
	//	String str="{\"name\":\"sarang\",\"Id\":1}";
//
//	    String s="{\"name\":\"sonoo\",\"salary\":600000.0,\"age\":27}";  
//	    Object obj=JSONValue.parse(s);  
//	    JSONObject jsonObject = (JSONObject) obj;  
//	  
//	    String name = (String) jsonObject.get("name");  
//	    double salary = (Double) jsonObject.get("salary");  
//	    long age = (Long) jsonObject.get("age");  
//	    System.out.println(name+" "+salary+" "+age); 

	/*
	 * String str="{\"name\":\"sarang\",\"age\":1}";
	 * 
	 * Object obj = JSONValue.parse(str);
	 * 
	 * JSObject jsObject=(JSObject) obj;
	 * 
	 * String name=(String) jsObject.get("name"); String age=(Integer)
	 * jsObject.get("age"); System.out.println(name+""+age);
	 */
	System.out.println("===========================================================");	
	

	
	Map<String, Integer> map=new HashMap(); 
	
	map.put("sarang", 1);
	map.put("sagar", 2);
	map.put("bhong", 3);
	
	Set<String> keySet = map.keySet();
	System.out.println("Keys > "+keySet);
	
	
	System.out.println("===========================================================");	
	
	Collection values = map.values();	
	 
	System.out.println("Values >"+values); 
	
	System.out.println("===========================================================");	
	
	for(Map.Entry m:map.entrySet()) {
		System.out.println(m);
	}
	System.out.println("===========================================================");	
	
	Iterator<String> iterator = map.keySet().iterator();
	while(iterator.hasNext()) {
		
		String next = iterator.next();
		
		System.out.println("keys > "+next+ "  "+"values>"+map.get(next));
	}
	
	
	
	
		}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
