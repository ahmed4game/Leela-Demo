
/* Auto show of TV full screen */
var idleInterval = undefined;
var idleTimeoutDuration = 30000;
rightcontentidleFrom = new Date();  ////Idle time show TV full screen
//idleInterval = setInterval(Fn_timer_hiderightcontent, idleTimeoutDuration);

var levelIndicator = [0, 0];



//debugger;
	var elementofnumber = 0;	
	
	var str_fromurl1 = queryString('fromurl').trim();
	if (str_fromurl1=='accomodation') 
	{
		levelIndicator[elementofnumber] = 1;
	}
	if (str_fromurl1=='dinning')
	{
		levelIndicator[elementofnumber] = 2;
	}
	if (str_fromurl1=='meeting')
	{
		levelIndicator[elementofnumber] = 3;
	}
	if (str_fromurl1=='fitness')
	{
		levelIndicator[elementofnumber] = 4;
	}
	if (str_fromurl1=='pool')
	{
		levelIndicator[elementofnumber] = 5;
	}

	
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
					if (keyCode == 37) {  //Left arrow key
					
						var totalsubmenu = $(".middle_pannel a").size();
            			$(".middle_pannel a").removeClass("test123");
            			if (levelIndicator[elementofnumber] - 1 < 0) {
                			levelIndicator[elementofnumber] = totalsubmenu-1;
						}else
						{
						levelIndicator[elementofnumber]--;
						}
		
						$(".middle_pannel a").eq(levelIndicator[elementofnumber]).addClass("test123");
					
					
					}
       			
				if (keyCode == 39) {  //Right arrow key
					//debugger;
				//alert(levelIndicator[elementofnumber]);
						var totalsubmenu = $(".middle_pannel a").size();
						
            			$(".middle_pannel a").removeClass("test123");
            			if (Number(levelIndicator[elementofnumber]) + 1 >= totalsubmenu) {
                			levelIndicator[elementofnumber] = 0;
						}else{
							levelIndicator[elementofnumber]++;
						}	
						
						$(".middle_pannel a").eq(levelIndicator[elementofnumber]).addClass("test123");
					
					
				}
			
	/*		if (keyCode == 38) {   //UP key
				
				elementofnumber=1;
				levelIndicator[elementofnumber]=0;
				$(".middle_pannel a").removeClass("test123");
				$(".middle_image_pannel a").eq(levelIndicator[elementofnumber]).addClass("workout123");
				}
				
				
			if (keyCode == 40) {   //DOWN key
				
				elementofnumber=0;
				levelIndicator[elementofnumber]=0;
				$(".middle_image_pannel a").removeClass("workout123");
				$(".middle_pannel a").eq(levelIndicator[elementofnumber]).addClass("test123");
				
				}
		
				*/
			if (keyCode == 13) {
					var levelName="";
					
					levelName = $(".middle_pannel .centertabtxt").eq(levelIndicator[elementofnumber]).text().trim();
					
					appsonclick(levelName);
			
				}
				console.log("diff:"+diff);
previous = Date.now();
        				 }
	 e.preventDefault();	
		
});

function appsonclick (levelName) {
	//alert(levelName);
			
			if (levelName === "About us") { 
				window.location = "aboutus.htm";
			}
			if (levelName === "Accommodation") { 					
				window.location = "accomodation.htm";
			}
			if (levelName === "Dining") { 					
				window.location = "dinning.htm";
			}
			if (levelName === "Meeting") { 
				window.location = "meeting.htm";
			}
			if (levelName === "Fitness") { 
				window.location = "fitness.htm";
			}			
			if (levelName === "Pool") { 
				window.location = "pool.htm";
			}
	
}
