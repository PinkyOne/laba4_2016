
function validateForm()
{
	var email=document.forms["myForm"]["email"].value;
	email = email.replace(/^\s+|\s+$/g, '');
if ((/^([a-z0-9_\-]+\.)*[a-z0-9_\-]+@([a-z0-9][a-z0-9\-]*[a-z0-9]\.)+[a-z]{2,4}$/i).test(email) == false)
  {
  alert("Не корректный адрес e-mail");
  return false;
}
}
