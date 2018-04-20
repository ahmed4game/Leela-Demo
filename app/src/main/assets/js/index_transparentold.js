
	var elementcount = 0;
	var htmlsourcestatus=0;
	var max = 6;
	$("#support_wrapper").hide();
	
	var str_fromurl1 = queryString('fromurl').trim();
	if (str_fromurl1=='ivod')
	{
		elementcount = 2;
	}

	if (str_fromurl1=='hinfo')
	{
		elementcount = 1;
	}
	

	if (str_fromurl1=='browser')
	{
		elementcount = 3;
	}
	if (str_fromurl1=='smart')
	{
		elementcount = 4;
		//console.log('kaeman Messagare :'+ elementcount);
	}
	if (str_fromurl1=='help')
	{
		elementcount = 5;
	}

/*	if (str_fromurl1=='userid')
	{
		elementcount = 2;
	} */

	$(".middle_pannel a").eq(elementcount).addClass("test123");
	if(elementcount==0)
	{
		$('.prev').hide();
	}
	/*if(elementcount>=6)
	{
		$(".middle_pannel ul li").eq(0).hide();
		$(".middle_pannel ul li").eq(1).hide();
		$(".middle_pannel ul li").eq(2).hide();

	}
	else
	{
		$(".middle_pannel ul").each(function(){

  			if ($(this).find("li").length > max) {
    		$(this)
      			.find('li:gt('+max+')')
      			.hide()
			}
			});
	}
	*/


//IndexKeyHandler: function(e) {
	$(document).keyup(function(e) {
				/*AUTO HIDE HANDLER */
				menuidleFrom = new Date();

				//$("#support_wrapper").hide();
				//$("#checkoutmess").hide();
				var keyCode = e.keyCode;
				

				//console.log("Key code of " + keyCode);
			//	console.log("HTML SOURCE STATS  " + htmlsourcestatus);
				//homescreenpage.htmlkeycodehandler(keyCode);
				//alert('IndexKeyHandler  ' + keyCode);
				//document.getElementById("spkmsg").innerHTML ="Key code of " + keyCode;
				if (!$('#menu_pannel_bg').is(':visible'))
				{
					$('#menu_pannel_bg').show();
					$(".bottom_pannel_right").show();
				}
				if (keyCode == 121) {     /* POWER BUTTON pressed */
					TVmode1(0);
				}

				rightcontentidleFrom = new Date();
				if (keyCode == 37) {   ///LEFT Arrow

  //your code to be executed after 1 second
  if (htmlsourcestatus==1)
							homescreenpage.htmlkeycodehandler('37');
					else{
						var totalsubmenu = $(".middle_pannel a").size();
						//alert(elementcount);
						$(".middle_pannel a").removeClass("test123");
						if(elementcount<0){
							elementcount=	totalsubmenu-1;
						}else{
							elementcount--;

						}
						$(".middle_pannel a").eq(elementcount).addClass("test123");
					}

					
				}

				//if (keyCode == 40) {
				if (keyCode == 39) {  ////RIGHT arrow
				//console.log("htmlsourcestatus  "+ htmlsourcestatus );
				
  //your code to be executed after 1 second
  if (htmlsourcestatus==1)
						homescreenpage.htmlkeycodehandler('39');
					else{
						var totalsubmenu = $(".middle_pannel a").size();
						$(".middle_pannel a").removeClass("test123");
						
						console.log("Ahmed elementcount  "+ elementcount + "  totalsubmenu  " + totalsubmenu);
						
						if(elementcount+1>=totalsubmenu)
							elementcount=0;
						else
							elementcount++;

						console.log("htmlsourcestatus  "+ elementcount );
						$(".middle_pannel a").eq(elementcount).addClass("test123");

					}

					

				  }
				if (keyCode == 33) {     /* CH+ */
					homescreenpage.htmlkeycodehandler('33');
				}
				if (keyCode == 34) {     /* CH+ */
					homescreenpage.htmlkeycodehandler('34');
				}
				/*if (keyCode == 228 && htmlsourcestatus==1) {     /* Media Next key HOME test */
			/*		htmlsourcestatus=0;
					homescreenpage.htmlkeycodehandler('228');
				}*/
				if (keyCode == 38) {     /* UP arrow */
					homescreenpage.htmlkeycodehandler('38');
				}
				if (keyCode == 40) {     /* Down arrow */
					homescreenpage.htmlkeycodehandler('40');
				}
				if (keyCode == 49) {     /* No 1 */
					homescreenpage.htmlkeycodehandler('1');
				}
				if (keyCode == 50) {     /* No 2 */
					homescreenpage.htmlkeycodehandler('2');
				}
				if (keyCode == 51) {     /* No 3 */
					homescreenpage.htmlkeycodehandler('3');
				}
				if (keyCode == 52) {     /* No 4 */
					homescreenpage.htmlkeycodehandler('4');
				}
				if (keyCode == 53) {     /* No 5 */
					homescreenpage.htmlkeycodehandler('5');
				}
				if (keyCode == 54) {     /* No 6 */
					homescreenpage.htmlkeycodehandler('6');
				}
				if (keyCode == 55) {     /* No 7 */
					homescreenpage.htmlkeycodehandler('7');
				}
				if (keyCode == 56) {     /* No 8 */
					homescreenpage.htmlkeycodehandler('8');
				}
				if (keyCode == 57) {     /* No 9 */
					homescreenpage.htmlkeycodehandler('9');
				}
				if (keyCode == 48) {     /* No 0 */
					homescreenpage.htmlkeycodehandler('0');
				}
				if (keyCode == 122 ) {     /* No 1 Mike event*/ 
						homescreenpage.launchvoicerecognizor('122');
				}
				//if (keyCode == tvKey.KEY_ENTER) {
				if (keyCode == 13) {
							var levelName = $(".middle_pannel .txt_info").eq(elementcount).text().trim();
							levelName =levelName.trim();
							//alert(levelName);
						
							if (htmlsourcestatus==1)
								homescreenpage.htmlkeycodehandler('ok');
							else
								buttonclichhandler(levelName);
					}
			//	}
	/*	if (htmlsourcestatus==1)
		{
			if (keyCode == 13) {
				homescreenpage.okbuttonpress('okbutton');
			}
		} */
		 e.preventDefault();

});
var statusinternet='';
function redirecttoapp(){
	console.log('inside  redirecttoapp');
	if (statusinternet!='' && statusinternet =='no')
	{
		homescreenpage.InternetActivitycall('');
	}



}
var checkinstatus ='yes';
var varcheckinroom ='';
function buttonclichhandler (levelName) {
		console.log(levelName);
		
		if (levelName == "BROWSER" || levelName == "MOVIES AND VIDEOS" || levelName == "SHARE CONTENT") {		
			if (checkinstatus == 'no') {	
				
				$("#checkoutmess").show();

				function fnhidebottombanner() {
					$("#checkoutmess").hide();
					clearTimeout(myVar);
				}

				var myVar;
				myVar = setTimeout(fnhidebottombanner, 10000);
				return;
			}	
		}
		
		if (levelName == "TV CHANNELS") { // Channels
			//fnupdate_status('TV channels clicked');
			homescreenpage.htmlkeycodehandler('tv');
		}
		if (levelName == "MOVIES AND VIDEOS") {
				window.location = "ivod.html";
		}
		if (levelName == "HOTEL INFO") { // Hotel INFO
			window.location = "hotelinfo.html";
		}
		if (levelName == "SHARE CONTENT") {
			window.location = "smartshare.html";
		}
		if (levelName == "BROWSER") {
				homescreenpage.launchappfromhtml('BROWSER');
		}
		if (levelName == "HELP") {
				homescreenpage.launchappfromhtml('helpresetadb');
				window.location = "help.htm";
		}
	}

var idleInterval = undefined;
var menubaridleInterval = undefined;
var setintervalcheckin1 = undefined;
var banneridleInterval = undefined;

var menubaridleTimeoutDuration = 30000;
var checkinidle = 30000;
var menuidleFrom = new Date();
/* Display HIDE RIGHT content */

function fn_hide_menubar () {
			$("#menu_pannel_bg").hide();
			$(".bottom_pannel_right").hide();


}
function fn_show_menubar () {
			rightcontentidleFrom = new Date();

			if (!$('#menu_pannel_bg').is(':visible'))
			{
					$('#menu_pannel_bg').show();
					$(".bottom_pannel_right").show();
			}
}

function Fn_timer_menu() {
    var timeElapsed = (new Date()).getTime() - menuidleFrom.getTime();
    if (timeElapsed > menubaridleTimeoutDuration) {
		fn_hide_menubar();
    }

}

//menubaridleInterval = setInterval(Fn_timer_menu, menubaridleTimeoutDuration);

function Fn_timer_checkin() {
 
   fnwelcome_message();
}

setintervalcheckin1 = setInterval(Fn_timer_checkin, checkinidle);


	var thismsgcount=0;

function fngetweather1() {

	$.simpleWeather({
    location: 'Ithaca',
    woeid: '',
    unit: 'f',
    success: function(weather) {
      html = '<h2><i></i> '+weather.temp+'&deg;'+weather.units.temp+'</h2>';
      html += '<ul><li>'+weather.city+'</li>';
      html += '<li class="currently">'+weather.currently+'</li>';
      html += '<li>'+weather.wind.direction+' '+weather.wind.speed+' '+weather.units.speed+'</li></ul>';

      $("#weather").html(html);
    },
    error: function(error) {
      $("#weather").html('<p>'+error+'</p>');
    }
  });

}