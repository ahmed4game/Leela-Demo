		$(".Privacy_info").hide();
		$(".clspromomessage").hide();
		
	function messageshowing(argmessage)
		{
			$(".Privacy_info").show();
			$('#messagecontent').text(argmessage); //argmessge
			
		}
		function promotionshowing(imageurl,argmessage)
		{
			$(".clspromomessage").show();
			$("#promoimageid").attr("src",imageurl);
			$('.clstext').text(argmessage); //argmessge
			
		}
	function testjsjava()
	{
		if ($('.Privacy_info').is(':visible')){
			$('.Privacy_info').hide();
		}
		if ($('.clspromomessage').is(':visible')){
			$('.clspromomessage').hide();
		}
	
	}
	function soucestatusjsjava()
	{
		htmlsourcestatus=1;	
	}
	function changesoucestatus()
	{
		htmlsourcestatus=0;	
	}
	
	function fnMsgWindowClose(msgWindow)
	{
		$("#msgtbl").show();
		if(msgWindow == 'Privacy_info')
			$(".Privacy_info").hide();
		else
			$(".clspromomessage").hide();
		var tmpMsgTbl = document.getElementById("msgtbl");
		tmpMsgTbl.style="visibility: visible;";
		
		fnReadMessage();
	}
	
	function displayMsgIcon(hideshow)
	{
		//console.log('I am called');
		/*var t = document.getElementById("msgiconid");
		hideshow = t.style.visibility;
		//alert(hideshow);
		console.log('I am visible' + hideshow);
		if(hideshow == "hidden")
			t.style="visibility: visible;";
		else
			t.style="visibility: hidden;";		*/
			$('#msgiconid').show();
	}
	function mydisplayMsgIcon()
	{
		$('#msgiconid').hide();
	}
	
	function fnReadMessage()
	{
		
		var jqxhr = $.get( "showmessage1.aspx", {ip : "192.168.22.64"}, function(data) {
		  document.getElementById("msgtbl").innerHTML=data;
		  $('#msgtbl table tbody tr:eq('+rowcount1+')').addClass('highlight2');
		})
		
	}
	/*function fnShowMsg(msgType, txtMessage, imagepath)
	{
		var tmpMsgTbl = document.getElementById("msgtbl");
		hideshow = tmpMsgTbl.style.visibility;
		tmpMsgTbl.style="visibility: hidden;";
		if(msgType == "message")
			messageshowing(txtMessage);
		else
			promotionshowing(imagepath,txtMessage);
	}*/
	function fnShowMsg(msgType, txtMessage, imagepath, RoomID, MessageID, ReadStatus)
	{
		var tmpMsgTbl = document.getElementById("msgtbl");
		hideshow = tmpMsgTbl.style.visibility;
	    tmpMsgTbl.style="visibility: hidden;";
	    if (ReadStatus != "True") {
	        fnMsgRead(RoomID, MessageID);
	    }
		if(msgType == "message")
			messageshowing(txtMessage);
		else
			promotionshowing(imagepath,txtMessage);
	}
	function fnMsgRead(RoomID, MessageID) {
	    $.ajax({
	        type: "GET",
	        url: "handlers/Action.aspx?action=UpdateMessageReadStatus&roomID=" + RoomID + "&msgID=" + MessageID,
	        contentType: "application/json",
	        dataType: "json",
	        async: false,
	        cache: false,
	        success: function (jsondata) {
	        }
	    });
	}
	function checkMessage()
	{
	
		//check message
		var tmpMsgTbl = document.getElementById("msgtbl");
		hideshow = tmpMsgTbl.style.visibility;
		if(hideshow == "visible")
			tmpMsgTbl.style="visibility: hidden;";
		else
		{
		
			fnReadMessage();
			tmpMsgTbl.style="visibility: visible;";	
		}
	}	