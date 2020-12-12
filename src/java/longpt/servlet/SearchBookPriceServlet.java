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
import longpt.tblcategory.TblCategoryDAO;
import longpt.tblcategory.TblCategoryDTO;
import longpt.tblproduct.TblProductDAO;
import longpt.tblproduct.TblProductDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author phamt
 */
@WebServlet(name = "SearchBookPriceServlet", urlPatterns = {"/SearchBookPriceServlet"})
public class SearchBookPriceServlet extends HttpServlet {

    private final String HOME_PAGE = "home.jsp";
    private final String DISPATCH_CONTROLLER = "DispatchController";
    private final static Logger logger = Logger.getLogger(SearchBookPriceServlet.class);

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
        try {
            String lowerPrice = request.getParameter("txtLowerPrice");
            String upperPrice = request.getParameter("txtUpperPrice");
            int searchLowerPrice = 0;
            if (lowerPrice != null && !lowerPrice.isEmpty()) {
                searchLowerPrice = Integer.valueOf(lowerPrice);
            }
            int searchUpperPrice = 0;
            if (upperPrice != null && !upperPrice.isEmpty()) {
                searchUpperPrice = Integer.valueOf(upperPrice);
            }

            //call DAO
            TblProductDAO productDAO = new TblProductDAO();
            //check search Value
            if (searchLowerPrice >= 0 && (searchUpperPrice >= searchLowerPrice)) {
                productDAO.searchByPrice(searchLowerPrice, searchUpperPrice);

                //get all books
                List<TblProductDTO> listBooks = productDAO.getListBooks();
                request.setAttribute("LIST_BOOKS", listBooks);

                //get all categories
                TblCategoryDAO categoryDAO = new TblCategoryDAO();
                categoryDAO.loadAllCategories();

                List<TblCategoryDTO> listCategories = categoryDAO.getListCategory();
                request.setAttribute("LIST_CATEGORIES", listCategories);
                url = HOME_PAGE;
            } else if (searchUpperPrice < searchLowerPrice) {
                url = DISPATCH_CONTROLLER;
            }
        } catch (SQLException ex) {
            logger.error("SearchBookPriceServlet _ SQLException: " + ex.getMessage());
        } catch (NamingException ex) {
            logger.error("SearchBookPriceServlet _ NamingException: " + ex.getMessage());
        } catch (IOException ex) {
            logger.error("SearchBookPriceServlet _ IOException: " + ex.getMessage());
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
