/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longpt.tblorderdetail;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingException;
import longpt.cart.Cart;
import longpt.dbulti.DbHelpers;
import longpt.tblproduct.TblProductDAO;
import longpt.tblproduct.TblProductDTO;

/**
 *
 * @author phamt
 */
public class TblOrderDetailDAO implements Serializable {

    private Map<TblOrderDetailDTO, String> orderDetailList;

    public Map<TblOrderDetailDTO, String> getOrderDetailList() {
        return orderDetailList;
    }

    public boolean addOrderDetail(Cart cart, String orderId) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        boolean flag = true;
        try {
            con = DbHelpers.makeConnection();
            if (con != null) {
                con.setAutoCommit(false); //dữ liệu sẽ chỉ update vào database khi gọi lệnh commit()
                String sql = "INSERT INTO tblOrderDetail(orderId, productId, quantity, totalPrice)"
                        + " VALUES(?,?,?,?)";
                stm = con.prepareStatement(sql);
                for (TblProductDTO dto : cart.getCompartment().keySet()) {
                    stm.setString(1, orderId);
                    stm.setInt(2, dto.getProductId());
                    stm.setInt(3, cart.getCompartment().get(dto));
                    stm.setInt(4, dto.getPrice());
                    stm.addBatch();
                }

                int[] result = stm.executeBatch();

                for (int i = 0; i < result.length; i++) {
                    if (result[i] == 0) { //if found err in result array (value == 0)
                        flag = false;
                    }
                }

                if (flag == true) {
                    con.commit();
                } else {
                    con.rollback();
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return flag;
    }

    public void loadOrderDetail(String orderId) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DbHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT detailId, productId, quantity, totalPrice"
                        + " FROM tblOrderDetail"
                        + " WHERE orderId=?";
                stm = con.prepareStatement(sql);
                stm.setString(1, orderId);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int detailId = rs.getInt("detailId");
                    int productId = rs.getInt("productId");

                    TblProductDAO productDAO = new TblProductDAO();
                    String title = productDAO.getProductNameById(productId);

                    int quantity = rs.getInt("quantity");
                    int price = rs.getInt("totalPrice");

                    TblOrderDetailDTO orderDetailDTO = new TblOrderDetailDTO(detailId, orderId, productId, quantity, price);

                    if (orderDetailList == null) {
                        orderDetailList = new HashMap<>();
                    }
                    orderDetailList.put(orderDetailDTO, title);
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
}
