<%-- 
    Document   : manage
    Created on : Dec 9, 2020, 3:53:14 PM
    Author     : phamt
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manage</title>
        <link rel="stylesheet" href="assets/css/bootstrap.min.css">
        <link rel="stylesheet" href="assets/css/customize.css">
    </head>
    <body>
        <c:set var="account" value="${sessionScope.ACCOUNT}"/>
        <c:set var="role" value="${sessionScope.ACCOUNT_ROLE}"/>
        <c:set var="listCategory" value="${requestScope.LIST_CATEGORIES}"/>
        
        <nav class="navbar navbar-dark navbar-expand-sm bg-primary">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link active" href="#">Home</a>
                </li>
                
                
                    <c:choose>
                        <c:when test="${role eq 'admin'}">  
                            <c:url var="urlInsert" value="DispatchController">
                                <c:param name="btnAction" value="LoadCategory"/>
                                <c:param name="hiddenbtnAction" value="Insert"/>
                            </c:url>
                        <li class="nav-item">
                            <a href="${urlInsert}" class="nav-link active">Insert new Book</a>
                        </li>
                        <li class="nav-item">
                            <a href="discount.jsp" class="nav-link active">Create Discount</a>
                        </li>
                        </c:when>
                        <c:otherwise>
                            <c:redirect url="HomeServlet"/>
                        </c:otherwise>
                    </c:choose>

            </ul>
            
            <ul class="navbar-nav ml-auto text-center">
                <!-- Log out form -->
                <form action="DispatchController" method="POST">
                    <c:choose>
                        <c:when test="${not empty account}">
                            <li class="nav-item">
                                <a class="welcome-user"> Welcome ${account.fullName}</a>
                            </li>

                            <li class="nav-item">
                                <input type="submit" value="Logout" name="btnAction"  class="btn btn-danger btn-sm my-2 my-sm-0 mx-3"/>
                            </li>
                        </c:when> 

                        <c:otherwise>
                            <li class="nav-item"> 
                                <input type="submit" value="Login" name="btnAction"  class="btn btn-success btn-sm my-2 my-sm-0 mx-3"/>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </form>
            </ul>
        </nav>
                            
        <div class="container">
            <!-- search form -->
            <form action="DispatchController" class="form-group">
                <input type="text" name="txtSearchValue" value="${param.txtSearchValue}" class="form-control my-2" placeholder="Search book's title..."/> 
                <div class="text-right">
                    <input type="submit" value="Search title" class="btn btn-success my-2" name="btnAction"/> 
                </div>
            </form>
            
            <form action="DispatchController" class="form-group">
                <label class="my-2">Price Range (VND)</label>
                    <input type="number" min="0" max="500000" name="txtLowerPrice" value="${param.txtLowerPrice}" 
                           onkeypress=" return (event.charCode == 8 || event.charCode == 0) ? null : event.charCode >= 48 && event.charCode <= 57"
                           class="form-control mr-sm-2 my-2" placeholder="Lower Limit"/>
                    
                    <input type="number" min="0" max="500000" name="txtUpperPrice" value="${param.txtUpperPrice}" 
                           onkeypress=" return (event.charCode == 8 || event.charCode == 0) ? null : event.charCode >= 48 && event.charCode <= 57"
                           class="form-control mr-sm-2 my-2" placeholder="Upper Limit"/>
                <div class="text-right">
                    <input type="submit" value="Search price" class="btn btn-success my-2" name="btnAction"/> 
                </div>
            </form>
            
            <form action="DispatchController" class="form-group">
                <label class="my-2">Category</label>
                <select name="cmbCategory" class="form-control">
                    <c:forEach var="category" items="${listCategory}">
                        <option value="${category.categoryId}"
                                <c:if test="${category.categoryId eq param.cmbCategory}">
                                    selected="true"
                                </c:if>        
                        >
                            ${category.categoryName}
                        </option>
                    </c:forEach>
                </select> 
                <div class="text-right">
                    <input type="submit" value="Search category" class="btn btn-success my-2" name="btnAction"/> 
                </div>
            </form>    
        </div>
        
        <!-- Display result -->
        <c:set var="result" value="${requestScope.LIST_BOOKS}"/>
        <c:if test="${not empty result}">
            <h3 class="text-center">BOOKS</h3>
                <!-- Display books -->
                <div class="row justify-content-center align-items-center">
                    <c:forEach var="item" items="${result}">
                        <form action="DispatchController" method="POST">
                            <input type="hidden" name="txtProductId" value="${item.productId}" />
                            <div class="col-12 my-3">
                                <div class="card h-100">
                                    <div class="card-header">
                                        <c:if test="${not empty item.image}">
                                        <img src="data:image/png;base64,${item.image}" style="width: 15rem" class="card-img-top"/>
                                        </c:if>
                                    </div>

                                    <div class="card-body">
                                        <h3 class="card-title text-uppercase text-center">${item.title}</h3>

                                        <div class="card-text">
                                            <h5 class="text-center">${item.description}</h5>
                                            <p>Author: ${item.author}</p>
                                            <p>Category: ${item.categoryId}</p>
                                            <!-- Check quantity -->
                                            <c:choose>
                                                <c:when test="${item.quantity gt 0}">
                                                    <p>Quantity: ${item.quantity}</p>
                                                </c:when>
                                                <c:otherwise>
                                                    <p>Quantity: <font class="red">Out of stock</font></p>
                                                </c:otherwise>
                                            </c:choose>
                                            <p>Price: ${item.price}</p>
                                        </div>
                                    </div>
                        </form>                
                                    <div class="card-footer">
                                        <c:choose>
                                            <c:when test="${role eq 'admin'}">  
                                                <c:url var="urlUpdate" value="DispatchController">
                                                    <c:param name="btnAction" value="LoadCategory"/>
                                                    <c:param name="hiddenbtnAction" value="Edit"/>
                                                    <c:param name="txtProductId" value="${item.productId}"/>
                                                </c:url>
                                                <a href="${urlUpdate}" class="nav-link badge badge-warning">Update Book</a>

                                                <c:url var="urlDelete" value="DispatchController">
                                                    <c:param name="btnAction" value="Delete Book"/>
                                                    <c:param name="productId" value="${item.productId}"/>
                                                </c:url>
                                                <a href="${urlDelete}" class="nav-link badge badge-danger" 
                                                    onclick="return confirm('Do you want to delete this book?');">
                                                    Delete Book
                                                 </a>
                                            </c:when>
                                            <c:otherwise>
                                                <c:redirect url="HomeServlet"/>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                            </div>
                    </c:forEach>
                </div>
        </c:if>
                
        <c:if test="${empty result}">
            <div class="container h-100">
                <div class="row h-100 justify-content-center align-items-center">
                    <div class="col-6 text-center">
                        <h2 class="text-danger">No book to search</h2>
                    </div>
                </div>
            </div>
        </c:if>
            
        <script src="assets/js/bootstrap.min.js"></script>                            
    </body>
</html>
