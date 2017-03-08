<script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="//cdn.poesis.kr/post/popup.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/swfobject.js"></script>
<script type="text/javascript" src="js/jquery.uploadify.v2.1.4.min.js"></script>

<%@ page import="nl.captcha.Captcha" %>
<script type="text/javascript">

    function checkemail(mailaddr){
        if (mailaddr==""){
            alert("put email");
            return -1;
        }

        var regex=/^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/;

        if(regex.test(mailaddr) === false) {
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

    function openPopup(){

        var popUrl = "fileupload";
        var popOption = "width=370, height 100, resizable=yes";
        window.open(popUrl, "", popOption);

    }

</script>


<% // We're doing this in a JSP here, but in your own app you'll want to put
    // this logic in your MVC framework of choice.
    Captcha captcha = (Captcha) session.getAttribute(Captcha.NAME);
    // Or, for an AudioCaptcha:
    // AudioCaptcha captcha = (AudioCaptcha) session.getAttribute(Captcha.NAME);
    request.setCharacterEncoding("UTF-8"); // Do this so we can capture non-Latin chars
    String answer = request.getParameter("answer");
    if (captcha.isCorrect(answer)) { %>
        <H1>Correct! Join us</H1>
        <form name="joinf" action="/join/email" method="post" onsubmit="return checkFrm()">
            e-mail: <input type="text" name="email"/>
            <br/>
            password: <input type="password" name="passwd"/>   *Password should be at least 8 characters long and should include a mix of letters, numbers, and symbols
            <br/>
            confirm password: <input type="password" name="passwdc"/>
            <br/>
            phone: <input type="text" name="phone"/> *Please insert numbers only, without '-'
            <br/>

            <div>
            <p><label>address</label><input type="text" class="postcodify_address" name="addr" /><button type=button id="search_button">search</button></p>
            <p><label>detailed address</label><input type="text" class="postcodify_details" name="daddr" /></p>
            </div>

            <p>
            <button type="button" id="uploadfile" onclick="return openPopup()">file upload </button>
            </p>

            <input type="submit" value="submit and join!"/>
        </form>


    <% }
    else { %>
        <b> Wrong Answer </b>

<% } %>

<script type="text/javascript">
    $("#search_button").postcodifyPopUp();
</script>


