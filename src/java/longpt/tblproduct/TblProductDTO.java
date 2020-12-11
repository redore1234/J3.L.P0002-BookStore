/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longpt.tblproduct;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

/**
 *
 * @author phamt
 */
public class TblProductDTO implements Serializable {

    private int productId;
    private String title;
    private int quantity;
    private String image;
    private String author;
    private Date Date;
    private String description;
    private int price;
    private String categoryId;
    private int statusId;

    public TblProductDTO() {
    }

    public TblProductDTO(int productId, String title, int quantity, String image, String author, Date Date, String description, int price, String categoryId, int statusId) {
        this.productId = productId;
        this.title = title;
        this.quantity = quantity;
        this.image = image;
        this.author = author;
        this.Date = Date;
        this.description = description;
        this.price = price;
        this.categoryId = categoryId;
        this.statusId = statusId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getDate() {
        return Date;
    }

    public void setDate(Date Date) {
        this.Date = Date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.productId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TblProductDTO other = (TblProductDTO) obj;
        if (!Objects.equals(this.productId, other.productId)) {
            return false;
        }
        return true;
    }
}
