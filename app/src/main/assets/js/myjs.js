// pure JS
/**var elem = document.getElementById('slider');
        var slider =
        Swipe(document.getElementById('slider'), {
          auto: 10000,
          continuous: true,
          callback: function(pos) {

            var i = bullets.length;
            while (i--) {
              bullets[i].className = ' ';
            }
              bullets[pos].className = 'on';
          }

        });



var bullets = document.getElementById('position').getElementsByTagName('li');

 $('li').on('click', function(event){
  event.preventDefault();
  var index = $("li").index(event.currentTarget);
  slider.slide(index);
});

// with jQuery
// window.mySwipe = $('#mySwipe').Swipe().data('Swipe');
**/

//Fix for slider bullets and text overlap


var elem = document.getElementById('slider');
   var slider =
        Swipe(document.getElementById('slider'), {
          auto: 10000,
          continuous: true,
          callback: function(pos) {
			 var i = bullets.length;
			if(i > pos ) {

			}
			else {
				if(pos == 2) { pos = 0;  }
				else if(pos == 3) {  pos = 1; }
				else  {  pos = 0; }

			}


            while (i--) {
              bullets[i].className = ' ';
            }
              bullets[pos].className = 'on';
          },
		transitionEnd: function(index, elem) {}

        });

var bullets = document.getElementById('position').getElementsByTagName('li');

var $navLi = $('#position  li');
$navLi.on('click', function() {
  slider.slide(
  $(this).index() + 0, 200);
  $(this).siblings().removeClass('on');
  $(this).addClass('on');
});


