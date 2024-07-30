<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Game Sales Demo</title>
</head>

<body>

<h2>Upload CSV File</h2>
<br />
<h5>${message}</h5>
<form method="post" action="/import" enctype="multipart/form-data">
File name:
<input type="file" name="file" />
<br /><br />
<input type="submit" value="Upload" />
</form>

<form method="get" action="/getGameSales">
<input type="submit" value="Next" />
</form>

</body>
</html>