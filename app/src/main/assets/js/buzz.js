
/* Auto show of TV full screen */
var idleInterval = undefined;
var idleTimeoutDuration = 30000;
rightcontentidleFrom = new Date();  ////Idle time show TV full screen
idleInterval = setInterval(Fn_timer_hiderightcontent, idleTimeoutDuration);

function Fn_timer_hiderightcontent() {
    var timeElapsed = (new Date()).getTime() - rightcontentidleFrom.getTime();
    if (timeElapsed > idleTimeoutDuration) {
	
    }

}	
/* Auto show of TV full screen */	

$(document).keydown(function(e) {
	
				var keyCode = e.keyCode;
				//alert(keyCode);
				if (keyCode == 122 ) {     /* No 1 Mike event*/ 
						homescreenpage.htmlkeycodehandler('122');
				}
				if (keyCode == 39) // Right arrow key
				{
					slider.next();
				}
				if (keyCode == 37) // Left arrow key
				{
					slider.prev();
				}
		 e.preventDefault();		
				
		
});


	
