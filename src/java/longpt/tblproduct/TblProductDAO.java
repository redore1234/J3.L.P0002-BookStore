/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longpt.tblproduct;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.tomcat.util.codec.binary.Base64;
import java.util.List;
import javax.naming.NamingException;
import longpt.cart.Cart;
import longpt.dbulti.DbHelpers;

/**
 *
 * @author phamt
 */
public class TblProductDAO implements Serializable {

    private List<TblProductDTO> bookList;

    public List<TblProductDTO> getListBooks() {
        return bookList;
    }

    public void loadAllBooks() throws SQLException, NamingException, IOException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DbHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT productId, title, quantity, image, author, date, description, price, categoryId, statusId"
                        + " FROM tblProduct"
                        + " WHERE quantity>? AND statusId=1";
                stm = con.prepareStatement(sql);
                stm.setInt(1, 0);

                rs = stm.executeQuery();
                while (rs.next()) {
                    int productId = rs.getInt("productId");
                    String title = rs.getString("title");
                    int quantity = rs.getInt("quantity");

                    //Get Image
                    byte[] imageByte = rs.getBytes("image");
                    String imageEncode = null;
                    if (imageByte != null) {
                        imageEncode = new String(Base64.encodeBase64(imageByte), "UTF-8");
                    }

                    String author = rs.getString("author");
                    Date date = rs.getDate("date");
                    String description = rs.getString("description");
                    int price = rs.getInt("price");
                    String categoryId = rs.getString("categoryId");
                    int statusId = rs.getInt("statusId");

                    TblProductDTO dto = new TblProductDTO(productId, title, quantity, imageEncode, author, date, description, price, categoryId, statusId);

                    if (bookList == null) {
                        bookList = new ArrayList<>();
                    }
                    bookList.add(dto);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    public TblProductDTO getProductByProductId(int productId) throws SQLException, NamingException, IOException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        TblProductDTO dto = null;
        try {
            con = DbHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT title, quantity, image, author, date, description, price, categoryId, statusId"
                        + " FROM tblProduct"
                        + " WHERE productId=? AND quantity>? AND statusId=1";
                stm = con.prepareStatement(sql);
                stm.setInt(1, productId);
                stm.setInt(2, 0);

                rs = stm.executeQuery();
                while (rs.next()) {
                    String title = rs.getString("title");
                    int quantity = rs.getInt("quantity");

                    //Get Image
                    byte[] imageByte = rs.getBytes("image");
                    String imageEncode = null;
                    if (imageByte != null) {
                        imageEncode = new String(Base64.encodeBase64(imageByte), "UTF-8");
                    }

                    String author = rs.getString("author");
                    Date date = rs.getDate("date");
                    String description = rs.getString("description");
                    int price = rs.getInt("price");
                    String categoryId = rs.getString("categoryId");
                    int statusId = rs.getInt("statusId");

                    dto = new TblProductDTO(productId, title, quantity, imageEncode, author, date, description, price, categoryId, statusId);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return dto;
    }

    public int getProductStatus(int productId) throws SQLException, NamingException, IOException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        int statusId = 0;
        try {
            con = DbHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT statusId"
                        + " FROM tblProduct"
                        + " WHERE productId=? AND quantity>?";
                stm = con.prepareStatement(sql);
                stm.setInt(1, productId);
                stm.setInt(2, 0);

                rs = stm.executeQuery();
                while (rs.next()) {
                    statusId = rs.getInt("statusId");
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return statusId;
    }
    
    public String getProductNameById(int productId) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        String title = null;
        try {
            con = DbHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT title"
                        + " FROM tblProduct"
                        + " WHERE productId=?";
                stm = con.prepareStatement(sql);
                stm.setInt(1, productId);

                rs = stm.executeQuery();
                while (rs.next()) {
                    title = rs.getString("title");
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return title;
    }

    public int getQuantity(int productId) throws SQLException, NamingException, IOException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        int quantity = 0;
        try {
            con = DbHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT quantity"
                        + " FROM tblProduct"
                        + " WHERE productId=? AND statusId=1";
                stm = con.prepareStatement(sql);
                stm.setInt(1, productId);

                rs = stm.executeQuery();
                while (rs.next()) {
                    quantity = rs.getInt("quantity");
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return quantity;
    }

    public void searchProductByTitle(String searchValue) throws SQLException, NamingException, IOException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DbHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT productId, title, quantity, image, author, date, description, price, categoryId, statusId"
                        + " FROM tblProduct"
                        + " WHERE title LIKE ? AND quantity>? AND statusId=1";
                stm = con.prepareStatement(sql);
                stm.setString(1, "%" + searchValue + "%");
                stm.setInt(2, 0);

                rs = stm.executeQuery();
                while (rs.next()) {
                    int productId = rs.getInt("productId");
                    String title = rs.getString("title");
                    int quantity = rs.getInt("quantity");

                    //Get Image
                    byte[] imageByte = rs.getBytes("image");
                    String imageEncode = null;
                    if (imageByte != null) {
                        imageEncode = new String(Base64.encodeBase64(imageByte), "UTF-8");

                    }

                    String author = rs.getString("author");
                    Date date = rs.getDate("date");
                    String description = rs.getString("description");
                    int price = rs.getInt("price");
                    String categoryId = rs.getString("categoryId");
                    int statusId = rs.getInt("statusId");
                    TblProductDTO dto = new TblProductDTO(productId, title, quantity, imageEncode, author, date, description, price, categoryId, statusId);
                    if (bookList == null) {
                        bookList = new ArrayList<>();
                    }
                    bookList.add(dto);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    public TblProductDTO searchProductByIdWithoutImage(int productId) throws SQLException, NamingException, IOException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        TblProductDTO dto = null;
        try {
            con = DbHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT  title, quantity, author, date, description, price, categoryId, statusId"
                        + " FROM tblProduct"
                        + " WHERE productId=? AND quantity>? AND statusId=1";
                stm = con.prepareStatement(sql);
                stm.setInt(1, productId);
                stm.setInt(2, 0);

                rs = stm.executeQuery();
                while (rs.next()) {
                    String title = rs.getString("title");
                    int quantity = rs.getInt("quantity");

                    String author = rs.getString("author");
                    Date date = rs.getDate("date");
                    String description = rs.getString("description");
                    int price = rs.getInt("price");
                    String categoryId = rs.getString("categoryId");
                    int statusId = rs.getInt("statusId");
                    dto = new TblProductDTO(productId, title, quantity, null, author, date, description, price, categoryId, statusId);
                }

            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return dto;
    }

    public void searchByPrice(int lowerPrice, int upperPrice) throws SQLException, NamingException, IOException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DbHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT productId, title, quantity, image, author, date, description, price, categoryId, statusId"
                        + " FROM tblProduct"
                        + " WHERE price>=? AND price<=? AND quantity>? AND statusId=1";
                stm = con.prepareStatement(sql);
                stm.setInt(1, lowerPrice);
                stm.setInt(2, upperPrice);
                stm.setInt(3, 0);

                rs = stm.executeQuery();
                while (rs.next()) {
                    int productId = rs.getInt("productId");
                    String title = rs.getString("title");
                    int quantity = rs.getInt("quantity");

                    //Get Image
                    byte[] imageByte = rs.getBytes("image");
                    String imageEncode = null;
                    if (imageByte != null) {
                        imageEncode = new String(Base64.encodeBase64(imageByte), "UTF-8");

                    }

                    String author = rs.getString("author");
                    Date date = rs.getDate("date");
                    String description = rs.getString("description");
                    int price = rs.getInt("price");
                    String categoryId = rs.getString("categoryId");
                    int statusId = rs.getInt("statusId");
                    TblProductDTO dto = new TblProductDTO(productId, title, quantity, imageEncode, author, date, description, price, categoryId, statusId);
                    if (bookList == null) {
                        bookList = new ArrayList<>();
                    }
                    bookList.add(dto);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    public void reduceQuantity(Cart cart) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DbHelpers.makeConnection();
            if (con != null) {
                con.setAutoCommit(false);

                for (TblProductDTO dto : cart.getCompartment().keySet()) {
                    String sql = "SELECT quantity"
                            + " FROM tblProduct"
                            + " WHERE productId=?";
                    stm = con.prepareStatement(sql);
                    stm.setInt(1, dto.getProductId());
                    rs = stm.executeQuery();
                    if (rs.next()) {
                        int quantity = rs.getInt("quantity");
                        sql = "UPDATE tblProduct"
                                + " SET quantity=?"
                                + " WHERE productId=?";
                        stm = con.prepareStatement(sql);
                        stm.setInt(1, quantity - cart.getCompartment().get(dto));
                        stm.setInt(2, dto.getProductId());
                        stm.addBatch();
                        stm.executeBatch();
                    }
                }
                con.commit();
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    public void searchByCategory(String category) throws SQLException, NamingException, IOException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DbHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT productId, title, quantity, image, author, date, description, price, categoryId, statusId"
                        + " FROM tblProduct"
                        + " WHERE categoryID=? AND quantity>? AND statusId=1";
                stm = con.prepareStatement(sql);
                stm.setString(1, category);
                stm.setInt(2, 0);

                rs = stm.executeQuery();
                while (rs.next()) {
                    int productId = rs.getInt("productId");
                    String title = rs.getString("title");
                    int quantity = rs.getInt("quantity");

                    //Get Image
                    byte[] imageByte = rs.getBytes("image");
                    String imageEncode = null;
                    if (imageByte != null) {
                        imageEncode = new String(Base64.encodeBase64(imageByte), "UTF-8");
                    }

                    String author = rs.getString("author");
                    Date date = rs.getDate("date");
                    String description = rs.getString("description");
                    int price = rs.getInt("price");
                    String categoryId = rs.getString("categoryId");
                    int statusId = rs.getInt("statusId");
                    TblProductDTO dto = new TblProductDTO(productId, title, quantity, imageEncode, author, date, description, price, categoryId, statusId);
                    if (bookList == null) {
                        bookList = new ArrayList<>();
                    }
                    bookList.add(dto);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    public void insertBook(String title, int quantity, InputStream image, String author, String description, int price, String categoryId) throws SQLException, NamingException, IOException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DbHelpers.makeConnection();
            if (con != null) {
                String sql = "INSERT INTO tblProduct(title, quantity, image, author, description, price, categoryId)"
                        + " VALUES(?,?,?,?,?,?,?)";
                stm = con.prepareStatement(sql);
                stm.setString(1, title);
                stm.setInt(2, quantity);

                if (image != null) {
                    stm.setBinaryStream(3, image);
                } else {
                    stm.setBinaryStream(3, null);
                }

                stm.setString(4, author);
                stm.setString(5, description);
                stm.setInt(6, price);
                stm.setString(7, categoryId);

                stm.executeUpdate();
            }
        } finally {
            if (image != null) {
                image.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    public void updateBook(int productId, String title, int quantity, InputStream image, String author, String description, int price, String categoryId) throws SQLException, NamingException, IOException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DbHelpers.makeConnection();
            if (con != null) {
                if (image != null) {  
                        //Update book with image
                    String sql = "UPDATE tblProduct"
                            + " SET title=?, quantity=?, image=?, author=?, description=?, price=?, categoryId=?"
                            + " WHERE productId=? AND statusId=1";
                    stm = con.prepareStatement(sql);
                    stm.setString(1, title);
                    stm.setInt(2, quantity);
                    stm.setBinaryStream(3, image);
                    stm.setString(4, author);
                    stm.setString(5, description);
                    stm.setInt(6, price);
                    stm.setString(7, categoryId);
                    stm.setInt(8, productId);

                    stm.executeUpdate();
                } else {
                        //Update book without image
                    String sql = "UPDATE tblProduct"
                            + " SET title=?, quantity=?, author=?, description=?, price=?, categoryId=?"
                            + " WHERE productId=? AND statusId=1";
                    stm = con.prepareStatement(sql);
                    stm.setString(1, title);
                    stm.setInt(2, quantity);
                    stm.setString(3, author);
                    stm.setString(4, description);
                    stm.setInt(5, price);
                    stm.setString(6, categoryId);
                    stm.setInt(7, productId);

                    stm.executeUpdate();
                }
            }
        } finally {
            if (image != null) {
                image.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    public void deleteBook(int productId) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DbHelpers.makeConnection();
            if (con != null) {
                String sql = "UPDATE tblProduct"
                        + " SET statusId=2" //2 means Inactive in DB
                        + " WHERE productId=?";
                stm = con.prepareStatement(sql);
                stm.setInt(1, productId);

                stm.executeUpdate();
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
}
