function showCurrentTime()
{
var myTime = new Date();
var hours = myTime.getHours();
var min = myTime.getMinutes();
var sec = myTime.getSeconds();

hourToString = hours + " : " + min + " : " + sec;

document.getElementById('showTime').innerHTML = hourToString;
}