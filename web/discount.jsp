<%-- 
    Document   : discount
    Created on : Dec 11, 2020, 11:00:21 PM
    Author     : phamt
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Discount</title>
        <link rel="stylesheet" href="assets/css/bootstrap.min.css">
        <link rel="stylesheet" href="assets/css/customize.css">
    </head>
    <body>
        <c:set var="account" value="${sessionScope.ACCOUNT}"/>
        <c:set var="role" value="${sessionScope.ACCOUNT_ROLE}"/>
        <c:if test="${empty account}">
            <c:redirect url="HomeServlet"/>
        </c:if>
        
        <nav class="navbar navbar-dark navbar-expand-sm bg-primary">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link active" href="HomeServlet">Home</a>
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
                        
                        <c:url var="urlCreateDiscount" value="DispatchController">
                            <c:param name="btnAction" value="View Discount"/>
                        </c:url>
                        <li class="nav-item">
                            <a href="${urlCreateDiscount}" class="nav-link active">View Discount</a>
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
                <!-- DISCOUNT FORM  -->
        <div class="container">
            <form action="DispatchController" method="POST" class="form-inline">
                <label>
                    Choose discount:
                </label>
                <select name="cbPercentage" class="form-control col-2 mx-5 my-3">
                    <option>3</option>
                    <option>5</option>
                </select>

                &ensp;
                <label>
                    Choose discount's quantity:
                </label>
                <select name="cbDisQuantity" class="form-control col-2 mx-5 my-3">
                    <option>1</option>
                    <option>2</option>
                </select>
                <c:choose>
                    <c:when test="${role eq 'admin'}">
                        <input type="submit" value="Create Discount" name="btnAction" />
                    </c:when>
                    <c:otherwise>
                        <c:redirect url="HomeServlet"/>
                    </c:otherwise>
                </c:choose>
            </form>
            
                <!-- Display result  -->
            <c:set var="result" value="${requestScope.DISCOUNT_LIST}"/>
            <c:choose>
                <c:when test="${not empty result}">
                        
                        <table class="table table-hover my-5">
                            <thead>
                                <tr>
                                    <th scope="col">#</th>
                                    <th scope="col">Percentage</th>
                                    <th scope="col">Date</th>
                                    <th scope="col">Status</th>
                                </tr>
                            </thead>

                            <tbody>
                                <c:forEach var="discount" items="${result}" varStatus="counter">
                                    <tr>
                                        <th scope="row">${counter.count}</th>
                                        <td>${discount.discountPer}</td>
                                        <td>${discount.date}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${discount.status == true}">
                                                    Available
                                                </c:when>
                                                <c:otherwise>
                                                    Not available
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                </c:when>
                
                <c:otherwise>
                    <div class="container h-100">
                        <div class="row h-100 justify-content-center align-items-center">
                            <div class="col-6 text-center">
                                <h2 class="text-danger">Don't have discount</h2>
                            </div>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
        
        <script src="assets/js/bootstrap.min.js"></script>
    </body>
</html>
