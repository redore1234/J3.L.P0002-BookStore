/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longpt.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import longpt.tblproduct.TblProductDAO;
import org.apache.log4j.Logger;

/**
 *
 * @author phamt
 */
@WebServlet(name = "UpdateBookServlet", urlPatterns = {"/UpdateBookServlet"})
@MultipartConfig
public class UpdateBookServlet extends HttpServlet {

    private final String DISPATCH_CONTROLLER = "DispatchController";
    private final static Logger logger = Logger.getLogger(UpdateBookServlet.class);

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
        InputStream fileInputStream = null;
        try {
            String product = request.getParameter("productId");

            int productId = 0;
            if (product != null) {
                productId = Integer.parseInt(product);
            }

            String title = request.getParameter("txtTitle");
            String Quantity = request.getParameter("txtQuantity");
            int quantity = 0;
            if (Quantity != null) {
                quantity = Integer.parseInt(Quantity);
            }

            String author = request.getParameter("txtAuthor");
            String description = request.getParameter("txtDescription");
            String Price = request.getParameter("txtPrice");
            int price = 0;
            if (Price != null) {
                price = Integer.parseInt(Price);
            }

            Part fileImage = request.getPart("txtImage");
            if (fileImage != null) {
                String fileName = fileImage.getSubmittedFileName();
                if (fileName.contains(".jpg") || fileName.contains(".PNG") || fileName.contains(".png")) {
                    fileInputStream = fileImage.getInputStream();
                } else {
                    request.setAttribute("ERROR_IMAGE", "Please choose image with *.jpg or *.PNG or .png extension");
                }
            }

            String categoryId = request.getParameter("cmbCategory");

            TblProductDAO productDAO = new TblProductDAO();
            productDAO.updateBook(productId, title, quantity, fileInputStream, author, description, price, categoryId);
            url = DISPATCH_CONTROLLER;
        } catch (SQLException ex) {
            logger.error("UpdateBookServlet _ SQLException: " + ex.getMessage());
        } catch (NamingException ex) {
            logger.error("UpdateBookServlet _ NamingException: " + ex.getMessage());
        } catch (IOException ex) {
            logger.error("UpdateBookServlet _ IOException:  " + ex.getMessage());
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
