

$(document).keydown(function(e) {
	//rightcontentidleFrom = new Date();  ////Idle time show TV full screen
				var keyCode = e.keyCode;
				//alert(keyCode);

                	if (keyCode == 122 ) {     /* No 1 Mike event*/ 
						homescreenpage.launchvoicerecognizor('122');
					}
	e.preventDefault();
		
});