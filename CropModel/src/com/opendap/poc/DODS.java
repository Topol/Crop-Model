/*
 * Copyright 1998-2009 University Corporation for Atmospheric Research/Unidata
 *
 * Portions of this software were developed by the Unidata Program at the
 * University Corporation for Atmospheric Research.
 *
 * Access and use of this software shall impose the following obligations
 * and understandings on the user. The user is granted the right, without
 * any fee or cost, to use, copy, modify, alter, enhance and distribute
 * this software, and any derivative works thereof, and its supporting
 * documentation for any purpose whatsoever, provided that this entire
 * notice appears in all copies of the software, derivative works and
 * supporting documentation.  Further, UCAR requests that the user credit
 * UCAR/Unidata in any publications that result from the use of this
 * software or in any product that includes this software. The names UCAR
 * and/or Unidata, however, may not be used in any advertising or publicity
 * to endorse or promote any products or commercial entity unless specific
 * written permission is obtained from UCAR/Unidata. The user also
 * understands that UCAR/Unidata is not obligated to provide the user with
 * any support, consulting, training or assistance of any kind with regard
 * to the use, operation and performance of this software nor to provide
 * the user with any updates, revisions, new versions or "bug fixes."
 *
 * THIS SOFTWARE IS PROVIDED BY UCAR/UNIDATA "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL UCAR/UNIDATA BE LIABLE FOR ANY SPECIAL,
 * INDIRECT OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING
 * FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT,
 * NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION
 * WITH THE ACCESS, USE OR PERFORMANCE OF THIS SOFTWARE.
 */
//package com.opendap.poc;

//import opendap.dap.*;
//import opendap.dap.parser.ParseException;

package com.opendap.poc;
import opendap.dap.*;
import opendap.dap.parser.ParseException;

import java.text.SimpleDateFormat;
import java.util.Vector;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.List;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Authenticator;
import java.net.PasswordAuthentication;

import java.sql.*;

import org.apache.log4j.Logger;

import com.opendap.poc.OpenDAPmain.MyAuthenticator;

import ucar.ma2.Array;
import ucar.ma2.DataType;
import ucar.ma2.Index;
import ucar.nc2.dods.DODSAttribute;
import ucar.nc2.dods.DODSNetcdfFile;
import ucar.unidata.util.StringUtil;
import ucar.nc2.NetcdfFile;
import ucar.nc2.iosp.netcdf3.*;
import ucar.nc2.dataset.*;
import ucar.nc2.Attribute;
import ucar.nc2.*;
import ucar.ma2.*;
import ucar.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;



 public class DODS {
	 
	 public DODS(){}
	 
	 public void BuildDODS() throws Exception
	 {
		 SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		 String DistrictID = "188";//kheda
		 String Crop = "Sorghum";
		 /**************Download server dataset******************/
		 ServerContentDownloader serverContentDownloader =  new ServerContentDownloader();
		 serverContentDownloader.createAndSaveUrl(simpleDateFormat.format(new Date()), DistrictID);
		 /***********Call the NHMM to build up the rainfall parameters**************/ 
		/*RunNHMM nhmm = new RunNHMM();
		 nhmm.RunNHMMModel(simpleDateFormat.format(new Date()), DistrictID);
		 /***************** Build the crop output based on NHMM Output ********************/	
		 /*ReadNHMMOutput rno = new ReadNHMMOutput();
		 rno.makeCropInputFiles(simpleDateFormat.format(new Date()),DistrictID,Crop);*/
		 /***************** Build the crop output based on the historical data ********************/
		/* ReadHistoricalRainfall hisrain = new ReadHistoricalRainfall();
			hisrain.makeCropInputFiles(simpleDateFormat.format(new Date()),DistrictID,Crop);
		*/	
	 }

public static void main(String args[]) throws Exception 
{
	//DODS obj = new DODS();
	/* calls the downloading information */
	/*SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	String DistrictID = "187";//kheda
	String Crop = "Sorghum";
	/**************Download server dataset******************/
	/*ServerContentDownloader serverContentDownloader =  new ServerContentDownloader();
	serverContentDownloader.createAndSaveUrl(simpleDateFormat.format(new Date()), DistrictID);
	
	/***********Call the NHMM to build up the rainfall parameters**************/ 
	/*RunNHMM nhmm = new RunNHMM();
	nhmm.RunNHMMModel(simpleDateFormat.format(new Date()), DistrictID);
	
	/***************** Build the crop output based on NHMM Output ********************/	
	/*ReadNHMMOutput rno = new ReadNHMMOutput();
	rno.makeCropInputFiles(simpleDateFormat.format(new Date()),DistrictID,Crop);
	/***************** Write the crop output based on NHMM into NetCDF ********************/
	/*WriteSimNetCDF writesimfile = new WriteSimNetCDF();
	writesimfile.WriteCDFFile(simpleDateFormat.format(new Date()), DistrictID, Crop);
		
	
	/***************** Build the crop output based on the historical data ********************/
	/*ReadHistoricalRainfall hisrain = new ReadHistoricalRainfall();
	hisrain.makeCropInputFiles(simpleDateFormat.format(new Date()),DistrictID,Crop);
	
	/***********************Write the output in Netcdf file format*****************************/
	/*WriteNetCDF writefile = new WriteNetCDF();
	writefile.WriteCDFFile(simpleDateFormat.format(new Date()), DistrictID, Crop);
	
	/******************************************************************************************/
 }
 


}