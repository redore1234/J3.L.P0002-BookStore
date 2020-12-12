<%-- 
    Document   : insertbook
    Created on : Dec 8, 2020, 10:42:42 PM
    Author     : phamt
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Insert Book</title>
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
        <c:if test="${role ne 'admin'}">
            <c:redirect url="HomeServlet"/>
        </c:if>
        
        <nav class="navbar navbar-dark navbar-expand-sm bg-primary">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link active" href="HomeServlet">Home</a>
                </li>
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
        
        <!-- Insert book form -->
        <div class="container-fluid h-100">
            <div class="row h-100 justify-content-center align-items-center">
                <div class="card col-10 col-md-8 col-lg-6 p-5">
                    <div class="text-center pb-3">
                        <h1>Insert Book Page</h1>
                    </div>
                    
                    <form action="DispatchController" method="POST" enctype="multipart/form-data">
                        <c:set var="ERROR_IMAGE" value="${requestScope.ERRORS}"/>
                        
                        <div class="form-group">
                            <label>Title</label>
                            <input type="text" class="form-control" name="txtTitle" 
                                   value="${param.txtTitle}" maxlength="50" required/>
                            <small  class="text-muted">Must be 8-50 chars</small>
                        </div>
                        
                        <div class="form-group">
                            <label>Quantity</label>
                            <input type="number" class="form-control" name="txtQuantity" onkeypress=" return (event.charCode == 8 || event.charCode == 0) ? null : event.charCode >= 48 && event.charCode <= 57" min="0" max="500000"
                                   value="" required/>
                            <small  class="text-muted">Must greater than 0</small>
                        </div>
                        
                        <div class="form-group"> 
                            <label>Image</label>
                            <input type="file" name="txtImage" accept="image/*" style="width: 15rem" class="form-control-file" required/>
                            <c:if test="${not empty ERROR_IMAGE}">
                                <p class="text-danger"> ${ERROR_IMAGE} </p>
                            </c:if>
                        </div>
                        
                        <div class="form-group">
                            <label>Author</label>
                            <input type="text" class="form-control" name="txtAuthor" onkeypress='return ((event.charCode >= 65 && event.charCode <= 90) || (event.charCode >= 97 && event.charCode <= 122) || (event.charCode == 32))'
                                   value="" maxlength="50" required/>
                            <small  class="text-muted">Must be 8-50 chars</small>
                        </div>
                        
                        <div class="form-group">
                            <label>Description</label>
                            <textarea type="text" class="form-control" name="txtDescription" cols="20" rows="10"
                                      value="" maxlength="200" required></textarea>
                            <small  class="text-muted">Must be 8-200 chars</small>
                        </div>
                        
                        <div class="form-group">
                            <label>Price</label>
                            <input type="number" class="form-control" name="txtPrice" onkeypress=" return (event.charCode == 8 || event.charCode == 0) ? null : event.charCode >= 48 && event.charCode <= 57"  min="0" max="500000" 
                                   value="" required/>
                            <small  class="text-muted">Must greater than 0</small>
                        </div>
                        
                        <div class="form-group">
                            <label>Category</label>
                            <c:set var="listCategory" value="${requestScope.LIST_CATEGORIES}"/>
                            <select name="cmbCategory" class="form-control">
                                <c:forEach var="category" items="${listCategory}">
                                    <option value="${category.categoryId}">
                                        ${category.categoryName}
                                    </option>
                                </c:forEach>
                            </select> 
                        </div>
                            <c:choose>
                                <c:when test="${role eq 'admin'}">
                                    <div class="form-group text-right">
                                        <input type="submit" class="btn btn-primary" value="Add" name="btnAction" />
                                        <input type="reset" value="Reset" class="btn btn-primary"/>
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
        <script src="assets/js/bootstrap.min.js"></script>
    </body>
</html>
