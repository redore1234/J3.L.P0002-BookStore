/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longpt.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import longpt.tblaccount.TblAccountDAO;
import longpt.tblaccount.TblAccountDTO;
import longpt.tblorderdetail.TblOrderDetailDAO;
import longpt.tblorderdetail.TblOrderDetailDTO;
import longpt.tblorders.TblOrdersDAO;
import longpt.tblorders.TblOrdersDTO;
import longpt.tblpayment.TblPaymentDAO;
import org.apache.log4j.Logger;

/**
 *
 * @author phamt
 */
@WebServlet(name = "TrackOrderServlet", urlPatterns = {"/TrackOrderServlet"})
public class TrackOrderServlet extends HttpServlet {

    private String TRACKING_PAGE = "trackorder.jsp";
    private final static Logger logger = Logger.getLogger(TrackOrderServlet.class);
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
        String url = TRACKING_PAGE;
        try {
            String orderId = request.getParameter("txtTracking");
            // Get username to track order
            String username = null;
            HttpSession session = request.getSession(false);
            if (session != null) {
                TblAccountDTO accountDTO = (TblAccountDTO) session.getAttribute("ACCOUNT");
                if (accountDTO != null) {
                    username = accountDTO.getUsername();
                }
            }
            
            //Get Order by orderId and username
            TblOrdersDAO ordersDAO = new TblOrdersDAO();
            TblOrdersDTO ordersDTO = ordersDAO.getOrder(orderId, username);
            request.setAttribute("ORDER", ordersDTO);
            if (ordersDTO != null) {
                if (ordersDTO.getName() == null) {  // do name không cần thiết nhập trong DB
                    TblAccountDAO accountDAO = new TblAccountDAO();
                    String name = accountDAO.searchNameByUsername(username);
                    request.setAttribute("NAME_INFO", name);
                }
                
                if (ordersDTO.getDate() != null) {
                    TblPaymentDAO paymentDAO = new TblPaymentDAO();
                    String payment = paymentDAO.searchNameById(ordersDTO.getPaymentId());
                    request.setAttribute("PAYMENT_METHOD", payment);
                }
                
                TblOrderDetailDAO orderDetailDAO = new TblOrderDetailDAO();
                orderDetailDAO.loadOrderDetail(orderId);
                Map<TblOrderDetailDTO, String> orderDetailList = orderDetailDAO.getOrderDetailList();
                request.setAttribute("MAP_ORDER_DETAIL", orderDetailList);
            }
        } catch (SQLException ex) {
            logger.error("TrackOrderServlet _ SQLException: " + ex.getMessage());
        } catch (NamingException ex) {
            logger.error("TrackOrderServlet _ NamingException: " + ex.getMessage());
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
