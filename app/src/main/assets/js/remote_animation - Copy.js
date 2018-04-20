
        var home_anim = function(){
    
            $('#home_section .circle').animate({width : '50px' , opacity: '1' , height : '50px'} , 2000);
            
            $('#home_section .circle img').animate({width : '50px' , opacity: '1' , height : '50px'} , 2000);   
            
            $('#home_section .line_txt span').delay(2000).animate({width : '150px'} , 1000);     
            
            $('#home_section .line_txt h1').delay(2000).animate({opacity : '1'  } , 1000);  
         
        }
        
        
         var home_anim_off = function(){
    
            $('#home_section .circle').animate({width : '0px' , opacity: '0' , height : '0px'} , 500);
            
            $('#home_section .circle img').animate({width : '0px' , opacity: '0' , height : '0px'} , 500);   
            
            $('#home_section .line_txt span').animate({width : '0px'} , 500);     
            
            $('#home_section .line_txt h1').animate({opacity : '0'  } , 500);  
         
        }
        
        
        
        
         function blink1(){
            
         $('#home_section .circle').animate({opacity : '1'} , 200);
                  
         $('#home_section .circle').animate({opacity : '.7'} , 200);
        
        }        
        
        home_anim();
        
        var startTime = new Date().getTime();
        
        var interval = setInterval(function(){ 
        
                blink1();      
        
            if(new Date().getTime() - startTime > 6000){
                clearInterval(interval);
                return;
            }
        
        
         }, 1000);
        
                
         setTimeout(home_anim_off, 6000);
         
         /* MIKE $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ */
         
         
         
         var mike_anim = function(){
    
            $('#mike_section .circle').animate({width : '50px' , opacity: '1' , height : '50px'} , 2000);
            
            $('#mike_section .circle img').animate({width : '50px' , opacity: '1' , height : '50px'} , 2000);   
            
            $('#mike_section .line_txt span').delay(2000).animate({width : '150px'} , 1000);     
            
            $('#mike_section .line_txt h1').delay(2000).animate({opacity : '1'  } , 1000);  
         
        }
        
        
         var mike_anim_off = function(){
    
            $('#mike_section .circle').animate({width : '0px' , opacity: '0' , height : '0px'} , 500);
            
            $('#mike_section .circle img').animate({width : '0px' , opacity: '0' , height : '0px'} , 500);   
            
            $('#mike_section .line_txt span').animate({width : '0px'} , 500);     
            
            $('#mike_section .line_txt h1').animate({opacity : '0'  } , 500);  
         
        }
        
        
         function blink2(){
            
         $('#mike_section .circle').animate({opacity : '1'} , 200);
                  
         $('#mike_section .circle').animate({opacity : '.7'} , 200);
        
        }        
        
        
        
          setTimeout(mike_anim, 1000);
           
        
       
        
        var startTime1 = new Date().getTime();
        
        var interval2 = setInterval(function(){ 
        
                blink2();      
        
            if(new Date().getTime() - startTime1 > 6000){
                clearInterval(interval2);
                return;
            }
        
        
         }, 1000);
        
        
          setTimeout(mike_anim_off, 6000);
        
        
                
        
       
       
      