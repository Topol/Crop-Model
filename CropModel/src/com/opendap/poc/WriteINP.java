package com.opendap.poc;

import java.io.*;
public class WriteINP {
	public static void main(String args[]){
	String searchText = "Loam"; 
	String fileName = "soil.sol"; 
	StringBuilder sb = 	new StringBuilder();
	try {
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		//Reads until the end-of-file met
		while (reader.ready()) {
			//Read line-by-line directly
			sb.append(reader.readLine());
			System.out.println (reader.readLine());
		}
	}
	catch(IOException ex) {
	ex.printStackTrace();
	}
	String fileText = sb.toString();
	int ind = fileText.indexOf(searchText);
	System.out.println("Position in file : " + ind);
	String st = fileText.substring(ind-100, ind + 500 );
	System.out.println(st);
	String newline = System.getProperty("line.separator");
	System.out.println(st.indexOf("\n"));
	System.out.println(sb.substring(ind, ind + 100).indexOf(newline));
	//int ind2 = 
	System.out.println(st.substring(st.indexOf("*")+1,st.indexOf("*")+11 ));
	
	//System.out.println(fileText.getChars(srcBegin, srcEnd, dst, dstBegin))
}
	
/*	public static void main(String args[])
	  {
	  try{
	  // Open the file that is the first 
	  // command line parameter
	  FileInputStream fstream = new FileInputStream("soil.sol");
	  StringBuilder sb =  new StringBuilder();
	  // Get the object of DataInputStream
	  DataInputStream in = new DataInputStream(fstream);
	  BufferedReader br = new BufferedReader(new InputStreamReader(in));
	  String strLine;
	  //Read File Line By Line
	  while ((strLine = br.readLine()) != null)   {
	  // Print the content on the console
	  System.out.println (strLine);
	  }
	  //Close the input stream
	  in.close();
	    }catch (Exception e){//Catch exception if any
	  System.err.println("Error: " + e.getMessage());
	  }
	  }
*/
}


