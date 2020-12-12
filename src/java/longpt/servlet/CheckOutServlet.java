/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longpt.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import longpt.cart.Cart;
import longpt.errors.QuantityError;
import longpt.tblaccount.TblAccountDTO;
import longpt.tblorderdetail.TblOrderDetailDAO;
import longpt.tblorders.TblOrdersDAO;
import longpt.tblproduct.TblProductDAO;
import longpt.tblproduct.TblProductDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author phamt
 */
@WebServlet(name = "CheckOutServlet", urlPatterns = {"/CheckOutServlet"})
public class CheckOutServlet extends HttpServlet {

    private final String VIEW_CART_CONTROLLER = "ViewCartServlet";
    private final String HOME_CONTROLLER = "HomeServlet";
    private final static Logger logger = Logger.getLogger(CheckOutServlet.class);

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String url = VIEW_CART_CONTROLLER;
        boolean foundErr = false;
        try {
            String name = request.getParameter("txtCustomerName");
            String address = request.getParameter("txtAddress");
            String phone = request.getParameter("txtPhone");

            HttpSession session = request.getSession(false);
            Cart cart = (Cart) session.getAttribute("CART");
            if (cart != null) {
                if (cart.getCompartment() != null) {
                    //call DAO
                    TblProductDAO productDAO = new TblProductDAO();
                    QuantityError error = new QuantityError();
                    int total = 0;
                    for (TblProductDTO productDTO : cart.getCompartment().keySet()) {
                        total += productDTO.getPrice() * cart.getCompartment().get(productDTO);

                        //check if product's quantity less than 0 
                        if (cart.getCompartment().get(productDTO) > productDAO.getQuantity(productDTO.getProductId())) {
                            foundErr = true;
                            error.getErrorList().add("Product " + productDTO.getTitle() + " is only have " + productDTO.getQuantity() + " left!!!");
                            request.setAttribute("ERROR_QUANTITY", error);
                        }
                    }

                    if (foundErr == false) {
                        //Call DAO
                        TblOrdersDAO ordersDAO = new TblOrdersDAO();
                        //Get username of account
                        String username = null;
                        TblAccountDTO accountDTO = (TblAccountDTO) session.getAttribute("ACCOUNT");
                        if (accountDTO != null) {
                            username = accountDTO.getUsername();
                        }
                        String orderId = ordersDAO.createOrder(username, name, address, phone, total, "COD");
                        if (orderId != null) {
                            TblOrderDetailDAO orderDetailDAO = new TblOrderDetailDAO();
                            boolean result = orderDetailDAO.addOrderDetail(cart, orderId);
                            if (result == true) {
                                productDAO.reduceQuantity(cart);
                                session.setAttribute("CART", null);

                                request.setAttribute("TRACKING", orderId);
                            }
                        }
                    }
                }
            } else {
                url = HOME_CONTROLLER;
            }
        } catch (SQLException ex) {
            logger.error("CheckOutServlet _ SQLException: " + ex.getMessage());
        } catch (NamingException ex) {
            logger.error("CheckOutServlet _ NamingException: " + ex.getMessage());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
