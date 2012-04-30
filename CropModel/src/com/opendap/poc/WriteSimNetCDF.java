package com.opendap.poc;

/* This is part of the netCDF package.
Copyright 2006 University Corporation for Atmospheric Research/Unidata.
See COPYRIGHT file for conditions of use.

This is an example program which writes some 4D pressure and
temperatures. It is intended to illustrate the use of the netCDF
Java API. The companion program pres_temp_4D_rd.java shows how
to read the netCDF data file created by this program.

This example demonstrates the netCDF Java API.

Full documentation of the netCDF Java API can be found at:
http://www.unidata.ucar.edu/software/netcdf-java/
*/

import ucar.nc2.NetcdfFileWriteable;
import ucar.nc2.Dimension;
import ucar.ma2.DataType;
import ucar.ma2.ArrayFloat;
import ucar.ma2.InvalidRangeException;

import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class WriteSimNetCDF {
	public WriteSimNetCDF(){}
	/**********************This method returns the unique values *****************/
	public Vector UniqueValue(Vector vec)
	{
		Vector uniqueVals = new Vector();
		
		for (int k = 0; k < vec.size(); k++){
            if (!uniqueVals.contains(vec.get(k).toString()))
                uniqueVals.add(vec.get(k).toString());				        
	    }
	 
		return uniqueVals;
		
	}

 //public static void main(String args[]) throws Exception
	public void WriteCDFFile(String pathname, String DistrictID, String Crop) throws IOException
 {
	// try{
		 File inputFile = new File("Data/"+ pathname + "/"+DistrictID+"/SimulationResults.tsv");
		 BufferedReader input =  new BufferedReader(new FileReader(inputFile));
		 String line = null; //not declared within while loop  
		 Vector vLat = new Vector();
		 Vector vLon = new Vector();
		 Vector vSimulations = new Vector();
		 Vector vYear = new Vector();
		 Vector vSoil = new Vector();
		 Vector vCropOutput = new Vector();
		 
		 
		 while ((( line = input.readLine()) != null) ){
		   StringTokenizer st = new StringTokenizer(line,"\t");
     	   String Lat = st.nextElement().toString();
     	   vLat.add(Lat);
     	   String Lon = st.nextElement().toString();
     	   vLon.add(Lon);
     	   String Simulations = st.nextElement().toString();
     	   vSimulations.add(Simulations);
     	   String year = st.nextElement().toString();
     	   //vYear.add(year);
     	   String soil = st.nextElement().toString();
     	   vSoil.add(soil);
     	   String cropOutput = st.nextElement().toString();
     	   vCropOutput.add(cropOutput);
		 }
		 /* Input according to the ensotime of datalibrary */
		 for(int i = -102; i <=594; i=i+12)
		     vYear.add(i);
		 WriteSimNetCDF unique = new WriteSimNetCDF();
		 /*Find the unique values of dataset*/
		 Vector uLat = unique.UniqueValue(vLat);
		 Vector uLon = unique.UniqueValue(vLon);
		 Vector uSimulations = unique.UniqueValue(vSimulations);
		 Vector uYear = unique.UniqueValue(vYear);
		 Vector uSoil = unique.UniqueValue(vSoil);
		 //Vector uCropOutput = unique.UniqueValue(vCropOutput);
	
		 for (int k = 0; k < uLon.size(); k++){
	            //System.out.println(uLon.get(k));
		    }
		 
	  //}catch(Exception e){e.printStackTrace();}
	 
	 
	   final int NLAT = uLat.size();
	   final int NLON = uLon.size();
	   final int NSOIL = uSoil.size();
	   final int NYEAR = uYear.size();
	   final int NSIM = uSimulations.size();
	   /*final int NLAT = uGridPoints.size();
	   final int NLAT = uYear.size();
	   final int NLAT = uSoil.size();*/
	   final int NREC = 2;
	   
	   final float START_LAT = Float.parseFloat(uLat.get(0).toString());
       final float START_LON = Float.parseFloat(uLat.get(0).toString());
       //final float START_LON = -125.0f;
       
       // Create the file.
       
         File dir = new File("/beluga/data/arindam/CropModelOutput/"+DistrictID);
		 dir.mkdirs();
	     String filename = "/beluga/data/arindam/CropModelOutput/"+DistrictID+"/sim_cropmodel_"+Crop+".nc";
	     NetcdfFileWriteable dataFile = null;
	
		try {
         // Create new netcdf-3 file with the given filename
         dataFile = NetcdfFileWriteable.createNew(filename, false);

         //add dimensions  where time dimension is unlimit
         //Dimension lvlDim = dataFile.addDimension("level", NLVL ); 
         Dimension latDim = dataFile.addDimension("Y", NLAT );
         Dimension lonDim = dataFile.addDimension("X", NLON );
         Dimension simDim = dataFile.addDimension("simulation", NSIM );
         Dimension yearDim = dataFile.addDimension("T",NYEAR); // should not be need second argument
         Dimension soilDim = dataFile.addDimension("soil", NSOIL );
         ArrayList dims =  null;

         // Define the coordinate variables.
         dataFile.addVariable("Y", DataType.FLOAT, new Dimension[] {latDim});
         dataFile.addVariable("X", DataType.FLOAT, new Dimension[] {lonDim});
         dataFile.addVariable("simulation", DataType.FLOAT, new Dimension[] {simDim});
         dataFile.addVariable("T", DataType.FLOAT, new Dimension[] {yearDim});
         dataFile.addVariable("soil", DataType.FLOAT, new Dimension[] {soilDim});

         // Define units attributes for data variables.
         dataFile.addVariableAttribute("Y", "units", "degrees_north");
         dataFile.addVariableAttribute("X", "units", "degrees_east");
         dataFile.addVariableAttribute("simulation", "units", "");
         dataFile.addVariableAttribute("T", "units", "months since 1960-01-01");
         dataFile.addVariableAttribute("soil", "units", "");
		
		// Define the netCDF variables for the pressure and temperature
         // data.
         dims =  new ArrayList();
         //dims.add(timeDim);
         dims.add(latDim);
         dims.add(lonDim);
         dims.add(simDim);
         dims.add(yearDim);
         dims.add(soilDim);
         dataFile.addVariable("CropOutput", DataType.FLOAT, dims);
         //dataFile.addVariable("pressure", DataType.FLOAT, dims);
         //dataFile.addVariable("temperature", DataType.FLOAT, dims);

         // Define units attributes for data variables.
          dataFile.addVariableAttribute("CropOutput", "units", "kg");
         //dataFile.addVariableAttribute("pressure", "units", "hPa");
         //dataFile.addVariableAttribute("temperature", "units", "celsius");
                    
	      // Create some pretend data. If this wasn't an example program, we
         // would have some real data to write for example, model output.
         ArrayFloat.D1 lats = new ArrayFloat.D1(latDim.getLength());
         ArrayFloat.D1 lons = new ArrayFloat.D1(lonDim.getLength());
         ArrayFloat.D1 sims = new ArrayFloat.D1(simDim.getLength());
         ArrayFloat.D1 years = new ArrayFloat.D1(yearDim.getLength());
         ArrayFloat.D1 soils = new ArrayFloat.D1(soilDim.getLength());
         int i,j,k,l,m;

         for (i=0; i<latDim.getLength(); i++) {
             lats.set(i,  Float.parseFloat(uLat.get(i).toString()) );
         }

         for (j=0; j<lonDim.getLength(); j++) {
            lons.set(j,   Float.parseFloat(uLon.get(j).toString()));
         }
         for (m=0; m<simDim.getLength(); m++) {
             sims.set(m,   Float.parseFloat(uSimulations.get(m).toString()));
          }
         for (k=0; k<yearDim.getLength(); k++) {
        	 years.set(k,   Float.parseFloat(uYear.get(k).toString()));
          }
         for (l=0; l<soilDim.getLength(); l++) {
        	 soils.set(l,   Float.parseFloat(uSoil.get(l).toString()));
          }


         // Create the pretend data. This will write our surface pressure and
         // surface temperature data.
         ArrayFloat.D5 dataCropOutput = new ArrayFloat.D5(latDim.getLength(),lonDim.getLength(),simDim.getLength(),yearDim.getLength(),soilDim.getLength());
         //ArrayFloat.D4 dataTemp = new ArrayFloat.D4(NREC,lvlDim.getLength(),latDim.getLength(), lonDim.getLength());
         //ArrayFloat.D4 dataPres = new ArrayFloat.D4(NREC,lvlDim.getLength(),latDim.getLength(), lonDim.getLength());

         //for(int record = 0; record < NREC; record++) {
           // i = 10;
            //for (int lvl = 0; lvl < NLVL; lvl++)
               for (int lat = 0; lat < NLAT; lat++)
                 for (int lon = 0; lon < NLON; lon++)
                	 for(int sim = 0; sim < NSIM; sim++)
                	 for(int year=0;year<NYEAR;year++)
                		 for(int soil=0;soil < NSOIL;soil++)
                		 {	
                	     try{		 
                		 dataCropOutput.set(lat,lon,sim,year,soil,Float.parseFloat(vCropOutput.get(lat*(NLON*NSIM*NYEAR*NSOIL)+lon*(NSIM*NYEAR*NSOIL)+sim*(NYEAR*NSOIL) + (year*(NSOIL)) + soil).toString()));	 
                		 //dataCropOutput.set(lat,lon,sim,year,soil,Float.parseFloat(vCropOutput.get(lat*(NLAT)+lon*(NLON)+(year*(NSOIL)) + soil).toString()));      
                    	 System.out.println(lat + "\t" + lon + "\t" + year + "\t" +soil + "\t" +  dataCropOutput.get(lat,lon,sim,year,soil));
                	     }catch(Exception e){}
                	     }
                	 
                	 
                     
                 
         

         //Create the file. At this point the (empty) file will be written to disk
         dataFile.create();

         // A newly created Java integer array to be initialized to zeros.
         int[] origin = new int[100];

         dataFile.write("Y", lats);
         dataFile.write("X", lons);
         dataFile.write("simulation", sims);
         dataFile.write("T", years);
         dataFile.write("soil", soils);
         
         dataFile.write("CropOutput",origin,dataCropOutput);
         System.out.println("here");
         //dataFile.write("pressure", origin, dataPres);
         //dataFile.write("temperature", origin, dataTemp);


     } catch (IOException e) {
             e.printStackTrace(System.err);
     } catch (InvalidRangeException e) {
             e.printStackTrace(System.err);
     }finally {
         if (dataFile != null)
         try {
          dataFile.close();
         } catch (IOException ioe) {
          ioe.printStackTrace();
         }
     }
     System.out.println("*** SUCCESS writing example file "+filename);
 }

}
 
 

	  
	  
/*	 final int NLVL = 2;
     final int NLAT = 6;
     final int NLON = 12;
     final int NREC = 2;

     final float SAMPLE_PRESSURE = 900.0f;
     final float SAMPLE_TEMP = 9.0f;
     final float START_LAT = 25.0f;
     final float START_LON = -125.0f;

     // Create the file.
     String filename = "pres_temp_4D.nc";
     NetcdfFileWriteable dataFile = null;

     try {
         // Create new netcdf-3 file with the given filename
         dataFile = NetcdfFileWriteable.createNew(filename, false);

         //add dimensions  where time dimension is unlimit
         Dimension lvlDim = dataFile.addDimension("level", NLVL ); 
         Dimension latDim = dataFile.addDimension("latitude", NLAT );
         Dimension lonDim = dataFile.addDimension("longitude", NLON );
         Dimension timeDim = dataFile.addUnlimitedDimension("time"); // should not be need second argument

         ArrayList dims =  null;

         // Define the coordinate variables.
         dataFile.addVariable("latitude", DataType.FLOAT, new Dimension[] {latDim});
         dataFile.addVariable("longitude", DataType.FLOAT, new Dimension[] {lonDim});

         // Define units attributes for data variables.
         dataFile.addVariableAttribute("latitude", "units", "degrees_north");
         dataFile.addVariableAttribute("longitude", "units", "degrees_east");

         // Define the netCDF variables for the pressure and temperature
         // data.
         dims =  new ArrayList();
         dims.add(timeDim);
         dims.add(lvlDim);
         dims.add(latDim);
         dims.add(lonDim);
         dataFile.addVariable("pressure", DataType.FLOAT, dims);
         dataFile.addVariable("temperature", DataType.FLOAT, dims);

         // Define units attributes for data variables.
         dataFile.addVariableAttribute("pressure", "units", "hPa");
         dataFile.addVariableAttribute("temperature", "units", "celsius");




         // Create some pretend data. If this wasn't an example program, we
         // would have some real data to write for example, model output.
         ArrayFloat.D1 lats = new ArrayFloat.D1(latDim.getLength());
         ArrayFloat.D1 lons = new ArrayFloat.D1(lonDim.getLength());
         int i,j;

         for (i=0; i<latDim.getLength(); i++) {
             lats.set(i,  START_LAT + 5.f * i );
         }

         for (j=0; j<lonDim.getLength(); j++) {
            lons.set(j,   START_LON + 5.f * j);
         }


         // Create the pretend data. This will write our surface pressure and
         // surface temperature data.
         ArrayFloat.D4 dataTemp = new ArrayFloat.D4(NREC,lvlDim.getLength(),latDim.getLength(), lonDim.getLength());
         ArrayFloat.D4 dataPres = new ArrayFloat.D4(NREC,lvlDim.getLength(),latDim.getLength(), lonDim.getLength());

         for(int record = 0; record < NREC; record++) {
            i = 0;
            for (int lvl = 0; lvl < NLVL; lvl++)
               for (int lat = 0; lat < NLAT; lat++)
                 for (int lon = 0; lon < NLON; lon++)
                 {
                     dataPres.set(record, lvl, lat, lon, SAMPLE_PRESSURE + i);
                     dataTemp.set(record, lvl, lat, lon, SAMPLE_TEMP + i++);
                 }
         }

         //Create the file. At this point the (empty) file will be written to disk
         dataFile.create();

         // A newly created Java integer array to be initialized to zeros.
         int[] origin = new int[4];

         dataFile.write("latitude", lats);
         dataFile.write("longitude", lons);
         dataFile.write("pressure", origin, dataPres);
         dataFile.write("temperature", origin, dataTemp);


     } catch (IOException e) {
             e.printStackTrace(System.err);
     } catch (InvalidRangeException e) {
             e.printStackTrace(System.err);
     }finally {
         if (dataFile != null)
         try {
          dataFile.close();
         } catch (IOException ioe) {
          ioe.printStackTrace();
         }
     }
     System.out.println("*** SUCCESS writing example file "+filename);
 }

}
*/
