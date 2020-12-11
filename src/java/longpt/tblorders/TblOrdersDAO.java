/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longpt.tblorders;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.naming.NamingException;
import longpt.dbulti.DbHelpers;

/**
 *
 * @author phamt
 */
public class TblOrdersDAO implements Serializable {

    public String createOrder(String username, String name, String address, String phone, double total, String paymentId) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        String orderId = null;
        try {
            con = DbHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT NEWID()"
                        + " AS orderId";
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                if (rs.next()) {
                    orderId = rs.getString("orderId");
                }

                sql = "INSERT INTO tblOrders(orderId, username, name, address, phoneNumber, total, paymentId)"
                        + " VALUES(?,?,?,?,?,?,?)";
                stm = con.prepareStatement(sql);
                stm.setString(1, orderId);
                stm.setString(2, username);
                stm.setString(3, name);
                stm.setString(4, address);
                stm.setString(5, phone);
                stm.setDouble(6, total);
                stm.setString(7, paymentId);

                stm.executeUpdate();
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
        return orderId;
    }

    public TblOrdersDTO getOrder(String orderId, String username) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        TblOrdersDTO dto = null;
        try {
            con = DbHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT name, address, phoneNumber, total, orderDate, paymentId"
                        + " FROM tblOrders"
                        + " WHERE orderId=? AND username=?";
                stm = con.prepareStatement(sql);
                stm.setString(1, orderId);
                stm.setString(2, username);
                rs = stm.executeQuery();
                if (rs.next()) {
                    String name = rs.getString("name");
                    String address = rs.getString("address");
                    String phone = rs.getString("phoneNumber");
                    int total = rs.getInt("total");
                    Timestamp orderDate = rs.getTimestamp("orderDate");
                    String payment = rs.getString("paymentId");
                    
                    dto = new TblOrdersDTO(orderId, username, name, address, phone, total, orderDate, payment);
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
}
