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
    </head>
    <body>
        <c:set var="account" value="${sessionScope.ACCOUNT}"/>
        <c:set var="role" value="${sessionScope.ACCOUNT_ROLE}"/>
        
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
                            <c:url var="urlCreateDiscount" value="DispatchController">
                                <c:param name="btnAction" value="Create Discount"/>
                            </c:url>
                        <li class="nav-item">
                            <a href="${urlCreateDiscount}" class="nav-link active">Create Discount</a>
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
            <form action="DispatchController" method="POST">
                
            </form>
        </div>
    </body>
</html>
