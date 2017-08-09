<%-- //[START all]--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- //[START imports]--%>
<%@ page import="com.gclouddemo.ecommerce.catalog.bean.Product" %>
<%-- //[END imports]--%>

<%@ page import="java.util.List" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <link type="text/css" rel="stylesheet" href="/stylesheets/main.css"/>
</head>

<body>
<form action="/product" method="post">
    <table>
    <tr><td><div>Catalog Name</div></td><td><div><input type="text" name="catalogName" value="${fn:escapeXml(catalogName)}"/></div></td>
    <tr><td><div>Product Name</div></td><td><div><input type="text" name="name" value="${fn:escapeXml(name)}"/></div></td>
    <tr><td><div>Description</div></td><td><div><input type="text" name="desc" value="${fn:escapeXml(desc)}"/></div></td>
    <tr><td><div>Price</div></td><td><div><input type="text" name="price" value="${fn:escapeXml(price)}"/></div></td>
    <tr><td><div>Unit</div></td><td><div><input type="text" name="unit" value="${fn:escapeXml(unit)}"/></div></td>
    <tr><td>&nbsp;</td><td><div><input type="submit" value="Save"/></div></td></tr>
    </table>
</form>


<a href="/product">View All Products</a>

</body>
</html>
<%-- //[END all]--%>
