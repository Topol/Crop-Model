<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script language="javascript" src="script/asyncCalls.js"></script>
</head>
<body>
<h2></h2>
    	
<form method="post" action="#" name="DODSForm">
  <ul style='list-style-type:none;margin-left: 50px;'>
  <li><b>Country:</b><img src="images/spacer.gif" width="148px" height="1px">
  	<select onchange="updateState()" name="selCountry" id="selCountry" STYLE="font-family : verdana; font-size : 8pt"> 
		<option  value="0" selected>..Select Country..</option>
		<OPTION VALUE="1">India</OPTION> 
	</select><br/><br/></li>
  <li><b>State:</b><img src="images/spacer.gif" width="170px" height="1px"><select onchange="updateDistrict()" id="selState" name="selState" STYLE="font-family : verdana; font-size : 8pt">
		<option value="0" selected></option> 
	</select><br/><br/></li>
  <li><b>District:</b><img src="images/spacer.gif" width="155px" height="1px"><select name="selDistrict" id="selDistrict" STYLE="font-family : verdana; font-size : 8pt">
		<option value="0" selected></option>
	</select><br/><br/></li>
  <li><b>Crop name:</b><img src="images/spacer.gif" width="132px" height="1px"><select name="selCropName" id="selCropName" style="font-family : verdana; font-size : 8pt">
			<option value="0" selected>..Select..</option> 
			<option value="1">Wheat</option>
			<option value="2">Rice</option>
			<option value="3">Maize</option>
			<option value="4">Millet</option>
			<option value="5">Jowar</option>
			<option value="6">Bajra</option>
			<option value="7">Sorghum</option>
			<option value="8">Peanut</option>
			<option value="9">Sugarcane</option> 
		</select><br/><br/></li>
 
   <li><b>Croping Season:</b><img src="images/spacer.gif" width="100px" height="1px"><select name="selCropSeason" id="selCropSeason" style="font-family : verdana; font-size : 8pt">
			<option value="0" selected>..Select..</option> 
			<option value="1">JFMA</option>
			<option value="2">FMAM</option>
			<option value="3">MAMJ</option>
			<option value="4">AMJJ</option>
			<option value="5">MJJA</option>
			<option value="6">JJAS</option>
			<option value="7">JASO</option>
			<option value="8">ASON</option>
			<option value="9">SOND</option>
			<option value="10">ONDJ</option>
			<option value="11">NDJF</option>
			<option value="12">DJFM</option>
		</select><br/><br/></li>
		
  <li><b>General Circulation Model:</b><img src="images/spacer.gif" width="25px" height="1px"><select name="selGeneralCirculationModel" id="selGeneralCirculationModel" style="font-family : verdana; font-size : 8pt">
			<option value="0" selected>..Select..</option> 
			<option value="1">ECHAM 4.5</option>
			<option value="2">ECHAM 4.0</option>
			<option value="3">ECHAM 5.0</option>
			<option value="4">COP</option> 
		</select><br/><br/></li>
		
 <li><b>Forecast initialization month:</b><img src="images/spacer.gif" width="100px" height="1px"><select name="selGCMSeason" id="selGCMSeason" style="font-family : verdana; font-size : 8pt">
			<option value="0" selected>..Select..</option> 
			<option value="1">Jan</option>
			<option value="2">Feb</option>
			<option value="3">Mar</option>
			<option value="4">Apr</option>
			<option value="5">May</option>
			<option value="6">Jun</option>
			<option value="7">Jul</option>
			<option value="8">Aug</option>
			<option value="9">Sep</option>
			<option value="10">Oct</option>
			<option value="11">Nov</option>
			<option value="12">Dec</option>
		</select><br/><br/></li>
  <li><input type="button" value="Run NHMM on rainfall" onclick="runNHMMModel()" />&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="Run Crop Model(Historical Yeild)" onclick="runCropModelHistorical()" />&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="Run CropModel(NHMM Predictions)" onclick="runSimulationsNHMM()" /></li>
  </ul>
  
 <li><a href>NHMM Input seeds</a></li> 
 <li><a href>Crop Model Genetic Algorithm Parameters</a></li>
 </form>
 <hr/>
 <b>Simulation Outputs:</b>
 <div id="simulationLoader"></div>
 <div id="simulationOutput" style="display:none">
	<fieldset>
		<table>
			<tr>
				<td><a href="http://iridl.ldeo.columbia.edu/expert/%28/beluga/data/arindam/CropModelOutput/188/hist_cropmodel_Sorghum.nc%29readCDF/.CropOutput/figviewer.html?map.url=soil+year+fig-+colors+-fig&my.help=more+options">Historical Crop Yield per grid point</a></td>
			</tr><tr></tr>
			<tr>
				<td><a href="http://iridl.ldeo.columbia.edu/expert/%28/beluga/data/arindam/CropModelOutput/187/hist_cropmodel_Sorghum.nc%29readCDF/.CropOutput/figviewer.html?map.url=X+Y+fig-+colors+coasts+lakes+-fig&my.help=more+options">Historical Crop Yield per grid point per soil</a></td>
			</tr><tr></tr>
			<tr>
				<td><a href="http://iridl.ldeo.columbia.edu/expert/%28/beluga/data/arindam/CropModelOutput/187/sim_cropmodel_Sorghum.nc%29readCDF/.CropOutput/figviewer.html">Rainfall Simulation Output from machine Learning</a></td>
			</tr><tr></tr>
			<tr>
				<td><a href="http://iridl.ldeo.columbia.edu/expert/SOURCES/.NOAA/.NCDC/.ERSST/.version3b/.sst/T/%28Jun%201951%29%28Sep%202009%29RANGE/T/4/runningAverage/T/12/STEP/%28/beluga/data/arindam/CropModelOutput/187/hist_cropmodel_Sorghum.nc%29readCDF/.CropOutput/T/1/shiftGRID/X/72.5/VALUE/Y/22.5/VALUE/X/removeGRID/Y/removeGRID/exch%5BT%5Dcorrelate/X+Y+fig-+colors+coasts+-fig+//soil/3676./plotvalue//XOVY+null+psdef//plotborder+72+psdef//plotaxislength+432+psdef/figviewer.html?my.help=&map.soil.plotvalue=3676.&map.Y.units=degree_north&map.Y.plotlast=89N&map.url=+&map.domain=+%7B+%2Fsoil+3858.+plotvalue+X+-30+330+plotrange+%7D&map.domainparam=+%2Fplotaxislength+432+psdef+%2Fplotborder+72+psdef+%2FXOVY+null+psdef&map.zoom=Zoom&map.Y.plotfirst=89S&map.X.plotfirst=30W&map.X.units=degree_east&map.X.modulus=360&map.X.plotlast=30W&map.correlation.plotfirst=-0.9960159&map.correlation.units=unitless&map.correlation.plotlast=0.9960159&map.plotaxislength=432&map.plotborder=72&map.fnt=Helvetica&map.fntsze=12&map.color_smoothing=1&map.XOVY=auto&map.iftime=25&map.mftime=25&map.fftime=200">Corellation of Crop with SSTs per soil</a></td>
			</tr><tr></tr>
			<tr>
				<td><a href="http://iridl.ldeo.columbia.edu/expert/SOURCES/.IMD/.NCC1-2005/.v2p0/.rf/T/%28Jun%201971%29%28Sep%202009%29RANGE/T/monthlyAverage/T/4/runningAverage/T/12/STEP/%28/beluga/data/arindam/CropModelOutput/187/hist_cropmodel_Sorghum.nc%29readCDF/.CropOutput/T/1/shiftGRID/X/73.5/VALUE/Y/22.5/VALUE/X/removeGRID/Y/removeGRID/exch%5BT%5Dcorrelate/figviewer.html?map.url=X+Y+fig-+colors+coasts+lakes+-fig&my.help=more+options">Corellation of Crop Output with rainfall per soil</a></td>
			</tr><tr></tr>
			<tr>
				<td><a href="http://iridl.ldeo.columbia.edu/expert/SOURCES/.NOAA/.ESRL/.PSD/.rean20thcent/.V2/.six-hourly/.monolevel/.sig995/.uwnd/time//T/renameGRID/T/monthlyAverage/T/%28Jun%201951%29%28Sep%202009%29RANGE/T/4/runningAverage/T/12/STEP/%28/beluga/data/arindam/CropModelOutput/187/hist_cropmodel_Sorghum.nc%29readCDF/.CropOutput/T/1/shiftGRID/X/72.5/VALUE/Y/22.5/VALUE/X/removeGRID/Y/removeGRID/exch%5BT%5Dcorrelate/figviewer.html?my.help=more+options&map.soil.plotvalue=3858.&map.lat.units=degree_north&map.lat.plotlast=90N&map.url=lon+lat+fig-+colors+coasts+lakes+-fig&map.domain=+%7B+%2Fsoil+3676.+plotvalue+lat+-90.+90.+plotrange+%7D&map.domainparam=+%2Fplotaxislength+432+psdef+%2Fplotborder+72+psdef&map.zoom=Zoom&map.lat.plotfirst=90S&map.lon.plotfirst=1W&map.lon.units=degree_east&map.lon.modulus=360&map.lon.plotlast=1W&map.correlation.plotfirst=-0.9960159&map.correlation.units=unitless&map.correlation.plotlast=0.9960159&map.newurl.grid0=lon&map.newurl.grid1=lat&map.newurl.land=draw+coasts&map.newurl.plot=colors&map.plotaxislength=432&map.plotborder=72&map.fnt=Helvetica&map.fntsze=12&map.XOVY=auto&map.color_smoothing=1&map.framelbl=framelabelstart&map.framelabeltext=&map.iftime=25&map.mftime=25&map.fftime=200">Corellation of Crop with u-wind per soil</a></td>
			</tr><tr></tr>
			<tr>
				<td><a href="http://iridl.ldeo.columbia.edu/expert/SOURCES/.NOAA/.ESRL/.PSD/.rean20thcent/.V2/.six-hourly/.monolevel/.sig995/.vwnd/time//T/renameGRID/T/monthlyAverage/T/%28Jun%201951%29%28Sep%202009%29RANGE/T/4/runningAverage/T/12/STEP/%28/beluga/data/arindam/CropModelOutput/187/hist_cropmodel_Sorghum.nc%29readCDF/.CropOutput/T/1/shiftGRID/X/72.5/VALUE/Y/22.5/VALUE/X/removeGRID/Y/removeGRID/exch%5BT%5Dcorrelate/lon/lat/fig-/colors/coasts/-fig//soil/3858./plotvalue/lat/-90/90/plotrange//XOVY/null/psdef//plotborder/72/psdef//plotaxislength/432/psdef/figviewer.html?map.here.x=250&map.here.y=119&map.url=+&map.domain=+%7B+%2Fsoil+3676.+plotvalue+%7D&map.domainparam=+%2Fplotaxislength+432+psdef+%2Fplotborder+72+psdef+%2FXOVY+null+psdef&map.lon.width=360.&map.lat.width=180.&map.plotaxislength=432&map.plotborder=72&map.fnt=Helvetica&map.fntsze=12&map.color_smoothing=1&map.XOVY=auto&map.iftime=25&map.mftime=25&map.fftime=200">Corellation of crop model with v-wind per soil</a></td>
			</tr><tr></tr>
			<tr>
				<td><a href="http://iridl.ldeo.columbia.edu/expert/%28/beluga/data/arindam/CropModelOutput/187/hist_cropmodel_Sorghum.nc%29readCDF/.CropOutput/soil/3858/VALUE/X/72.5/VALUE/Y/22.5/VALUE/%28/beluga/data/arindam/CropModelOutput/187/hist_cropmodel_Sorghum.nc%29readCDF/.CropOutput/soil/3858/VALUE/X/73.5/VALUE/Y/22.5/VALUE/%28/beluga/data/arindam/CropModelOutput/187/hist_cropmodel_Sorghum.nc%29readCDF/.CropOutput/soil/3858/VALUE/X/73.5/VALUE/Y/23.5/VALUE/T/fig-/green/line/red/line/blue/line/-fig//rf/1.043575/13.73149/plotrange//rf/1.043575/13.73149/plotrange//plotaxislength/432/psdef//XOVY/null/psdef//plotborder/72/psdef/">Crop Response to soil on historical data</a></td>
			</tr><tr></tr>
			
		</table>
	</fieldset>
</div>
</body>
</html>