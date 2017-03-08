<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>file upload</title>

</head>
<body>

    <H4> Upload File </H4>

	<form name="uploadfile" action="/join/upload.action" method="post" enctype="multipart/form-data">

        <input type=file name=fileinput id=fileinput />
        <input type=submit id=imgupload value=upload />
        <br/>


</body>
</html>
