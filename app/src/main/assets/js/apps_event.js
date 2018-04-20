var elementcount = 0;

/* Auto show of TV full screen */
var idleInterval = undefined;
var idleTimeoutDuration = 60000;
rightcontentidleFrom = new Date();  ////Idle time show TV full screen
idleInterval = setInterval(Fn_timer_hiderightcontent, idleTimeoutDuration);

function Fn_timer_hiderightcontent() {
    var timeElapsed = (new Date()).getTime() - rightcontentidleFrom.getTime();
    if (timeElapsed > idleTimeoutDuration) {
	/*	if (!$('#timer_app').is(":visible"))
		{ */
		//fnsetfullVideoSize();
	 	//window.location = "tv.html";
	//	}
    }

}
/* Auto show of TV full screen */


	$(".middle_pannel a").eq(elementcount).addClass("test123");

var previous=0
$(document).keydown(function(e) {
	rightcontentidleFrom = new Date();  ////Idle time show TV full screen
				var keyCode = e.keyCode;
				//alert(keyCode);
				 var diff = Date.now() - previous;
            		 
            if(diff > 450){
				if (keyCode == 122 ) {     /* No 1 Mike event*/ 
						homescreenpage.launchvoicerecognizor('122');
				}
				//if (keyCode == 38) {
				if (keyCode == 37) {   ///LEFT Arrow
					//alert(keyCode);
					//rightcontentidleFrom = new Date();  ////Idle time show TV full screen
				 	var totalsubmenu = $(".middle_pannel a").size();
            		$(".middle_pannel a").removeClass("test123");
            		if (elementcount - 1 < 0) {
                		elementcount = totalsubmenu-1;
					}else
					{
					elementcount--;
					}


					$(".middle_pannel a").eq(elementcount).addClass("test123");

				}
       			//if (keyCode == 40) {
			if (keyCode == 39) {  ////RIGHT arrow
					//rightcontentidleFrom = new Date();  ////Idle time show TV full screen
					var totalsubmenu = $(".middle_pannel a").size();
            		$(".middle_pannel a").removeClass("test123");
            		if (Number(elementcount) + 1 >= totalsubmenu) {
                		elementcount = 0;
					}else{
						elementcount++;
					}

						$(".middle_pannel a").eq(elementcount).addClass("test123");

				}
		
			
				//if (keyCode == tvKey.KEY_ENTER) {
			if (keyCode == 13) {
					//var levelName = $(".middle_pannel .centertabtxt").eq(elementcount).text().trim();
					var levelName = $(".middle_pannel .txt_info").eq(elementcount).text().trim();
					//document.getElementById("spkmsg").innerHTML ="Key pressed: " + levelName;
					levelName =levelName.trim();
					//alert(levelName);
					if (levelName == "Prev") { 
						window.location = "index.html?fromurl=smart";
					}
					appsonclick(levelName);
				

				}
					console.log("diff:"+diff);
previous = Date.now();
        	}
		 e.preventDefault();
});
	
		function appsonclick (levelName) {
		//alert(levelName);
       				
					if (levelName == "NETFLIX") { // Netflix
						//document.getElementById("spkmsg").innerHTML +="<br>Indide: " + levelName;
						homescreenpage.launchappfromhtml('netflix');							
					}
	
					if (levelName == "YOUTUBE") { // youtube
						homescreenpage.launchappfromhtml('YouTube');
					}
					if (levelName == "VIMEO") { // Vimeo
						homescreenpage.launchappfromhtml('Vimeo');
					}
					if (levelName == "HULU") { // Hotstar
						homescreenpage.launchappfromhtml('hulu');
					}					
					if (levelName == "AMAZON VIDEOS") { // iPhone Device connectivity
						homescreenpage.launchappfromhtml('amazon');
					}
					
    }

  
function readCookie(name) {
    var nameEQ = name + "=";
    var ca = document.cookie.split(';');
    for(var i=0;i < ca.length;i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1,c.length);
        if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length,c.length);
    }
    return null;
}
