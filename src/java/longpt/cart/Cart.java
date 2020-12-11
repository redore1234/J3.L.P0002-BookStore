/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longpt.cart;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import longpt.tblproduct.TblProductDTO;

/**
 *
 * @author phamt
 */
public class Cart implements Serializable {

    private Map<TblProductDTO, Integer> compartment;

    public Map<TblProductDTO, Integer> getCompartment() {
        return compartment;
    }

    public void setCompartment(Map<TblProductDTO, Integer> compartment) {
        this.compartment = compartment;
    }

    public void addProductToCart(TblProductDTO dto) {
        if (compartment == null) {
            compartment = new HashMap<>();
        }

        int quantity = 1;
        if (compartment.containsKey(dto)) {
            quantity = compartment.get(dto) + 1;
        }
        compartment.put(dto, quantity);
    }

    public void updateQuantity(int productId, int quantity) {
        for (TblProductDTO dto : compartment.keySet()) {
            if (dto.getProductId() == productId) {
                compartment.put(dto, quantity);
            }
        }
        int total = getTotalPrice();
    }

    public void removeProductFromCart(TblProductDTO dto) {
        if (compartment == null) {
            return;
        }
        if (compartment != null) {
            compartment.remove(dto);
            if (compartment.isEmpty()) {
                compartment = null;  //remove cart if no item
            }
        }
    }

    public int getTotalPrice() {
        int total = 0;
        for (TblProductDTO dto : compartment.keySet()) {
            int quantityInCart = compartment.get(dto);
            total += dto.getPrice() * quantityInCart;
        }
        return total;
    }
}
