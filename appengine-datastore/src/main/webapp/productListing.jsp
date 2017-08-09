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
<table>
<tr>
<td><b>Catalog Name</b></td>
<td><b>Product Name</b></td>
<td><b>Description</b></td>
<td><b>Price</b></td>
<td><b>Unit</b></td>

</tr>

<% 
List<Product> products=(List<Product>)request.getAttribute("products");
for (Product product : products) {
	%><tr><td><%	
	out.println(product.getAncestor()); %></td><td><%
	out.println(product.name); %></td><td><%
	out.println(product.description);%></td><td><%
	out.println(product.price);%></td><td><%
	out.println(product.unit);%></td><td><%
	%></tr><%
}

%>
</table>

<a href="/product?add=true">Add Product</a>

</body>
</html>
<%-- //[END all]--%>
