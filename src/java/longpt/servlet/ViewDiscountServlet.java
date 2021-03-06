/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longpt.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import longpt.tblaccount.TblAccountDTO;
import longpt.tbldiscount.TblDiscountDAO;
import longpt.tbldiscount.TblDiscountDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author phamt
 */
@WebServlet(name = "ViewDiscountServlet", urlPatterns = {"/ViewDiscountServlet"})
public class ViewDiscountServlet extends HttpServlet {

    private final String HOME_CONTROLLER = "HomeServlet";
    private final String DISCOUNT_PAGE = "discount.jsp";
    private final static Logger logger = Logger.getLogger(ViewDiscountServlet.class);

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

        String url = DISCOUNT_PAGE;
        boolean isAdmin = true;
        try {
            //Check role 
            HttpSession session = request.getSession(false);
            if (session != null) {
                TblAccountDTO dto = (TblAccountDTO) session.getAttribute("ACCOUNT");
                if (dto != null) {
                    if (dto.getRoleId().equals("admin")) {
                        isAdmin = true;

                        TblDiscountDAO discountDAO = new TblDiscountDAO();
                        discountDAO.loadAllDiscounts();

                        List<TblDiscountDTO> discountList = discountDAO.getDiscountList();
                        request.setAttribute("DISCOUNT_LIST", discountList);
                    } else {
                        isAdmin = false;
                    }
                } else if (dto == null) {
                    isAdmin = false;
                }

                if (isAdmin == false) {
                    url = HOME_CONTROLLER;
                }
            }
        } catch (SQLException ex) {
            logger.error("ViewDiscountServlet - SQLException: " + ex.getMessage());
        } catch (NamingException ex) {
            logger.error("ViewDiscountServlet - NamingException: " + ex.getMessage());
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
