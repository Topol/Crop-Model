package com.opendap.poc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;

public class WriteWTHFile {

	public WriteWTHFile(){}
	
	public void WriteWTH(String folderPath, String fname)
	{
		try {
		      BufferedReader input =  new BufferedReader(new FileReader("GATI0301.WTH"));
		      File writeFile = new File(folderPath+"/" + fname);
		      Writer output = new BufferedWriter(new FileWriter(writeFile));
		      try {
		        String line = null; //not declared within while loop
		        while ((( line = input.readLine()) != null) ){
		        		output.write(line);
		        		output.write("/n");
		        }
		      }catch(Exception e){}
		 }catch(Exception e){}
			        					
		
	}
}
