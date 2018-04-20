   /* This Function has been used for getting the user name password from the URL*/
        function PageQuery(q)
        {
	        if(q.length > 1) this.q = q.substring(1, q.length);
	        else this.q = null;
	        this.keyValuePairs = new Array();
		    if(q) 
	            {
	    	        for(var i=0; i < this.q.split("&").length; i++)
		        {
		    	    this.keyValuePairs[i] = this.q.split("&")[i];
		        }
	    }
	     this.getKeyValuePairs = function() 
		        { 
		            return this.keyValuePairs; 
		        }       
	     this.getValue = function(s)
	            {
		            for(var j=0; j < this.keyValuePairs.length; j++)
		        {       
			        if(this.keyValuePairs[j].split("=")[0] == s)
			        return this.keyValuePairs[j].split("=")[1];
		        }
		            return 'false';
	    }
	    this.getParameters = function() 
	   {
	    	var a = new Array(this.getLength());
		   for(var j=0; j < this.keyValuePairs.length; j++) 
		  {
			a[j] = this.keyValuePairs[j].split("=")[0];
		  }
		  return a;
	    }
		this.getLength = function() 
		{
		 return this.keyValuePairs.length; 
		}
	}
	/* USer name and Password capturing function has finished*/

function queryString(key)
	{
		var page = new PageQuery(window.location.search);
		return unescape(page.getValue(key));
	}
	
	// function for voice recognition and navigation // touchcloud - 27-05-2017
	function openPageFromVoice(command)
	{
		console.log("command   "+command);
		// if you want to show the voice command as a toast message in app uncomment the below line
		//voice_recognizer.showMessage("TextFromPage: ("+command+")");
		if ((command.indexOf('info') !== -1)  || (command.indexOf('hotel') !== -1))
		{
			window.location = "hotelinfo.html";	
		}
		else if (command.indexOf('about') !== -1)
		{
			window.location = "aboutus.htm";	
		}
		else if ((command.indexOf('rooms') !== -1) || (command.indexOf('accommodation') !== -1))
		{
			window.location = "accomodation.htm";	
		}
		else if ((command.indexOf('dining') !== -1) || (command.indexOf('restaurant') !== -1))
		{
			window.location = "dinning.htm";	
		}
		else if ((command.indexOf('hungry') !== -1) || (command.indexOf('food') !== -1))
		{
			window.location = "dinning.htm";	
		}
		else if ((command.indexOf('meeting') !== -1)  || (command.indexOf('conference') !== -1))
		{
			window.location = "meeting.htm";	
		}
		else if ((command.indexOf('fitness') !== -1) || (command.indexOf('gym') !== -1))
		{
			window.location = "fitness.htm";	
		}
		else if ((command.indexOf('movie') !== -1) || (command.indexOf('video') !== -1))
		{
			window.location = "ivod.html";	
		}		
	/*	else if (command.indexOf('share') !== -1)
		{
			window.location = "smartshare.html";	
		} */
		else if (command.indexOf('help') !== -1)
		{
			window.location = "help.htm";	
		}
	/*	else if (command.indexOf('laptop') !== -1)
		{
			window.location = "smartsharelaptop.html";	
		}
		else if ((command.indexOf('ios') !== -1)  || (command.indexOf('ipad') !== -1)  || (command.indexOf('ihone') !== -1))
		{
			window.location = "smartshareiphone.html";	
		} */
		else if ((command.indexOf('pool') !== -1)  || (command.indexOf('swimming') !== -1))
		{
			window.location = "pool.htm";	
		}
		else if ((command.indexOf('home') !== -1))
		{
			window.location = "index.html";	
		}
		
		
	}