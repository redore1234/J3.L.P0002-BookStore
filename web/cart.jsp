<%-- 
    Document   : cart
    Created on : Dec 10, 2020, 7:30:08 PM
    Author     : phamt
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cart</title>
        <link rel="stylesheet" href="assets/css/bootstrap.min.css">
        <link rel="stylesheet" href="assets/css/customize.css">
    </head>
    <body>
        
            <!-- Nav bar -->
            <c:set var="account" value="${sessionScope.ACCOUNT}"/>
            <c:set var="role" value="${sessionScope.ACCOUNT_ROLE}"/>
            <c:if test="${empty account}">
                <c:redirect url="HomeServlet"/>
            </c:if>
            <c:if test="${role ne 'user'}">
                <c:redirect url="HomeServlet"/>
            </c:if>

            <nav class="navbar navbar-dark navbar-expand-sm bg-primary">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item">
                        <a class="nav-link active" href="HomeServlet">Home</a>
                    </li>

                    <c:choose>
                        <c:when test="${role eq 'user'}">  
                            <c:url var="urlViewCart" value="DispatchController">
                                <c:param name="btnAction" value="View Cart"/>
                            </c:url>
                            <li class="nav-item">
                                <a href="${urlViewCart}" class="nav-link active">View Cart</a>
                            </li>

                            <!-- Load Order Form -->
                            <c:url var="urlViewHistory" value="DispatchController">
                                <c:param name="btnAction" value="View History"/>
                            </c:url>
                            <li class="nav-item">
                                <a href="${urlViewHistory}" class="nav-link active">Purchase History</a>
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
            <c:set var="cart" value="${sessionScope.CART}"/>
            <c:set var="total" value="${sessionScope.TOTAL_PRICE}"/>
                <!-- CART FORM -->
            <c:choose>
                <c:when test="${not empty cart}">
                    <h4 class="card-header text-center" style="font-weight: bold">YOUR CART</h4>
                        <c:set var="compartment"  value="${cart.compartment}"/>
                        <c:if test="${not empty compartment}">
                                <!-- Display cart -->
                            <table class="table table-hover">

                            <c:forEach var="product" items="${compartment}" varStatus="counter">
                                <form action="DispatchController" method="POST">
                                    <input type="hidden" name="productId" value="${product.key.productId}" />
                                    <tr>
                                            <td>${counter.count}</td>

                                            <td>${product.key.title}</td>

                                            <td>${product.key.price}</td>

                                            <td>
                                                <input type="number" name="txtQuantity"
                                                onkeypress=" return (event.charCode == 8 || event.charCode == 0) ? null : event.charCode >= 48 && event.charCode <= 57"   
                                                min="1" value="${product.value}" />
                                                <input type="hidden" name="txtQuantity" value="${product.value}" />
                                            </td>
                                                <!-- Delete form cart -->
                                            <c:choose>
                                                <c:when test="${role eq 'user'}">
                                                    <td>
                                                        <input type="submit" value="Update quantity" class="btn btn-warning" name="btnAction"/>
                                                    </td>
                                                    <td colspan="2">
                                                        <a href="DispatchController?btnAction=Delete Cart&txtProductId=${product.key.productId}" class="btn btn-outline-danger" 
                                                            onclick="return confirm('Do you want to delete ${product.key.title}?');">
                                                            Delete
                                                        </a>
                                                    </td>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:redirect url="HomeServlet"/>
                                                </c:otherwise>
                                            </c:choose>
                                        </th>
                                    </tr>
                                </form>
                            </c:forEach>

                            <tr class="alert-primary" style="font-weight: bold">
                                <td scope="row" colspan="5">Total Price:</td>
                                <td scope="row">${total} VND</td>
                            </tr>   
                            </table>


            <c:set var="quantityErr" value="${requestScope.ERROR_QUANTITY}"/>
            <c:forEach var="error" items="${quantityErr.errorList}">
                <p class="text-danger" style="font-weight: bold">${error}</p>
            </c:forEach>

                            <!-- Check out form -->

                            <div class="my-5">
                                <div class="card">
                                    <h4 class="card-header text-center" style="font-weight: bold">CHECKOUT</h4>
                                    <div class="m-3">
                                        <div class="text-danger text-center" style="font-weight: bold">
                                            Please check your Cart before click Check out to ensure everything is correct!
                                        </div>
                                        <form action="DispatchController" method="POST">
                                            <div class="form-group">
                                                <label>Name</label>
                                                <c:if test="${not empty account}"> 
                                                    <input type="text" name="txtCustomerName" value="${account.fullName}" 
                                                        onkeypress='return ((event.charCode >= 65 && event.charCode <= 90) || (event.charCode >= 97 && event.charCode <= 122) || (event.charCode == 32))'
                                                        maxlength="50" class="form-control" required/>
                                                </c:if>
                                            </div>
                                            <div class="form-group">
                                                <label>Address</label>
                                                <input type="text" name="txtAddress" value="${param.txtAddress}" maxlength="200" class="form-control"/>
                                            </div>

                                            <div class="form-group">
                                                <label>Phone</label>
                                                <input type="tel" name="txtPhone" pattern="[0-9]{10}" maxlength="10" value="${param.txtPhone}"
                                                    onkeypress=" return (event.charCode == 8 || event.charCode == 0) ? null : event.charCode >= 48 && event.charCode <= 57" 
                                                    class="form-control" >
                                            </div>
                                                <c:choose>
                                                    <c:when test="${role eq 'user'}">
                                                        <div class="text-right">
                                                            <input type="submit" value="Check out" class="btn btn-success mt-3" name="btnAction"/>
                                                        </div>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:redirect url="HomeServlet"/>
                                                    </c:otherwise>
                                                </c:choose>
                                           
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </c:if>
                </c:when>

                <c:otherwise>
                <div class="container h-100">
                    <div class="row h-100 justify-content-center align-items-center">
                        <div class="col-6 text-center">
                            <h2 class="text-danger">Your cart is empty!</h2>
                        </div>
                    </div>
                </div>
                </c:otherwise>
            </c:choose>
            <br/>
                <!-- TRACKING AREA -->
            <c:set var="tracking" value="${requestScope.TRACKING}"/>
            <c:if test="${not empty tracking}">
                <p class="alert alert-success" style="text-align: center;">
                Your tracking code is:
                <input type="text" value="${tracking}" disabled="" size="70"/>
                </p>
            </c:if>
        </div>
        <script src="assets/js/bootstrap.min.js"></script>
    </body>
</html>
