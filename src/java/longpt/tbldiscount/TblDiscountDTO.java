/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longpt.tbldiscount;

import java.io.Serializable;

/**
 *
 * @author phamt
 */
public class TblDiscountDTO implements Serializable{
    private int discountPer;

    public TblDiscountDTO() {
    }

    public TblDiscountDTO(int discountPer) {
        this.discountPer = discountPer;
    }

    public int getDiscountPer() {
        return discountPer;
    }

    public void setDiscountPer(int discountPer) {
        this.discountPer = discountPer;
    }
    
}
