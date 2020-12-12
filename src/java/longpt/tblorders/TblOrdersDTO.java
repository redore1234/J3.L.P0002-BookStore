/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longpt.tblorders;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author phamt
 */
public class TblOrdersDTO implements Serializable {

    private String orderId;
    private String username;
    private String name;
    private String address;
    private String phone;
    private int total;
    private Timestamp date;
    private String paymentId;

    public TblOrdersDTO() {
    }

    public TblOrdersDTO(String orderId, String username, String name, String address, String phone, int total, Timestamp date, String paymentId) {
        this.orderId = orderId;
        this.username = username;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.total = total;
        this.date = date;
        this.paymentId = paymentId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

}
