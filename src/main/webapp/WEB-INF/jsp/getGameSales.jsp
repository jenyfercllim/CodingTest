<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Game Sales Demo</title>
</head>

<body>
<h2>Game Sales List</h2>
<form method="post" action="/getGameSales" enctype="multipart/form-data">
Start Date: <input type="text" name="startDate">
End Date: <input type="text" name="endDate">
<br /><br />
Sale Price: <input type="text" name="salePrice">
Sale Range: 
<input type="radio" name="range" value="<" checked /> Less than
<input type="radio" name="range" value=">" /> More than
<br /><br />
<input type="submit" name="search" value="Search" />
<input type="reset" name="clear" value="Clear" />
<input type="submit" name="search" value="Search All" />
</form>

<h4>${message}</h4>

<c:if test="${gameSalesList!=null && message == null}">
Game Sales Result

<table border="1" style="border-collapse: collapse;">
<thead>
<tr>
<th>Id</th>
<th>Game No</th>
<th>Game Name</th>
<th>Game Code</th>
<th>Type</th>
<th>Cost Price</th>
<th>Tax</th>
<th>Sale Price</th>
<th>Date Of Sale</th>
</tr>
</thead>

<tbody>
<c:forEach items="${gameSalesList}" var="gameSales">
<tr>
<td>${gameSales.id}</td>
<td>${gameSales.gameNo}</td>
<td>${gameSales.gameName}</td>
<td>${gameSales.gameCode}</td>
<td>${gameSales.type}</td>
<td>${gameSales.costPrice}</td>
<td>${gameSales.tax}</td>
<td>${gameSales.salePrice}</td>
<td>${gameSales.dateOfSale}</td>
</tr>
</c:forEach>
<tbody>
</table>
</c:if>

</body>
</html>