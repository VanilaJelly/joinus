<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>join us</title>
    <script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js" charset="UTF-8"></script>
    <script type="text/javascript" src="//cdn.poesis.kr/post/popup.min.js" charset="UTF-8"></script>

</head>
<body>

<script type="text/javascript">

	function checkemail(mailaddr){
		if (mailaddr==""){
			alert("put email");
			return -1;
		}


		var index = mailaddr.indexOf('@');
		var index2 = mailaddr.indexOf('.', index);
		var result= 0;

		if (index < 0 || index2 < 0){
			alert("put correct email");
			return -1;
		}

		return 0;
	}

	function checkpasswd(passwd, passwdc){
		if (passwd != passwdc){
			alert("Password and Confirm Password values do not match");
			return -1;
		}
		if (passwd.length < 8){
			alert("Password should be at least 8 characters long");
			return -1;
		}

		var num = passwd.replace(/[^0-9]/g,"");
		var alp = passwd.replace(/[^A-z]/g,"");
		var spc = passwd.replace(/[/\s/]/g, "");

		if (num.length < 1){
			alert ("Password should contain at least one number");
			return -1;
		}
		if (alp.length < 1){
			alert ("Password should contain at least one alphabet");
			return -1;
		}
		if (passwd.length - num.length - alp.length < 1){
			alert ("Password should contain at least one symbol");
			return -1;
		}
		var blank_pattern = /[\s]/g;
		if( blank_pattern.test(passwd.value) == true){
		    alert("Password should not contain blanks");
		    return -1;
		}

		return 0;
}

	function checkphone(phone){
		var num = phone.replace(/[^0-9]/g,"");

		if (num.length != phone.length){
			alert('Invalid Phone number');
			return -1;
		}

		if (phone.substring(0,2) != "01" || phone.length != 11){
			alert ('Invalid Phone number');
			return -1;
		}

		return 0;
	}

	function checkFrm(){

		var result = 0;
		result = result + checkemail(document.joinf.email.value);
		result = result + checkpasswd(document.joinf.passwd.value, document.joinf.passwdc.value);
		result = result + checkphone(document.joinf.phone.value);

		//result == 0 if and only if there was no error at all
		if (result < 0){
			return false;
		}
	}

    $(document).ready(function () {
        $('#fileInput').uploadify({
            'uploader': 'uploadify.swf',
            'script': 'uploadify.ashx',
            'cancelImg': 'cancel.png',
            'auto': false,
            'folder': '/Uploads',
            'onComplete': function (event, queueID, fileObj, response, data) {

            $('#lblFile').append('<a href="/WebJQuery' + fileObj.filePath + '">' + fileObj.name + '</a></ br>');

            }
            $('#btn').click(function () { $('#fileInput').uploadifyUpload(); });

    });


            $('#btn').click(function () { $('#fileInput').uploadifyUpload(); });

        });

</script>
	<H2> Join Us </H2>

	<form name="joinf" action="/join/welcome" method="post" onsubmit="return checkFrm()">
		e-mail: <input type="text" name="email"/>
		<br/>
		password: <input type="password" name="passwd"/>   *Password should be at least 8 characters long and should include a mix of letters, numbers, and symbols
		<br/>
		confirm password: <input type="password" name="passwdc"/>
		<br/>
		phone: <input type="text" name="phone"/> *Please insert numbers only, without '-'
		<br/>

    	<div>
        <p><label>address</label><input type="text" class="postcodify_address" /><button type=button id="search_button">search</button></p>
        <p><label>detailed address</label><input type="text" class="postcodify_details" /></p>
        </div>

        <input type=file name=fileinput id=fileinput />
        <button type=button id=imgupload value=upload /> upload </button>
        <br/>

        <input type="submit" value="submit and join!"/>
	</form>

	<img src="/join/stickyImg" />
    <form action="/captchaSubmit.jsp" method="post">
        <input name="answer" />
    </form>

    <script type="text/javascript">
        $("#search_button").postcodifyPopUp();
    </script>


</body>
</html>
