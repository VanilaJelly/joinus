<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>join us</title>

</head>
<body>

<script type="text/javascript">

    function imgRefresh(){
        var d = new Date();
        $("#captchaImg").attr("src", "/join/stickyImg?"+d.getTime());
    }

</script>
	<H2> Automatic Input protection </H2>
	<img src="/join/stickyImg" />
    <form action="/join/captchaSubmit" method = "POST">
        <input name="answer" />
    <input type=submit value=answer />
    </p>

</body>
</html>
