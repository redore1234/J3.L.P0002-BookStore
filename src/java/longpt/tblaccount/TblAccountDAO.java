/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longpt.tblaccount;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import longpt.dbulti.DbHelpers;

/**
 *
 * @author phamt
 */
public class TblAccountDAO implements Serializable {

    public TblAccountDTO checkLogin(String username, String password) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        TblAccountDTO dto = null;
        try {
            con = DbHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT fullName, roleId, statusId"
                        + " FROM tblAccount"
                        + " WHERE username=? AND password=? AND statusId=1";
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                stm.setString(2, password);
                rs = stm.executeQuery();
                if (rs.next()) {
                    String fullName = rs.getString("fullName");
                    String roleId = rs.getString("roleId");
                    int statusId = rs.getInt("statusId");
                    dto = new TblAccountDTO(username, password, fullName, roleId, statusId);
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

    public String searchNameByUsername(String username) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        String name = null;
        try {
            con = DbHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT fullName"
                        + " FROM tblAccount"
                        + " WHERE username=? AND statusId=1";
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                rs = stm.executeQuery();
                if (rs.next()) {
                    name = rs.getString("fullName");
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
        return name;
    }
}
