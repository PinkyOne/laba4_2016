
function checkQ()
{
var x=document.forms["myForm"]["username"].value;
var y=document.forms["myForm"]["email"].value;
var z=document.forms["myForm"]["question"].value;

if (x==null || x=="" || y==null || y=="" || z==null || z=="" || p==null || p=="")

{
alert("Ошибка! Все поля обязательны к заполнению!");
return false;
}
}
