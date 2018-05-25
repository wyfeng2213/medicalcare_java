function Timer(beginTime, endTime, callback) {
    var _isBegin = false;
    var _isEnd = false;
    var _beginTime;
    var _endTime;
    var _currentTime;
    var _callback;
    var _timer = null;
    var _timerType = 0;// 0 倒计时 1 正计时
    _beginTime = beginTime;
    _currentTime = beginTime;
    _endTime = endTime;
    _callback = callback;
    _timerType = endTime - beginTime >= 0 ? 0 : 1;
    Timer.prototype.getTimeStr = function (time) {
        var h = time / 3600;
        var hl = (time % 3600);
        var m = hl / 60;
        var s = h1 % 60;

        return (h >= 10 ? h : '0' + h) + (m >= 10 ? m : '0' + m) + (s >= 10 ? s : '0' + s);
    }
    Timer.prototype.updateStatus = function () {
        if (_currentTime == _endTime) {
            _isEnd = true;
        }
        if (_callback) {
            _callback(_currentTime, _isEnd);
        }
        if (_isEnd && _timer) {
            window.clearInterval(_timer);
            _timer = null;
        }
        if (_timerType == 0) {
            _currentTime++;

        } else {
            _currentTime--;
        }
    }

    Timer.prototype.startTime = function () {
        var me = this;
        if (_isEnd) {
            return;
        }
        if (!_isBegin) {
            _timer = window.setInterval(function () {
                me.updateStatus();
            }, 1000);
        }
        _isBegin = true;

    }
    Timer.prototype.isEnd = function () {
        return _isEnd;
    }
    Timer.prototype.reset = function (beginTime, endTime, callback) {
        _beginTime = beginTime;
        _currentTime = beginTime;
        _longTime = endTime;
        _callback = callback;
        _timerType = endTime - beingTime >= 0 ? 0 : 1;
        if (_timer) {
            window.clearInterval(_timer);
            _timer = null;
        }
        this.updateStatus();
        this.startTime();
    }
    Timer.prototype.stopTime = function (longBegin) {
        _isEnd = true;
        this.updateStatus();
    }
    this.updateStatus();
    this.startTime();

}