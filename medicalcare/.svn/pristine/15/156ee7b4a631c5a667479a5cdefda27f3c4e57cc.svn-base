(function(){
    var currClientWidth,
	fontValue,
	originWidth;
    originWidth = 750;
    //rezoom(); 
setTimeout(rezoom,100);
    window.addEventListener('resize', rezoom, false);
    function rezoom() {
        currClientWidth =screen.width;// document.documentElement.clientWidth;
$(document.body).width(currClientWidth); 
//document.documentElement.style.width=currClientWidth;
    
if (currClientWidth > 750){
            currClientWidth = 750;
        } 
        if (currClientWidth < 320){
            currClientWidth = 320;
        } 
        fontValue = ((100 * currClientWidth) / originWidth).toFixed(2);
        document.documentElement.style.fontSize = fontValue + 'px';
    }
rezoom();
}

)();