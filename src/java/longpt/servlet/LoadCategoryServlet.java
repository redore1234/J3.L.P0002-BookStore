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

/**
 *
 * @author phamt
 */
@WebServlet(name = "LoadCategoryServlet", urlPatterns = {"/LoadCategoryServlet"})
public class LoadCategoryServlet extends HttpServlet {

    private final String INSERT_PAGE = "insertbook.jsp";
    private final String UPDATE_PAGE = "updatebook.jsp";
    private final String HOME_CONTROLLER = "HomeServlet";

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
            //put productId into request scope
            String product = request.getParameter("txtProductId");
            int productId = 0;
            if (product != null) {
                productId = Integer.parseInt(product);
            }
            request.setAttribute("PRODUCT_ID", productId);

            //Get Product Status 
            TblProductDAO dao = new TblProductDAO();
            int status = dao.getProductStatus(productId);

            //get detail of product in updatebook.jsp
            TblProductDTO dto = dao.getProductByProductId(productId);
            if (dto != null) {
                request.setAttribute("PRODUCT_DETAIL", dto);
            }

            String button = request.getParameter("hiddenbtnAction");
            if (button.equals("Insert")) {
                url = INSERT_PAGE;
            } else {
                if (status == 1) {  //check xem product đó đã bị xóa chưa 
                    if (button.equals("Edit")) {
                        url = UPDATE_PAGE;
                    }
                } else {
                    url = HOME_CONTROLLER;
                }
            }

            //get all categories
            TblCategoryDAO categoryDAO = new TblCategoryDAO();
            categoryDAO.loadAllCategories();

            List<TblCategoryDTO> listCategories = categoryDAO.getListCategory();
            request.setAttribute("LIST_CATEGORIES", listCategories);
        } catch (SQLException ex) {
            log("LoadCategoryServlet _ SQLException: " + ex.getMessage());
        } catch (NamingException ex) {
            log("LoadCategoryServlet _ NamingException: " + ex.getMessage());
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
