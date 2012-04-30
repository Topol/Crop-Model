package com.opendap.poc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import ucar.ma2.Array;
import ucar.ma2.Index;
import ucar.nc2.NCdump;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;
import ucar.nc2.dataset.NetcdfDataset;

public class TemperatureMax {
	
	public TemperatureMax(){}
	
	public void TempData()
	{
		//Getting the Soil Information 	
		String filename = "rainfall.cdf";  
		  NetcdfFile ncfile = null;
		  	try {
		  		 ncfile = NetcdfDataset.openFile(filename, null);  
			     } catch (IOException ioe) {} 
			try{
				  String varName = "aprod"; 
				  Variable v = ncfile.findVariable(varName);
				  
				  if (null == v) return;
				  try {
					  
					  Array data = v.read();
					  //Array data2D = data;
					  
					  
					  
					  /*int[] varShape = v.getShape();
					  int[] origin = new int[] {2, 0, 0};
					  int[] size = new int[] {1, varShape[1], varShape[2]};
					  Array data3D = v.read(origin, size);
					  Array data2D = data3D.reduce();
					  */
					  
					  /*for (int i = 0; i < varShape[0]; i++) {
					    origin[0] = i;
					    data2D = v.read(origin, size).reduce(0); 
					    for (int j = 0; j < data2D.getSize(); j++ ){
						    Object y2k = data2D.getObject(j);	
						    //System.out.println(y2k);
						    }
					  } 
					  /*int[] origin = new int[] {2, 0, 0};
					  int[] size = new int[] {1, 500, 720};
					  Array data3D = v.read(origin, size);
					  Array data2D = data3D.reduce();*/
					  
					  /*int[] varShape = v.getShape();
					  int[] origin = new int[3];					  
					  int[] size = new int[] {1, varShape[1]};				  
					  System.out.println(varShape[0]);					    
					  for (int i = 0; i < varShape[0]; i++) {
					    origin[0] = i;
					    data2D = v.read(origin, size).reduce(0);
					    //System.out.println("Here");
					    Vector vec1 = new Vector();
					    Vector vec2 = new Vector();
					    Vector vec3 = new Vector();
					    for (int j = 0; j < data.getSize(); j++ ){
					    Object y2k = data.getObject(j);	
					    //System.out.println(y2k);
					    }
					    	
					  } */					     
					  //}
					     int[] shape = data.getShape();
						  //Index index = data.getIndex();
						  System.out.println(shape[0]);
						  System.out.println(shape[1]);
						  System.out.println(shape[2]);
						  Vector vec1 = new Vector();
						  Vector vec2 = new Vector();
						  Vector vec3 = new Vector();
						  Index index = data.getIndex();
						  for (int i=0; i<shape[0]; i++) {
						    for (int j=0; j<shape[1]; j++) {
						     for (int k=0; k<shape[2]; k++) {
						      //double dval = data.getDouble(index.set(i,j,k));
						    	 vec1.add(data.getDouble(index.set(i,j,k)));
							     vec2.add(data.getDouble(index.set(i,j,k)));
							     vec3.add(data.getDouble(index.set(i,j,k)));
						      /*vec1.add(data.getDouble(index.set(i,0,0)));
						      vec2.add(data.getDouble(index.set(i,1,1)));
						      vec3.add(data.getDouble(index.set(i,2,2)));*/
						      //System.out.println(i+"\t"+j+"\t"+k+"\t"+dval);
						     }
						    }
						  }
						  File aFile = new File("To_rain.txt");
						  Writer output = new BufferedWriter(new FileWriter(aFile,true));
						  for (int k = 0; k < vec1.size(); k++){
					    		 //System.out.println(k + "\t" + vec1.get(k));
					    		 System.out.println(k + "\t" + vec1.get(k) + "\t" + vec2.get(k) +"\t" + vec3.get(k));
					    		 output.write(vec1.get(k).toString() + "\t" + vec2.get(k).toString() +"\t" + vec3.get(k).toString());
					    		 output.write("\n");
							 }
						  
						  System.out.println("Here");
						  
						  //NCdump.printArray(data, varName, System.out, null);
						  //System.out.println(data.getObject(4513));
					  } 
					  catch (IOException ioe) {
					   System.out.println(ioe);
					  }
					}
				  catch (Exception e) {
					  System.out.println(e);
				  }
				//doit(urlName);
	}
			


}


