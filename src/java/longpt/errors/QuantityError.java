/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longpt.errors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author phamt
 */
public class QuantityError implements Serializable {

    private List<String> errorList = new ArrayList<>();

    public List<String> getErrorList() {
        return errorList;
    }

    public void setErrorList(List<String> errorList) {
        this.errorList = errorList;
    }
}
