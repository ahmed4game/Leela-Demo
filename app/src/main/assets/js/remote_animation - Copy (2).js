       $(window).load(function() {
        
        
        function power_anim(num1){
         
    
            $('#home_section .circle').delay(num1).animate({width : '50px' , opacity: '1' , height : '50px'} , 500); 
            
            $('#home_section .circle img').delay(num1).animate({width : '50px' , opacity: '1' , height : '50px'} , 500);   
            
            $('#home_section .line_txt span').delay(num1).animate({width : '200px'} , 500);   
            
            $('#home_section .line_txt h1').delay(num1).animate({opacity : '1'  } , 500 , function() { });  
            
            
            
            // OFF ANIMATION
            
        }
         
        function power_anim_off(num1_1){ 
         
         $('#home_section .circle').delay(num1_1).animate({width : '0px' , opacity: '0' , height : '0px' , boder : 'solid 0px red'} , 500);
            
         $('#home_section .line_txt span').delay(num1_1).animate({width : '0px'} , 500);     
            
         $('#home_section .line_txt h1').delay(num1_1).animate({opacity : '0'  } , 500 , 'linear', function() {  });          
        
        }
        
                
        power_anim("1000");
             
        power_anim_off("4000");
           
        
          function navigation(num6){ 
    
            $('#navigation .circle').delay(num6).animate({width : '50px' , opacity: '1' , height : '50px'} , 500);
            
            $('#navigation .circle img').delay(num6).animate({width : '50px' , opacity: '1' , height : '50px'} , 500);   
            
            $('#navigation .line_txt span').delay(num6).animate({width : '200px'} , 500);     
            
            $('#navigation .line_txt h1').delay(num6).animate({opacity : '1'  } , 500 , function() {   });   
            
        }
         
                  
        function navigation_off(num6_6){ 
        
        
         $('#navigation .circle').delay(num6_6).animate({width : '0px' , opacity: '0' , height : '0px' , boder : 'solid 0px red'} , 500);
            
         $('#navigation .line_txt span').delay(num6_6).animate({width : '0px'} , 500);     
            
         $('#navigation .line_txt h1').delay(num6_6).animate({opacity : '0'  } , 500 , 'linear', function() {  });          
        
        }
        
        
         navigation("1000");
            
         navigation_off("4000");
         
         
         
         var mike_anim = function(num3){
         
         
           $('#mike_section .circle').delay(num3).animate({width : '50px' , opacity: '1' , height : '50px'} , 500);
            
            $('#mike_section .circle img').delay(num3).animate({width : '50px' , opacity: '1' , height : '50px'} , 500);   
            
            $('#mike_section .line_txt span').delay(num3).animate({width : '200px'} , 500);     
            
            $('#mike_section .line_txt h1').delay(num3).animate({opacity : '1'  } , 500 , function() {  });  
            
            
         
        }
        
        
         var mike_anim_off = function(num3_3){
         
         
          $('#mike_section .circle').delay(num3_3).animate({width : '0px' , opacity: '0' , height : '0px' , boder : 'solid 0px red'} , 500);
            
         $('#mike_section .circle img').delay(num3_3).animate({width : '0px'} , 500); 
         
          $('#mike_section .line_txt span').delay(num3_3).animate({width : '0px'} , 500);    
            
         $('#mike_section .line_txt h1').delay(num3_3).animate({opacity : '0'  } , 500 , 'linear', function() {  }); 
         
          
         
        }
        
        
       mike_anim("6000");
        
       mike_anim_off("4000");
        
        
        
        // Channel list
         
         
         function channel_list(num4){
         
    
            $('#channel_list .circle').delay(num4).animate({width : '50px' , opacity: '1' , height : '50px'} , 500);
            
            $('#channel_list .circle img').delay(num4).animate({width : '50px' , opacity: '1' , height : '50px'} , 500);   
            
            $('#channel_list .line_txt span').delay(num4).animate({width : '200px'} , 500);     
            
            $('#channel_list .line_txt h1').delay(num4).animate({opacity : '1'  } , 500 , function() {   });  
            
            // OFF ANIMATION
            
        }
         
         
        function channel_list_off(num4_4){ 
        
        
         $('#channel_list .circle').delay(num4_4).animate({width : '0px' , opacity: '0' , height : '0px' , boder : 'solid 0px red'} , 500);
            
         $('#channel_list .line_txt span').delay(num4_4).animate({width : '0px'} , 500);     
            
         $('#channel_list .line_txt h1').delay(num4_4).animate({opacity : '0'  } , 500 , 'linear', function() {   });          
        
        }
        
        
        channel_list("6000");
        
        channel_list_off("4000");
     
     
         
         function mouse_on(num2){
         
    
            $('#mouse_onoff .circle').delay(num2).animate({width : '50px' , opacity: '1' , height : '50px'} , 500);
            
            $('#mouse_onoff .circle img').delay(num2).animate({width : '50px' , opacity: '1' , height : '50px'} , 500);   
            
            $('#mouse_onoff .line_txt span').delay(num2).animate({width : '200px'} , 500);     
            
            $('#mouse_onoff .line_txt h1').delay(num2).animate({opacity : '1'  } , 500 , function() {   });  
            
            // OFF ANIMATION
            
        }
         
         
        function mouse_off(num2_2){ 
        
        
         $('#mouse_onoff .circle').delay(num2_2).animate({width : '0px' , opacity: '0' , height : '0px' , boder : 'solid 0px red'} , 500);
            
         $('#mouse_onoff .line_txt span').delay(num2_2).animate({width : '0px'} , 500);     
            
         $('#mouse_onoff .line_txt h1').delay(num2_2).animate({opacity : '0'  } , 500 , 'linear', function() {   });          
        
        }
        
        
       mouse_on("1000");
        
       mouse_off("4000");
             
         
        
       
         
         
         /* MIKE $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ */
         
         
         
         
        
        
        
        
        
        // RETURN
         
         
         function return_btn(num5){
         
    
            $('#return_btn .circle').delay(num5).animate({width : '50px' , opacity: '1' , height : '50px'} , 500);
            
            $('#return_btn .circle img').delay(num5).animate({width : '50px' , opacity: '1' , height : '50px'} , 500);   
            
            $('#return_btn .line_txt span').delay(num5).animate({width : '200px'} , 500);     
            
            $('#return_btn .line_txt h1').delay(num5).animate({opacity : '1'  } , 500 , function() {   });  
            
            // OFF ANIMATION
            
        }
         
         
        function return_btn_off(num5_5){ 
        
        
         $('#return_btn .circle').delay(num5_5).animate({width : '0px' , opacity: '0' , height : '0px' , boder : 'solid 0px red'} , 500);
            
         $('#return_btn .line_txt span').delay(num5_5).animate({width : '0px'} , 500);     
            
         $('#return_btn .line_txt h1').delay(num5_5).animate({opacity : '0'  } , 500 , 'linear', function() {   });          
        
        }
        
        
   return_btn("1000");
        
     return_btn_off("4000");
       
        
        
        
          function home_btn(num8){
         
    
            $('#home_btn .circle').delay(num8).animate({width : '50px' , opacity: '1' , height : '50px'} , 500);
            
            $('#home_btn .circle img').delay(num8).animate({width : '50px' , opacity: '1' , height : '50px'} , 500);   
            
            $('#home_btn .line_txt span').delay(num8).animate({width : '200px'} , 500);     
            
            $('#home_btn .line_txt h1').delay(num8).animate({opacity : '1'  } , 500 , function() {   });  
            
            // OFF ANIMATION
            
        }
         
         
        function home_btn_off(num8_8){ 
        
        
         $('#home_btn .circle').delay(num8_8).animate({width : '0px' , opacity: '0' , height : '0px' , boder : 'solid 0px red'} , 500);
            
         $('#home_btn .line_txt span').delay(num8_8).animate({width : '0px'} , 500);     
            
         $('#home_btn .line_txt h1').delay(num8_8).animate({opacity : '0'  } , 500 , 'linear', function() {   });          
        
        }
        
        
   home_btn("1000");
        
    home_btn_off("4000");
        
        
          // Mouse anim
         
         
       
          function anim_mouse(num9){
         
    
            $('#mouse2 .circle').delay(num9).animate({width : '50px' , opacity: '1' , height : '50px'} , 500);
            
            $('#mouse2 .circle img').delay(num9).animate({width : '50px' , opacity: '1' , height : '50px'} , 500);   
            
            $('#mouse2 .line_txt span').delay(num9).animate({width : '250px'} , 500);     
            
            $('#mouse2 .line_txt h1').delay(num9).animate({opacity : '1'  } , 500 , function() {   });  
            
            // OFF ANIMATION
            
        }
         
         
        function anim_mouse_off(num9_9){ 
        
        
         $('#mouse2 .circle').delay(num9_9).animate({width : '0px' , opacity: '0' , height : '0px' , boder : 'solid 0px red'} , 500);
            
         $('#mouse2 .line_txt span').delay(num9_9).animate({width : '0px'} , 500);     
            
         $('#mouse2 .line_txt h1').delay(num9_9).animate({opacity : '0'  } , 500 , 'linear', function() {   });          
        
        }
        
        
   anim_mouse("1000");
        
   anim_mouse_off("4000");
   
   
   // Channel Up
   
   
     function channel_up(num10){
         
    
            $('#channel_up .circle').delay(num10).animate({width : '50px' , opacity: '1' , height : '50px'} , 500);
            
            $('#channel_up .circle img').delay(num10).animate({width : '50px' , opacity: '1' , height : '50px'} , 500);   
            
            $('#channel_up .line_txt span').delay(num10).animate({width : '150px'} , 500);     
            
            $('#channel_up .line_txt h1').delay(num10).animate({opacity : '1'  } , 500 , function() {   });  
            
            // OFF ANIMATION
            
        }
         
         
        function channel_up_off(num10_10){ 
        
        
         $('#channel_up .circle').delay(num10_10).animate({width : '0px' , opacity: '0' , height : '0px' , boder : 'solid 0px red'} , 500);
            
         $('#channel_up .line_txt span').delay(num10_10).animate({width : '0px'} , 500);     
            
         $('#channel_up .line_txt h1').delay(num10_10).animate({opacity : '0'  } , 500 , 'linear', function() {   });          
        
        }
        
        
   channel_up("1000");
        
   channel_up_off("4000");
   
   
   
     function channel_down(num9){
         
    
            $('#channel_down .circle').delay(num9).animate({width : '50px' , opacity: '1' , height : '50px'} , 500);
            
            $('#channel_down .circle img').delay(num9).animate({width : '50px' , opacity: '1' , height : '50px'} , 500);   
            
            $('#channel_down .line_txt span').delay(num9).animate({width : '240px'} , 500);     
            
            $('#channel_down .line_txt h1').delay(num9).animate({opacity : '1'  } , 500 , function() {   });  
            
            // OFF ANIMATION
            
        }
         
         
        function channel_down_off(num9_9){ 
        
        
         $('#channel_down .circle').delay(num9_9).animate({width : '0px' , opacity: '0' , height : '0px' , boder : 'solid 0px red'} , 500);
            
         $('#channel_down .line_txt span').delay(num9_9).animate({width : '0px'} , 500);     
            
         $('#channel_down .line_txt h1').delay(num9_9).animate({opacity : '0'  } , 500 , 'linear', function() {   });          
        
        }
        
        
   channel_down("1000");
        
   channel_down_off("4000");
   
   
   
   
    function mike_icon(num10){
         
    
            $('#mike_icon .circle').delay(num10).animate({width : '50px' , opacity: '1' , height : '50px'} , 500);
            
            $('#mike_icon .circle img').delay(num10).animate({width : '50px' , opacity: '1' , height : '50px'} , 500);   
            
            $('#mike_icon .line_txt span').delay(num10).animate({width : '80px'} , 500);     
            
            $('#mike_icon .line_txt h1').delay(num10).animate({opacity : '1'  } , 500 , function() {   });  
            
            // OFF ANIMATION
            
        }
         
         
        function mike_icon_off(num10_10){ 
        
        
         $('#mike_icon .circle').delay(num10_10).animate({width : '0px' , opacity: '0' , height : '0px' , boder : 'solid 0px red'} , 500);
            
         $('#mike_icon .line_txt span').delay(num10_10).animate({width : '0px'} , 500);     
            
         $('#mike_icon .line_txt h1').delay(num10_10).animate({opacity : '0'  } , 500 , 'linear', function() {   });          
        
        }
        
        
   mike_icon("1000");
        
   mike_icon_off("4000");
   
   
         
        // Repeat Animation
        
              
        
      //  setTimeout(function () {  home_anim("12000");}, 1000); 
        
     //   setTimeout(function () {  mouse_on("12000");}, 1000);
        
      //  setTimeout(function () {  mike_anim("9000");}, 1000);
        
      //  setTimeout(function () {  channel_list("9000");}, 1000);
        
      //  setTimeout(function () {  return_btn("6000");}, 1000);
        
       // setTimeout(function () {  navigation("9000");}, 1000);
          
        
        
        });
        
         
           function left_shade(){
       
       
       $('.left_shade').animate({opacity : '1'  } , 500 , function() {   }); 
       
        $('.left_shade').delay(2000).animate({opacity : '0'  } , 500 , function() {left_shade()  });  
        
       
       }
       
           function right_shade(){
       
       
       $('.right_shade').animate({opacity : '1'  } , 500 , function() {   }); 
       
        $('.right_shade').delay(3000).animate({opacity : '0'  } , 500 , function() {right_shade()  });  
        
       
       }
       
      
      function spk_now(){
      
        $('.spk_now').animate({opacity : '1'  } , 500 , function() {   }); 
       
       $('.spk_now').delay(1000).animate({opacity : '0'  } , 500 , function() { spk_now()  });  
      
      }
      
      
        
     
       
//       function mike_none(){
//       
//       $(".mike_txt1 .step1").css("display" ,"none");
//        $(".mike_txt1 .step2").css("display" ,"none");
//         $(".mike_txt1 .step3").css("display" ,"none");
//          $(".mike_txt1 .step4").css("display" ,"none");
//       
//       }
       
       
        function spk_now_text(){
        
        $(".mike_txt1 .spk_now1").animate({opacity : '1' , left : '0px' } , 500); 
         
         $(".mike_txt1 .spk_now1").delay(4000).animate({opacity : '0', height : '0px'} , 500); 
         
          
         
         
         $(".mike_txt1 .spk_now2").delay(5000).animate({opacity : '1' , left : '0px'} , 500); 
         
         $(".mike_txt1 .spk_now2").delay(4000).animate({opacity : '0' , height : '0px'} , 500); 
         
          
        
        
         
         $(".mike_txt1 .spk_now3").delay(10000).animate({opacity : '1' , left : '0px'} , 500); 
         
         $(".mike_txt1 .spk_now3").delay(4000).animate({opacity : '0' , height : '0px'} , 500 ); 
         
         
         
         
         $(".mike_txt1 .spk_now4").delay(15000).animate({opacity : '1' , left : '0px'} , 500); 
         
         $(".mike_txt1 .spk_now4").delay(4000).animate({opacity : '0' , height : '0px'} , 500 ); 
         
         
          $(".mike_txt1 .spk_now5").delay(20000).animate({opacity : '1' , left : '0px'} , 500); 
         
         $(".mike_txt1 .spk_now5").delay(4000).animate({opacity : '0' , height : '0px'} , 500 ); 
         
        
         
           $(".mike_txt1 .spk_now6").delay(25000).animate({opacity : '1' , left : '0px'} , 500); 
            
         
         $(".mike_txt1 .spk_now6").delay(4000).animate({opacity : '0' , height : '0px'} , 500 , function(){spk_now_text()} ); 
         
         
         
        }
        
         
       
       right_shade();
        
       left_shade();
       
       spk_now();
       
       spk_now_text();
       
       
       setTimeout(location.reload.bind(location), 28000);
       
       
//       function mike_txt(){
//       
//               $(".mike_txt1 .spk_now1").each(function(index) {
//               
//           $(this).delay(2000*index).fadeIn(300).delay(1500*index).fadeOut(300 , function(){mike_txt()});
//           
//            
//        })

//        }
//        
//        
      // mike_txt()
        
        
                
        
       
       
      