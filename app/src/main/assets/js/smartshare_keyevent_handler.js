
	$(".middle_pannel a").eq(elementcount).addClass("test123");
	
		
$(document).keyup(function(e) {
	
				var keyCode = e.keyCode;
				//alert(keyCode);
               	if (keyCode == 122 ) {     /* No 1 Mike event*/ 
						homescreenpage.launchvoicerecognizor('122');
				}
				//if (keyCode == 38) {
				if (keyCode == 37) {   ///LEFT Arrow
		
					Redirectdecider ('left');	
					
				}
       			//if (keyCode == 40) {
				if (keyCode == 39) {  ////RIGHT arrow
				
					Redirectdecider ('right');
				}
		
				//if (keyCode == tvKey.KEY_ENTER) {
			if (keyCode == 13) {
				//var levelName = $(".middle_pannel div p").eq(elementcount).text();
				var levelName = $(".middle_pannel .centertabtxt").eq(elementcount).text();
				levelName =levelName.trim();
				//alert(levelName);
				appsonclick(levelName);
				}
			 e.preventDefault();		
		
});
function appsonclick (levelName) {
		if (levelName == "Android") { // Android Device connectivity	
			homescreenpage.launchappfromhtml('miracast');		
		}
		if (levelName == "iPhone") { // Android Device connectivity	
			window.location = "smartshareiphone.html";	
		}
		if (levelName == "Laptop") { // Android Device connectivity	
			window.location = "smartsharelaptop.html";		
		}
}

function Redirectdecider (levelName) {
		var currentLocation = String(window.location);		
		//alert(""+ currentLocation + " " + levelName);
		if ((currentLocation.indexOf('smartshare') !== -1)  && (levelName == "left"))
		{
			window.location = "smartsharelaptop.html";	
		}
		if ((currentLocation.indexOf('smartshare') !== -1)  && (levelName == "right"))
		{
			window.location = "smartshareiphone.html";	
		}
		if ((currentLocation.indexOf('iphone') !== -1) && (levelName == "left"))
		{
			window.location = "smartshare.html";	
		}
		if ((currentLocation.indexOf('iphone') !== -1) && (levelName == "right"))
		{
			window.location = "smartsharelaptop.html";	
		}
		if ((currentLocation.indexOf('laptop') !== -1) && (levelName == "left"))
		{
			window.location = "smartshareiphone.html";	
		}
		if ((currentLocation.indexOf('laptop') !== -1) && (levelName == "right"))
		{
			window.location = "smartshare.html";	
		}
		
}