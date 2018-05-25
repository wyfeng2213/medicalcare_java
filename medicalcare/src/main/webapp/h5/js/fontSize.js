


!(function (doc, win) {
    var docEle = doc.documentElement,
        evt = "onorientationchange" in window ? "orientationchange" : "resize",
        fn = function () {
            var clientWidth = docEle.clientWidth;
            if (!clientWidth) return;
            if (clientWidth >= 640) {
                docEle.style.fontSize = (640 / 3.75) + 'px';

            } else {
                docEle.style.fontSize = (clientWidth / 3.75) + 'px';
            }
        };
    
    win.addEventListener(evt, fn, false);
    doc.addEventListener("DOMContentLoaded", fn, false);

}(document, window));