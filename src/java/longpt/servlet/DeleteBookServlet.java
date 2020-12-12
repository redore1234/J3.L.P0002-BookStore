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
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import longpt.tblaccount.TblAccountDTO;
import longpt.tblproduct.TblProductDAO;
import org.apache.log4j.Logger;

/**
 *
 * @author phamt
 */
@WebServlet(name = "DeleteBookServlet", urlPatterns = {"/DeleteBookServlet"})
public class DeleteBookServlet extends HttpServlet {

    private final String DISPATCH_CONTROLLER = "DispatchController";
    private final String HOME_CONTROLLER = "HomeServlet";
    private final static Logger logger = Logger.getLogger(DeleteBookServlet.class);

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

        String url = "";
        boolean isAdmin = false;
        try {
            String product = request.getParameter("productId");
            HttpSession session = request.getSession(false);
            if (session != null) {
                TblAccountDTO dto = (TblAccountDTO) session.getAttribute("ACCOUNT");
                if (dto != null) {
                    if (dto.getRoleId().equals("admin")) {
                        isAdmin = true;
                        int productId = 0;
                        if (product != null) {
                            productId = Integer.parseInt(product);
                        }

                        //Call DAO
                        TblProductDAO productDAO = new TblProductDAO();
                        productDAO.deleteBook(productId);

                    }
                } else {
                    isAdmin = false;
                }
            }
            if (isAdmin == true) {
                url = DISPATCH_CONTROLLER;
            } else {
                url = HOME_CONTROLLER;
            }
        } catch (SQLException ex) {
            logger.error("DeleteBookServlet _ SQLException: " + ex.getMessage());
        } catch (NamingException ex) {
            logger.error("DeleteBookServlet _ NamingException: " + ex.getMessage());
        } finally {
            response.sendRedirect(url);
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
