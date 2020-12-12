<%-- 
    Document   : trackorder
    Created on : Dec 11, 2020, 8:47:51 PM
    Author     : phamt
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Track Order</title>
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
            <c:set var="orderHistory" value="${requestScope.ORDER_HISTORY}"/>
                <!-- Display order history -->
            <div class="text-center" style="font-weight: bold">
                
            </div>
            
            <c:if test="${not empty orderHistory}">
                <table class="table table-hover">
                    <h3 class="text-center my-5" style="font-weight: bold"></h3>
                    <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">Order ID</th>
                            <th scope="col">Username</th>
                            <th scope="col">Name</th>
                            <th scope="col">Date</th>
                        </tr>
                    </thead>

                    <tbody>
                        <c:forEach var="orderList" items="${orderHistory}" varStatus="counter">
                            <tr>
                                <th scope="row">${counter.count}</th>
                                <td>${orderList.orderId}</td>
                                <td>${orderList.username}</td>
                                <td>${orderList.name}</td>
                                <td>${orderList.date}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
            
            
            <c:set var="orderInfo" value="${requestScope.ORDER}" />
            <c:set var="fullName" value="${requestScope.NAME_INFO}" />
            <c:set var="paymentMethod" value="${requestScope.PAYMENT_METHOD}" />
            <c:set var="orderDetailMap" value="${requestScope.MAP_ORDER_DETAIL}" />
                <!-- TRACKING ORDER DETAIL AREA -->
            <div class="text-center" style="font-weight: bold">
                <h3 class="my-3">TRACK ORDER DETAIL</h3>
            </div>
            <div class="form-group">
                <form action="DispatchController" method="POST">
                    <input type="text" name="txtTracking" value="${param.txtTracking}" placeholder="Tracking your order" required class="form-control mr-sm-2 my-2" />
                    <c:choose>
                        <c:when test="${role eq 'user'}">
                            <div class="text-right">
                                <input type="submit" value="Track" class="btn btn-success my-2 my-sm-0" name="btnAction"/>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <c:redirect url="HomeServlet"/>
                        </c:otherwise>
                    </c:choose>
                    
                </form>
            </div>
                
            <c:choose>
                <c:when test="${not empty orderInfo}">
                    <div class="card">
                        <h5 class="card-header text-center" style="font-weight: bold">Order ID: ${orderInfo.orderId}</h5>
                        <div class="card-body">
                            <div>
                                <span style="font-weight: bold">Name: </span> ${orderInfo.name}
                            </div>
                            <div>
                                <span style="font-weight: bold">Address: </span> ${orderInfo.address}
                            </div>
                            <div>
                                <span style="font-weight: bold">Phone: </span> ${orderInfo.phone}
                            </div>
                            <div>
                                <span style="font-weight: bold">Order Date: </span> ${orderInfo.date}
                            </div>
                            <div>
                                <span style="font-weight: bold">Payment Method: </span> ${orderInfo.paymentId}
                            </div>
                        </div>
                    </div>
                    
                    <table class="table table-hover">
                        <caption class="text-right" style="font-weight: bold">Total: ${orderInfo.total} VND</caption>
                        <thead>
                            <tr>
                                <th scope="col">#</th>
                                <th scope="col">Book title</th>
                                <th scope="col">Quantity</th>
                                <th scope="col">Price</th>
                            </tr>
                        </thead>
                        
                        <tbody>
                            <c:forEach var="orderDetailInfo" items="${orderDetailMap}" varStatus="counter">
                                <tr>
                                    <th scope="row">${counter.count}</th>
                                    <td>${orderDetailInfo.value}</td>
                                    <td>${orderDetailInfo.key.quantity}</td>
                                    <td>${orderDetailInfo.key.price} VND</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:when>
                
                <c:otherwise>
                    <h3 class="alert alert-danger" style="text-align: center">Not found your tracking order!</h3>
                </c:otherwise>
            </c:choose>
        </div>
        <script src="assets/js/bootstrap.min.js"></script>
    </body>
</html>
