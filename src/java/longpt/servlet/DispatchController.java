/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longpt.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author phamt
 */
@MultipartConfig
public class DispatchController extends HttpServlet {

    private final String LOGIN_CONTROLLER = "LoginServlet";
    private final String LOGOUT_CONTROLLER = "LogoutServlet";
    private final String HOME_CONTROLLER = "HomeServlet";
    private final String LOAD_CATEGORY_CONTROLLER = "LoadCategoryServlet";
    private final String INSERT_BOOK_CONTROLLER = "InsertBookServlet";
    private final String UPDATE_BOOK_CONTROLLER = "UpdateBookServlet";
    private final String SEARCH_BOOK_CONTROLLER = "SearchBookServlet";
    private final String SEARCH_BOOK_PRICE_CONTROLLER = "SearchBookPriceServlet";
    private final String SEARCH_BOOK_CATEGORY_CONTROLLER = "SearchBookCategoryServlet";
    private final String DELETE_BOOK_CONTROLLER = "DeleteBookServlet";
    private final String ADD_TO_CART_CONTROLLER = "AddToCartServlet";
    private final String DELETE_FROM_CART_CONTROLLER = "DeleteFromCartServlet";
    private final String VIEW_CART_CONTROLLER = "ViewCartServlet";
    private final String UPDATE_CART_CONTROLLER = "UpdateCartServlet";
    private final String CHECK_OUT_CONTROLLER = "CheckOutServlet";
    private final String CREATE_DISCOUNT_CONTROLLER = "CreateDiscountServlet";
    private final String VIEW_DISCOUNT_CONTROLLER = "ViewDiscountServlet";
    private final String TRACK_ORDER_CONTROLLER = "TrackOrderServlet";
    private final String LOAD_ORDER_LIST_CONTROLLER = "LoadOrderServlet";

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
            String button = request.getParameter("btnAction");

            HttpSession session = request.getSession(false);
            if (button == null) {
                url = HOME_CONTROLLER;
            } else if (button.equals("Login")) {
                url = LOGIN_CONTROLLER;
            } else if (button.equals("Logout") && session != null) {
                url = LOGOUT_CONTROLLER;
            } else if (button.equals("LoadCategory") && session != null) {
                url = LOAD_CATEGORY_CONTROLLER;
            } else if (button.equals("Add") && session != null) {
                url = INSERT_BOOK_CONTROLLER;
            } else if (button.equals("Update") && session != null) {
                url = UPDATE_BOOK_CONTROLLER;
            } else if (button.equals("Search title")) {
                url = SEARCH_BOOK_CONTROLLER;
            } else if (button.equals("Search price")) {
                url = SEARCH_BOOK_PRICE_CONTROLLER;
            } else if (button.equals("Search category")) {
                url = SEARCH_BOOK_CATEGORY_CONTROLLER;
            } else if (button.equals("Delete Book") && session != null) {
                url = DELETE_BOOK_CONTROLLER;
            } else if (button.equals("Add to Cart") && session != null) {
                url = ADD_TO_CART_CONTROLLER;
            } else if (button.equals("Delete Cart") && session != null) {
                url = DELETE_FROM_CART_CONTROLLER;
            } else if (button.equals("View Cart") && session != null) {
                url = VIEW_CART_CONTROLLER;
            } else if (button.equals("Update quantity") && session != null) {
                url = UPDATE_CART_CONTROLLER;
            } else if (button.equals("Check out") && session != null) {
                url = CHECK_OUT_CONTROLLER;
            } else if (button.equals("Track") && session != null) {
                url = TRACK_ORDER_CONTROLLER;
            } else if (button.equals("Create Discount") && session != null) {
                url = CREATE_DISCOUNT_CONTROLLER;
            } else if (button.equals("View Discount") && session != null) {
                url = VIEW_DISCOUNT_CONTROLLER;
            } else if (button.equals("View History") && session != null) {
                url = LOAD_ORDER_LIST_CONTROLLER;
            }
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
