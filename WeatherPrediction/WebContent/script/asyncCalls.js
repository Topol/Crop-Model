/*
   Provide the XMLHttpRequest constructor for IE 5.x-6.x:
   Other browsers (including IE 7.x-8.x) do not redefine
   XMLHttpRequest if it already exists.
 
   This example is based on findings at:
   http://blogs.msdn.com/xmlteam/archive/2006/10/23/using-the-right-version-of-msxml-in-internet-explorer.aspx
*/


// stores the reference to the XMLHttpRequest object
var xmlHttp = createXmlHttpRequestObject(); 


// creates an XMLHttpRequest instance
function createXmlHttpRequestObject() 
{
  // will store the reference to the XMLHttpRequest object
  var xmlHttp;
  // this should work for all browsers except IE6 and older
  try
  {
    // try to create XMLHttpRequest object
    xmlHttp = new XMLHttpRequest();
  }
  catch(e)
  {
    // assume IE6 or older
    var XmlHttpVersions = new Array("MSXML2.XMLHTTP.6.0",
                                    "MSXML2.XMLHTTP.5.0",
                                    "MSXML2.XMLHTTP.4.0",
                                    "MSXML2.XMLHTTP.3.0",
                                    "MSXML2.XMLHTTP",
                                    "Microsoft.XMLHTTP");
    // try every prog id until one works
    for (var i=0; i<XmlHttpVersions.length && !xmlHttp; i++) 
    {
      try 
      { 
        // try to create XMLHttpRequest object
        xmlHttp = new ActiveXObject(XmlHttpVersions[i]);
      } 
      catch (e) {}
    }
  }
  // return the created object or display an error message
  if (!xmlHttp)
    alert("Error creating the XMLHttpRequest object.");
  else 
    return xmlHttp;
}



// retrieves the XMLHttpRequest object
function createXmlHttpRequestObject_small() 
{	
  // will store the reference to the XMLHttpRequest object
  var xmlHttp;
  // if running Internet Explorer
  if(window.ActiveXObject) {
    try{
      xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
    }catch (e) {
      xmlHttp = false;
    }
  }
  // if running Mozilla or other browsers
  else {
    try{
      xmlHttp = new XMLHttpRequest();
    } catch (e) {
      xmlHttp = false;
    }
  }
  // return the created object or display an error message
  if (!xmlHttp){
     alert("Error creating the XMLHttpRequest object.");
  } else {
    return xmlHttp;
  }
}

// make asynchronous HTTP request using the XMLHttpRequest object 
function updateState()
{
	xmlHttp = createXmlHttpRequestObject(); 
	
  // proceed only if the xmlHttp object isn't busy
  if (xmlHttp.readyState == 4 || xmlHttp.readyState == 0)
  {
    // retrieve the Country  selected by the user on the form
    countryNo = document.DODSForm.selCountry.value;
	if( countryNo == "0"){
		// Remove all entries from State drop down
		objSelectState =  document.DODSForm.selState;
		
		var optionLen = objSelectState.options.length;
		//alert(optionLen);

		// remove all options from dropdown and change
    	for (var i = 1 ; i <= optionLen ; i = i + 1 ){
    		objSelectState.options[0] = null;
			//alert(i);
		}
		
    	// Remove all entries from District drop down
    	objSelectDistrict =  document.DODSForm.selDistrict;
		
		var optionLen = objSelectDistrict.options.length;
		//alert(optionLen);

		// remove all options from dropdown and change
    	for (var i = 1 ; i <= optionLen ; i = i + 1 ){
    		objSelectDistrict.options[0] = null;
			//alert(i);
		}
    	
	}else{
		// execute the GetState.jsp page from the server
		xmlHttp.open("GET", "jsp/GetState.jsp?cntryNo=" + countryNo, true);  
		// define the method to handle server responses
		xmlHttp.onreadystatechange = handleStateResponse;
		// make the server request
		xmlHttp.send(null);
	}
  }
  else
    // if the connection is busy, try again after one second  
    setTimeout('updateState()', 1000);
}

// executed automatically when a message is received from the server fetching State List
function handleStateResponse() 
{
	objSelectState =  document.DODSForm.selState;

    // http post call is made
    if(xmlHttp.readyState == 1){ 
    	// remove all options from dropdown and change 
    	// value to "Loading..."

		var optionLen = objSelectState.options.length;
		//alert(optionLen);

		// remove all options from dropdown and change
    	for (var i = 1 ; i <= optionLen ; i = i + 1 ){
        	objSelectState.options[0] = null;
			//alert(i);
		}
		
    	var option = document.createElement("OPTION");
		objSelectState.options.add(option);
 		option.innerHTML = "Loading...";
	
 		
 		// Remove all entries from District drop down
    	objSelectDistrict =  document.DODSForm.selDistrict;
		
		var optionLen = objSelectDistrict.options.length;
		//alert(optionLen);

		// remove all options from dropdown and change
    	for (var i = 1 ; i <= optionLen ; i = i + 1 ){
    		objSelectDistrict.options[0] = null;
			//alert(i);
		}
    }

  // move forward only if the transaction has completed
  if (xmlHttp.readyState == 4) 
  {
	  	
    // status of 200 indicates the transaction completed successfully
    if (xmlHttp.status == 200) 
    {
		var optionLen = objSelectState.options.length;
		//alert(optionLen);

		// remove all options from dropdown and change
    	for (var i = 1 ; i <= optionLen ; i = i + 1 ){
        	objSelectState.options[0] = null;
			//alert(i);
		}
		      
	  // extract the XML retrieved from the server
      xmlResponse = xmlHttp.responseXML;
	 // alert(xmlResponse);

	  parseStateResponseXML(xmlResponse);
    } 
    // a HTTP status different than 200 signals an error
    else 
    {
      alert("There was a problem accessing the server: " + xmlHttp.statusText);
    }
  }
}

// Parse the State response XML and add State dropdown options
function parseStateResponseXML(xmlResponse){
	rootNode = xmlResponse.documentElement;
	if (rootNode == null)
		alert("Could not retrieve States");
	else{
	    objSelectState =  document.DODSForm.selState;
		StateNodes = rootNode.getElementsByTagName('State');
		if( StateNodes.length > 0){
			var option = document.createElement("OPTION");
			objSelectState.options.add(option);
			option.innerHTML = "..Select State..";
			option.value = "0";
		
			for (var i = 0 ; i < StateNodes.length ; i++) {
				State = StateNodes[i];
				var StateId = State.getElementsByTagName('id')[0].firstChild.nodeValue;
				var StateName = State.getElementsByTagName('name')[0].firstChild.nodeValue;
				var option = document.createElement("OPTION");
				objSelectState.options.add(option);
				option.value = StateId;
				option.innerHTML = StateName;
			}
		}
	}
}   

//make asynchronous HTTP request using the XMLHttpRequest object 
function updateDistrict()
{
	xmlHttp = createXmlHttpRequestObject();
  // proceed only if the xmlHttp object isn't busy
  if (xmlHttp.readyState == 4 || xmlHttp.readyState == 0)
  {
    // retrieve the Country  selected by the user on the form
    countryNo = document.DODSForm.selCountry.value;
    stateNo = document.DODSForm.selState.value;
	if( stateNo == "0"){
		objSelectDistrict =  document.DODSForm.selDistrict;
		
		var optionLen = objSelectDistrict.options.length;
		//alert(optionLen);

		// remove all options from dropdown and change
    	for (var i = 1 ; i <= optionLen ; i = i + 1 ){
    		objSelectDistrict.options[0] = null;
			//alert(i);
		}
		
	}else{

		// execute the GetDistrict.jsp page from the server
		xmlHttp.open("GET", "jsp/GetDistrict.jsp?cntryNo=" + countryNo + "&stateNo=" + stateNo, true);  
		// define the method to handle server responses
		xmlHttp.onreadystatechange = handleDistrictResponse;
		// make the server request
		xmlHttp.send(null);
	}
  }
  else
    // if the connection is busy, try again after one second  
    setTimeout('updateDistrict()', 1000);
}

// executed automatically when a message is received from the server fetching District List
function handleDistrictResponse() 
{
	objSelectDistrict =  document.DODSForm.selDistrict;

    // http post call is made
    if(xmlHttp.readyState == 1){ 
    	// remove all options from dropdown and change 
    	// value to "Loading..."

		var optionLen = objSelectDistrict.options.length;
		//alert(optionLen);

		// remove all options from dropdown and change
    	for (var i = 1 ; i <= optionLen ; i = i + 1 ){
        	objSelectDistrict.options[0] = null;
			//alert(i);
		}
		
    	var option = document.createElement("OPTION");
		objSelectDistrict.options.add(option);
 		option.innerHTML = "Loading...";
	
    }

  // move forward only if the transaction has completed
  if (xmlHttp.readyState == 4) 
  {
	  	
    // status of 200 indicates the transaction completed successfully
    if (xmlHttp.status == 200) 
    {
		var optionLen = objSelectDistrict.options.length;
		//alert(optionLen);

		// remove all options from dropdown and change
    	for (var i = 1 ; i <= optionLen ; i = i + 1 ){
        	objSelectDistrict.options[0] = null;
			//alert(i);
		}
		      
	  // extract the XML retrieved from the server
      xmlResponse = xmlHttp.responseXML;
	 // alert(xmlResponse);

	  parseDistrictResponseXML(xmlResponse);
    } 
    // a HTTP status different than 200 signals an error
    else 
    {
      alert("There was a problem accessing the server: " + xmlHttp.statusText);
    }
  }
}

// Parse the District response XML and add District dropdown options
function parseDistrictResponseXML(xmlResponse){
	rootNode = xmlResponse.documentElement;
	if (rootNode == null)
		alert("Could not retrieve Districts");
	else{
	    objSelectDistrict =  document.DODSForm.selDistrict;
		DistrictNodes = rootNode.getElementsByTagName('District');
		if( DistrictNodes.length > 0){
			var option = document.createElement("OPTION");
			
			objSelectDistrict.options.add(option);
			option.innerHTML = "..Select District..";
			option.value = "0";
		
			for (var i = 0 ; i < DistrictNodes.length ; i++) {
				District = DistrictNodes[i];
				var DistrictId = District.getElementsByTagName('id')[0].firstChild.nodeValue;
				var DistrictName = District.getElementsByTagName('name')[0].firstChild.nodeValue;
				var option = document.createElement("OPTION");
				objSelectDistrict.options.add(option);
				option.value = DistrictId;
				option.innerHTML = DistrictName;
			}
		}
	}
}   

//make asynchronous HTTP request using the XMLHttpRequest object 
function runCropModelHistorical()
{
    // retrieve the values selected by the user on the form
	xmlHttp = createXmlHttpRequestObject();
    countryNo = document.DODSForm.selCountry.value;
    stateNo = document.DODSForm.selState.value;
    districtNo = document.DODSForm.selDistrict.value;
    cropName = document.DODSForm.selCropName.value;
    generalCirculationModel = document.DODSForm.selGeneralCirculationModel.value;
	if( countryNo == "0" || stateNo == "0" || districtNo == "0" || cropName == "0" || generalCirculationModel == "0" ){
		//Do Nothing
		
	}else{
		xmlHttp.open("GET", "jsp/GetCropModel.jsp?cntryNo=" + countryNo + "&stateNo=" + stateNo + "&districtNo=" + districtNo + "&cropName=" + cropName, true);
		// define the method to handle server responses
		xmlHttp.onreadystatechange = handleModelResponse;
		// make the server request
		xmlHttp.send(null);
		simulationOutput = document.getElementById("simulationOutput");
		simulationOutput.style.display = 'none';
		simulationLoader = document.getElementById("simulationLoader");
		simulationLoader.innerHTML = '<img src="images/loading.gif" width="400">';
		setTimeout('showSimulationOutput()', 120000); // 3 min
	}
}

function handleModelResponse() 
{
	if(xmlHttp.readyState == 1){ 
		
	}
	if (xmlHttp.readyState == 4) 
	  {
		if (xmlHttp.status == 200) 
	    {
		 // extract the XML retrieved from the server
		  xmlResponse = xmlHttp.responseXML;
		  parseModelResponseXML(xmlResponse);
	    }
	  }
}

//Parse the Model response XML and add District dropdown options
function parseModelResponseXML(xmlResponse){
	rootNode = xmlResponse.documentElement;
	xmlHttp = createXmlHttpRequestObject();
	if (rootNode == null)
		alert("Could not retrieve Districts");
	else{
	    objSelectDistrict =  document.DODSForm.selDistrict;
		DistrictNodes = rootNode.getElementsByTagName('District');
		//alert("Number of Districts: " + DistrictNodes.length);
		xmlHttp.open('GET','HelloServlet', true);
		xmlHttp.send(null);
		if( DistrictNodes.length > 0){
			var option = document.createElement("OPTION");
			
			objSelectDistrict.options.add(option);
			option.innerHTML = "..Select District..";
			option.value = "0";
		
			for (var i = 0 ; i < DistrictNodes.length ; i++) {
				District = DistrictNodes[i];
				var DistrictId = District.getElementsByTagName('id')[0].firstChild.nodeValue;
				var DistrictName = District.getElementsByTagName('name')[0].firstChild.nodeValue;
				var option = document.createElement("OPTION");
				objSelectDistrict.options.add(option);
				option.value = DistrictId;
				option.innerHTML = DistrictName;
			}
		}
	}
}  

function showSimulationOutput(){
	simulationLoader = document.getElementById("simulationLoader");
	simulationLoader.innerHTML = "";
	simulationOutput = document.getElementById("simulationOutput");
	simulationOutput.style.display = 'block';
	//setTimeout('runCropModelHistorical()', 10000);	// 30 Sec
}